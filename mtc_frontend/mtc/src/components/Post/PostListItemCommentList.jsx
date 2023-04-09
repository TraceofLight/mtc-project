import PostListItemCommentListItem from "./PostListItemCommentListItem"
import { useState } from "react";
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import { Margin } from "@mui/icons-material";
// import axios from "axios";

const PostListItemCommentList = (props) => {
  // 프롭스로 해당 글 인덱스를 받아서 댓글 인덱스 목록 가져옴
  const [isEvaluate, setIsEvaluate] = useState(0);
  const checkEvaluate = () => {
    setIsEvaluate((target) => target + 1);
    console.log(isEvaluate)
  };


  const commentView = props.commentList.map((comment) => (
    <ListItem>
      <PostListItemCommentListItem key={comment} comment={comment} checkEvaluate={checkEvaluate} />
    </ListItem>
  ));

  return (
    <List sx={{width:"100%", marginLeft:-2}}>
      {commentView}
    </List>
  );
};

export default PostListItemCommentList;

// import PostListItemCommentListItem from "./PostListItemCommentListItem"
// import { useState } from "react";
// import axios from "axios";

// const PostListItemCommentList = (props) => {
//   // 프롭스로 해당 글 인덱스를 받아서 댓글 인덱스 목록 가져옴
//   const [isEvaluate, setIsEvaluate] = useState(0);
//   const [evaluateState, setEvaluateState] = useState(0)
//   const checkEvaluate = () => {
//     setIsEvaluate((target) => target + 1);
//     console.log(isEvaluate)
//   };



//   const commentView = (comment, index) => {
//     const EvaluateState = async (comment) => {
//       try {
//         const response = await axios.get(`http://localhost:8080/comment/readCommentLikeByUserIndexAndCommentIndex/${comment.commentIndex}&1`);
        
//         // return response.data
//       } catch(error) {
//         console.log('오류ㅜ남');
//       }
//     };
//     return (
//     <div>
//       <PostListItemCommentListItem comment={comment} index={index} checkEvaluate={checkEvaluate} state={evaluateState} />
//     </div>)
//   };

//   return (
//     <div>
//       {props.commentList.map((comment, index) => (
//         commentView(comment, index)
//       ))}
//     </div>
//   );
// };

// export default PostListItemCommentList;

// import PostListItemCommentListItem from "./PostListItemCommentListItem"
// import { useState, useEffect } from "react";
// import axios from "axios";

// const PostListItemCommentList = (props) => {
//   // 프롭스로 해당 글 인덱스를 받아서 댓글 인덱스 목록 가져옴
//   const [isEvaluate, setIsEvaluate] = useState(0);
//   const [evaluateState, setEvaluateState] = useState(0)
//   const checkEvaluate = () => {
//     setIsEvaluate((target) => target + 1);
//     console.log(isEvaluate)
//   };



//   const commentView = (comment, index) => {
//     <div>
//           <PostListItemCommentListItem comment={comment} index={index} checkEvaluate={checkEvaluate} state={evaluateState} />
//         </div>
//   };
    
//   const EvaluateState = async (comment) => {
//     try {
//       const response = await axios.get(`http://localhost:8080/comment/readCommentLikeByUserIndexAndCommentIndex/${comment.commentIndex}&1`);
//       console.log("asdfasdfasdf");
// console.log(response.data);
// console.log("asdfasdfasdf");
// setEvaluateState(response.data)
//       return response.data
//     } catch(error) {
//       console.log('오류ㅜ남');
//     }
//   };
  

//   return (
//     <div>
//       {props.commentList.map((comment, index) => {
        
//         EvaluateState(comment)
//       return (
//         <PostListItemCommentListItem comment={comment} index={index} checkEvaluate={checkEvaluate} state={evaluateState} />
//         );
// })}
//   </div>
//   );
// };

// export default PostListItemCommentList;