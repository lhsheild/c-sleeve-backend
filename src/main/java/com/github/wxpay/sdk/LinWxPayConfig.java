package com.github.wxpay.sdk;

import java.io.InputStream;

/**
 * @author a7818
 */
public class LinWxPayConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return "wx4xxxxxxxx";
    }

    @Override
    public String getMchID() {
        return "12391283712983";
    }

    @Override
    public String getKey() {
        return "sdfjkasdlfjasdjf9908";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
