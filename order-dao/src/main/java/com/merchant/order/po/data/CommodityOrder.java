package com.merchant.order.po.data;

import com.merchant.data.order.BaseOrder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * Description:
 *
 * @author wangyf
 * @date 2019/5/24
 */
@Data
@ToString
@Document(collection = "t_yf_commodity_order")
public class CommodityOrder extends BaseOrder {

    /**
     * 订单明细 数组结构的JSON数据
     * [{},{},{}...]
     * <p>
     * <p>
     * private List<CommodityOrderDetail> commodityOrderDetailList;
     */
    private String commodityOrderDetailList;

    /**
     * 优惠明细 数组结构的JSON数据
     * [{},{},{}...]
     * <p>
     * private List<CommodityOrderOfferDetail> orderOfferDetails;
     */
    private String orderOfferDetails;

}
