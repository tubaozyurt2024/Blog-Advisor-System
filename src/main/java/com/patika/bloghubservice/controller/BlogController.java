package com.patika.bloghubservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.patika.bloghubservice.client.advisor.AdvisorService;
import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.dto.BlogAdvise;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@Slf4j
public class BlogController {
    private final BlogService blogService;
    private final AdvisorService advisorService;
    private final OpenAiChatModel openAiChatModel;
    @PostMapping("/users/{email}")
    public GenericResponse<BlogResponse> createBlog(@RequestBody BlogSaveRequest request, @PathVariable String email) {
        return GenericResponse.success(blogService.createBlog(email, request), HttpStatus.CREATED);
    }
    @GetMapping
    public GenericResponse<List<Blog>> getAllBlogs() {
        return GenericResponse.success(blogService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{title}")
    public Blog getBlogByTitle(@PathVariable String title) {
        return blogService.getBlogByTitle(title);
    }

    @PutMapping("/{title}/users/{email}")
    public void addComment(@PathVariable String title, @PathVariable String email, @RequestBody String comment) {
        blogService.addComment(title, email, comment);
    }
    @PutMapping("/{title}/users/{email}/like-count")
    public void likeBlog(@PathVariable String title, @PathVariable String email) {
        //bir kullanıcı sadece maksimum 50 kere beğenebilir
        blogService.likeBlog(title, email);
    }
    @GetMapping("/{title}/like-count")
    public Long getLikeCountByTitle(@PathVariable String title) {
        return blogService.getLikeCountByTitle(title);
    }


    @GetMapping("/advise/{prop}")
    public String chat(@PathVariable String prop) {
        List<BlogAdvise> blogs = blogService.getAll().stream().map(BlogConverter::toResponseAdvise).toList();

        String blogsData = blogs.stream().map(blog -> String.join("" , " { title :  " + blog.getTitle())
                + " , " + String.join(", " , " text : "+blog.getText() + " } , "))
                .collect(Collectors.joining());

        String prompt = "Bu bloglar içerisinden ; " + blogsData
                + " . bu bloglar içerisinden doğa, yazılım konularını okuyan birinin ilgisini çekebilecek yazıları kapsayan verileri json olarak geri dön."
                + " json da sadece title ve text field ları olsun"
                + " json ın başına herhangi bir field koyma" ;

        log.info(prompt);
        String response  = openAiChatModel.call(prop);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<BlogAdvise> blogPosts = objectMapper.readValue(prompt, new TypeReference<List<BlogAdvise>>() {});
            blogPosts.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
