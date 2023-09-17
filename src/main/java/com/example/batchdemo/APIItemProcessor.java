package com.example.batchdemo;
import com.example.batchdemo.Item;
//import com.example.batchdemo.model.Item;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class APIItemProcessor implements ItemProcessor<Item, Item> {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Item process(Item item) {
        ResponseEntity<String> response = restTemplate.getForEntity("https://dummyjson.com/products/1", String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String apiResponse = response.getBody();
            item.setData(item.getData() + "-" + apiResponse);
            return item;
        } else {
            System.out.println("API call for item " + item.getId() + " was not successful. Status code: " + response.getStatusCode());
            return null;
        }
    }
}
