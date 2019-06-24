package com.merchant.order.service.order;

import com.merchant.order.bo.order.request.OrderBORequest;
import com.merchant.order.manage.order.OrderManager;
import com.merchant.user.bo.CommonBOResult;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/5/24
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
@Log4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderManager orderManager;


    @Override
    public CommonBOResult createOrder(OrderBORequest orderBORequest) {
        return this.orderManager.createOrder(orderBORequest);
    }
}
