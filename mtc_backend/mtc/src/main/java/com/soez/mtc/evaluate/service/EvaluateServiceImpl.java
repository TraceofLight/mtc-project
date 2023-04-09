package com.soez.mtc.evaluate.service;

import com.soez.mtc.alarm.dto.AlarmInputDto;
import com.soez.mtc.alarm.entity.AlarmArticleEntity;
import com.soez.mtc.alarm.repository.AlarmArticleJpaRepository;
import com.soez.mtc.alarm.service.AlarmService;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.evaluate.dto.EvaluateDto;
import com.soez.mtc.evaluate.dto.EvaluateStatisticsDto;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.evaluate.entity.EvaluateValue;
import com.soez.mtc.evaluate.repository.EvaluateJpaRepository;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.repository.SettingJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {

    private final UserJpaRepository userJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final EvaluateJpaRepository evaluateJpaRepository;
    private final AlarmService alarmService;
    private final SettingJpaRepository settingJpaRepository;
    private final AlarmArticleJpaRepository alarmArticleJpaRepository;

    /**
     * 유저 인덱스, 게시물 인덱스, 평가 타입을 DTO로 전달받는다.
     * 기존에 유저가 게시물을 평가한 기록이 있다면 평가 타입을 업데이트하고
     * 기록이 없다면 평가 엔티티를 새로 만들어 저장한다. -> 시간 순
     * @param evaluateDto
     * @return
     */
    @Override
    public boolean createEvaluate(EvaluateDto evaluateDto) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(evaluateDto.getUser());
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(evaluateDto.getArticle());
        if(articleEntity == null){
            return false;
        }

        EvaluateEntity evaluateEntity = evaluateJpaRepository.findEvaluateEntityByEvaluateUserIndexAndEvaluateArticleIndex(userEntity, articleEntity);

        // 게시물의 좋아요 수를 계산하고 처음으로 10^n에 도달하는 경우 알람을 보낸다.
        if(evaluateDto.getEvaluateType() == EvaluateValue.GOOD){
            int goodCount = evaluateJpaRepository.countEvaluateEntitiesByEvaluateArticleIndexAndEvaluateValue(articleEntity, EvaluateValue.GOOD) + 1;

            // 10의 제곱수이며 최대 좋아요 수 보다 높은 경우 알람을 보낸다.
            if (isNthPowerOfTen(goodCount) && goodCount > articleEntity.getArticleMaxGood()) {
                articleEntity.setArticleMaxGood(goodCount);

                UserEntity writer = articleEntity.getArticleUserIndex();
                SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(writer);

                // 세팅이 열려있으면 보내
                if(settingEntity.getSettingIgnoreEvaluate() == Boolean.FALSE) {
                    AlarmArticleEntity alarmArticleEntity = new AlarmArticleEntity();
                    alarmArticleEntity.setAlarmArticleIndex(articleEntity);
                    alarmArticleEntity.setAlarmCheck(Boolean.FALSE);
                    alarmArticleEntity.setAlarmTime(LocalDateTime.now());
                    alarmArticleEntity.setAlarmUserIndex(writer);

                    alarmArticleJpaRepository.save(alarmArticleEntity);
                }
            }
        }
        // 기존에 평가한 기록이 없으면 새로 만들고 있으면 업데이트한다.
        if(evaluateEntity == null){
            evaluateEntity = EvaluateEntity.builder()
                    .evaluateUserIndex(userEntity)
                    .evaluateArticleIndex(articleEntity)
                    .evaluateValue(evaluateDto.getEvaluateType())
                    .build();
            evaluateJpaRepository.save(evaluateEntity);
        }
        else{
            evaluateEntity.setEvaluateValue(evaluateDto.getEvaluateType());
            evaluateEntity.setEvaluateDate(LocalDateTime.now());
        }

        return true;
    }

    @Override
    public EvaluateStatisticsDto getEvaluateStatistics(Long userIndex) {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);
        return EvaluateStatisticsDto.builder()
                .goodCount(evaluateJpaRepository.totalCountEvaluateEntitiesByEvaluateUserIndexAndEvaluateValue(userEntity, EvaluateValue.GOOD))
                .sosoCount(evaluateJpaRepository.totalCountEvaluateEntitiesByEvaluateUserIndexAndEvaluateValue(userEntity, EvaluateValue.SOSO))
                .badCount(evaluateJpaRepository.totalCountEvaluateEntitiesByEvaluateUserIndexAndEvaluateValue(userEntity, EvaluateValue.BAD))
                .build();
    }

    boolean isNthPowerOfTen(int number){
        while(number % 10 == 0){
            number /= 10;
            if(number == 1) return true;
        }
        return false;
    }
}
