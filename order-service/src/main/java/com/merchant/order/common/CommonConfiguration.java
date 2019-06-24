package com.merchant.order.common;

import com.merchant.convert.ConvertManager;
import com.merchant.convert.impl.ConvertManagerImpl;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author wangyf
 * @date 2019/5/20
 */
@Configuration
public class CommonConfiguration {

    @Bean
    public ConvertManager convertManager(){
        return new ConvertManagerImpl();
    }


    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        DozerBeanMapper mapper = new DozerBeanMapper();
        List<String> fileNameList = new ArrayList<>();
//        fileNameList.add("dozer/mapping-bo.xml");
        mapper.setMappingFiles(fileNameList);
        return mapper;
    }
}

