package com.soez.mtc.article.controller;

import com.soez.mtc.article.dto.ArticlePublishDto;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.dto.ArticleViewListDto;
import com.soez.mtc.evaluate.dto.EvaluateStatisticsDto;
import com.soez.mtc.filter.FirebaseTokenFilter;
import com.soez.mtc.hashtag.dto.HashtagDto;
import com.soez.mtc.hashtag.entity.HashtagEntity;
import com.soez.mtc.article.service.ArticleService;
import com.soez.mtc.hashtag.service.HashtagService;
import com.soez.mtc.evaluate.dto.EvaluateDto;
import com.soez.mtc.evaluate.service.EvaluateService;
import com.soez.mtc.recent.service.RecentViewService;
import com.soez.mtc.s3.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//http://localhost:8080/swagger-ui.html
@RestController
@RequestMapping("/article")
@Api(tags = {"게시물 API(모든 게시물 목록은 사용자가 차단한 유저의 게시물을 제외한 목록을 반환)"})
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final HashtagService hashtagService;
    private final RecentViewService recentViewService;
    private final EvaluateService evaluateService;
    private final S3Service s3Service;

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @ApiOperation(value = "유저가 작성한 게시물 목록 조회", notes = "사용자 인덱스(viewer)와 작성자 인덱스(writer)를 인자로 넘기면 작성자가 작성한 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/")
    public ResponseEntity<?> readArticlesByUserIndex(Long writer, Long viewer, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readArticlesByUserIndex(writer, viewer, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 생성 및 수정", notes = "작성자 인덱스(user), 제목(title), 해시태그, 공유 여부, 사진 파일을 인자로 받는다.")
    @PostMapping("/")
    public ResponseEntity<?> createArticle(@ModelAttribute ArticlePublishDto articlePublishDto, MultipartFile multipartFile) {
        String url = "";

        try {
            if(articlePublishDto.getArticle() == null && !multipartFile.isEmpty()){
                url = s3Service.uploadFile(multipartFile, "article");
            }
            return new ResponseEntity<ArticleViewDto>(articleService.createArticle(articlePublishDto, url), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "게시물 인덱스를 통한 게시물 조회", notes = "사용자(user)와 게시물 인덱스(article)을 넘기면 해당 게시물의 정보를 반환한다.")
    @GetMapping("/{article}")
    public ResponseEntity<?> readArticlesByArticleIndex(@RequestParam("user") Long userIndex, @PathVariable("article") Long articleIndex) {
        return new ResponseEntity<ArticleViewDto>(articleService.readArticleByArticleIndex(userIndex, articleIndex), HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 삭제", notes = "작성자 인덱스(user)와 게시물 인덱스(article)를 인자로 받아 해당 게시물을 삭제한다.")
    @DeleteMapping("/{article}")
    public ResponseEntity<?> deleteArticle(@RequestParam("user") Long userIndex, @PathVariable("article") Long articleIndex) {
        try{
            if(articleService.deleteArticle(userIndex, articleIndex)){
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @ApiOperation(value = "평가한 게시물 목록 조회", notes = "사용자 인덱스(user)를 인자로 받아 해당 유저가 평가한 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/evaluate")
    public ResponseEntity<?> readEvaluateArticlesByUserIndex(@RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readEvaluateArticles(userIndex, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 평가", notes = "사용자 인덱스(user), 게시물 인덱스(article), 평가(evaluateValue)를 받아 사용자가 해당 게시물을 평가한 정보를 저장한다.")
    @PostMapping("/evaluate")
    public ResponseEntity<?> createEvaluate(@ModelAttribute EvaluateDto evaluateDto) {
        if(evaluateService.createEvaluate(evaluateDto)){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "팔로우한 게시물 목록 조회", notes = "사용자 인덱스(user)를 인자로 넘기면 해당 사용자가 팔로우한 사람들이 업로드한 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/follow")
    public ResponseEntity<?> readFollowingArticles(@RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readFollowingArticles(userIndex, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "키워드가 포함된 해시태그 목록 조회", notes = "키워드(keyword)를 인자로 보내면 키워드가 포함된 해시태그 목록을 반환한다.")
    @GetMapping("/hashtag")
    public ResponseEntity<?> readHashtagsByKeyword(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<List<HashtagDto>>(hashtagService.readHashtagEntitiesByTagname(keyword), HttpStatus.OK);
    }

    @ApiOperation(value = "인기 게시물 목록 조회", notes = "사용자 인덱스(user)를 인자로 받아 인기 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/hot")
    public ResponseEntity<?> readHotArticles(@RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readHotArticles(userIndex, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "메인 게시물 목록 조회", notes = "사용자 인덱스(user)를 인자로 받아 해당 사용자에게 추천할 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/main")
    public ResponseEntity<?> readMainArticles(@RequestParam("user") Long userIndex, @RequestParam(required = false) String page, @RequestParam(required = false) String size) {
        try {
            return new ResponseEntity<List<ArticleViewDto>>(articleService.readRecommendArticles(userIndex, page, size), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "최근 본 게시물 조회", notes = "사용자 인덱스(user)를 인자로 넘기면 해당 유저가 최근에 본 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/recent")
    public ResponseEntity<?> readRecentArticlesByUserIndex(@RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readRecentArticles(userIndex, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "최근 본 게시물 추가", notes = "사용자 인덱스(user)와 게시물 인덱스(article)를 인자로 넘기면 사용자가 해당 게시물을 봤다는 정보를 저장하며 해당 게시물의 조회수를 1 증가시킨다.")
    @PostMapping("/recent")
    public ResponseEntity<?> createRecentView(@RequestParam("user") Long userIndex, @RequestParam("article") Long articleIndex) {
        if(recentViewService.createRecentView(userIndex, articleIndex)){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "해시태그로 검색한 게시물 목록 조회", notes = "사용자 인덱스(user)와 해시태그(hashtag)를 인자로 보내면 사용자가 해당 해시태그로 검색한 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/search/hashtag")
    public ResponseEntity<?> readArticlesByHashtag(@RequestParam String hashtag, @RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readArticlesByHashtag(userIndex, hashtag, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "검색어가 제목에 포함된 게시물 목록 조회", notes = "사용자 인덱스(user)와 키워드(keyword)를 인자로 보내면 사용자가 해당 키워드로 검색한 게시물 목록을 반환한다.\n page와 size를 인자로 넘기면 페이징 처리된 목록을 반환한다. page는 0부터 시작하며 두 인자의 기본값은 page=0, size=20 이다.")
    @GetMapping("/search/title")
    public ResponseEntity<?> readArticlesByKeyword(@RequestParam("keyword") String keyword,@RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readArticlesByArticleTitle(userIndex, keyword, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "유저가 받은 평가 수 조회", notes = "유저가 올린 게시물이 받은 총 평가 수를 반환한다.")
    @GetMapping("/statistics")
    public ResponseEntity<?> readStatistics(@RequestParam("user") Long userIndex){
        return new ResponseEntity<EvaluateStatisticsDto>(evaluateService.getEvaluateStatistics(userIndex), HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 최신순 조회", notes = "최근에 업로드된 게시물 목록을 반환한다.")
    @GetMapping("/list")
    public ResponseEntity<?> readArticles(@RequestParam("user") Long userIndex, Pageable pageable) {
        return new ResponseEntity<ArticleViewListDto>(articleService.readArticles(userIndex, pageable), HttpStatus.OK);
    }
}
