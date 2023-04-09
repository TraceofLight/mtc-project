// import PostListItemCommentListItemReplyListItem from "./PostListItemCommentListItemReplyListItem"

// const PostListItemCommentListItemReplyList = (props) => {

//   const replys = ['답', '글', '입'];

//   const replyView = replys.map((replyId, index) => (
//     <div>
//       <PostListItemCommentListItemReplyListItem replyId={replyId} index={index}/>
//     </div>
//   ));

//   return (
//     <div>
//       {replyView}
//     </div>
//   );
// }

// export default PostListItemCommentListItemReplyList;
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import PostListItemCommentListItemReplyListItem from "./PostListItemCommentListItemReplyListItem";
import { useState } from "react";
// import axios from "axios";

const PostListItemCommentListItemReplyList = (props) => {
  // 프롭스로 해당 글 인덱스를 받아서 댓글 인덱스 목록 가져옴
  const [isEvaluate, setIsEvaluate] = useState(0);
  const checkEvaluate = () => {
    setIsEvaluate((target) => target + 1);
    console.log(isEvaluate);
    console.log('삭제')
  };

  const replyView = props.replyList.map((reply) => (
    <ListItem>
      <PostListItemCommentListItemReplyListItem
        key={reply.index}
        reply={reply}
        checkEvaluate={checkEvaluate}
      />
    </ListItem>
  ));

  return <List sx={{ width: "100%"}}>{replyView}</List>;
};

export default PostListItemCommentListItemReplyList;
