package com.soez.mtc.reply.controller;

import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.reply.dto.ReplyPublishDto;
import com.soez.mtc.reply.dto.ReplyViewDto;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.reply.service.ReplyService;
import com.soez.mtc.replylike.dto.ReplyLikePublishDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@Api(tags={"Reply(답글) 정보를 제공하는 컨트롤러"})
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    //****************************************** CREATE ****************************************
    @ApiOperation(value = "답글 작성", notes = "답글을 작성.'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/create")
    public ResponseEntity<String> createReply(@RequestBody @ApiParam(value = "답글 정보", required = true) ReplyPublishDto replyPublishDto) throws Exception {
        if (replyService.createReply(replyPublishDto)!=null) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }

//    @ApiOperation(value = "답글 평가", notes = "답글에 좋아요 혹은 싫어요를 함.'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//    @PostMapping("/replyEvaluate")
//    public ResponseEntity<String> createReplyEvaluate(@RequestBody @ApiParam(value = "답글 정보", required = true) ) throws Exception {
//
//    }
    @ApiOperation(value = "답글 좋아요와 싫어요 생성", notes = "좋아요 혹은 싫어요 생성.'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/createLike")
    public ResponseEntity<String> createReplyLike(@RequestBody @ApiParam(value = "답글 좋아요 싫어요 정보", required = true)ReplyLikePublishDto replyLikePublishDto) throws Exception {
        if(replyService.createReplyLike(replyLikePublishDto)!=null){
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }


    //****************************************** READ ****************************************
    @ApiOperation(value = "답글을 인덱스로 찾기", notes = "답글을 답글 인덱스로 찾기.", response = ReplyEntity.class)
    @GetMapping("/readReplyByReplyIndex/{reply-index}")
    public ResponseEntity<ReplyViewDto> readReplyByReplyIndex(@PathVariable("reply-index") @ApiParam(value = "답글 인덱스", required = true) Long replyIndex){
        ReplyViewDto replyViewDto=replyService.readReplyByReplyIndex(replyIndex);
        if(replyViewDto!=null){
            return new ResponseEntity<ReplyViewDto>(replyViewDto,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<ReplyViewDto>(replyViewDto,HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "답글을 댓글로 찾기", notes = "답글을 댓글 인덱스로 찾기.", response = List.class)
    @GetMapping("/readReplyByCommentIndex/{comment-index}")
    public ResponseEntity<List<ReplyViewDto>> readReplyByCommentIndex(@PathVariable("comment-index") @ApiParam(value = "댓글 인덱스", required = true) Long commentIndex){
        List<ReplyViewDto> resultReply=replyService.readReplyByCommentIndex(commentIndex);
        if(resultReply.size()!=0){
            return new ResponseEntity<List<ReplyViewDto>>(resultReply,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<List<ReplyViewDto>>(resultReply,HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "답글을 유저로 찾기", notes = "답글을 유저 인덱스로 찾기.", response = List.class)
    @GetMapping("/readReplyByUserIndex/{user-index}")
    public ResponseEntity<List<ReplyViewDto>> readReplyByUserIndex(@PathVariable("user-index") @ApiParam(value = "유저 인덱스", required = true) Long userIndex){
        List<ReplyViewDto> resultReply=replyService.readReplyByUserIndex(userIndex);
        if(resultReply.size()!=0){
            return new ResponseEntity<List<ReplyViewDto>>(resultReply,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<List<ReplyViewDto>>(resultReply,HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "답글 좋아요를 유저와 답글 인덱스로 찾기", notes = "1이면 좋아요. -1이면 싫어요. 0이면 평가 취소함", response = Integer.class)
    @GetMapping("/readReplyLikeByUserIndexAndReplyIndex/{reply-index}&{user-index}")
    public ResponseEntity<Integer> readReplyLikeByUserIndexAndReplyIndex(@PathVariable("reply-index") @ApiParam(value = "답글 인덱스", required = true) Long replyIndex, @PathVariable("user-index") @ApiParam(value = "유저 인덱스", required = true) Long userIndex){
        Integer value= replyService.findReplyValuateByReplyIndexAndUserIndex(replyIndex,userIndex);
        if(value!=null){
            return new ResponseEntity<Integer>(value, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Integer>(value, HttpStatus.NO_CONTENT);
        }
    }


    @ApiOperation(value = "답글을 댓글로 페이징을 이용해 최신순으로 찾기 ", notes = "답글인덱스와 페이지 번호를 받아서 최신순으로 20개씩 페이징해서 보여줍니다.", response = List.class)
    @GetMapping("/readReplyByCommentIndexwithPaging/{comment-index}&{page-start}")
    public ResponseEntity<Page<ReplyViewDto>> readReplyByCommentIndex(@PathVariable("comment-index") @ApiParam(value = "댓글 인덱스", required = true) Long commentIndex,@PathVariable("page-start") @ApiParam(value = "페이지 번호(0부터 시작) 20개씩 보여줌 ", required = true) Long pageStart){
        Page<ReplyViewDto> pages=replyService.readReplyByCommentIndexWithPaging(commentIndex,Long.valueOf(pageStart).intValue(),20);
        if(pages.hasContent()){
            return new ResponseEntity<Page<ReplyViewDto>>(pages,HttpStatus.OK);
        }
        return new ResponseEntity<Page<ReplyViewDto>>(pages,HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "답글로 게시글 찾기", notes = "게시글을 답글 인덱스로(Long) 찾기.", response = ArticleViewDto.class)
    @GetMapping("/readArticleByReplyIndex/{reply-index}")
    public ResponseEntity<ArticleViewDto> readArticleByReplyIndex(@PathVariable("reply-index") @ApiParam(value = "답글 인덱스", required = true) Long replyIndex){
        ArticleViewDto articleViewDto= replyService.findArticleViewDtoByReplyIndex(replyIndex);
        if(articleViewDto!=null){
            return new ResponseEntity<ArticleViewDto>(articleViewDto,HttpStatus.OK);
        }
        return new ResponseEntity<ArticleViewDto>(articleViewDto,HttpStatus.NO_CONTENT);
    }


    //****************************************** DELETE ****************************************
    @ApiOperation(value = "답글을 답글인덱스로 삭제하기", notes = "답글 인덱스로 답글을 삭제합니다", response = List.class)
    @DeleteMapping("/deleteReplyByReplyIndex/{reply-index}")
    public ResponseEntity<String> deleteReplyByReplyIndex(@PathVariable("reply-index") @ApiParam(value = "답글 인덱스", required = true) Long replyIndex){
        replyService.deleteReplyByReplyIndex(replyIndex);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
