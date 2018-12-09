package org.hut.kkcmsworkflowable.config;

import org.hut.kkcmsworkflowable.util.KieUtils;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Created by hutwanghui on 2018/12/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Configuration
public class DroolsConfiguration {
    private static final String RULES_PATH = "rules/";


    /**
     * KieServices就是一个中心，通过它来获取的各种对象来完成规则构建、管理和执行等操作
     * 1、获取KieContainer：访问KBase和KSession等信息
     * 2、获取KieRepository对象：管理KieModule
     * @return
     */
    private KieServices getKieServices() {
        //固定写法
        return KieServices.Factory.get();
    }

    /**
     * 读取resource目录下的rules文件
     * @return
     * @throws IOException
     */
    private Resource[] getRuleFiles() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
    }

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        for (Resource file : getRuleFiles()) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }
        return kieFileSystem;
    }

    /**
     * KieContainer就是一个KieBase的容器。
     * 1、提供了获取KieBase的方法和创建KieSession的方法。
     * @return
     * @throws IOException
     */
    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() throws IOException {
        KieServices kieServices =  getKieServices();
        final KieRepository kieRepository = kieServices.getRepository();

        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }

        kieBuilder.buildAll();

        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        KieUtils.setKieContainer(kieContainer);
        return kieContainer;
    }

    /**
     * KieBase就是一个知识仓库，包含了若干的规则、流程、方法等
     * KieBase本身并不包含运行时的数据之类的，如果需要执行规则KieBase中的规则的话，就需要根据KieBase创建KieSession
     * @return
     * @throws IOException
     */
    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    /**
     * KieSession就是应用程序跟规则引擎进行交互的会话通道。
     * @return
     * @throws IOException
     */
    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() throws IOException {
        KieSession kieSession = kieContainer().newKieSession();
        KieUtils.setKieSession(kieSession);
        return kieSession;
    }

    @Bean
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}
