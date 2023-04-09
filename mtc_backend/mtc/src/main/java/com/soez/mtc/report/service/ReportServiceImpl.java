package com.soez.mtc.report.service;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.article.repository.ArticleJpaRepository;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.comment.repository.CommentJpaRepository;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.reply.repository.ReplyJpaRepository;
import com.soez.mtc.report.dto.ReportRegistDto;
import com.soez.mtc.report.dto.ReportViewDto;
import com.soez.mtc.report.entity.ReportArticleEntity;
import com.soez.mtc.report.entity.ReportCommentEntity;
import com.soez.mtc.report.entity.ReportEntity;
import com.soez.mtc.report.entity.ReportReplyEntity;
import com.soez.mtc.report.repository.ReportArticleJpaRepository;
import com.soez.mtc.report.repository.ReportCommentJpaRepository;
import com.soez.mtc.report.repository.ReportJpaRepository;
import com.soez.mtc.report.repository.ReportReplyJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ArticleJpaRepository articleJpaRepository;

    @Autowired
    CommentJpaRepository commentJpaRepository;

    @Autowired
    ReplyJpaRepository replyJpaRepository;

    @Autowired
    ReportArticleJpaRepository reportArticleJpaRepository;

    @Autowired
    ReportCommentJpaRepository reportCommentJpaRepository;

    @Autowired
    ReportReplyJpaRepository reportReplyJpaRepository;

    @Autowired
    ReportJpaRepository reportJpaRepository;

    @Override
    public List<ReportViewDto> readReportByReportIndex(Long reportIndex) {
        return null;
    }

    @Override
    public List<ReportViewDto> readReportByUserIndex(Long userIndex) {
        return null;
    }

    @Override
    public ReportEntity createReport(ReportRegistDto reportRegistDto) {
        UserEntity userEntity=userJpaRepository.findUserEntityByUserIndex(reportRegistDto.getReportUserIndex());
        switch (reportRegistDto.getReportDtype()){
            case("A")://Article
                ArticleEntity articleEntity=articleJpaRepository.findArticleEntityByArticleIndex(reportRegistDto.getReportArticleIndex());
                ReportArticleEntity check=reportArticleJpaRepository.findByReportArticleIndexAndReportUser(articleEntity,userEntity);

                if(check!=null){//이미 그 유저가 해당 글을 신고를 했다면
                    check.setReportContent(reportRegistDto.getReportConetent());
                    return check;
                    //내용만 업데이트를 해준다.
                }

                else if(reportArticleJpaRepository.countByReportArticleIndex(articleEntity)>=1){
                    //새로 신고가 등록된 게시글의 신고 숫자가 삭제해야할 숫자조건을 넘어섰다면
                    UserEntity reportedUser=articleEntity.getArticleUserIndex();
                    articleJpaRepository.deleteArticleEntityByArticleIndex(reportRegistDto.getReportArticleIndex());
                    reportedUser.setUserReportedCount(reportedUser.getUserReportedCount()+1);
                    return null;
                    //삭제하고 유저가 처벌받은 카운트에 1을 더해 기록한다.
                }

                else{
                    //두 경우 모두 아닐경우 신규 신고가 등록된다.
                    ReportArticleEntity reportArticleEntity=ReportArticleEntity.builder()
                            .reportUserIndex(userEntity)
                            .reportArticleIndex(articleEntity)
                            .reportContent(reportRegistDto.getReportConetent())
                            .build();
                    return reportJpaRepository.save(reportArticleEntity);
                }


            case("C")://Comment
                CommentEntity commentEntity=commentJpaRepository.findCommentEntityByCommentIndex(reportRegistDto.getReportCommentIndex());
                ReportCommentEntity checkC=reportCommentJpaRepository.findByReportCommentIndexAndReportUser(commentEntity,userEntity);

                if(checkC!=null){
                    checkC.setReportContent(reportRegistDto.getReportConetent());
                    return checkC;
                }

                if(reportCommentJpaRepository.countByReportCommentIndex(commentEntity)>=1){
                    UserEntity reportedUser=commentEntity.getCommentUserIndex();
                    commentJpaRepository.deleteByCommentIndex(reportRegistDto.getReportCommentIndex());
                    reportedUser.setUserReportedCount(reportedUser.getUserReportedCount()+1);
                    return null;
                }

                else{
                    ReportCommentEntity reportCommentEntity=ReportCommentEntity.builder()
                            .reportUserIndex(userEntity)
                            .reportContent(reportRegistDto.getReportConetent())
                            .reportCommentIndex(commentEntity)
                            .build();
                    return reportJpaRepository.save(reportCommentEntity);
                }


            case("R")://reply
                ReplyEntity replyEntity=replyJpaRepository.findByReplyIndex(reportRegistDto.getReportReplyIndex());
                ReportReplyEntity checkR=reportReplyJpaRepository.findByReportReplyIndexAndReportUser(replyEntity,userEntity);

                if(checkR!=null){
                    checkR.setReportContent(reportRegistDto.getReportConetent());
                    return checkR;
                }

                else if(reportReplyJpaRepository.countByReportReplyIndex(replyEntity)>=1){
                    UserEntity reportedUser=replyEntity.getReplyUserIndex();
                    replyJpaRepository.deleteByReplyIndex(replyEntity.getReplyIndex());
                    reportedUser.setUserReportedCount(reportedUser.getUserReportedCount()+1);
                    return null;
                }
                else{
                    ReportReplyEntity reportReplyEntity=ReportReplyEntity.builder()
                            .reportUserIndex(userEntity)
                            .reportContent(reportRegistDto.getReportConetent())
                            .reportReplyIndex(replyEntity)
                            .build();
                    return reportJpaRepository.save(reportReplyEntity);
                }

            default:
                return null;
        }
    }
}
