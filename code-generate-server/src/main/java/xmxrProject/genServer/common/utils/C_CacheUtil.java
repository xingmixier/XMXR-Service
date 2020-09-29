package xmxrProject.genServer.common.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: C_CacheUtil.java
 * @Author: 行米希尔
 * @Date: 2020/9/2 11:33
 * @Description:
 */
public class C_CacheUtil {

    //设置初始容量
    private static int initialCapacity = 1024*8;

    private static float loadFactor = 0.75f;

    private static ConcurrentHashMap<Object, Value> cache = new ConcurrentHashMap<Object, Value>(initialCapacity);

    //刷新缓存计数
    private static int flushCount = 0;


    /**
     * @param key
     * @param value
     * @return
     */
    public static Object put(Object key, Object value) {
        return put(key, value, 0);
    }

    /**
     * @param key
     * @param value
     * @param outTime
     * @return
     */
    public static Object put(Object key, Object value, long outTime) {
        flush();
        Object retVal = cache.get(key) == null ? null : cache.get(key).getValue();
        cache.put(key, new Value(outTime * 1000, value));
        return retVal;
    }

    /**
     * @param key
     * @param outTime
     * @return
     */
    public static boolean addOutTime(String key, long outTime) {
        return cache.get(key) != null && cache.get(key).addOutTime(outTime) != null;
    }

    /**
     * @param key
     * @param outTime
     * @return
     */
    public static boolean setOutTime(String key, long outTime) {
        return cache.get(key) != null && cache.get(key).setOutTime(outTime) != null;
    }


    /**
     * @param key
     * @return
     */
    public static Object get(String key) {
        return cache.get(key) == null ? null : cache.get(key).getValue();
    }


    /**
     * 刷新缓存 删除已超时数据
     */
    public static void flush() {
        flushCount++;
        if (flushCount > initialCapacity * loadFactor) {
            cache.entrySet().parallelStream().filter(o -> o.getValue().isOutTime()).forEach(o -> {
                cache.remove(o.getKey());
            });
            flushCount = 0;
            System.gc();
        }
    }


    private static class Value {
        private long outTime;
        private long saveTime;
        private Object value;

        public Value(long outTime, Object value) {
            this.outTime = outTime;
            this.saveTime = System.currentTimeMillis();
            this.value = value;
        }

        private Object getValue() {
            return isOutTime() ? null : value;
        }

        private boolean isOutTime() {
            return outTime > 0 && System.currentTimeMillis() - saveTime > outTime;
        }

        public Value setOutTime(long outTime) {
            this.outTime = outTime*1000;
            return this;
        }
        public Value addOutTime(long outTime) {
            this.outTime += outTime*1000;
            return this;
        }
        public Value subOutTime(long outTime) {
            this.outTime -= outTime*1000;
            return this;
        }
    }




}//end
