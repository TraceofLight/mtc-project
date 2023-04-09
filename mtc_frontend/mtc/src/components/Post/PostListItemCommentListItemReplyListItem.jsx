// import PostCommentMoreButton from "./PostCommentMoreButton";
// import Avatar from "@mui/material/Avatar";
// import Stack from "@mui/material/Stack";
// import Box from "@mui/material/Box";
// import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
// import ThumbDownOffAltIcon from "@mui/icons-material/ThumbDownOffAlt";

// const PostListItemCommentListItemReplyListItem = (props) => {
//   return (
//     <Box marginLeft={5}>
//       <Stack direction="row" spacing={2}>
//         <Avatar

//           sx={{ width: 24, height: 24 }}
//         >S</Avatar>
//         <Box>
//           <div>
//             {props.index}번 답글이에[요 내용은 {props.replyId}다
//           </div>
//           <Stack direction="row" spacing={2}>
//             <ThumbUpOffAltIcon />
//             <ThumbDownOffAltIcon />
//           </Stack>
//         </Box>
//         <PostCommentMoreButton />
//       </Stack>
//     </Box>
//   );
// };

// export default PostListItemCommentListItemReplyListItem;

import { useState, useEffect } from "react";
import Box from "@mui/system/Box";
import PostReplyMoreButton from "./PostReplyMoreButton";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
import ThumbDownOffAltIcon from "@mui/icons-material/ThumbDownOffAlt";
import ThumbUpAltIcon from "@mui/icons-material/ThumbUpAlt";
import ThumbDownAltIcon from "@mui/icons-material/ThumbDownAlt";
import axios from "axios";
import { useSelector } from "react-redux";
import Grid from "@mui/material/Grid";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { requestGet } from "api/axios";
import { useNavigate } from "react-router-dom";
const PostListItemCommentListItemReplyListItem = (props) => {
  const navigate = useNavigate();
  const userStatus = useSelector((state) => state.userStatus);
  const [evaluate, setEvaluate] = useState(0);
  const [bad, setBad] = useState(props.reply.bad);
  const [good, setGood] = useState(props.reply.good);
  const toUserPage = () => {
    navigate(`/user/${props.reply.replyUserIndex}`)
  }
  const evaluateReply = async (evaluate) => {
    try {
      const response = await axios.post(
        `${urls.reply}createLike`,
        {
          replyLikeReplyIndex: props.reply.replyIndex,
          replyLikeUserIndex: userStatus.userIndex,
          replyLikeValuate: evaluate,
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

  const goodReply = () => {
    evaluateReply(1);
    setGood((target) => target + 1);
    setEvaluate(1);
  };
  const badReply = () => {
    evaluateReply(-1);
    setBad((target) => target + 1);
    setEvaluate(-1);
  };
  const cancelGoodReply = () => {
    evaluateReply(0);
    setGood((target) => target - 1);
    setEvaluate(0);
  };
  const cancelBadReply = () => {
    evaluateReply(0);
    setBad((target) => target - 1);
    setEvaluate(0);
  };
  const changeBadReply = () => {
    evaluateReply(-1);
    setBad((target) => target + 1);
    setGood((target) => target - 1);
    setEvaluate(-1);
  };
  const changeGoodReply = () => {
    evaluateReply(1);
    setBad((target) => target - 1);
    setGood((target) => target + 1);
    setEvaluate(1);
  };

  useEffect(() => {
    const EvaluateState = async (reply) => {
      const response = await requestGet(
        `${urls.reply}readReplyLikeByUserIndexAndReplyIndex/${reply.replyIndex}&${userStatus.userIndex}`
      );

      console.log(reply.replyIndex);
      console.log(response);
      console.log("asdfasdfasdf");
      console.log(userStatus.userIndex);
      if (response) {
        setEvaluate(response);
      } else {
        setEvaluate(0);
      }
    };
    EvaluateState(props.reply);
  }, [props.reply]);

  switch (evaluate) {
    default:
      return <div></div>;
    case 0:
      return (
        <Box sx={{ width: "100%" }}>
          <Grid container spacing={3.6}>
            <Grid item xs={10}>
              <div>
                <Stack direction="row" spacing={2}>
                  <Avatar
                    src={props.reply.replyUserPictureSource}
                    sx={{ width: "1.5em", height: "1.5em" }}
                    onClick={toUserPage}
                  ></Avatar>
                  <Box>
                    <div>{props.reply.replyContent}</div>
                    <Stack direction="row" spacing={2}>
                      <ThumbUpOffAltIcon onClick={goodReply} />
                      <>{good}</>
                      <ThumbDownOffAltIcon onClick={badReply} />
                      <>{bad}</>
                    </Stack>
                  </Box>
                </Stack>
              </div>
            </Grid>
            <Grid item xs={1}>
              <PostReplyMoreButton data={props.reply} type={"reply"} />
            </Grid>
          </Grid>
        </Box>
      );
    case 1:
      return (
        <Box sx={{ width: "100%" }}>
          <Grid container spacing={3.6}>
            <Grid item xs={10}>
              <div>
                <Stack direction="row" spacing={2}>
                  <Avatar
                    src={props.reply.replyUserPictureSource}
                    sx={{ width: "1.5em", height: "1.5em" }}
                    onClick={toUserPage}
                  ></Avatar>
                  <Box>
                    <div>{props.reply.replyContent}</div>
                    <Stack direction="row" spacing={2}>
                      <ThumbUpAltIcon onClick={cancelGoodReply} />
                      <>{good}</>
                      <ThumbDownOffAltIcon onClick={changeBadReply} />
                      <>{bad}</>
                    </Stack>
                  </Box>
                </Stack>
              </div>
            </Grid>
            <Grid item xs={1}>
              <PostReplyMoreButton data={props.reply} type={"reply"} />
            </Grid>
          </Grid>
        </Box>
      );

    case -1:
      return (
        <Box sx={{ width: "100%" }}>
          <Grid container spacing={3.6}>
            <Grid item xs={10}>
              <div>
                <Stack direction="row" spacing={2}>
                  <Avatar
                    src={props.reply.replyUserPictureSource}
                    sx={{ width: "1.5em", height: "1.5em" }}
                    onClick={toUserPage}
                  ></Avatar>
                  <Box>
                    <div>{props.reply.replyContent}</div>
                    <Stack direction="row" spacing={2}>
                      <ThumbUpOffAltIcon onClick={changeGoodReply} />
                      <>{good}</>
                      <ThumbDownAltIcon onClick={cancelBadReply} />
                      <>{bad}</>
                    </Stack>
                  </Box>
                </Stack>
              </div>
            </Grid>
            <Grid item xs={1}>
              <PostReplyMoreButton data={props.reply} type={"reply"} />
            </Grid>
          </Grid>
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

export default PostListItemCommentListItemReplyListItem;
