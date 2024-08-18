package com.patika.bloghubservice.client.advisor;

import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AdvisorService {
    private final Advisor advisor;
    private final BlogService blogService;
    public String getAdvice(){
        return advisor.advise(blogService.getAll().stream().map(BlogConverter::toResponseAdvise).toList());
    }
}
