package com.merchant.order.manage.order.impl;

import com.alibaba.fastjson.JSON;
import com.merchant.convert.ConvertManager;
import com.merchant.data.po.result.CommonResultPO;
import com.merchant.order.bo.order.request.OrderBORequest;
import com.merchant.order.constant.order.OrderConstant;
import com.merchant.order.dao.order.OrderDao;
import com.merchant.order.manage.order.OrderManager;
import com.merchant.order.manage.order.OrderNumberManager;
import com.merchant.order.po.data.CommodityOrder;
import com.merchant.order.util.ResultOrderServiceCodeUtil;
import com.merchant.user.bo.CommonBOResult;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/5/24
 */
@Component
@Log4j
public class OrderManagerImpl implements OrderManager {

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderNumberManager orderNumberManager;


    @Resource
    private ConvertManager convertManager;

    @Override
    public CommonBOResult createOrder(OrderBORequest orderBORequest) {
        CommonBOResult commonBOResult = new CommonBOResult();
        CommodityOrder commodityOrder = convertManager.tran(orderBORequest, CommodityOrder.class);
        commodityOrder.setPlatform(OrderConstant.OrderPlatform.MMALPLATFORM);
        commodityOrder.setCommodityOrderDetailList(JSON.toJSONString(orderBORequest.getOrderDetailParam()));
        commodityOrder.setOrderOfferDetails(JSON.toJSONString(orderBORequest.getOrderCheapDetailParamList()));
        CommonResultPO result = orderDao.createOrder(this.assembleOrderInformation(commodityOrder));
        if (!result.isSuccess()) {
            log.error("create order error in createOrder ...");
            return commonBOResult;
        }

        ResultOrderServiceCodeUtil.resultSuccess(commonBOResult);
        return commonBOResult;
    }

    /**
     * 拼装订单信息
     *
     * @param commodityOrder
     * @return
     */
    private CommodityOrder assembleOrderInformation(CommodityOrder commodityOrder) {
        /**
         * 生成订单号uuid
         */
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("create orderUniqueId is :" + uuid + " in AssembleOrderInformation ...");
        commodityOrder.setOrderUniqueId(uuid);
        commodityOrder.setCreateOrderTime(new Date());
        commodityOrder.setUpdateOrderTime(new Date());
        commodityOrder.setOrderStatus(OrderConstant.OrderStatusConstant.TOBEPAYORDER);
        String numberId = orderNumberManager.createNumber();
        if (StringUtils.isBlank(numberId)) {
            log.error("OrderNumber create error ...");
            return null;
        }
        commodityOrder.setOrderNumber(numberId);
        return commodityOrder;
    }


}
