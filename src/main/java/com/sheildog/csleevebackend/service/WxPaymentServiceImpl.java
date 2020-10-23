package com.sheildog.csleevebackend.service;

import com.github.wxpay.sdk.LinWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.sheildog.csleevebackend.core.LocalUser;
import com.sheildog.csleevebackend.exception.http.ForbiddenException;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
import com.sheildog.csleevebackend.model.Order;
import com.sheildog.csleevebackend.repository.OrderRepository;
import com.sheildog.csleevebackend.util.CommonUtil;
import com.sheildog.csleevebackend.util.HttpRequestProxy;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxPaymentServiceImpl implements PaymentService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    private static LinWxPayConfig linWxPayConfig = new LinWxPayConfig();

    @Value("${c-sleeve-backend.order.wx.pay-callback-host}")
    private String payCallbackHost;
    @Value("${c-sleeve-backend.order.wx.pay-callback-path}")
    private String payCallbackPath;

    @Override
    public Map<String, String> preOrder(Long oId) {
        Long uId = LocalUser.getUser().getId();
        Optional<Order> optionalOrder = this.orderRepository.findFirstByUserIdAndId(uId, oId);
        Order order = optionalOrder.orElseThrow(
                () -> new NotFoundException(50009)
        );
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }
        WXPay wxPay = this.assembleWxPayConfig();
        Map<String, String> params = this.makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo());
        Map<String, String> wxOrder;
        try {
            wxOrder = wxPay.unifiedOrder(params);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        if (this.unifiedOrderSuccess(wxOrder)) {
            this.orderService.updateOrderPrepayId(order.getId(), wxOrder.get("prepay_id"));
        }
        return this.makePaySignature(wxOrder);
    }

    public WXPay assembleWxPayConfig() {
        WXPay wxPay;
        try {
            wxPay = new WXPay(WxPaymentServiceImpl.linWxPayConfig);
        } catch (Exception exception) {
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }

    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice, String orderNo) {
        String payCallbackUrl = this.payCallbackHost + this.payCallbackPath;
        Map<String, String> data = new HashMap<>();
        data.put("body", "Sleeve");
        data.put("out_trade_no", orderNo);
        data.put("device_info", "Sleeve");
        data.put("fee_type", "CNY");
        data.put("trade_type", "JSAPI");

        data.put("total_fee", CommonUtil.yuanToFenPlainString(serverFinalPrice));
        data.put("open_id", LocalUser.getUser().getOpenid());
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());

        data.put("notify_url", payCallbackUrl);

        return data;
    }

    private boolean unifiedOrderSuccess(Map<String, String> wxOrder) {
        if (!wxOrder.get("return_code").equals("SUCCESS") || !wxOrder.get("result_code").equals("SUCCESS")) {
            throw new ParameterException(10007);
        }
        return true;
    }

    private Map<String, String> makePaySignature(Map<String, String> wxOrder) {
        String packeges = "prepay_id=" + wxOrder.get("prepay_id");
        Map<String, String> wxPayMap = new HashMap<>();
        wxPayMap.put("timeStamp", CommonUtil.timestamp10());
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
        wxPayMap.put("package", packeges);
        wxPayMap.put("signType", "HMAC-SHA256");
        wxPayMap.put("appId", WxPaymentServiceImpl.linWxPayConfig.getAppID());
        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap, WxPaymentServiceImpl.linWxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        Map<String, String> miniPayParams = new HashMap<>();
        miniPayParams.put("paySign", sign);
        miniPayParams.putAll(wxPayMap);
        miniPayParams.remove("appId");

        return miniPayParams;
    }
}