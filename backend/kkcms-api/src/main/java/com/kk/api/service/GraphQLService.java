package com.kk.api.service;

import com.kk.api.resolver.Scalar.GraphQLDate;
import com.kk.api.service.datafetcher.*;
import graphql.GraphQL;
import graphql.language.ScalarTypeDefinition;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by msi- on 2018/2/8.
 */
@Service
public class GraphQLService {

    @Value("classpath:graphql/song.graphqls")
    private Resource resource;

    private GraphQL graphQL;
    /**
     * 数据获取器
     */
    @Autowired
    private FindOneDataFetcher findByIdDataFetcher;
    @Autowired
    private FindAllDataFetcher findAllDataFetcher;
    @Autowired
    private NewObjectDataFetcher newObjectDataFetcher;
    @Autowired
    private UpdateObjectDataFetcher updateObjectDataFetcher;
    @Autowired
    private DeleteObjectDataFetcher deleteObjectDataFetcher;
    /**
     * 自定义scalar转换器
     */
    @Autowired
    private GraphQLDate graphQLDate;

    @PostConstruct
    public void loadShema() throws IOException {
        //use the file to parse
        File schemaFile = resource.getFile();
        // parse 解析schema，通过特定文件，由graphQL进行type definitiion的注册
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        //包装schemas，用以映射需要什么和在运行时会发生什么
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        //用于保存实际返回的对象（由前端请求需要具体的参数）
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        //增加一些数据获取（dataFetch），目的是让graphql知道向哪张表获取什么数据
        return RuntimeWiring.newRuntimeWiring().type("Query", typeWiring ->
                typeWiring
                        .dataFetcher("findSongById", findByIdDataFetcher)
                        .dataFetcher("findAllSong", findAllDataFetcher)
                        .dataFetcher("findAllCommentByUserId", findAllDataFetcher)
                        .dataFetcher("findAllComment", findAllDataFetcher)
                        .dataFetcher("findAllMomentByUserId", findAllDataFetcher)
                        .dataFetcher("findAllMoment", findAllDataFetcher)
        ).type("Mutation", typeWiring -> typeWiring
                .dataFetcher("newComment", newObjectDataFetcher)
                .dataFetcher("updateComment", updateObjectDataFetcher)
                .dataFetcher("deleteComment", deleteObjectDataFetcher)
        ).scalar(graphQLDate)
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
