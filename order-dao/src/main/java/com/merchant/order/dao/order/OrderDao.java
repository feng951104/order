package com.merchant.order.dao.order;

import com.merchant.data.po.result.CommonResultPO;
import com.merchant.order.po.data.CommodityOrder;
import com.merchant.order.po.request.CommodityOrderRequest;
import com.merchant.order.po.result.CommodityOrderResult;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/5/24
 */
public interface OrderDao {

    /**
     * 增加一个订单
     * @param commodityOrder
     * @return
     */
    CommonResultPO createOrder(CommodityOrder commodityOrder);


    /**
     * 修改订单得状态
     * @param commodityOrder
     * @return
     */
    CommonResultPO updateOrderStatus(CommodityOrder commodityOrder);

    /**
     * 根据条件查询商品订单表
     * @param commodityOrderRequest
     * @return
     */
    CommodityOrderResult queryCommodityOrderByrequest(CommodityOrderRequest commodityOrderRequest);


    /**
     * 聚合查询
     * @param commodityOrderRequest
     * @return
     */
    CommodityOrderResult queryCommodityOrderByrequestGroup(CommodityOrderRequest commodityOrderRequest);


}
