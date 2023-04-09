package com.soez.mtc.alarm.service;

import com.soez.mtc.alarm.dto.AlarmDto;
import com.soez.mtc.alarm.dto.AlarmInputDto;
import com.soez.mtc.alarm.dto.AlarmSelectDto;
import com.soez.mtc.alarm.dto.AlarmViewTotalDto;
import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.relation.dto.FollowDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AlarmService {

    List<AlarmDto> selectAlarm(Long userIndex) throws Exception;

    AlarmInputDto createFollowAlarm(Long followDto)throws Exception;
    AlarmInputDto createCommentAlarm(Long commentIndex)throws Exception;

    AlarmInputDto createReplyAlarm(Long replyIndex) throws Exception;

    AlarmInputDto createArticleAlarm(ArticleEntity articleEntity) throws Exception;

    void deleteAlarm(AlarmSelectDto alarmSelectDto) throws Exception;

    Long countAlarm(Long userIndex)throws Exception;

    public Page<AlarmViewTotalDto> readAlarmWithPaging(Long userIndex, int page, int size);

    void deleteAlarmAll(Long userIndex) throws Exception;
}
