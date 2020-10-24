package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.core.interceptors.ScopeLevel;
import com.sheildog.csleevebackend.lib.WxNotify;
import com.sheildog.csleevebackend.service.PaymentNotifyService;
import com.sheildog.csleevebackend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author a7818
 */
@RequestMapping("payment")
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentNotifyService paymentNotifyService;

    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    @Validated
    public Map<String, String> preWxOrder(
            @PathVariable(name = "id") @Positive Long oId
    ) {
        return this.paymentService.preOrder(oId);
    }

    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return WxNotify.fail();
        }
        String xml = WxNotify.readNotify(s);
        try {
            this.paymentNotifyService.processPayNotify(xml);
        } catch (Exception e) {
            return WxNotify.fail();
        }
        return WxNotify.success();
    }
}
