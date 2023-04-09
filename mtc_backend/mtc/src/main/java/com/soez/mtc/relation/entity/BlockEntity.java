package com.soez.mtc.relation.entity;

import com.soez.mtc.user.entity.UserEntity;
import lombok.*;
import javax.persistence.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="block")
public class BlockEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="block_user_index")
    private UserEntity blockUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="block_target_index")
    private UserEntity blockTargetIndex;



}



