package com.merchant.order.manage.order;

import com.merchant.data.po.result.CommonResultPO;
import com.merchant.order.bo.order.request.OrderBORequest;
import com.merchant.user.bo.CommonBOResult;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/5/24
 */
public interface OrderManager {


    /**
     * 创建一个订单
     * @param orderBORequest
     * @return
     */
    CommonBOResult createOrder(OrderBORequest orderBORequest);


}
