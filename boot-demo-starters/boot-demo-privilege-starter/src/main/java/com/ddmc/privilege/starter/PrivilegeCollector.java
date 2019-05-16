package com.ddmc.privilege.starter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 权限收集器
 *
 * @Author wude
 * @Create 2019-04-30 14:40
 */
@Service
public class PrivilegeCollector {

    private Logger logger = LoggerFactory.getLogger(PrivilegeCollector.class);

    private static final String CACHE_KEY = "privilege";
    private LoadingCache<String, List<PrivilegeInfo>> loadingCache = null;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final PrivilegeProperties privilegeProperties;

    public PrivilegeCollector(PrivilegeProperties privilegeProperties, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.privilegeProperties = privilegeProperties;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @PostConstruct
    public void initCacheLoader() throws NoSuchMethodException {
        CacheLoader<String, List<PrivilegeInfo>> cacheLoader = new CacheLoader<String, List<PrivilegeInfo>>() {
            @Override
            public List<PrivilegeInfo> load(String key) {
                return collect();
            }
        };
        loadingCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .expireAfterWrite(privilegeProperties.getIdle(), TimeUnit.MINUTES)
                .initialCapacity(1)
                .maximumSize(1)
                .recordStats() // // 设置要统计缓存的命中率
                .removalListener(notification -> logger.info("### Guava缓存[{}]被移除了: {}", notification.getKey(), notification.getCause()))
                .build(cacheLoader);

        if (this.privilegeProperties.isInit()) {
            PrivilegeController privilegeController = null;
            try {
                privilegeController = BeanUtils.instantiateClass(PrivilegeController.class.getDeclaredConstructor(this.getClass()), this);
            } catch (NoSuchMethodException e) {
                logger.error("### 实例化Bean[{}]出错了!", PrivilegeController.class.getName(), e);
                throw e;
            }

            String basePath = "";
            RequestMapping requestMapping = PrivilegeController.class.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                basePath = requestMapping.value()[0];
            }
            RequestMappingInfo requestMappingInfo = null;
            for (Method method : PrivilegeController.class.getMethods()) {
                requestMapping = method.getAnnotation(RequestMapping.class);
                if (requestMapping != null) {
                    requestMappingInfo = RequestMappingInfo.paths(basePath + "/" + requestMapping.value()[0]).build();
                    requestMappingHandlerMapping.registerMapping(requestMappingInfo, privilegeController, method);
                }
            }
            long start = System.currentTimeMillis();
            requestMappingHandlerMapping.afterPropertiesSet();
            long end = System.currentTimeMillis();
            logger.info("### 注册PrivilegeController到RequestMappingHandlerMapping后刷新mapping耗时：{}毫秒!", end - start);
        }
    }

    /**
     * 搜集权限
     *
     * @return
     */
    private List<PrivilegeInfo> collect() {
        List<PrivilegeInfo> privilegeInfos = new ArrayList<>();
        // requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Privilege privilege = handlerMethod.getMethod().getAnnotation(Privilege.class);
            if (privilege != null) {
                PrivilegeInfo privilegeInfo = new PrivilegeInfo();
                privilegeInfo.setController(handlerMethod.getMethod().getDeclaringClass().getCanonicalName());
                privilegeInfo.setMethod(handlerMethod.getMethod().getName());
                if ("".equalsIgnoreCase(privilege.authItem().trim())) {
                    privilegeInfo.setAuthItem(privilegeInfo.getController() + "_" + privilegeInfo.getMethod());
                } else {
                    privilegeInfo.setAuthItem(privilege.authItem());
                }
                privilegeInfo.setAlias(privilege.name());
                privilegeInfo.setUri(requestMappingInfo.getPatternsCondition().getPatterns());
                privilegeInfos.add(privilegeInfo);
            }
        }
        return privilegeInfos;
    }

    /**
     * 获取权限集
     *
     * @return
     * @throws ExecutionException
     */
    public List<PrivilegeInfo> get() throws ExecutionException {
        return loadingCache.get(CACHE_KEY);
    }
}