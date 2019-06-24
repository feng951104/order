package com.merchant.order.util;

import com.merchant.user.bo.CommonBOResult;
import com.merchant.user.util.ResultConstant;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/4/29
 */
public class ResultOrderServiceCodeUtil {

    /**
     * 返回结果success
     * @param result
     */
    public static void resultSuccess(CommonBOResult result) {
        result.setMessage(ResultConstant.MESSAGE.DEFAULT_SUCCESS_MESSAGE);
        result.setStatus(ResultConstant.CODE.SUCCESS);

    }





    /**
     * 返回结果失败
     * @param result
     */
    public static void resultError(CommonBOResult result) {
        result.setMessage(ResultConstant.MESSAGE.DEFAULT_ERROR_MESSAGE);
        result.setStatus(ResultConstant.CODE.ERROR);

    }




}
