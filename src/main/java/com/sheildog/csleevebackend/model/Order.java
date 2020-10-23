package com.sheildog.csleevebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sheildog.csleevebackend.core.enumeration.OrderStatus;
import com.sheildog.csleevebackend.dto.OrderAddressDTO;
import com.sheildog.csleevebackend.util.CommonUtil;
import com.sheildog.csleevebackend.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author a7818
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private Date expiredTime;
    private Date placedTime;
    private String snapImg;
    private String snapTitle;
    @Column(name = "snap_items")
    private String snapItems;
    @Column(name = "snap_address")
    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems() {
        List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {
                });
        return list;
    }

    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO o = GenericAndJson.jsonToObject(this.snapAddress, new TypeReference<OrderAddressDTO>() {
        });
        return o;
    }

    public void setSnapAddress(OrderAddressDTO address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }

    @JsonIgnore
    public OrderStatus getStatusEnum() {
        return OrderStatus.toType(this.status);
    }

    public Boolean needCancel(Long period) {
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return true;
        }
        return CommonUtil.isOutOfDate(this.getPlacedTime(), period);
    }

    public Boolean needCancel() {
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return true;
        }
        return CommonUtil.isOutOfDate(this.getExpiredTime());
    }
}
