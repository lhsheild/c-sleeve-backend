package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.bo.PageCounter;
import com.sheildog.csleevebackend.core.LocalUser;
import com.sheildog.csleevebackend.core.interceptors.ScopeLevel;
import com.sheildog.csleevebackend.dto.OrderDTO;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.logic.OrderChecker;
import com.sheildog.csleevebackend.model.Order;
import com.sheildog.csleevebackend.service.OrderService;
import com.sheildog.csleevebackend.util.CommonUtil;
import com.sheildog.csleevebackend.vo.OrderIdVO;
import com.sheildog.csleevebackend.vo.OrderPureVO;
import com.sheildog.csleevebackend.vo.OrderSimplifyVO;
import com.sheildog.csleevebackend.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("order")
@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${c-sleeve-backend.order.pay-time-limit}")
    private Long payTimeLimit;

    @ScopeLevel()
    @PostMapping("")
    public OrderIdVO placeOrder(
            @RequestBody OrderDTO orderDTO
    ) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }

    @ScopeLevel
    @GetMapping("/status/unpaid")
    public PagingDozer<Order, OrderSimplifyVO> getUnpaid(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> {
            ((OrderSimplifyVO) o).setPeriod(Long.valueOf(this.payTimeLimit));
        });
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    public PagingDozer<Order, OrderSimplifyVO> getByStatus(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count,
            @PathVariable int status
    ) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> {
            ((OrderSimplifyVO) o).setPeriod(Long.valueOf(this.payTimeLimit));
        });
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/detail/{oid}")
    public OrderPureVO getOrderDetail(
            @PathVariable Long oid
    ) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(oid);
        return orderOptional.map((o) -> new OrderPureVO(o, this.payTimeLimit))
                .orElseThrow(() ->
                        new NotFoundException(50009)
                );
    }
}
