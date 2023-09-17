package com.example.batchdemo;


import com.example.batchdemo.Item;
import com.example.batchdemo.ConsoleItemWriter;
import com.example.batchdemo.BatchConfig;
import com.example.batchdemo.APIItemProcessor;
//import com.example.batchdemo.model.Item;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter implements ItemWriter<Item> {
    @Override
    public void write(List<? extends Item> items) {
        for (Item item : items) {
            System.out.println(item);
        }
    }
}

