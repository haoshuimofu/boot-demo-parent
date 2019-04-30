package com.ddmc.privilege.starter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
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
public class PrivilegeCollector {

    private Logger logger = LoggerFactory.getLogger(PrivilegeCollector.class);

    private static final String CACHE_KEY = "privilege";
    private LoadingCache<String, List<PrivilegeInfo>> loadingCache = null;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public PrivilegeCollector(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @PostConstruct
    public void initCacheLoader() {
        CacheLoader<String, List<PrivilegeInfo>> cacheLoader = new CacheLoader<String, List<PrivilegeInfo>>() {
            @Override
            public List<PrivilegeInfo> load(String key) {
                return collect();
            }
        };
        loadingCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .initialCapacity(1)
                .maximumSize(1)
                .recordStats() // // 设置要统计缓存的命中率
                .removalListener(notification -> logger.info("### Guava缓存[{}]被移除了: {}", notification.getKey(), notification.getCause()))
                .build(cacheLoader);
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
                if ("".equalsIgnoreCase(privilege.authTtem().trim())) {
                    privilegeInfo.setAuthItem(privilegeInfo.getController() + "_" + privilegeInfo.getMethod());
                } else {
                    privilegeInfo.setAuthItem(privilege.authTtem());
                }
                privilegeInfo.setAlias(privilege.alias());
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