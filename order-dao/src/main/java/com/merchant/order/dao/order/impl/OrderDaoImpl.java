package com.merchant.order.dao.order.impl;

import com.merchant.data.po.result.CommonResultPO;
import com.merchant.order.dao.order.OrderDao;
import com.merchant.order.po.data.CommodityOrder;
import com.merchant.order.po.request.CommodityOrderRequest;
import com.merchant.order.po.result.CommodityOrderResult;
import com.merchant.order.util.ResultOrderUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/6/3
 */
@Component
@Log4j
public class OrderDaoImpl implements OrderDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public CommonResultPO createOrder(CommodityOrder commodityOrder) {
        CommonResultPO commonResultPO = new CommonResultPO();
        try {
            mongoTemplate.save(commodityOrder);
            ResultOrderUtil.resultSuccess(commonResultPO);
            commonResultPO.setCount(1);
        } catch (Exception e) {
            log.error("save order error in OrderDao ...", e);

        }
        return commonResultPO;
    }

    @Override
    public CommonResultPO updateOrderStatus(CommodityOrder commodityOrder) {
        CommonResultPO commonResultPO = new CommonResultPO();
        try {
            mongoTemplate.updateFirst(this.createQuery(commodityOrder), this.createUpdate(commodityOrder), CommodityOrder.class);
            ResultOrderUtil.resultSuccess(commonResultPO);
            commonResultPO.setCount(1);
        } catch (Exception e) {
            log.error("update order status error ...", e);
        }
        return commonResultPO;
    }

    @Override
    public CommodityOrderResult queryCommodityOrderByrequest(CommodityOrderRequest commodityOrderRequest) {
        CommodityOrderResult commodityOrderResult = new CommodityOrderResult();

        try {
            List<CommodityOrder> commodityOrderList = mongoTemplate.find(new Query(this.createCriteria(commodityOrderRequest)), CommodityOrder.class);
            commodityOrderResult.setValues(commodityOrderList);
            commodityOrderResult.setCount(commodityOrderList.size());
            ResultOrderUtil.resultSuccess(commodityOrderResult);
        } catch (Exception e) {
            log.error("query commodityOrder error by request in OrderDao", e);
        }

        return commodityOrderResult;
    }

    @Override
    public CommodityOrderResult queryCommodityOrderByrequestGroup(CommodityOrderRequest commodityOrderRequest) {
        CommodityOrderResult commodityOrderResult = new CommodityOrderResult();

        try {

            /**
             * 条件
             */
            Criteria criteria = this.createCriteria(commodityOrderRequest);
            criteria.and("originalTotalPrice").nin(null, "");
            Aggregation agg = Aggregation.newAggregation(
                    CommodityOrder.class,
                    /**
                     * 匹配条件
                     */
                    Aggregation.match(criteria),
                    /**
                     * 保留哪几个字段
                     */
                    Aggregation.project("originalTotalPrice", "currentTotalPrice"),

                    /**
                     * 按什么分组   求和什么字段
                     */
                    Aggregation.group("orderStatus").sum("currentTotalPrice").as("currentTotalPrice"),

                    Aggregation.project("originalTotalPrice").and("currentTotalPrice").previousOperation(),
                    /**
                     * 排序方式
                     */
                    Aggregation.sort(Sort.Direction.DESC, "currentTotalPrice"),
                    /**
                     * 每页显示几个
                     */
                    Aggregation.limit(2)
            );

            AggregationResults<CommodityOrder> results = mongoTemplate.aggregate(agg, "t_yf_commodity_order", CommodityOrder.class);
            commodityOrderResult.setValues(results.getMappedResults());
            ResultOrderUtil.resultSuccess(commodityOrderResult);
        } catch (Exception e) {
            log.error("query commodity by request group error ...", e);
        }
        return commodityOrderResult;
    }


    /**
     * 组装查询条件 Criteria
     *
     * @param commodityOrderRequest
     * @return
     */
    private Criteria createCriteria(CommodityOrderRequest commodityOrderRequest) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(commodityOrderRequest.getOrderUniqueId())) {
            criteria.and("orderUniqueId").is(commodityOrderRequest.getOrderUniqueId());
        }
        if (StringUtils.isNotBlank(commodityOrderRequest.getOrderNumber())) {
            criteria.and("orderNumber").is(commodityOrderRequest.getOrderNumber());
        }
        /**
         * 小于等于
         */
        if (commodityOrderRequest.getMaxOriginalTotalPrice() != null) {
            criteria.and("originalTotalPrice").lte(commodityOrderRequest.getMaxOriginalTotalPrice());
        }
        /**
         * 大于等于
         */
        if (commodityOrderRequest.getMinOriginalTotalPrice() != null) {
            criteria.and("originalTotalPrice").gte(commodityOrderRequest.getMinOriginalTotalPrice());
        }
        /**
         * 小于等于
         */
        if (commodityOrderRequest.getMaxCurrentTotalPrice() != null) {
            criteria.and("currentTotalPrice").lte(commodityOrderRequest.getMaxCurrentTotalPrice());
        }
        /**
         * 大于等于
         */
        if (commodityOrderRequest.getMinCurrentTotalPrice() != null) {
            criteria.and("currentTotalPrice").gte(commodityOrderRequest.getMinCurrentTotalPrice());
        }
        /**
         * in 查询
         */
        if (!CollectionUtils.isEmpty(commodityOrderRequest.getPlatforms())) {
            criteria.and("platform").in(commodityOrderRequest.getPlatforms());
        }
        /**
         * 订单状态 in 查询
         */
        if (!CollectionUtils.isEmpty(commodityOrderRequest.getOrderStatuses())) {
            criteria.and("orderStatus").in(commodityOrderRequest.getOrderStatuses());
        }

        return criteria;
    }


    /**
     * 创建修改时得查询条件
     *
     * @param commodityOrder
     * @return
     */
    private Query createQuery(CommodityOrder commodityOrder) {
        Query query = new Query();
        if (StringUtils.isNotBlank(commodityOrder.getOrderUniqueId())) {
            query.addCriteria(Criteria.where("orderUniqueId").is(commodityOrder.getOrderUniqueId()));
        }
        return query;
    }

    /**
     * 创建修改时得修改条件
     *
     * @param commodityOrder
     * @return
     */
    private Update createUpdate(CommodityOrder commodityOrder) {
        Update update = new Update();
        if (StringUtils.isNotBlank(commodityOrder.getOrderStatus())) {
            update.set("orderStatus", commodityOrder.getOrderStatus());
        }

        return update;
    }


}
