package com.soez.mtc.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ArticleViewListDto {
    List<ArticleViewDto> articleViewDtoList;
    int totalPage;
}
