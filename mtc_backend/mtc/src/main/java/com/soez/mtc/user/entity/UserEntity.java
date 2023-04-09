package com.soez.mtc.user.entity;

import com.soez.mtc.alarm.entity.AlarmCommentEntity;
import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.alarm.entity.AlarmFollowEntity;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.report.entity.ReportEntity;
import com.soez.mtc.setting.entity.SettingEntity;
import com.sun.el.parser.AstLambdaExpression;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="user")
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_index")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userIndex;
    private String userNickname;
    private String userPictureSource;
    private String userUid;
    private Long userReportedCount;

    @Builder
    public UserEntity(String userUid, String userNickname, String userPictureSource, Long userReportedCount) {

        this.userUid = userUid;
        this.userNickname = userNickname;
        this.userPictureSource = userPictureSource;
        this.blockTargetEntities = new ArrayList<>();
        this.followTargetEntities = new ArrayList<>();
        this.followingTargetEntities = new ArrayList<>();
        this.alarmEntities = new ArrayList<>();
        this.userReportedCount = userReportedCount;
        this.articleEntities=new ArrayList<>();
        this.commentEntities=new ArrayList<>();
        this.replyEntities=new ArrayList<>();
        this.reportEntities=new ArrayList<>();

    }

    @OneToMany(mappedBy = "blockUserIndex",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlockEntity> blockTargetEntities;//

    @OneToMany(mappedBy = "followUserIndex",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowEntity> followTargetEntities;//

    @OneToMany(mappedBy = "followUserIndex",cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<FollowEntity> followingTargetEntities;//

    @OneToOne(mappedBy = "settingUserIndex",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private SettingEntity settingUserEntity;

    @OneToMany(mappedBy="alarmIndex",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlarmEntity> alarmEntities;//

    @OneToMany(mappedBy = "articleUserIndex",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleEntity> articleEntities;//

    @OneToMany(mappedBy = "commentUserIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> commentEntities;//

    @OneToMany(mappedBy = "replyUserIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyEntity> replyEntities;//

    @OneToMany(mappedBy = "alarmFollowUserIndex",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlarmFollowEntity> alarmFollowIndex;

    @OneToMany(mappedBy = "reportUser",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reportEntities;//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userUid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}