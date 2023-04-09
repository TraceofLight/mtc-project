package com.soez.mtc.alarm.entity;

import com.soez.mtc.article.entity.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
@Setter
@Getter
public class AlarmArticleEntity extends AlarmEntity {

    @JoinColumn(name = "alarm_article_index")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity alarmArticleIndex;
}
