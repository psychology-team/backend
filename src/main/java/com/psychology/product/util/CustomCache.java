import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

public class CustomCache {
    private Cache<String, String> cache;

    public MyCache() {
        // ????????? ?????????? Cache ? ???????? 1000 ??????? ? ????????? 10 ??????
        cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.getIfPresent(key);
    }
}
