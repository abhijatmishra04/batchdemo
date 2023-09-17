package com.example.batchdemo;

//import com.example.batchdemo.model.Item;
import com.example.batchdemo.Item;
import com.example.batchdemo.APIItemProcessor;
import com.example.batchdemo.ConsoleItemWriter;
//import com.example.batchdemo.processor.APIItemProcessor;
//import com.example.batchdemo.writer.ConsoleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public JdbcCursorItemReader<Item> reader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Item>()
                .dataSource(dataSource)
                .name("itemReader")
                .sql("SELECT id, data FROM item")
                .rowMapper(new BeanPropertyRowMapper<>(Item.class))
                .build();
    }

    @Bean
    public APIItemProcessor processor() {
        return new APIItemProcessor();
    }

    @Bean
    public ConsoleItemWriter writer() {
        return new ConsoleItemWriter();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     JdbcCursorItemReader<Item> reader,
                     APIItemProcessor processor,
                     ConsoleItemWriter writer) {
        return stepBuilderFactory.get("step")
                .<Item, Item>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step) {
        return jobBuilderFactory.get("importItemJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }
}

