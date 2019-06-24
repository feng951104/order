package com.merchant.order.util;

import com.merchant.data.po.request.CommonRequestPO;
import com.merchant.data.po.result.CommonResultPO;
import com.merchant.user.util.ResultConstant;

import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/4/30
 */
public class ResultOrderUtil {

    /**
     * 如果使用xml的查询，page从0开始的
     */
    public static void dealPageForXml(CommonRequestPO request){
        if (request.isPaging() && request.getPage() > 0) {
            int page = request.getPage();
            page--;
            request.setPage(page);
        }
    }

    /**
     * 如果使用xml，把page恢复回去从1开始
     */
    public static void revertPageForXml(CommonRequestPO request){
        if (request.isPaging()){
            int page = request.getPage();
            page++;
            request.setPage(page);
        }
    }

    /**
     * 处理结果状态和编码为成功
     * @param result
     */
    public static void resultSuccess(CommonResultPO result){
        result.setStatus(ResultConstant.CODE.SUCCESS);
        result.setMessage(ResultConstant.MESSAGE.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * @param result 需要处理的结果
     * @param values 需要加入的数据
     * @param <T> 数据类型
     */
    public static <T> void resultValues(CommonResultPO<T> result, List<T> values){
        if (values != null){
            result.setValues(values);
            result.setCount(values.size());
            result.setTotalCount(values.size());
        }
    }

    /**
     * @param result 需要处理的结果
     * @param count 需要加入的数据
     * @param page 页码
     */
    public static void resultCountWithPaging(CommonResultPO result, Integer count, Integer page){
        result.setTotalCount(count);
        result.setPage(page);
    }

    /**
     * @param status 新增和编辑的返回状态
     * @param entity 实体类
     * @param res 需要处理的结果
     * @param <T> 实体类类型
     */
    public static <T> void dealUpsert(int status, T entity, CommonResultPO<T> res){
        if (status > 0) {
            List<T> list = Collections.singletonList(entity);
            resultSuccess(res);
            resultValues(res, list);
        }
    }



}
