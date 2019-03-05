package guava.cache;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author ddmc
 * @Create 2019-03-05 13:27
 */
public class GuavaCacheDemo1 {
    public static void main(String[] args) {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                Thread.sleep(1000);
                if ("key".equals(key)) {
                    return null;
                }
                System.out.println(key + " is loaded from a cacheLoader!");
                return key + "'s value";
            }
        };

        RemovalListener<String, String> removalListener = new RemovalListener<String, String>() {
            @Override
            public void onRemoval(RemovalNotification<String, String> removal) {
                System.out.println("[" + removal.getKey() + ":" + removal.getValue() + "] is evicted!");
            }
        };

        LoadingCache<String, String> testCache = CacheBuilder.newBuilder()
                .maximumSize(7)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(removalListener)
                .build(loader);
        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            String value = "value" + i;
            testCache.put(key, value);
            System.out.println("[" + key + ":" + value + "] is put into cache!");
        }

        System.out.println(testCache.getIfPresent("key6"));
        try {
            System.out.println(testCache.get("key"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(testCache.getIfPresent("key66"));
    }

}