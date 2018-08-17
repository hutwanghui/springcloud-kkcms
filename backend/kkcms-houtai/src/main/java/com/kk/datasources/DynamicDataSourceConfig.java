package com.kk.datasources;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 */
@Configuration
@Order(-10)
@MapperScan(basePackages = "com.kk.modules.*.dao")
public class DynamicDataSourceConfig {

    @Bean(name = "firstDatasource")
    @Qualifier(value = "firstDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "secondDatasource")
    @Qualifier(value = "secondDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.second")
    public DataSource secondDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @DependsOn({"firstDatasource", "secondDatasource"})
    public DynamicDataSource dataSource() {

        DataSource master = firstDataSource();
        DataSource slave = secondDataSource();
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, master);
        targetDataSources.put(DataSourceNames.SECOND, slave);

        return new DynamicDataSource(master, targetDataSources);
    }
}
