package com.soez.mtc.recent.service;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.recent.entity.RecentViewEntity;
import com.soez.mtc.recent.repository.RecentViewJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RecentViewServiceImpl implements RecentViewService{

    private final UserJpaRepository userJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final RecentViewJpaRepository recentViewJpaRepository;


    /**
     * userIndex와 articleIndex를 인자로 받아 유저가 게시물을 조회한 기록과 조회한 시간을 저장한다.
     * 해당 게시물의 조회수를 1 증가시킨다.
     * 만약 유저가 해당 게시물을 전에 조회한 적이 있다면 조회한 시간을 업데이트하고
     * 조회한 적이 없다면 조회했다는 기록을 새로 저장한다.
     * @param userIndex
     * @param articleIndex
     * @return  RecentViewEntity
     */
    @Override
    public boolean createRecentView(Long userIndex, Long articleIndex) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(articleIndex);
        if(articleEntity == null){
            return false;
        }
        articleEntity.setArticleHit(articleEntity.getArticleHit() + 1);

        RecentViewEntity recentViewEntity = recentViewJpaRepository.findRecentViewEntityByRecentViewUserIndexAndRecentViewArticleIndex(userEntity, articleEntity);

        if(recentViewEntity == null) {
            recentViewEntity = RecentViewEntity.builder()
                    .recentViewUserIndex(userEntity)
                    .recentViewArticleIndex(articleEntity)
                    .recentViewTime(LocalDateTime.now())
                    .build();
            recentViewJpaRepository.save(recentViewEntity);
        }
        else{
            recentViewEntity.setRecentViewTime(LocalDateTime.now());
        }
        return true;
    }
}
