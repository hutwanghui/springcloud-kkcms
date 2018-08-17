package com.kk.datasources.annotation;

import com.kk.datasources.DataSourceNames;

import java.lang.annotation.*;

/**
 * 多数据源注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default DataSourceNames.FIRST;
}
