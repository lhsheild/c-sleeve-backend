package com.sheildog.csleevebackend.service;

import java.util.Map;

/**
 * @author a7818
 */
public interface PaymentService {
    Map<String, String> preOrder(Long oId);
}
