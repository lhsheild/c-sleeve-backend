package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.core.LocalUser;
import com.sheildog.csleevebackend.core.interceptors.ScopeLevel;
import com.sheildog.csleevebackend.dto.OrderDTO;
import com.sheildog.csleevebackend.logic.OrderChecker;
import com.sheildog.csleevebackend.service.OrderService;
import com.sheildog.csleevebackend.vo.OrderIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("order")
@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ScopeLevel()
    @PostMapping("")
    public OrderIdVO placeOrder(
            @RequestBody OrderDTO orderDTO
    ){
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return null;
    }
}
