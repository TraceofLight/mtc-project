package com.soez.mtc.commentlike.entity;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.commentlike.dto.CommentLikePublishDto;
import com.soez.mtc.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="comment_like")
public class CommentLikeEntity {
    @Id
    @Column(name="comment_like_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_like_user_index")
    private UserEntity commentLikeUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_like_comment_index")
    private CommentEntity commentLikeCommentIndex;

    //comment_like_valuate
    //commnet_like_valuate
    @Column(name="comment_like_valuate")
    private Integer commentLikeValuate;

    @Builder
    public CommentLikeEntity(UserEntity commentLikeUserIndex, CommentEntity commentLikeCommentIndex, Integer commentLikeValuate){
        this.commentLikeValuate=commentLikeValuate;
        this.commentLikeCommentIndex=commentLikeCommentIndex;
        this.commentLikeUserIndex=commentLikeUserIndex;
    }

}
