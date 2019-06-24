package com.merchant.order.po.request;

import com.merchant.data.po.request.CommonRequestPO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/6/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommodityOrderRequest extends CommonRequestPO {

    /**
     * 订单号
     */
    private String orderUniqueId;
    /**
     * 订单流水号
     */
    private String orderNumber;

    /**
     * 订单状态集合
     * {@link com.merchant.order.constant.order}
     */
    private List<String> orderStatuses;

    /**
     * 订单来源类型集合   mmal 商城
     * {@link com.merchant.order.constant.order}
     */
    private List<String> platforms;

    /**
     * 最大原总价
     */
    private Double maxOriginalTotalPrice;

    /**
     * 最小原总价
     */
    private Double minOriginalTotalPrice;

    /**
     * 最大实付总价
     */
    private Double maxCurrentTotalPrice;

    /**
     * 最小实付总价
     */
    private Double minCurrentTotalPrice;
}
