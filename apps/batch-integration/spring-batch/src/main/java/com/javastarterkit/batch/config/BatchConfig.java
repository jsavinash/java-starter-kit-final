package com.javastarterkit.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.javastarterkit.batch.entity.UserRecord;
import com.javastarterkit.batch.processor.UserProcessor;
import com.javastarterkit.batch.writer.UserWriter;

@Configuration
public class BatchConfig {

    @Bean
    public Job userImportJob(JobRepository jobRepository, Step userImportStep) {
        return new JobBuilder("userImportJob", jobRepository)
                .start(userImportStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @StepScope
    @Bean
    public Step userImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<UserRecord> reader,
            ItemProcessor<UserRecord, UserRecord> processor,
            ItemWriter<UserRecord> writer) {
        return new StepBuilder("userImportStep", jobRepository)
                .<UserRecord, UserRecord>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public UserProcessor userProcessor() {
        return new UserProcessor();
    }

    @Bean
    public UserWriter userWriter() {
        return new UserWriter();
    }
}