package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @Author ddmc
 * @Create 2019-03-05 11:21
 */
public class guavaSample {
    public static void main(String[] args) {

        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(10)  //最多存放十个数据
                .expireAfterWrite(10, TimeUnit.SECONDS)  //缓存200秒
                .recordStats()   //开启 记录状态数据功能
                .build(new CacheLoader<String, Integer>() {
                    //数据加载，默认返回-1,也可以是查询操作，如从DB查询
                    @Override
                    public Integer load(String key) throws Exception {
                        return -1;
                    }
                });

        //只查询缓存，没有命中，即返回null。 miss++
        System.out.println(cache.getIfPresent("key1")); //null
        //put数据，放在缓存中
        cache.put("key1", 1);
        //再次查询，已存在缓存中, hit++
        System.out.println(cache.getIfPresent("key1")); //1
        //失效缓存
        cache.invalidate("key1");
        //失效之后，查询，已不在缓存中, miss++
        System.out.println(cache.getIfPresent("key1")); //null

        try{
            //查询缓存，未命中，调用load方法，返回-1. miss++
            System.out.println(cache.get("key2"));   //-1
            //put数据，更新缓存
            cache.put("key2", 2);
            //查询得到最新的数据, hit++
            System.out.println(cache.get("key2"));    //2
            System.out.println("size :" + cache.size());  //1

            //插入十个数据
            for(int i=3; i<13; i++){
                cache.put("key"+i, i);
            }
            //超过最大容量的，删除最早插入的数据，size正确
            System.out.println("size :" + cache.size());  //10
            //miss++
            System.out.println(cache.getIfPresent("key2"));  //null

            Thread.sleep(5000); //等待5秒
            cache.put("key1", 1);
            cache.put("key2", 2);
            //key5还没有失效，返回5。缓存中数据为key1，key2，key5-key12. hit++
            System.out.println(cache.getIfPresent("key5")); //5

            Thread.sleep(5000); //等待5秒
            //此时key5-key12已经失效，但是size没有更新
            System.out.println("size :" + cache.size());  //10
            //key1存在, hit++
            System.out.println(cache.getIfPresent("key1")); //1
            System.out.println("size :" + cache.size());  //10
            //获取key5，发现已经失效，然后刷新缓存，遍历数据，去掉失效的所有数据, miss++
            System.out.println(cache.getIfPresent("key5")); //null
            //此时只有key1，key2没有失效
            System.out.println("size :" + cache.size()); //2

            System.out.println("status, hitCount:" + cache.stats().hitCount()
                    + ", missCount:" + cache.stats().missCount()); //4,5
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}