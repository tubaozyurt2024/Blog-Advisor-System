package com.patika.bloghubservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogAdvise {
    private String title;
    private String text;
    @Override
    public String toString() {
        return "BlogPost{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
