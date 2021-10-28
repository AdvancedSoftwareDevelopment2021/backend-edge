package cn.edu.sjtu.ist.ecssbackendedge.componect;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Quartz 调度器访问类
 */
@Component
public class QuartzScheduler {
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws Exception {
        this.scheduler = new StdSchedulerFactory().getScheduler();
        this.scheduler.start();
    }

    @PreDestroy
    public void destroy() throws Exception {
        this.scheduler.shutdown();
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }
}
