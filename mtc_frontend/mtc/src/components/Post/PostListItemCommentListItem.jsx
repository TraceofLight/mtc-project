import { useState, useEffect } from "react";
import PostListItemCommentListItemReply from "./PostListItemCommentListItemReply";
import Box from "@mui/system/Box";
import PostCommentMoreButton from "./PostCommentMoreButton";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
import ThumbDownOffAltIcon from "@mui/icons-material/ThumbDownOffAlt";
import ThumbUpAltIcon from "@mui/icons-material/ThumbUpAlt";
import ThumbDownAltIcon from "@mui/icons-material/ThumbDownAlt";
import axios from "axios";
import Grid from "@mui/material/Grid";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { useSelector } from "react-redux";
import { requestGet } from "api/axios";
import { useNavigate } from "react-router-dom";
const PostListItemCommentListItem = (props) => {
  const navigate = useNavigate();
  const userStatus = useSelector((state) => state.userStatus);
  const [replyTogle, setReplyTogle] = useState(false);
  const [evaluate, setEvaluate] = useState(0);
  const [bad, setBad] = useState(props.comment.bad);
  const [good, setGood] = useState(props.comment.good);
  const [replyLength, setReplyLength] = useState(0);

  const toUserPage = () => {
    navigate(`/user/${props.comment.commentUserIndex}`)
  }
  const evaluateComment = async (evaluate) => {
    try {
      const response = await axios.post(
        `${urls.comment}createLike`,

        {
          commentLikeCommentIndex: props.comment.commentIndex,
          commentLikeUserIndex: userStatus.userIndex,
          commentLikeValuate: evaluate,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
          },
        }
      );
      console.log(response);

      props.checkEvaluate();
    } catch (error) {
      console.log(error);
    }
  };

  const goodComment = () => {
    evaluateComment(1);
    setGood((target) => target + 1);
    setEvaluate(1);
  };
  const badComment = () => {
    evaluateComment(-1);
    setBad((target) => target + 1);
    setEvaluate(-1);
  };
  const cancelGoodComment = () => {
    evaluateComment(0);
    setGood((target) => target - 1);
    setEvaluate(0);
  };
  const cancelBadComment = () => {
    evaluateComment(0);
    setBad((target) => target - 1);
    setEvaluate(0);
  };
  const changeBadComment = () => {
    evaluateComment(-1);
    setBad((target) => target + 1);
    setGood((target) => target - 1);
    setEvaluate(-1);
  };
  const changeGoodComment = () => {
    evaluateComment(1);
    setBad((target) => target - 1);
    setGood((target) => target + 1);
    setEvaluate(1);
  };
  const changeTogle = () => {
    if (replyTogle) {
      setReplyTogle(false);
    } else {
      setReplyTogle(true);
    }
  };

  useEffect(() => {
    const EvaluateState = async (comment) => {
      const response = await requestGet(
        `${urls.comment}readCommentLikeByUserIndexAndCommentIndex/${comment.commentIndex}&${userStatus.userIndex}`
      );

      setEvaluate(response);
    };
    const getReply = async (comment) => {
      try {
        const response = await axios.get(
          `${urls.reply}readReplyByCommentIndex/${comment.commentIndex}`,
          {headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
          },}
        )
        if (response.status === 204) {
          setReplyLength(0);
        } else {
          setReplyLength(response.data.length);
        }
        
  
      } catch (error) {
        console.log('안됨')
      }
      
    }
    
    EvaluateState(props.comment);
    getReply(props.comment);
  }, [props.comment]);

  switch (evaluate) {
    default:
      return <div></div>;
    case 0:
      return (
        <Box sx={{ width: "100%" }}>
          <Grid container spacing={2}>
            <Grid item xs={10}>
              <div>
                <Stack direction="row" spacing={2}>
                  <Avatar
                    src={props.comment.commentUserPictureSource}
                    sx={{ width: "2em", height: "2em" }}
                    onClick={toUserPage}
                  ></Avatar>
                  <Box>
                    <div>{props.comment.commentContent}</div>
                    <Stack direction="row" spacing={2}>
                      <ThumbUpOffAltIcon onClick={goodComment} />
                      <>{good}</>
                      <ThumbDownOffAltIcon onClick={badComment} />
                      <>{bad}</>
                      <div onClick={changeTogle}>
                        {replyTogle === true ? <>답글 접기</> : 
                        replyLength !== 0 ?
                        <>답글 {replyLength}개</>
                      :
                      <>답글</>
                      }
                      </div>
                    </Stack>
                  </Box>
                </Stack>
              </div>
            </Grid>
            <Grid item sx={1}>
              <PostCommentMoreButton data={props.comment} type={"comment"} />
            </Grid>
          </Grid>

          {replyTogle === true && (
            <Grid item xs={12}>
              <PostListItemCommentListItemReply
                commentIndex={props.comment.commentIndex}
              />
            </Grid>
          )}
        </Box>
      );
    case 1:
      return (
        <Box sx={{ width: "100%" }}>
          <Grid container spacing={2}>
            <Grid item xs={10}>
              <div>
                <Stack direction="row" spacing={2}>
                  <Avatar
                    src={props.comment.commentUserPictureSource}
                    sx={{ width: "2em", height: "2em" }}
                    onClick={toUserPage}
                  ></Avatar>
                  <Box>
                    <div>{props.comment.commentContent}</div>
                    <Stack direction="row" spacing={2}>
                      <ThumbUpAltIcon onClick={cancelGoodComment} />
                      <>{good}</>
                      <ThumbDownOffAltIcon onClick={changeBadComment} />
                      <>{bad}</>
                      <div onClick={changeTogle}>
                        {replyTogle === true ? <>답글 접기</> : 
                        replyLength !== 0 ?
                        <>답글 {replyLength}개</>
                      :
                      <>답글</>
                      }
                      </div>
                    </Stack>
                  </Box>
                </Stack>
              </div>
            </Grid>
            <Grid item sx={1}>
              <PostCommentMoreButton data={props.comment} type={"comment"} />
            </Grid>
          </Grid>

          {replyTogle === true && (
            <Grid item xs={12}>
              <PostListItemCommentListItemReply
                commentIndex={props.comment.commentIndex}
              />
            </Grid>
          )}
        </Box>
      );

    case -1:
      return (
        <Box sx={{ width: "100%" }}>
          <Grid container spacing={2}>
            <Grid item xs={10}>
              <div>
                <Stack direction="row" spacing={2}>
                  <Avatar
                    src={props.comment.commentUserPictureSource}
                    sx={{ width: "2em", height: "2em" }}
                    onClick={toUserPage}
                  ></Avatar>
                  <Box>
                    <div>{props.comment.commentContent}</div>
                    <Stack direction="row" spacing={2}>
                      <ThumbUpOffAltIcon onClick={changeGoodComment} />
                      <>{good}</>
                      <ThumbDownAltIcon onClick={cancelBadComment} />
                      <>{bad}</>
                      <div onClick={changeTogle}>
                        {replyTogle === true ? <>답글 접기</> : 
                        replyLength !== 0 ?
                        <>답글 {replyLength}개</>
                      :
                      <>답글</>
                      }
                      </div>
                    </Stack>
                  </Box>
                </Stack>
              </div>
            </Grid>
            <Grid item sx={1}>
              <PostCommentMoreButton data={props.comment} type={"comment"} />
            </Grid>
          </Grid>

          {replyTogle === true && (
            <Grid item xs={12}>
              <PostListItemCommentListItemReply
                commentIndex={props.comment.commentIndex}
              />
            </Grid>
          )}
        </Box>
      );
  }

  // return (
  //   <div>
  //     <Box>
  //       <div>
  //         <Stack direction="row" spacing={2}>
  //           <Avatar sx={{ width: 24, height: 24 }}></Avatar>
  //           <Box>
  //             <div>{props.comment.commentContent}</div>
  //             <Stack direction="row" spacing={2}>
  //               <ThumbUpOffAltIcon onClick={goodComment} />
  //               <>{commentInfo.good}</>
  //               <ThumbUpAltIcon onClick={cancelComment} />
  //               <>{commentInfo.good}</>
  //               <ThumbDownOffAltIcon onClick={badComment} />
  //               <>{commentInfo.bad}</>
  //               <ThumbDownAltIcon onClick={cancelComment} />
  //               <>{commentInfo.bad}</>
  //               <div onClick={changeTogle}>
  //                 {replyTogle === true ? <>답글 접기</> : <>답글</>}
  //               </div>
  //             </Stack>
  //           </Box>

  //           <PostCommentMoreButton />
  //         </Stack>
  //       </div>
  //     </Box>
  //     <div>{replyTogle === true && <PostListItemCommentListItemReply />}</div>
  //   </div>
  // );
};

export default PostListItemCommentListItem;
