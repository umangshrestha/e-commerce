package com.ecommerce.demo.utils.customPage;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class CustomPage<T> {
    @JsonView(CustomPageView.Default.class)
    private List<T> data;
    @JsonView(CustomPageView.Default.class)
    private String nextPageUrl;
    @JsonView(CustomPageView.Default.class)
    private String lastPageUrl;
    @JsonView(CustomPageView.Default.class)
    private Long totalCount;

    public CustomPage(Page<T> page, String baseUrl) {
        this.data = page.getContent();
        this.totalCount = page.getTotalElements();
        if (page.hasPrevious())
            this.lastPageUrl = buildPageUrl(page.getNumber() - 1, page.getSize(), baseUrl);

        if (page.hasNext())
            this.nextPageUrl = buildPageUrl(page.getNumber() + 1, page.getSize(), baseUrl);
    }

    private String buildPageUrl(int page, int size, String baseUrl) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
                .build().toUriString();
    }

}
