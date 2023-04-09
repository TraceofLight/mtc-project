package com.soez.mtc.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmArticleDto extends AlarmDto{

    private Long alarmArticleIndex;
    private int alarmArticleMaxGood;
    private String alarmArticlePictureSource;
    private String alarmArticleTitle;


    @Builder
    public AlarmArticleDto(Long alarmArticleIndex, int alarmArticleMaxGood , String alarmArticlePictureSource,String alarmArticleTitle ) {
            this.alarmArticleIndex = alarmArticleIndex;
            this.alarmArticleMaxGood = alarmArticleMaxGood;
            this.alarmArticlePictureSource = alarmArticlePictureSource;
            this.alarmArticleTitle = alarmArticleTitle;
    }


}
