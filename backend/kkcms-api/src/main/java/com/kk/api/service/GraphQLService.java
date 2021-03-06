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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;

/**
 * Created by msi- on 2018/2/8.
 */
@Service
public class GraphQLService {

    @Value("classpath:/graphql/song.graphqls")
    private Resource resource;

    private GraphQL graphQL;
    /**
     * 数据获取器
     */
    @Autowired
    private FindOneDataFetcher findOneDataFetcher;
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
        //File schemaFile = resource.getFile();
        InputStream is=this.getClass().getResourceAsStream("/graphql/song.graphqls");
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        // parse 解析schema，通过特定文件，由graphQL进行type definitiion的注册
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(br);
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
                        .dataFetcher("findSongById", findOneDataFetcher)
                        .dataFetcher("findAllSong", findAllDataFetcher)
                        .dataFetcher("findAllCommentByUserId", findAllDataFetcher)
                        .dataFetcher("findAllComment", findAllDataFetcher)
                        .dataFetcher("findAllMomentByUserId", findAllDataFetcher)
                        .dataFetcher("findAllMoment", findAllDataFetcher)
                        .dataFetcher("findAllTop10", findAllDataFetcher)
                        .dataFetcher("findAllBehavior", findAllDataFetcher)
                        .dataFetcher("findAllBehaviorChina_1", findAllDataFetcher)
                        .dataFetcher("findAllBehaviorChina_2", findAllDataFetcher)
                        .dataFetcher("findAllBehaviorChina_3", findAllDataFetcher)
                        .dataFetcher("findAllBehaviorChina_4", findAllDataFetcher)
                        .dataFetcher("findOnePurchesMouth", findOneDataFetcher)
                        .dataFetcher("findOneBrowseMouth", findOneDataFetcher)
                        .dataFetcher("findOneAddcartMouth", findOneDataFetcher)
                        .dataFetcher("findOneCollectMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_1UpMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_1DownMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_2UpMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_2DownMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_3UpMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_3DownMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_4UpMouth", findOneDataFetcher)
                        .dataFetcher("findOneBehavior_4DownMouth", findOneDataFetcher)
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
