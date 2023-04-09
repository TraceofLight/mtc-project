package com.soez.mtc.report.entity;

import com.soez.mtc.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "report_dtype")
@Table(name="report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_index")
    private Long reportIndex;

    @Column(name="report_content")
    private String reportContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_user_index")
    private UserEntity reportUser;
}
