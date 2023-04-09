package com.soez.mtc.article.service;

import com.soez.mtc.article.controller.ArticleController;
import com.soez.mtc.article.dto.ArticlePublishDto;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.dto.ArticleViewListDto;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.hashtag.entity.HashtagEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.article.repository.ArticleRepository;
import com.soez.mtc.hashtag.repository.HashtagJpaRepository;
import com.soez.mtc.hashtagging.entity.HashtaggingEntity;
import com.soez.mtc.hashtagging.repository.HashtaggingJpaRepository;
import com.soez.mtc.relation.dto.BlockUserDto;
import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.repository.BlockJpaRepository;
import com.soez.mtc.relation.service.RelationService;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleJpaRepository articleJpaRepository;
    private final ArticleRepository articleRepository;
    private final ServletContext servletContext;
    private final HashtagJpaRepository hashtagJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final HashtaggingJpaRepository hashtaggingJpaRepository;
    private final BlockJpaRepository blockJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);


    /**
     * writer == viewer 인 경우 공개 여부(visible)에 상관 없이 전체 게시물 목록 반환
     * writer != viewer 인 경우 공개로 설정된 게시물 목록 반환
     * @param writer
     * @param viewer
     * @param pageable
     * @return ArticleViewListDto
     */
    @Override
    public ArticleViewListDto readArticlesByUserIndex(Long writer, Long viewer, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(writer);
        Page<ArticleEntity> articleEntities;

        // 게시물을 목록을 보는 사람이 작성자인 경우 공개 여부에 상관 없이 전체 게시물 목록 반환
        if(writer == viewer)
            articleEntities = articleJpaRepository.findAllByArticleUserIndexOrderByArticleRegistTimeDesc(userEntity, pageable);
            // 작성자가 아닌 사람이 게시물 목록을 보는 경우 공개로 설정된 게시물만 볼 수 있음
        else
            articleEntities = articleJpaRepository.findAllByArticleUserIndexAndArticleVisibleOrderByArticleRegistTimeDesc(userEntity, true, pageable);

        List<ArticleViewDto> articleViewDtoList = new ArrayList<>();
        for(ArticleEntity articleEntity : articleEntities) {
            articleViewDtoList.add(articleRepository.getArticleViewDto(viewer, articleEntity.getArticleIndex()));
        }
        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(articleEntities.getTotalPages())
                .build();
    }

    /**
     * 인자로 받은 게시물 인덱스로 게시물을 조회한다.
     * 게시물이 존재한다면 수정에 대한 로직을 수행하고
     * 게시물이 존재하지 않는다면 새로운 게시물을 생성하는 로직을 수행한다.
     * @param articlePublishDto
     * @param url
     * @return ArticleViewDto
     * @throws IOException
     */
    @Override
    @Transactional
    public ArticleViewDto createArticle(ArticlePublishDto articlePublishDto, String url) throws IOException, URISyntaxException {
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(articlePublishDto.getArticle());
        // 게시물 생성 로직
        if (articleEntity == null) {
            // 요청으로 받은 게시물 DTO를 게시물 엔티티로 변환
            articleEntity = ArticleEntity.builder()
                    .articleUserIndex(userJpaRepository.findUserEntityByUserIndex(articlePublishDto.getUser()))
                    .articleTitle(articlePublishDto.getTitle())
                    .articlePictureSource(url)
                    .articleVisible(articlePublishDto.isVisible())
                    .build();

            articleEntity = articleJpaRepository.save(articleEntity);
        }
        // 게시물 수정 로직
        else {
            // 수정. 게시물 공유 여부 변경 및 아래에서 해시태그 추가하기 위해 기존에 있던 해시태그 정보 삭제
            boolean prevVisible = articleEntity.isArticleVisible();
            boolean nextvisible = articlePublishDto.isVisible();
            articleEntity.setArticleVisible(nextvisible);
            hashtaggingJpaRepository.deleteHashtaggingEntitiesByHashtaggingArticleIndex(articleEntity);

            // 공개 상태의 게시물을 비공개로 전환하는 경우 미리 계산해놓은 추천 목록에서 삭제
            if(prevVisible && !nextvisible){
                updateRecommendList(articlePublishDto.getArticle());
            }
        }

        // 입력으로 들어온 해시태그 리스트를 돌며 데이터베이스에 해시태그가 없다면 해시태그 새로 저장
        String[] hashtagList = articlePublishDto.getHashtagList();
        if(hashtagList != null){
            for (String hashtag : hashtagList) {
                hashtag = hashtag.replaceAll(" ","");
                if(hashtag.isBlank())   continue;
                HashtagEntity hashtagEntity = hashtagJpaRepository.findHashtagEntityByTagname(hashtag);
                if(hashtagEntity == null){
                    hashtagEntity = hashtagJpaRepository.save(new HashtagEntity(hashtag));
                }
                HashtaggingEntity hashtaggingEntity = HashtaggingEntity.builder()
                        .hashtaggingArticleIndex(articleEntity)
                        .hashtaggingTagname(hashtagEntity)
                        .build();
                hashtaggingJpaRepository.save(hashtaggingEntity);
            }
        }

        // DTO에 응답 값 담기
        return articleRepository.getArticleViewDto(articleEntity.getArticleUserIndex().getUserIndex(), articleEntity.getArticleIndex());
    }

    private static void updateRecommendList(Long articleIndex) throws URISyntaxException, IOException {
        URI uri = new URI("http://i8a710.p.ssafy.io:5000/update");
        uri = new URIBuilder(uri).addParameter("article", articleIndex.toString()).build();

        HttpClient httpClient = HttpClientBuilder.create().build();
        httpClient.execute(new HttpGet(uri));
    }

    /**
     * 인자로 받은 게시물 인덱스에 해당하는 게시물 DTO를 반환한다.
     * @param userIndex
     * @param articleIndex
     * @return ArticleViewDto
     */
    @Override
    public ArticleViewDto readArticleByArticleIndex(Long userIndex, Long articleIndex) {
        return articleRepository.getArticleViewDto(userIndex, articleIndex);
    }

    /**
     * 게시물 인덱스를 인자로 받아 먼저 해당 게시물을 조회한다.
     * 게시물이 존재하고 그 게시물의 작성자가 인자로 받은 userIndex와 같으면 게시물 삭제 로직을 실행한다.
     * 게시물이 존재하지 않거나(articleEntity == null) 게시물의 작성자가 userIndex와 다르다면 삭제 로직을 실행하지 않는다.
     * 삭제 로직이 실행되면 true를, 실행되지 않으면 false를 반환한다.
     * @param userIndex
     * @param articleIndex
     * @return boolean
     */
    @Override
    @Transactional
    public boolean deleteArticle(Long userIndex, Long articleIndex) throws URISyntaxException, IOException {
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(articleIndex);
        if(articleEntity == null)   {
            return false;
        }
        if (articleEntity.getArticleUserIndex().getUserIndex() == userIndex) {
            updateRecommendList(articleIndex);
            articleJpaRepository.deleteArticleEntityByArticleIndex(articleIndex);
            return true;
        }
        return false;
    }

    /**
     * 사용자 인덱스(userIndex)를 인자로 받아 해당 사용자가 평가한 게시물의 목록을 반환한다.
     * @param userIndex
     * @param pageable
     * @return ArticleViewListDto
     */
    @Override
    public ArticleViewListDto readEvaluateArticles(Long userIndex, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<EvaluateEntity> evaluateArticleEntities = articleJpaRepository.findEvaluateArticleEntities(userEntity, pageable);

        List<ArticleViewDto> articleViewDtoList = evaluateArticleEntities
                .stream()
                .map(evaluateEntity -> articleRepository.getArticleViewDto(userIndex, evaluateEntity.getEvaluateArticleIndex().getArticleIndex()))
                .collect(Collectors.toList());

        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(evaluateArticleEntities.getTotalPages())
                .build();
    }

    /**
     * 유저 인덱스를 인자로 받아 해당 유저가 팔로우한 유저들의 게시물을 조회한다.
     * @param userIndex
     * @return List<ArticleViewDto>
     */
    @Override
    public ArticleViewListDto readFollowingArticles(Long userIndex, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<ArticleEntity> articleEntities = articleJpaRepository.findFollowingArticleEntities(userEntity, pageable);

        List<ArticleViewDto> articleViewDtoList = articleEntities
                .stream()
                .map(articleEntity -> articleRepository.getArticleViewDto(userIndex, articleEntity.getArticleIndex()))
                .collect(Collectors.toList());

        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(articleEntities.getTotalPages())
                .build();
    }

    /**
     * 유저 인덱스를 받아 해당 유저가 차단한 유저의 게시물을 제외한 인기 게시물을 반환한다.
     * @param userIndex
     * @return
     */
    @Override
    public ArticleViewListDto readHotArticles(Long userIndex, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<ArticleEntity> articleEntities = articleJpaRepository.findArticleEntitiesOrderByGoood(userEntity, pageable);

        List<ArticleViewDto> articleViewDtoList = articleEntities
                .stream()
                .map(articleEntity -> articleRepository.getArticleViewDto(userIndex, articleEntity.getArticleIndex()))
                .collect(Collectors.toList());

        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(articleEntities.getTotalPages())
                .build();
    }

    @Override
    public ArticleViewListDto readArticles(Long userIndex, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<ArticleEntity> articleEntities = articleJpaRepository.findArticles(userEntity, pageable);

        List<ArticleViewDto> articleViewDtoList = articleEntities
                .stream()
                .map(articleEntity -> articleRepository.getArticleViewDto(userIndex, articleEntity.getArticleIndex()))
                .collect(Collectors.toList());

        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(articleEntities.getTotalPages())
                .build();
    }

    /**
     * 유저 인덱스를 인자로 받아 플라스크 서버로 해당 유저에게 추천할 게시물 목록을 얻어오고
     * 얻어온 목록을 DTO로 바꾸어 변환한다.
     * @param userIndex
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public List<ArticleViewDto> readRecommendArticles(Long userIndex, String page, String size) throws URISyntaxException, IOException {

        if (page == null) page = "null";
        if (size == null) size = "null";

        URI uri = new URI("http://i8a710.p.ssafy.io:5000/recommend");
        uri = new URIBuilder(uri).addParameter("user", userIndex.toString()).addParameter("page", page).addParameter("size", size).build();


        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(new HttpGet(uri));

        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);

        String[] recommendList = content.split("\n");
        logger.info("recommend : {}", recommendList);

        List<ArticleViewDto> articleViewDtoList = new ArrayList<>();

        for (int i = 1; i < recommendList.length - 1; i++) {
            Long articleIndex = Long.parseLong(recommendList[i].replaceAll("^\\s+","").replaceAll(",$", ""));
            articleViewDtoList.add(articleRepository.getArticleViewDto(userIndex, articleIndex));
        }

        return articleViewDtoList;
    }

    /**
     * userIndex를 인자로 받아 해당 유저가 최근 본 게시물 목록을 최신순으로 반환한다.
     * @param userIndex
     * @return  List<ArticleViewDto>
     */
    @Override
    public ArticleViewListDto readRecentArticles(Long userIndex, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<ArticleEntity> recentArticleEntities = articleJpaRepository.findRecentArticleEntities(userEntity, pageable);

        List<ArticleViewDto> articleViewDtoList = recentArticleEntities
                .stream()
                .map(articleEntity -> articleRepository.getArticleViewDto(userIndex, articleEntity.getArticleIndex()))
                .collect(Collectors.toList());
        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(recentArticleEntities.getTotalPages())
                .build();
    }

    /**
     * 해시태그를 인자로 받고 해당 해시태그를 가지고있는 게시물 목록을 반환한다.
     * @param hashtag
     * @return List<ArticleViewDto>
     */
    @Override
    public ArticleViewListDto readArticlesByHashtag(Long userIndex, String hashtag, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        HashtagEntity hashtagEntity = hashtagJpaRepository.findHashtagEntityByTagname(hashtag);
        Page<HashtaggingEntity> hashtaggingEntities = hashtaggingJpaRepository.findHashtaggingEntitiesByHashtaggingTagname(hashtagEntity, userEntity, pageable);

        List<ArticleViewDto> articleViewDtoList = hashtaggingEntities
                .stream()
                .map(hashtaggingEntity -> articleRepository.getArticleViewDto(userIndex, hashtaggingEntity.getHashtaggingArticleIndex().getArticleIndex()))
                .collect(Collectors.toList());

        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(hashtaggingEntities.getTotalPages())
                .build();
    }

    /**
     * keyword를 인자로 받고 제목에 keyword가 포함된 게시물 목록을 반환
     * @param keyword
     * @return  List<ArticleViewDto>
     */
    @Override
    public ArticleViewListDto readArticlesByArticleTitle(Long userIndex, String keyword, Pageable pageable) {
        Page<ArticleEntity> articleEntities = articleJpaRepository.findArticleEntitiesByArticleTitle(keyword, pageable);

        // 차단 유저 목록 받아오기
        List<BlockEntity> blockEntities = blockJpaRepository.userBlockList(userJpaRepository.findUserEntityByUserIndex(userIndex));
        List<UserEntity> targetUserEntities = new ArrayList<>();
        for (BlockEntity blockEntity : blockEntities) {
            targetUserEntities.add(blockEntity.getBlockTargetIndex());
        }

        List<ArticleViewDto> articleViewDtoList = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntities) {
            if (targetUserEntities.contains(articleEntity.getArticleUserIndex()))
                continue;
            articleViewDtoList.add(articleRepository.getArticleViewDto(userIndex, articleEntity.getArticleIndex()));
        }
        return ArticleViewListDto.builder()
                .articleViewDtoList(articleViewDtoList)
                .totalPage(articleEntities.getTotalPages())
                .build();
    }


}
