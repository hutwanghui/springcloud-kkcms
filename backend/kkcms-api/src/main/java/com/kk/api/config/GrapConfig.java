package com.kk.api.config;

import com.kk.api.resolver.Scalar.GraphQLDate;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by msi- on 2018/2/12.
 */
@Configuration
public class GrapConfig {
    @Bean
    public GraphQLDate graphQLScalarType() {
        GraphQLDate graphQLDate = new GraphQLDate();
        return graphQLDate;
    }
}
