package com.soez.mtc.comment.service;

import com.soez.mtc.alarm.dto.AlarmInputDto;
import com.soez.mtc.alarm.entity.AlarmCommentEntity;
import com.soez.mtc.alarm.repository.AlarmCommentJpaRepository;
import com.soez.mtc.alarm.service.AlarmService;
import com.soez.mtc.alarm.service.AlarmServiceimpl;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.article.repository.ArticleRepository;
import com.soez.mtc.comment.dto.CommentPublishDto;
import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.comment.repository.CommentJpaRepository;
import com.soez.mtc.comment.repository.CommentPagingRepository;
import com.soez.mtc.comment.repository.CommentRepository;
import com.soez.mtc.commentlike.dto.CommentLikePublishDto;
import com.soez.mtc.commentlike.entity.CommentLikeEntity;
import com.soez.mtc.commentlike.repository.CommentLikeJpaRepository;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.repository.SettingJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentJpaRepository commentJpaRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ArticleJpaRepository articleJpaRepository;
    @Autowired
    private CommentLikeJpaRepository commentLikeJpaRepository;
    @Autowired
    private AlarmServiceimpl alarmServiceimpl;

    @Autowired
    private AlarmCommentJpaRepository alarmCommentJpaRepository;
    @Autowired
    private SettingJpaRepository settingJpaRepository;

    @Autowired
    private CommentPagingRepository commentPagingRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public CommentEntity createComment(CommentPublishDto commentPublishDto) {
        CommentEntity commentEntity=CommentEntity.builder()
                .commentUserIndex(userJpaRepository.findUserEntityByUserIndex(commentPublishDto.getCommentUserIndex()))
                .commentArticleIndex(articleJpaRepository.findArticleEntityByArticleIndex(commentPublishDto.getCommentArticleIndex()))
                .commentContent(commentPublishDto.getCommentContent())
                .commentRegistTime(LocalDateTime.now())
                .build();

        //알람과 아웃풋을 위한 커멘트 아웃풋 엔티티
        CommentEntity commentOutputEntity = commentJpaRepository.save(commentEntity);

        createCommentAlarm(commentOutputEntity.getCommentIndex());

        return commentOutputEntity;
    }

    public CommentViewDto readCommentByCommentIndex(Long commentIndex){
        CommentEntity commentEntity=commentJpaRepository.findCommentEntityByCommentIndex(commentIndex);
        CommentViewDto commentViewDto=commentRepository.getCommentViewDtoByCommentEntity(commentEntity);

        return commentViewDto;
    }

    public List<CommentViewDto> readCommentByUserIndex(Long userIndex){
        List<CommentEntity> commentEntities=commentJpaRepository.findAllByCommentUserIndex(userJpaRepository.findUserEntityByUserIndex(userIndex));
        List<CommentViewDto> commentViewDtos=new ArrayList<>();
        for(CommentEntity commentEntity: commentEntities){
            commentViewDtos.add(commentRepository.getCommentViewDtoByCommentEntity(commentEntity));
        }
        return commentViewDtos;
    }

    public List<CommentViewDto> readCommentByArticleIndex(Long articleIndex){
        List<CommentEntity> commentEntities=commentJpaRepository.findAllByCommentArticleIndex(articleJpaRepository.findArticleEntityByArticleIndex(articleIndex));
        List<CommentViewDto> commentViewDtos=new ArrayList<>();
        for(CommentEntity commentEntity: commentEntities){
            CommentViewDto commentViewDto = commentRepository.getCommentViewDtoByCommentEntity(commentEntity);
            UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(commentEntity.getCommentUserIndex().getUserIndex());
            commentViewDto.setCommentUserPictureSource(userEntity.getUserPictureSource());
            commentViewDtos.add(commentViewDto);
        }
        return commentViewDtos;
    }

    public void deleteCommentByCommentIndex(Long commentIndex){
        commentJpaRepository.deleteByCommentIndex(commentIndex);
    }

    @Override
    public CommentLikeEntity createCommentLike(CommentLikePublishDto commentLikePublishDto) {
        CommentLikeEntity commentLikeEntity=commentLikeJpaRepository.findCommentLikeEntityByCommentLikeCommentIndexAndCommentLikeUserIndex(commentJpaRepository.findCommentEntityByCommentIndex(commentLikePublishDto.getCommentLikeCommentIndex()),userJpaRepository.findUserEntityByUserIndex(commentLikePublishDto.getCommentLikeUserIndex()));
        if(commentLikeEntity!=null){
            //이미 좋아요나 싫어요가 있으면 업데이트
            commentLikeEntity.setCommentLikeValuate(commentLikePublishDto.getCommentLikeValuate());
            return commentLikeEntity;
        }
        else{
            commentLikeEntity=CommentLikeEntity.builder()
                    .commentLikeCommentIndex(commentJpaRepository.findCommentEntityByCommentIndex(commentLikePublishDto.getCommentLikeCommentIndex()))
                    .commentLikeUserIndex(userJpaRepository.findUserEntityByUserIndex(commentLikePublishDto.getCommentLikeUserIndex()))
                    .commentLikeValuate(commentLikePublishDto.getCommentLikeValuate())
                    .build();

            return commentLikeJpaRepository.save(commentLikeEntity);
        }
    }

    @Override
    public Integer readCommentLikeByUserIndexAndCommentIndex(Long userIndex, Long commentIndex) {
        UserEntity userEntity=userJpaRepository.findUserEntityByUserIndex(userIndex);
        CommentEntity commentEntity=commentJpaRepository.findCommentEntityByCommentIndex(commentIndex);
        try{
            CommentLikeEntity commentLikeEntity= commentLikeJpaRepository.findCommentLikeEntityByCommentLikeCommentIndexAndCommentLikeUserIndex(commentEntity,userEntity);
            return commentLikeEntity.getCommentLikeValuate();
        }
        catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Page<CommentViewDto> readCommentByArticleIndexWithPagingSortByLike(Long articleIndex, int page, int size) {
        List<CommentEntity> commentEntities=commentJpaRepository.findAllByCommentArticleIndex(articleJpaRepository.findArticleEntityByArticleIndex(articleIndex));
        List<CommentViewDto> commentViewDtos = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntities) {
            commentViewDtos.add(commentRepository.getCommentViewDtoByCommentEntity(commentEntity));
        }
        Collections.sort(commentViewDtos, new Comparator<CommentViewDto>() {
            @Override
            public int compare(CommentViewDto o1, CommentViewDto o2) {
                return o2.getGood().compareTo(o1.getGood());
            }
        });
        int start=page*size;
        if(start+1>commentViewDtos.size()){
            return null;
        }
        else if(start+size>=commentViewDtos.size()){
            Page<CommentViewDto> pages = new PageImpl<>(commentViewDtos.subList(start,commentViewDtos.size()));
            return pages;
        }
        Page<CommentViewDto> pages = new PageImpl<>(commentViewDtos.subList(start,start+size));
        return pages;
    }

    @Override
    public Page<CommentViewDto> readCommentByArticleIndexWithPaging(Long articleIndex, int page, int size) {
        Page<CommentEntity> commentEntities = commentPagingRepository.findByCommentArticleIndex_ArticleIndex(articleIndex, PageRequest.of(page, size, Sort.by("commentIndex").descending()));
        List<CommentViewDto> commentViewDtos = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntities) {
            commentViewDtos.add(commentRepository.getCommentViewDtoByCommentEntity(commentEntity));
        }
        return new PageImpl<>(commentViewDtos, commentEntities.getPageable(), commentEntities.getTotalElements());
    }

    @Override
    public ArticleViewDto readArticleViewDtoByCommentIndex(Long commentIndex) {
        CommentEntity commentEntity=commentJpaRepository.findCommentEntityByCommentIndex(commentIndex);
        ArticleEntity articleEntity=commentEntity.getCommentArticleIndex();
        ArticleViewDto articleViewDto=articleRepository.getArticleViewDto(articleEntity.getArticleUserIndex().getUserIndex(), articleEntity.getArticleIndex());
        return articleViewDto;
    }


    //댓글 알람=================================================================================
    public void createCommentAlarm(Long commentIndex) {

        //댓글 확인
        //댓글에 있는 아티클을 불러옴
        CommentEntity commentEntity = commentJpaRepository.findById(commentIndex).get();
        //아티클에 있는 유저를 불러옴
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(commentEntity.getCommentArticleIndex().getArticleIndex());
        //본인이면 return null
        if(commentEntity.getCommentUserIndex()==articleEntity.getArticleUserIndex()) return ;


        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(articleEntity.getArticleUserIndex().getUserIndex());
        //그 유저의 셋팅을 확인하기 위해 셋팅을 불러옴
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        //세팅 확인 //만약 거부해 놓는거면 안보낼거임;
        if(settingEntity.getSettingIgnoreComment()) return;
        else{


            AlarmCommentEntity alarmCommentEntity = new AlarmCommentEntity();
            alarmCommentEntity.setAlarmUserIndex(userEntity);
            alarmCommentEntity.setAlarmCommentIndex(commentEntity); //댓글 index
            alarmCommentEntity.setAlarmTime(LocalDateTime.now());
            alarmCommentEntity.setAlarmCheck(Boolean.FALSE);

            alarmCommentJpaRepository.save(alarmCommentEntity);

        }
    }


}
