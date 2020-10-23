package com.sheildog.csleevebackend.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.sheildog.csleevebackend.core.enumeration.OrderStatus;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
import com.sheildog.csleevebackend.model.Order;
import com.sheildog.csleevebackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author a7818
 */
@Service
public class WxPaymentNotifyService implements PaymentNotifyService {
    @Autowired
    private WxPaymentServiceImpl wxPaymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public void processPayNotify(String data) {
        Map<String, String> dataMap = new HashMap<>();
        try {
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        WXPay wxPay = this.wxPaymentService.assembleWxPayConfig();
        boolean valid;
        try {
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }
        String returnCode = dataMap.get("return_code");
        String orderNo = dataMap.get("out_trade_no");

        if (!returnCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (orderNo == null) {
            throw new ServerErrorException(9999);
        }
    }

    private void deal(String orderNo) {
        Optional<Order> orderOptional = this.orderRepository.findFirstByOrderNo(orderNo);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        this.orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
    }
}
