package com.my.Reflex;

import java.lang.reflect.Method;

/**
 * Created by GZP on 2017/7/21.
 */

public class FinClass {

    /**
     *
     * @param classname 完整的类名 包+类
     * @param methodname 方法名
     * @param obj 参数值
     * @return
     * @throws Exception
     */
    public static Object getvalue(String classname,String methodname,Object[] obj) throws Exception {
        Class<?> clname=Class.forName(classname);
        Method[] method=clname.getMethods();
        for (int i=0;i<method.length;i++){
            if (method[i].equals(methodname)){
              return   method[i].invoke(clname.newInstance(),obj);
            }
        }
        return null;
    }

}
