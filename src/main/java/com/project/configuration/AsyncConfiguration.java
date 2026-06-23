package com.project.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration for asynchronous event processing.
 * Configures thread pool for @Async annotated methods.
 */
@Configuration
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {
    /**
     * Configure the executor for async event processing.
     *
     * @return Configured thread pool executor
     */
    @Bean(name = "eventExecutor")
    @Override
    public Executor getAsyncExecutor() {
        //creates a thread pool
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // minimum no. workers(threads)..whether work complete or not they will always be ready
        executor.setCorePoolSize(5);

        // if there is so much work(100email,200sms) then maximum workers will be 10
        executor.setMaxPoolSize(10);

        // Queue capacity - number of tasks to queue before creating new threads
        //if 10 threads are busy and 100th task comes..it will also stand at end of queue rather than a new thread comes
        executor.setQueueCapacity(100);

        // Thread name prefix for debugging
        //instead of thread-1,thread-2--->even-handler-1..
        executor.setThreadNamePrefix("event-handler-");

        // server Wait for tasks to complete on shutdown
        //if false->Server band->half email send->Task cancel
        //if true->Server Wait...->Email complete->Ab shutdown
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // Max time to wait for tasks to complete (30 seconds)
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();
        return executor;
    }

    //Exception Handler->Handle exceptions thrown by async methods
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, params) -> {
            log.error("Exception in async method: {} with params: {}",
                    method.getName(), params, throwable);

        };
    }

}
