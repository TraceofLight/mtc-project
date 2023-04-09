package com.soez.mtc.comment.controller;

import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.comment.dto.CommentPublishDto;
import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.comment.service.CommentService;
import com.soez.mtc.commentlike.dto.CommentLikePublishDto;
import com.soez.mtc.reply.service.ReplyService;
import com.soez.mtc.user.service.UserService;
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
@RequestMapping("/comment")
@Api(tags={"Comment(댓글) 정보를 제공하는 컨트롤러"})
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    //****************************************** CREATE ****************************************
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성.'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody @ApiParam(value = "커멘트 정보", required = true) CommentPublishDto commentPublishDto) throws Exception {
        if (commentService.createComment(commentPublishDto)!=null) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "댓글 좋아요와 싫어요 생성", notes = "좋아요 혹은 싫어요 생성.'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/createLike")
    public ResponseEntity<String> createCommentLike(@RequestBody @ApiParam(value = "답글 좋아요 싫어요 정보", required = true) CommentLikePublishDto commentLikePublishDto) throws Exception {
        if(commentService.createCommentLike(commentLikePublishDto)!=null){
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
    }

    //****************************************** READ ****************************************
    @ApiOperation(value = "댓글을 인덱스로 찾기", notes = "댓글을 댓글 인덱스로 찾기.", response = CommentEntity.class)
    @GetMapping("/readCommentByCommentIndex/{comment-index}")
    public ResponseEntity<CommentViewDto> readCommentByCommentIndex(@PathVariable("comment-index") @ApiParam(value = "댓글 인덱스", required = true) Long commentIndex){
        CommentViewDto resultComment=commentService.readCommentByCommentIndex(commentIndex);

        if(resultComment!=null){
            return new ResponseEntity<CommentViewDto>(resultComment, HttpStatus.OK);
        }

        return  new ResponseEntity<CommentViewDto>(resultComment, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "댓글 유저로 찾기", notes = "댓글을 유저 인덱스로 찾기.", response = List.class)
    @GetMapping("/readCommentByUserIndex/{user-index}")
    public ResponseEntity<List<CommentViewDto>> readCommentByUserIndex(@PathVariable("user-index") @ApiParam(value = "유저 인덱스", required = true) Long userIndex){
        List<CommentViewDto> resultComment=commentService.readCommentByUserIndex(userIndex);
        if(resultComment.size()!=0){
            return new ResponseEntity<List<CommentViewDto>>(resultComment, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<CommentViewDto>>(resultComment, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "댓글 게시글로 찾기", notes = "댓글을 게시글 인덱스로 찾기.", response = List.class)
    @GetMapping("/readCommentByArticleIndex/{article-index}")
    public ResponseEntity<List<CommentViewDto>> readCommentByArticleIndex(@PathVariable("article-index") @ApiParam(value = "게시글 인덱스", required = true) Long articleIndex){
        List<CommentViewDto> resultComment=commentService.readCommentByArticleIndex(articleIndex);
        if(resultComment.size()!=0){
            return new ResponseEntity<List<CommentViewDto>>(resultComment, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<CommentViewDto>>(resultComment, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "댓글 게시글로 최신순으로 찾기 with 페이징", notes = "게시글 인덱스와 페이지번호(0부터 시작)을 받아서 최신순으로 페이징된 리스트를 준다.", response = List.class)
    @GetMapping("/readCommentByArticleIndexWithPagingSortByRecent/{article-index}&{page-start}")
    public ResponseEntity<Page<CommentViewDto>> readCommentByArticleIndexWithPagingSortByRecent(@PathVariable("article-index") @ApiParam(value = "게시글 인덱스", required = true) Long articleIndex, @PathVariable("page-start") @ApiParam(value = "페이지 번호(0부터 시작) 20개씩 보여줌 ", required = true) Long pageStart){
        Page<CommentViewDto> pages=commentService.readCommentByArticleIndexWithPaging(articleIndex,Long.valueOf(pageStart).intValue(),20);
        if(pages.hasContent()){
            return new ResponseEntity<Page<CommentViewDto>>(pages,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Page<CommentViewDto>>(pages,HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "댓글 게시글로 추천순으로 찾기 with 페이징", notes = "게시글 인덱스와 시작 댓글번호를 받아서 추천순으로 페이징된 리스트를 준다.", response = List.class)
    @GetMapping("/readCommentByArticleIndexWithPagingSortByLike/{article-index}&{page-start}")
    public ResponseEntity<Page<CommentViewDto>> readCommentByArticleIndexWithPagingSortByLike(@PathVariable("article-index") @ApiParam(value = "게시글 인덱스", required = true) Long articleIndex, @PathVariable("page-start") @ApiParam(value = "페이지 번호, 0부터 시작", required = true) Long pageStart){
        Page<CommentViewDto> pages=commentService.readCommentByArticleIndexWithPagingSortByLike(articleIndex,Long.valueOf(pageStart).intValue(),20);
        if(pages!=null){
            return new ResponseEntity<Page<CommentViewDto>>(pages,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Page<CommentViewDto>>(pages,HttpStatus.NO_CONTENT);
        }
    }


    @ApiOperation(value = "댓글 좋아요 유저와 댓글 인덱스로 찾기", notes = "1이면 좋아요. -1이면 싫어요. 0이면 평가 취소함", response = Integer.class)
    @GetMapping("/readCommentLikeByUserIndexAndCommentIndex/{comment-index}&{user-index}")
    public ResponseEntity<Integer> readCommentLikeByUserIndexAndCommentIndex(@PathVariable("comment-index") @ApiParam(value = "댓글 인덱스", required = true) Long commentIndex, @PathVariable("user-index") @ApiParam(value = "유저 인덱스", required = true) Long userIndex){
        Integer value=commentService.readCommentLikeByUserIndexAndCommentIndex(userIndex,commentIndex);
        if(value!=null){
            return new ResponseEntity<Integer>(value, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Integer>(value, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "게시글을 댓글 인dex로 찾기", notes = "게시글을 댓글 인덱스로 찾기.", response = ArticleViewDto.class)
    @GetMapping("/readArticleByCommentIndex/{comment-index}")
    public ResponseEntity<ArticleViewDto> readArticleByCommentIndex(@PathVariable("comment-index") @ApiParam(value = "댓글 인덱스", required = true) Long commentIndex){
        ArticleViewDto articleViewDto=commentService.readArticleViewDtoByCommentIndex(commentIndex);
        if(articleViewDto!=null){
            return new ResponseEntity<ArticleViewDto>(articleViewDto, HttpStatus.OK);
        }
        return new ResponseEntity<ArticleViewDto>(articleViewDto, HttpStatus.NO_CONTENT);
    }



    //****************************************** UPDATE ****************************************



    //****************************************** DELETE ****************************************
    @ApiOperation(value = "댓글 인덱스로 삭제", notes = "댓글을 댓글 인덱스로 삭제.", response = String.class)
    @DeleteMapping("/deleteCommentByCommentIndex/{comment-index}")
    public ResponseEntity<String> deleteCommentByCommentIndex(@PathVariable("comment-index") @ApiParam(value = "댓글 인덱스", required = true) Long commentIndex){
        commentService.deleteCommentByCommentIndex(commentIndex);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
