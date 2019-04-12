package com.java.base.annotation.ioc;

import com.java.base.annotation.auto.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解辅助
 *
 * @author xuweizhi
 * @date 2019/04/11 20:29
 */
public class MyAnnotationAssistant {

    public final Map<String, Class<? extends Annotation>> componentRegister = new HashMap<>();

    public final Map<String, Class<? extends Annotation>> appRegister = new HashMap<>();

    {
        componentRegister.put("MyComponent", MyComponent.class);
        componentRegister.put("MySql", MySql.class);
        componentRegister.put("MyValue", MyValue.class);
        componentRegister.put("MyAutowired", MyAutowired.class);

        appRegister.put("MyApplication", MyApplication.class);
    }

}
