package com.merchant.order.dao;

import com.merchant.data.po.result.CommonResultPO;
import com.merchant.order.dao.order.OrderDao;
import com.merchant.order.base.BaseDaoTest;
import com.merchant.order.constant.order.OrderConstant;
import com.merchant.order.po.data.CommodityOrder;
import com.merchant.order.po.request.CommodityOrderRequest;
import com.merchant.order.po.result.CommodityOrderResult;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

public class OrderDaoTest extends BaseDaoTest {


    @Resource
    private OrderDao orderDao;

    @Test
    public void createOrder() {

        CommodityOrder commodityOrder = new CommodityOrder();
        commodityOrder.setPlatform(OrderConstant.OrderPlatform.MMALPLATFORM);
        commodityOrder.setOrderStatus(OrderConstant.OrderStatusConstant.ORDERCOMPLETE);
        commodityOrder.setOrderNumber("999999999999999999999999");
        commodityOrder.setUpdateOrderTime(new Date());
        commodityOrder.setCreateOrderTime(new Date());
        commodityOrder.setOrderUniqueId(UUID.randomUUID().toString().replaceAll("-", ""));
        commodityOrder.setCurrentTotalPrice(76543.00);
        commodityOrder.setOriginalTotalPrice(887654.00);
        commodityOrder.setRewardAddress("上海市浦东新区");
        commodityOrder.setRewardPhone("18016302686");
        commodityOrder.setRewardName("王先生");


        CommonResultPO commonResultPO = orderDao.createOrder(commodityOrder);
        System.out.println(commonResultPO.getMessage());
    }

    @Test
    public void updateOrderStatus() {
        CommodityOrder commodityOrder = new CommodityOrder();
        commodityOrder.setOrderUniqueId("27cd78e33bfb41c08770d4cfc27dddc5");
        commodityOrder.setOrderStatus(OrderConstant.OrderStatusConstant.PAYEDORDERNOTSHIP);

        CommonResultPO commonResultPO = orderDao.updateOrderStatus(commodityOrder);
        System.out.println(commonResultPO.getMessage());

    }

    @Test
    public void queryCommodityOrderByrequest() {
        CommodityOrderRequest commodityOrderRequest = new CommodityOrderRequest();

        commodityOrderRequest.setMaxCurrentTotalPrice(3000.00);
        CommodityOrderResult commodityOrderResult = orderDao.queryCommodityOrderByrequest(commodityOrderRequest);
        System.out.println("---------"+commodityOrderResult.getCount());
        for (CommodityOrder commodityOrder : commodityOrderResult.getValues()) {
            System.out.println("-----------------"+commodityOrder.toString());
        }
    }

    @Test
    public void queryCommodityOrderByrequestGroup() {
        CommodityOrderRequest commodityOrderRequest = new CommodityOrderRequest();

        CommodityOrderResult commodityOrderResult = orderDao.queryCommodityOrderByrequestGroup(commodityOrderRequest);
        for (CommodityOrder commodityOrder : commodityOrderResult.getValues()) {
            System.out.println(commodityOrder.toString());
        }
    }
}