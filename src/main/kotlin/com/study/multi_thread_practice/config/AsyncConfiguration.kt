package com.study.multi_thread_practice.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfiguration {
    private val LOGGER: Logger = LoggerFactory.getLogger(AsyncConfiguration::class.java)

    @Bean
    fun taskExecutor(): Executor {
        LOGGER.debug("Creating Async Task Executor")
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 4
        executor.maxPoolSize = 4
        executor.setQueueCapacity(100)
        executor.setThreadNamePrefix("CarThread-")
        executor.initialize()

        return executor
    }
}