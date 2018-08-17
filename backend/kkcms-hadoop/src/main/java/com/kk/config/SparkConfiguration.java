package com.kk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.net.URISyntaxException;

/**
 * Created by msi- on 2018/7/13.
 */
@Configuration
@EnableTransactionManagement
public class SparkConfiguration implements Serializable{
    private static final long serialVersionUID = 1L;


/*    @Bean(name = "sparkConf")
    @ConditionalOnMissingBean(SparkConf.class)
    public SparkConf sparkconf() {
        SparkConf conf = new SparkConf()
                .setAppName("com.kk.movieCF")
                .setMaster("spark://master:7077")
                .set("spark.executor.memory", "1g")
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .set("spark.testing.memory", "2147480000");
        return conf;
    }

    @Bean(name = "javaSparkContext")
    @ConditionalOnMissingBean(JavaSparkContext.class)
    public JavaSparkContext javaSparkContext(@Autowired SparkConf sparkConf) {
        return new JavaSparkContext(sparkConf);
    }*/

}
