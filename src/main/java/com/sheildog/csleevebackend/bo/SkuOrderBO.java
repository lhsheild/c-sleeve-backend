package com.sheildog.csleevebackend.bo;

import com.sheildog.csleevebackend.dto.SkuInfoDTO;
import com.sheildog.csleevebackend.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author a7818
 */
@Getter
@Setter
public class SkuOrderBO {
    private BigDecimal actualPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice() {
        return this.actualPrice.multiply(BigDecimal.valueOf(this.count));
    }
}
