package xmxrProject.genServer.common.utils;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @FileName: C_CacheUtil.java
 * @Author: 行米希尔
 * @Date: 2020/9/2 11:33
 * @Description:
 */
public class C_CacheUtil {

    //设置初始容量
    private static int initialCapacity = 1024;

    private static float loadFactor = 0.75f;

    private static ConcurrentHashMap<String,Value> cache = new ConcurrentHashMap<String,Value>(initialCapacity);

    //刷新缓存计数
    private static int flushCount = 0;


    /**
     *
     * @param key
     * @param value
     * @return
     */
    public static Object put(String key , Object value){
        flush();
        Object retVal = cache.get(key).getValue();
        cache.put(key,new Value(null,System.currentTimeMillis(),value));
        return retVal;
    }

    /**
     *
     * @param key
     * @param outTime
     * @return
     */
    public static boolean addOutTime(String key ,long outTime){
        Value value = cache.get(key);
        if(value == null){
            return false;
        }
        value.outTime+=outTime;
        return true;
    }

    /**
     *
     * @param key
     * @param outTime
     * @return
     */
    public static boolean setOutTime(String key ,long outTime){
        Value value = cache.get(key);
        if(value == null){
            return false;
        }
        value.outTime=outTime;
        return true;
    }


    /**
     *
     * @param key
     * @param value
     * @param outTime
     * @return
     */
    public static Object put(String key , Object value , long outTime){
        flush();
        Object retVal = cache.get(key).getValue();
        cache.put(key,new Value(outTime*1000,System.currentTimeMillis(),value));
        return retVal;
    }

    /**
     *
     * @param key
     * @return
     */
    public static Object get(String key){
        Value value = cache.get(key);
        if(value.isOutTime()){
            return null;
        }
        return value.getValue();
    }

    /**
     * 刷新缓存 删除已超时数据
     */
    public static void flush(){
        flushCount++;
        if(flushCount > initialCapacity*loadFactor){
            cache.entrySet().parallelStream().filter(o->o.getValue().isOutTime()).forEach(o->{o.getValue().clear();cache.remove(o.getKey());});
            flushCount = 0;
            System.gc();
        }
    }



    private static class Value{
        private Long outTime;
        private Long saveTime;
        private Object value;

        public Value(Long outTime, Long saveTime, Object value) {
            this.outTime = outTime;
            this.saveTime = saveTime;
            this.value = value;
        }

        private Object getValue(){
            return  isOutTime() ? null : value;
        }
        private boolean isOutTime(){
            return !(outTime == null) ||System.currentTimeMillis() - saveTime > outTime ;
        }

        private void clear(){
            value = null;
            outTime = null;
            saveTime = null;

        }

    }

}//end
