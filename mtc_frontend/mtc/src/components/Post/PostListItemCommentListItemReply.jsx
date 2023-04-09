// import PostListItemCommentListItemReplyInput from './PostListItemCommentListItemReplyInput'
// import PostListItemCommentListItemReplyList from './PostListItemCommentListItemReplyList'

// const PostListItemCommentListItemReply = (props) => {
//   return (
//     <div>
//       <PostListItemCommentListItemReplyInput commentIndex={props.commentIndex} />
//       <PostListItemCommentListItemReplyList commentIndex={props.commentIndex} />
//     </div>
    
//   );
// };

// export default PostListItemCommentListItemReply;

import PostListItemCommentListItemReplyInput from './PostListItemCommentListItemReplyInput'
import PostListItemCommentListItemReplyList from './PostListItemCommentListItemReplyList'
import { useState,useEffect } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import Box from "@mui/material/Box";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";

const PostListItemCommentListItemReply = (props) => {
  const userStatus = useSelector((state) => state.userStatus);
  const [replyList, setReplyList] = useState([]);
  const [isUpdate, setIsUpdate] = useState(1);
  const [isLoading, setLoading] = useState(true);

  const getReply = async () => {
    try {
      const response = await axios.get(
        `${urls.reply}readReplyByCommentIndex/${props.commentIndex}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },}
      )
      if (response.status === 204) {
        setReplyList([]);
      } else {
        setReplyList(response.data);
      }
      
      setLoading(false)

    } catch (error) {
      console.log('안됨')
    }
    
  }

  const inputReply = () => {
    console.log('asdfasdf')
    setIsUpdate((target) => target + 1);
    console.log(isUpdate)
  }

  useEffect(() => {
    console.log(replyList)
    getReply();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isUpdate]);

  if (isLoading) {
    return <></>;
  }

  return (
    <>
    <Box sx={{marginY:2, marginLeft:2}}>
      <PostListItemCommentListItemReplyInput index={props.commentIndex} inputReply={inputReply}/>
    </Box>
    <Box>

      {replyList !== [] && <PostListItemCommentListItemReplyList replyList={replyList}/>}
    </Box>
    </>
  );
};

export default PostListItemCommentListItemReply;
