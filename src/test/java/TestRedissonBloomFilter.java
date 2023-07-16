import cn.hutool.core.text.StrBuilder;
import com.ukayunnuo.App;
import com.ukayunnuo.bloomfilter.core.RedissonBloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 测试
 *
 * @author yunnuo
 * @since 1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestRedissonBloomFilter {

    /**
     * 预期插入值
     */
    private final static long capacity = 1000L;

    /**
     * 出错率
     */
    private final static double errorRate = 0.0001;

    @Resource
    private RedissonBloomFilter redissonBloomFilter;


    @Test
    public void test() {

        RBloomFilter<Long> bloomFilter = redissonBloomFilter.create("DEMO:BOOM-FILTER", capacity, errorRate);

        for (long i = 0; i < capacity; i++) {
            bloomFilter.add(i);
        }

        long count = bloomFilter.count();

        for (int k = 0; k < 100; k++) {
            // 范围内
            int innerErrorCount = 0;
            for (long i = 0; i < capacity; i++) {
                if (Boolean.FALSE.equals(bloomFilter.contains(i))) {
                    innerErrorCount++;
                }
            }

            // 范围外
            int outErrorCount = 0;
            StrBuilder strBuilder = StrBuilder.create();
            for (long i = capacity; i < capacity * 2L; i++) {
                if (bloomFilter.contains(i)) {
                    outErrorCount++;
                    strBuilder.append(String.valueOf(i)).append(",");

                }
            }

            log.info("测试次数：{}, bloomFilter.count:{}, 范围内误判次数 = {}, 范围外误判次数 = {}, 范围外具体数值：{}", k, count, innerErrorCount, outErrorCount, strBuilder);

        }
        bloomFilter.delete();

    }


}
