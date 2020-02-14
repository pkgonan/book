package io.book.search.service;

import lombok.Data;

import java.util.List;

@Data
public class DocumentDto {
    private String title;
    private String description;
    private String url;
    private String isbn;
    private String publishAt;
    private List<String> authors;
    private String publisher;
    private List<String> translators;
    private Integer price;
    private Integer salePrice;
    private String thumbnail;
    private String status;
}