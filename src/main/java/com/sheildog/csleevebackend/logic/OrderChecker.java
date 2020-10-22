package com.sheildog.csleevebackend.logic;

import com.sheildog.csleevebackend.bo.SkuOrderBO;
import com.sheildog.csleevebackend.dto.OrderDTO;
import com.sheildog.csleevebackend.dto.SkuInfoDTO;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.model.OrderSku;
import com.sheildog.csleevebackend.model.Sku;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Service
//@Scope(value = "prototype")
public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;
    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public void isOk() {
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();
        // orderTotalPrice serverTotalPrice
        // 下架
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(), serverSkuList.size());

        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            // 售罄
            this.containsSoldOutSku(sku);
            // 超出库存
            this.beyondSkuStock(sku, skuInfoDTO);

            serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }

        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);

        if (this.couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    private void totalPriceIsOk(BigDecimal totalPrice, BigDecimal serverTotalPrice) {
        if (totalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }

    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }

    public String getLeaderImg() {
        return this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount(){
        return this.orderDTO.getSkuInfoList().stream()
                .map(skuInfoDTO -> skuInfoDTO.getCount())
                .reduce((a, b) -> Integer.sum(a, b))
                .orElse(0);
    }
}
