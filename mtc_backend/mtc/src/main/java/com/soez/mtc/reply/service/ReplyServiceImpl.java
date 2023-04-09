package com.soez.mtc.reply.service;

import com.soez.mtc.alarm.dto.AlarmInputDto;
import com.soez.mtc.alarm.entity.AlarmReplyEntity;
import com.soez.mtc.alarm.repository.AlarmReplyJpaRepository;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.article.repository.ArticleRepository;
import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.comment.repository.CommentJpaRepository;
import com.soez.mtc.reply.dto.ReplyPublishDto;
import com.soez.mtc.reply.dto.ReplyViewDto;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.reply.repository.ReplyJpaRepository;
import com.soez.mtc.reply.repository.ReplyPagingRepository;
import com.soez.mtc.reply.repository.ReplyRepository;
import com.soez.mtc.replylike.dto.ReplyLikePublishDto;
import com.soez.mtc.replylike.entity.ReplyLikeEntity;
import com.soez.mtc.replylike.repository.ReplyLikeJpaRepository;
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
import java.util.List;
@Transactional
@Service
public class ReplyServiceImpl implements ReplyService{
    @Autowired
    private ReplyJpaRepository replyJpaRepository;

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ReplyLikeJpaRepository replyLikeJpaRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private SettingJpaRepository settingJpaRepository;
    @Autowired
    private AlarmReplyJpaRepository alarmReplyJpaRepository;
    @Autowired
    private ReplyPagingRepository replyPagingRepository;
    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public ReplyEntity createReply(ReplyPublishDto replyPublishDto) {
        ReplyEntity replyEntity= ReplyEntity.builder()
                .replyRegistTime(LocalDateTime.now())
                .replyContent(replyPublishDto.getReplyContent())
                .replyCommentIndex(commentJpaRepository.findCommentEntityByCommentIndex(replyPublishDto.getReplyCommentIndex()))
                .replyUserIndex(userJpaRepository.findUserEntityByUserIndex(replyPublishDto.getReplyUserIndex()))
                .build();


        ReplyEntity replyEntityAlarm = replyJpaRepository.save(replyEntity);
        createReplyAlarm(replyEntityAlarm.getReplyIndex());
        return replyEntityAlarm;
    }

    @Override
    public ReplyViewDto readReplyByReplyIndex(Long replyIndex) {
        ReplyEntity replyEntity= replyRepository.readReplyEntityByReplyIndex(replyIndex);

        ReplyViewDto replyViewDto= replyRepository.getReplyViewDtoByReplyEntity(replyEntity);

        return replyViewDto;
    }

    @Override
    public List<ReplyViewDto> readReplyByCommentIndex(Long commentIndex) {
        List<ReplyEntity> replyEntities= replyRepository.readReplyEntitiesByCommentIndex(commentIndex);
        List<ReplyViewDto> replyViewDtos=new ArrayList<>();
        for (ReplyEntity replyEntity: replyEntities){
            ReplyViewDto replyViewDto = replyRepository.getReplyViewDtoByReplyEntity(replyEntity);
            UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(replyEntity.getReplyUserIndex().getUserIndex());
            replyViewDto.setReplyUserPictureSource(userEntity.getUserPictureSource());
            replyViewDtos.add(replyViewDto);
        }
        return replyViewDtos;
    }


    @Override
    public List<ReplyViewDto> readReplyByUserIndex(Long userIndex) {
        List<ReplyEntity> replyEntities=replyRepository.readReplyEntitiesByUserIndex(userIndex);
        List<ReplyViewDto> replyViewDtos=new ArrayList<>();
        for (int i=0;i<replyEntities.size();i++){
            replyViewDtos.add(replyRepository.getReplyViewDtoByReplyEntity(replyEntities.get(i)));
        }
        return replyViewDtos;
    }

    @Override
    public void deleteReplyByReplyIndex(Long replyIndex) {
        replyJpaRepository.deleteByReplyIndex(replyIndex);
    }

    @Override
    public ReplyLikeEntity createReplyLike(ReplyLikePublishDto replyLikePublishDto) {
        ReplyLikeEntity check=replyLikeJpaRepository.findReplyLikeEntityByReplyLikeUserIndexAndReplyLikeReplyIndex(userJpaRepository.findUserEntityByUserIndex(replyLikePublishDto.getReplyLikeUserIndex()),replyRepository.readReplyEntityByReplyIndex(replyLikePublishDto.getReplyLikeReplyIndex()));
        if(check!=null){//이미 좋아요 관계가 있으면
            //업데이트 합니다.(새로 등록 안함)
            check.setReplyLikeValuate(replyLikePublishDto.getReplyLikeValuate());
            return check;
        }

        else{
            ReplyLikeEntity replyLikeEntity=ReplyLikeEntity.builder()
                    .replyLikeReplyIndex(replyRepository.readReplyEntityByReplyIndex(replyLikePublishDto.getReplyLikeReplyIndex()))
                    .replyLikeUserIndex(userJpaRepository.findUserEntityByUserIndex(replyLikePublishDto.getReplyLikeUserIndex()))
                    .replyLikeValuate(replyLikePublishDto.getReplyLikeValuate())
                    .build();

            return replyLikeJpaRepository.save(replyLikeEntity);
        }
    }

    @Override
    public Integer findReplyValuateByReplyIndexAndUserIndex(Long replyIndex, Long userIndex){
        ReplyEntity replyEntity=replyJpaRepository.findByReplyIndex(replyIndex);
        UserEntity userEntity=userJpaRepository.findUserEntityByUserIndex(userIndex);
        Integer replyValuate=0;
        try {
            replyValuate=replyLikeJpaRepository.findReplyLikeEntityByReplyLikeUserIndexAndReplyLikeReplyIndex(userEntity,replyEntity).getReplyLikeValuate();
        }
        catch (NullPointerException e){
            return replyValuate;
        }

//        System.out.println("replyValuate = " + replyValuate);
        return replyValuate;
    }

    @Override
    public Page<ReplyViewDto> readReplyByCommentIndexWithPaging(Long commentIndex, int page, int size) {
        Page<ReplyEntity> replyEntities=replyPagingRepository.findByReplyCommentIndex_CommentIndex(commentIndex, PageRequest.of(page,size, Sort.by("replyIndex").descending()));
        List<ReplyViewDto> replyViewDtos=new ArrayList<>();
        for(ReplyEntity replyEntity: replyEntities){
            replyViewDtos.add(replyRepository.getReplyViewDtoByReplyEntity(replyEntity));
        }
        return new PageImpl<>(replyViewDtos,replyEntities.getPageable(),replyEntities.getTotalElements());
    }

    @Override
    public ArticleViewDto findArticleViewDtoByReplyIndex(Long replyIndex) {
        ReplyEntity replyEntity=replyJpaRepository.findByReplyIndex(replyIndex);
        CommentEntity commentEntity=replyEntity.getReplyCommentIndex();
        ArticleEntity articleEntity=commentEntity.getCommentArticleIndex();

        ArticleViewDto articleViewDto=articleRepository.getArticleViewDto(articleEntity.getArticleUserIndex().getUserIndex(),articleEntity.getArticleIndex());

        return articleViewDto;
    }

    public void createReplyAlarm(Long replyIndex) {

        //답글 확인
        //답글에 있는 댓글을 불러옴
        ReplyEntity replyEntity = replyJpaRepository.findById(replyIndex).get();
        //댓글에 있는 유저를 불러옴
        CommentEntity commentEntity = commentJpaRepository.findCommentEntityByCommentIndex(replyEntity.getReplyCommentIndex().getCommentIndex());
        //본인이면 return null;
        if(replyEntity.getReplyUserIndex()==commentEntity.getCommentUserIndex()) return;

        //그 유저의 셋팅을 확인하기 위해 셋팅을 불러옴
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(commentEntity.getCommentUserIndex().getUserIndex());
        //세팅 확인 //만약 거부해 놓는거면 안보낼거임;
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        if(settingEntity.getSettingIgnoreReply()) return;
        else{


            AlarmReplyEntity alarmReplyEntity = new AlarmReplyEntity();
            alarmReplyEntity.setAlarmUserIndex(userEntity);
            alarmReplyEntity.setAlarmReplyIndex(replyEntity); //답글 index
            alarmReplyEntity.setAlarmTime(LocalDateTime.now());
            alarmReplyEntity.setAlarmCheck(Boolean.FALSE);

            alarmReplyJpaRepository.save(alarmReplyEntity);
            //output을 위한 followDto 생성
            AlarmInputDto alarmInputDto = AlarmInputDto.builder().
                    alarmUserIndex(userEntity.getUserIndex()).
                    alarmDtype("R").
                    alarmTime(alarmReplyEntity.getAlarmTime()).
                    alarmReplyIndex(alarmReplyEntity.getAlarmReplyIndex().getReplyIndex()).
                    build();

        }
    }
}
