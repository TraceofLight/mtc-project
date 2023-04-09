package com.soez.mtc.article.dto;

import com.soez.mtc.user.entity.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArticlePublishDto {

    private Long article;  // 게시물 인덱스. 게시물 수정 시에는 이 필드에 게시물 인덱스를 포함시켜야 함
    private Long user;  // 게시물 작성자 인덱스
    private String title;    // 게시물 제목
    private boolean visible; // 게시물 공유 여부
    private String[] hashtagList;   // 게시물에 연결된 해시태그 목록

    @Builder
    public ArticlePublishDto(Long article, Long user, String title, boolean visible, String[] hashtagList) {
        this.article = article;
        this.user = user;
        this.title = title;
        this.visible = visible;
        this.hashtagList = hashtagList;
    }
}
