package xmxrProject.genServer.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @FileName: 啊.java
 * @Author: 行米希尔
 * @Date: 2020/7/27 9:30
 * @Description:
 */
@Component
public class DataSourceInitListener  implements ApplicationListener<ContextRefreshedEvent> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DataSourceInitListener.class);

    public static ExecutorService cachedThreadPool =  Executors.newCachedThreadPool();

    @SuppressWarnings("unchecked")
    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
        //防止重复执行。
        if(ev.getApplicationContext().getParent() == null){
            try {
                cachedThreadPool.execute((Runnable) Class.forName("xmxrProject.genServer.common.task.GlobalDirectivesTask").newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}