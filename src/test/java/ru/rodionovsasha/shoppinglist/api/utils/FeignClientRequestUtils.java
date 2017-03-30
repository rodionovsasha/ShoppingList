package ru.rodionovsasha.shoppinglist.api.utils;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.rodionovsasha.shoppinglist.dto.ItemDto;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class FeignClientRequestUtils {
    @Value("${api.test.url}")
    private String apiTestUrl;

    private ItemFeignClient itemFeignClient;

    @PostConstruct
    public void init() {
        itemFeignClient = Feign
                .builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ItemFeignClient.class, apiTestUrl);
    }

    public ItemDto getItem(Long id) {
        return itemFeignClient.getItem(id);
    }
}