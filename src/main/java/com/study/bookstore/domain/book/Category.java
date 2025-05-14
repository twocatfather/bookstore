package com.study.bookstore.domain.book;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Category {
    private String name;
    private String code;

    public static Category create(String name, String code) {
        return Category.builder()
                .name(name)
                .code(code)
                .build();
    }
}
