package com.mes.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;


public class WebUtils {
    /**
     *
     * @param value  这里指的是传过来的Map数据
     * @param bean 这里指的是javaBean 对象，这里指的是User 对象
     * @return  返回User对象
     */
    public static<T> T copyParamToBean( Map value, T bean){
        // 将请求里面的参数全部放到bean对象中，这里指的是user对象
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static int parseInt(String strInt,Integer defaultValue){
            // 转换类型为int型

        try {
            return Integer.parseInt(strInt);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
