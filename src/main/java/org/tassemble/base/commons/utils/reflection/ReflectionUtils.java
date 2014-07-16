package org.tassemble.base.commons.utils.reflection;

import java.lang.reflect.Method;

import org.tassemble.base.commons.utils.text.NeteaseEduStringUtils;

/*
 * @author hzfjd@corp.netease.com
 * @date 2012-5-8
 */
public class ReflectionUtils {

    public static Method getPropertyMethod(Class clz, String propertyName) {
        Method mth = null;
        propertyName = NeteaseEduStringUtils.upperFirstChar(propertyName);
        try {

            mth = clz.getMethod("get" + propertyName);
        } catch (Exception e) {
            try {
                mth = clz.getMethod("is" + propertyName);
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        return mth;

    }

}
