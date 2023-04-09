package com.soez.mtc.alarm.service;

import com.soez.mtc.alarm.dto.*;
import com.soez.mtc.alarm.entity.*;
import com.soez.mtc.alarm.repository.*;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.comment.repository.CommentJpaRepository;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.relation.repository.FollowJpaRepository;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.reply.repository.ReplyJpaRepository;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.repository.SettingJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceimpl implements AlarmService {

    private final AlarmFollowJpaRepository alarmFollowJpaRepository;
    private final SettingJpaRepository settingJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FollowJpaRepository followJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final AlarmCommentJpaRepository alarmCommentJpaRepository;
    private final AlarmReplyJpaRepository alarmReplyJpaRepository;
    private final AlarmArticleJpaRepository alarmArticleJpaRepository;
    private final ReplyJpaRepository replyJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;
    private final AlarmPagingRepository alarmPagingRepository;

    @Override
    public List<AlarmDto> selectAlarm(Long userIndex) throws Exception {


        UserEntity userEntity = userJpaRepository.findById(userIndex).get();

        List<AlarmFollowEntity> alarmFollowEntities = alarmFollowJpaRepository.findAllByAlarmUserIndex(userEntity);
        List<AlarmCommentEntity> alarmCommentEntities = alarmCommentJpaRepository.findAllByAlarmUserIndex(userEntity);
        List<AlarmReplyEntity> alarmReplyEntities = alarmReplyJpaRepository.findAllByAlarmUserIndex(userEntity);
        List<AlarmArticleEntity> alarmArticleEntities = alarmArticleJpaRepository.findAllByAlarmUserIndex(userEntity);

        List<AlarmDto> alarmDtoList = new ArrayList<>();

        for(AlarmFollowEntity follow : alarmFollowEntities) {

            AlarmFollowDto alarmDto = new AlarmFollowDto();
            //알람 인덱스(정렬해야됨)
            alarmDto.setAlarmIndex(follow.getAlarmIndex());
            alarmDto.setAlarmTime(follow.getAlarmTime());
            alarmDto.setAlarmDtype("F");

            //나 팔로우한 애
            UserEntity userFollowEntity = userJpaRepository.findUserEntityByUserIndex(follow.getAlarmFollowUserIndex().getUserIndex());
            //나 팔로우한 애 인덱스
            alarmDto.setAlarmFollowUserIndex(userFollowEntity.getUserIndex());
            //나 팔로우한 애 닉네임
            alarmDto.setAlarmFollowUserNickname(userFollowEntity.getUserNickname());
            //나 팔로우한 애 사진

            alarmDto.setAlarmFollowUserPictureSource(userFollowEntity.getUserPictureSource());
            //알람 체크
            alarmDto.setAlarmCheck(follow.getAlarmCheck());
            alarmDtoList.add(alarmDto);

            follow.setAlarmCheck(Boolean.TRUE); //알람 DB에선 체크

        }
        for(AlarmCommentEntity comment : alarmCommentEntities) {
            AlarmCommentDto alarmDto = new AlarmCommentDto();
            alarmDto.setAlarmIndex(comment.getAlarmIndex());
            alarmDto.setAlarmTime(comment.getAlarmTime());
            alarmDto.setAlarmDtype("C");

            //댓글 엔티티
            CommentEntity commentEntity = commentJpaRepository.findCommentEntityByCommentIndex(comment.getAlarmCommentIndex().getCommentIndex());
            //댓글 내용
            alarmDto.setAlarmCommentContent(commentEntity.getCommentContent());
            //댓글 idx
            alarmDto.setAlarmCommentIndex(commentEntity.getCommentIndex());

            //댓글단 애 엔티티                                                                         //댓글의                  단 애의                인덱스
            UserEntity userCommentEntity = userJpaRepository.findUserEntityByUserIndex(comment.getAlarmCommentIndex().getCommentUserIndex().getUserIndex());
            //알람 쓴 애 index
            alarmDto.setAlarmCommentUserIndex(userCommentEntity.getUserIndex());
            //알람 쓴 애 nickname
            alarmDto.setAlarmCommentUserNickname(userCommentEntity.getUserNickname());
            //알람 쓴 애 사진
            alarmDto.setAlarmCommentUserPictureSource(userCommentEntity.getUserPictureSource());

            //아티클 엔티티
            ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(commentEntity.getCommentArticleIndex().getArticleIndex());
            
            //아티클 인덱스
            alarmDto.setAlarmCommentArticleIndex(articleEntity.getArticleIndex());
            //아티클 사진
            alarmDto.setAlarmCommentArticlePictureSource(articleEntity.getArticlePictureSource());

            alarmDto.setAlarmCheck(comment.getAlarmCheck());
            alarmDtoList.add(alarmDto);

            comment.setAlarmCheck(Boolean.TRUE); //알람 DB에선 체크
        }

        for(AlarmReplyEntity reply : alarmReplyEntities) {
            AlarmReplyDto alarmDto = new AlarmReplyDto();

            alarmDto.setAlarmIndex(reply.getAlarmIndex());
            alarmDto.setAlarmTime(reply.getAlarmTime());
            alarmDto.setAlarmDtype("R");
            alarmDto.setAlarmReplyIndex(reply.getAlarmReplyIndex().getReplyIndex());
            alarmDto.setAlarmCheck(reply.getAlarmCheck());

            //답글 내용, 답글 단 사람 인덱스,답글 단 사람 닉네임 댓글_index, 게시글_index,게시글 사진
            //답글 엔티티
            ReplyEntity replyEntity = replyJpaRepository.findByReplyIndex(reply.getAlarmReplyIndex().getReplyIndex());
            //답글 내용
            alarmDto.setAlarmReplyContent(replyEntity.getReplyContent());

            //댓글 엔티티
            CommentEntity commentEntity = commentJpaRepository.findById(replyEntity.getReplyCommentIndex().getCommentIndex()).get();
            //댓글 인덱스
            alarmDto.setAlarmReplyCommentIndex(commentEntity.getCommentIndex());
            //댓글 내용
            alarmDto.setAlarmReplyCommentContent(commentEntity.getCommentContent());

            //게시글 엔티티
            ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(commentEntity.getCommentArticleIndex().getArticleIndex());
            //게시글 인덱스
            alarmDto.setAlarmReplyArticleIndex(articleEntity.getArticleIndex());
            //게시글 사진
            alarmDto.setAlarmReplyArticlePictureSource(articleEntity.getArticlePictureSource());


            //답글 쓴 애 엔티티
            UserEntity userReplyEntity = userJpaRepository.findUserEntityByUserIndex(replyEntity.getReplyUserIndex().getUserIndex());
            //답글 쓴 애 인덱스
            alarmDto.setAlarmReplyUserIndex(userReplyEntity.getUserIndex());
            //답글 쓴 애 닉네임
            alarmDto.setAlarmReplyUserNickname(userReplyEntity.getUserNickname());
            //답글 쓴 애 사진
            alarmDto.setAlarmReplyUserPictureSource(userReplyEntity.getUserPictureSource());

            alarmDto.setAlarmCheck(reply.getAlarmCheck());
            alarmDtoList.add(alarmDto);

            reply.setAlarmCheck(Boolean.TRUE);
        }

        for(AlarmArticleEntity article : alarmArticleEntities) {

            AlarmArticleDto alarmDto = new AlarmArticleDto();

            //알람 인덱스(정렬해야됨)
            alarmDto.setAlarmIndex(article.getAlarmIndex());
            alarmDto.setAlarmTime(article.getAlarmTime());
            alarmDto.setAlarmDtype("A");

            //(어차피 내꺼니까) 게시글_index,게시글 사진, 추천개수
            //나
            UserEntity userArticleEntity = userJpaRepository.findUserEntityByUserIndex(article.getAlarmUserIndex().getUserIndex());

            //게시글 엔티티
            ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(article.getAlarmArticleIndex().getArticleIndex());

            //게시글 인덱스
            alarmDto.setAlarmArticleIndex(articleEntity.getArticleIndex());
            //게시글 사진
            alarmDto.setAlarmArticlePictureSource(articleEntity.getArticlePictureSource());
            //게시글 히트 수
            alarmDto.setAlarmArticleMaxGood(articleEntity.getArticleMaxGood());
            //게시글 제목
            alarmDto.setAlarmArticleTitle(articleEntity.getArticleTitle());

            alarmDto.setAlarmCheck(article.getAlarmCheck());
            alarmDtoList.add(alarmDto);

            article.setAlarmCheck(Boolean.TRUE); //알람 DB에선 체크

        }


        alarmDtoList.sort((o1,o2) -> o2.getAlarmIndex().compareTo(o1.getAlarmIndex()));

        //DB에는 트루

        return alarmDtoList;
    }

    @Override
    public AlarmInputDto createFollowAlarm(Long followIndex) throws Exception {




        //세팅 확인
        FollowEntity followEntity = followJpaRepository.findById(followIndex).get();

        //본인이면 return null
        if(followEntity.getFollowUserIndex()==followEntity.getFollowTargetIndex()) return null;

        //타겟 유저의 엔티티를 불러옴
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(followEntity.getFollowTargetIndex().getUserIndex());
        //그 유저의 셋팅을 확인하기 위해 셋팅을 불러옴
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        //세팅 확인 //만약 거부해 놓는거면 안보낼거임;
        if(settingEntity.getSettingIgnoreFollow()) return null;

        else{
            //팔로우를 신청한 사람
            UserEntity followerUserEntity = userJpaRepository.findById(followEntity.getFollowUserIndex().getUserIndex()).get();

            AlarmFollowEntity alarmFollowEntity = new AlarmFollowEntity();
            alarmFollowEntity.setAlarmUserIndex(userEntity);
            alarmFollowEntity.setAlarmFollowUserIndex(followerUserEntity);
            alarmFollowEntity.setAlarmTime(LocalDateTime.now());
            alarmFollowEntity.setAlarmCheck(Boolean.FALSE);

            alarmFollowJpaRepository.save(alarmFollowEntity);
            //output을 위한 followDto 생성
            AlarmInputDto alarmInputDto = AlarmInputDto.builder().
                    alarmUserIndex(userEntity.getUserIndex()).
                    alarmDtype("F").
                    alarmTime(alarmFollowEntity.getAlarmTime()).
                    alarmFollowUserIndex(followerUserEntity.getUserIndex()).
                    build();

            return alarmInputDto;
        }
    }

    @Override
    public AlarmInputDto createCommentAlarm(Long commentIndex) throws Exception {

        //댓글 확인
        //댓글에 있는 아티클을 불러옴
        CommentEntity commentEntity = commentJpaRepository.findById(commentIndex).get();
        //아티클에 있는 유저를 불러옴
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(commentEntity.getCommentArticleIndex().getArticleIndex());
        //본인이면 return null
        if(commentEntity.getCommentUserIndex()==articleEntity.getArticleUserIndex()) return null;


        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(articleEntity.getArticleUserIndex().getUserIndex());
        //그 유저의 셋팅을 확인하기 위해 셋팅을 불러옴
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        //세팅 확인 //만약 거부해 놓는거면 안보낼거임;
        if(settingEntity.getSettingIgnoreComment()) return null;
        else{


            AlarmCommentEntity alarmCommentEntity = new AlarmCommentEntity();
            alarmCommentEntity.setAlarmUserIndex(userEntity);
            alarmCommentEntity.setAlarmCommentIndex(commentEntity); //댓글 index
            alarmCommentEntity.setAlarmTime(LocalDateTime.now());
            alarmCommentEntity.setAlarmCheck(Boolean.FALSE);

            alarmCommentJpaRepository.save(alarmCommentEntity);
            //output을 위한 followDto 생성
            AlarmInputDto alarmInputDto = AlarmInputDto.builder().
                    alarmUserIndex(userEntity.getUserIndex()).
                    alarmDtype("C").
                    alarmTime(alarmCommentEntity.getAlarmTime()).
                    alarmCommentIndex(alarmCommentEntity.getAlarmCommentIndex().getCommentIndex()).
                    build();

            return alarmInputDto;
        }
    }


    @Override
    public AlarmInputDto createReplyAlarm(Long replyIndex) throws Exception {

        //답글 확인
        //답글에 있는 댓글을 불러옴
        ReplyEntity replyEntity = replyJpaRepository.findById(replyIndex).get();
        //댓글에 있는 유저를 불러옴
        CommentEntity commentEntity = commentJpaRepository.findCommentEntityByCommentIndex(replyEntity.getReplyCommentIndex().getCommentIndex());
        //본인이면 return null;
        if(replyEntity.getReplyUserIndex()==commentEntity.getCommentUserIndex()) return null;

        //그 유저의 셋팅을 확인하기 위해 셋팅을 불러옴
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(commentEntity.getCommentUserIndex().getUserIndex());
        //세팅 확인 //만약 거부해 놓는거면 안보낼거임;
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        if(settingEntity.getSettingIgnoreReply()) return null;
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

            return alarmInputDto;
        }
    }

    @Override
    public AlarmInputDto createArticleAlarm(ArticleEntity articleEntity) throws Exception {
        // 세팅을 확인해

        UserEntity userEntity = articleEntity.getArticleUserIndex();
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        if(settingEntity.getSettingIgnoreEvaluate() == Boolean.TRUE)    return null;

        // 세팅이 열려있으면 보내
        AlarmArticleEntity alarmArticleEntity = new AlarmArticleEntity();
        alarmArticleEntity.setAlarmArticleIndex(articleEntity);
        alarmArticleEntity.setAlarmCheck(Boolean.FALSE);
        alarmArticleEntity.setAlarmTime(LocalDateTime.now());
        alarmArticleEntity.setAlarmUserIndex(userEntity);

        alarmArticleJpaRepository.save(alarmArticleEntity);

        AlarmInputDto alarmInputDto = AlarmInputDto.builder().
                alarmUserIndex(userEntity.getUserIndex()).
                alarmDtype("A").
                alarmTime(alarmArticleEntity.getAlarmTime()).
                alarmReplyIndex(alarmArticleEntity.getAlarmArticleIndex().getArticleIndex()).
                build();

        return alarmInputDto;
    }

    @Override
    public void deleteAlarm(AlarmSelectDto alarmSelectDto) throws Exception {

        //어떤 애인지 구하기
        //F = 팔로우, C = 댓글 , R = 답글 , E = evaluate
        if(alarmSelectDto.getAlarmDtype().equals("F")){
            AlarmFollowEntity alarmFollowEntity = alarmFollowJpaRepository.findById(alarmSelectDto.getAlarmIndex()).get();
            alarmFollowJpaRepository.deleteAlarmByAlarmIndex(alarmFollowEntity.getAlarmIndex());
        }

        else if(alarmSelectDto.getAlarmDtype().equals("C")){
            AlarmCommentEntity alarmCommentEntity = alarmCommentJpaRepository.findById(alarmSelectDto.getAlarmIndex()).get();
            alarmCommentJpaRepository.deleteAlarmByAlarmIndex(alarmCommentEntity.getAlarmIndex());
        }

        else if(alarmSelectDto.getAlarmDtype().equals("R")) {
            AlarmReplyEntity alarmReplyEntity = alarmReplyJpaRepository.findById(alarmSelectDto.getAlarmIndex()).get();
            alarmReplyJpaRepository.deleteAlarmByAlarmIndex(alarmReplyEntity.getAlarmIndex());
        }
        else if(alarmSelectDto.getAlarmDtype().equals("A")) {
            AlarmArticleEntity alarmArticleEntity = alarmArticleJpaRepository.findById(alarmSelectDto.getAlarmIndex()).get();
            alarmReplyJpaRepository.deleteAlarmByAlarmIndex(alarmArticleEntity.getAlarmIndex());
        }

       // else if(alarmSelectDto.getAlarmDtype()=="E") deleteAlarmEntity = alarmEn.findById(alarmSelectDto.getAlarmIndex()).get();
        //평가는 보류

    }

    public void deleteAlarmAll(Long userIndex)throws Exception{

        UserEntity userEntity = userJpaRepository.findById(userIndex).get();
        System.out.println(userIndex+" 존시나");
        alarmJpaRepository.deleteAllByAlarmUserIndex(userEntity);


    }

    @Override
    public Long countAlarm(Long userIndex) throws Exception {
        UserEntity userEntity = userJpaRepository.findById(userIndex).get();
        return alarmJpaRepository.countAlarmEntitiesByAlarmUserIndexAndAlarmCheckIsFalse(userEntity);

    }

    @Override
    public Page<AlarmViewTotalDto> readAlarmWithPaging(Long userIndex, int page, int size) {
        UserEntity user=userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<AlarmEntity> alarmEntities=alarmPagingRepository.findByAlarmUserIndex(user, PageRequest.of(page, size, Sort.by("alarmIndex").descending()));
        List<AlarmViewTotalDto> alarmDtos=new ArrayList<>();
        for(AlarmEntity alarm:alarmEntities){
            Long alarmIndex=alarm.getAlarmIndex();
            String dType=alarm.getAlarmDtype();
            if(dType.equals("A")){
                AlarmArticleEntity alarmArticleEntity=alarmArticleJpaRepository.findAlarmArticleEntityByAlarmIndex(alarmIndex);
                AlarmViewTotalDto alarmViewTotalDto=AlarmViewTotalDto.builder().
                        alarmUserIndex(alarm.getAlarmUserIndex().getUserIndex()).
                        alarmDtype(dType).
                        alarmTime(alarm.getAlarmTime()).
                        alarmCheck(alarm.getAlarmCheck()).
                        alarmArticleIndex(alarmArticleEntity.getAlarmIndex()).
                        alarmArticleMaxGood(alarmArticleEntity.getAlarmArticleIndex().getArticleMaxGood()).
                        alarmArticlePictureSource(alarmArticleEntity.getAlarmArticleIndex().getArticlePictureSource()).
                        build();
                alarmDtos.add(alarmViewTotalDto);
            } else if (dType.equals("C")) {
                AlarmCommentEntity alarmCommentEntity=alarmCommentJpaRepository.findAlarmCommentEntityByAlarmIndex(alarmIndex);
                AlarmViewTotalDto alarmViewTotalDto=AlarmViewTotalDto.builder().
                        alarmUserIndex(alarm.getAlarmUserIndex().getUserIndex()).
                        alarmDtype(dType).
                        alarmTime(alarm.getAlarmTime()).
                        alarmCheck(alarm.getAlarmCheck()).
                        alarmCommentContent(alarmCommentEntity.getAlarmCommentIndex().getCommentContent()).
                        alarmCommentArticleIndex(alarmCommentEntity.getAlarmCommentIndex().getCommentArticleIndex().getArticleIndex()).
                        alarmCommentUserIndex(alarmCommentEntity.getAlarmUserIndex().getUserIndex()).
                        alarmCommentUserNickname(alarmCommentEntity.getAlarmCommentIndex().getCommentUserIndex().getUserNickname()).
                        alarmCommentArticlePictureSource(alarmCommentEntity.getAlarmCommentIndex().getCommentArticleIndex().getArticlePictureSource()).
                        alarmCommentIndex(alarmCommentEntity.getAlarmCommentIndex().getCommentIndex()).
                        build();
            } else if (dType.equals("R")) {
                AlarmReplyEntity alarmReplyEntity=alarmReplyJpaRepository.findAlarmReplyEntityByAlarmIndex(alarmIndex);
                ReplyEntity replyEntity=alarmReplyEntity.getAlarmReplyIndex();
                CommentEntity replyCommentEntity=alarmReplyEntity.getAlarmReplyIndex().getReplyCommentIndex();
                UserEntity replyUserEntity= replyEntity.getReplyUserIndex();
                ArticleEntity replyArticleEntity=replyCommentEntity.getCommentArticleIndex();

                AlarmViewTotalDto alarmViewTotalDto=AlarmViewTotalDto.builder().
                        alarmUserIndex(alarm.getAlarmUserIndex().getUserIndex()).
                        alarmDtype(dType).
                        alarmTime(alarm.getAlarmTime()).
                        alarmCheck(alarm.getAlarmCheck()).
                        alarmReplyContent(replyEntity.getReplyContent()).
                        alarmReplyUserNickname(replyUserEntity.getUserNickname()).
                        alarmReplyCommentIndex(replyCommentEntity.getCommentIndex()).
                        alarmReplyUserIndex(replyUserEntity.getUserIndex()).
                        alarmReplyArticleIndex(replyArticleEntity.getArticleIndex()).
                        alarmReplyArticlePictureSource(replyArticleEntity.getArticlePictureSource()).
                        alarmReplyIndex(replyEntity.getReplyIndex()).build();

                alarmDtos.add(alarmViewTotalDto);

            } else if (dType.equals("F")) {
                AlarmFollowEntity alarmFollowEntity=alarmFollowJpaRepository.findAlarmFollowEntityByAlarmIndex(alarmIndex);
                UserEntity followingUser=alarmFollowEntity.getAlarmFollowUserIndex();

                AlarmViewTotalDto alarmViewTotalDto=AlarmViewTotalDto.builder().
                        alarmUserIndex(alarm.getAlarmUserIndex().getUserIndex()).
                        alarmDtype(dType).
                        alarmTime(alarm.getAlarmTime()).
                        alarmCheck(alarm.getAlarmCheck()).
                        alarmFollowUserNickname(followingUser.getUserNickname()).
                        alarmFollowUserPictureSource(followingUser.getUserPictureSource()).
                        alarmFollowUserIndex(followingUser.getUserIndex()).
                        build();

                alarmDtos.add(alarmViewTotalDto);
            }
        }

        return new PageImpl<>(alarmDtos,alarmEntities.getPageable(),alarmEntities.getTotalElements());
    }
}
