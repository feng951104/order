package com.merchant.order.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/6/3
 */
public class CreateNumberIdUtil {

    private static CreateNumberIdUtil createNumberIdUtil = null;

    /**
     * 取得CreateNumberIdUtil的单例实现
     *
     * @return
     */
    public static CreateNumberIdUtil getInstance() {
        if (createNumberIdUtil == null) {
            synchronized (CreateNumberIdUtil.class) {
                if (createNumberIdUtil == null) {
                    createNumberIdUtil = new CreateNumberIdUtil();
                }
            }
        }
        return createNumberIdUtil;
    }


    /**
     * 生成订单随机流水号
     *
     * @param length
     * @return
     */
    public synchronized String createOrderNumber(Integer length) {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowDate = dateFormat.format(new Date());
        String randomNumber = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomNumber += random.nextInt(10);

        }

        return "mmal_" + nowDate + randomNumber;
    }



    /**
     * MMAL+年月日+8+0001   递增的流水号
     * 每月从0001开始计数
     * */
    public synchronized String getnumber(String thisCode){

        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        if(StringUtils.isBlank(thisCode)){
            id="MMAL"+formatter.format(date)+"8"+"0001";
            return id;
        }
        String thisData = thisCode.substring(4, 10);
        if(!formatter.format(date).equals(thisData)){
            thisData = formatter.format(date);
            id = "MMAL" + thisData + "80001";
        }else{
            DecimalFormat df = new DecimalFormat("0000");
            id ="MMAL"+ formatter.format(date)+"8"
                    + df.format(1 + Integer.parseInt(thisCode.substring(11, 15)));
        }
        return id;
    }


}
