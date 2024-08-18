package com.patika.bloghubservice.client.advisor;

import com.patika.bloghubservice.dto.BlogAdvise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "advice-service", url = "localhost:8070/advice")
public interface Advisor {
    @GetMapping
    String advise(List<BlogAdvise> blogs);
}
