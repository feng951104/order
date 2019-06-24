package com.merchant.order.manage.order.impl;

import com.merchant.order.manage.order.OrderNumberManager;
import com.merchant.order.util.CreateNumberIdUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/6/3
 */
@Log4j
@Component
public class OrderNumberManagerImpl implements OrderNumberManager {


    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    /**
     * 流水号存入redis的key
     */
    @Value("${order.number.key:number_id_key}")
    private String numberPrefix;

    @Override
    public String createNumber() {


        String numberId = CreateNumberIdUtil.getInstance().getnumber(this.getNumberIdFormRedis());

        if (StringUtils.isBlank(numberId)) {
            log.error("create numberId error in OrderNumberManagerImpl.createNumber ...");
            return null;
        }
        if (!this.setNumberIdToRedis(numberId)) {
            return null;
        }
        return numberId;
    }

    /**
     * 获取存入redis中的流水号
     *
     * @return
     */
    private String getNumberIdFormRedis() {
        return redisTemplate.boundValueOps(numberPrefix).get();
    }

    /**
     * 将新的流水号塞进redis
     *
     * @param numberId
     */
    private boolean setNumberIdToRedis(String numberId) {
        try {
            redisTemplate.boundValueOps(numberPrefix).set(numberId);
            log.info("now numberId set redis success ...");
            return true;
        } catch (Exception e) {
            log.error("now numberId set redis error ...", e);
            return false;
        }
    }


}
