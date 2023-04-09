import { useState, useEffect } from "react";
import axios from "axios";
import PostListItemCommentInput from "./PostListItemCommentInput";
import PostListItemCommentList from "./PostListItemCommentList";
import Container from "@mui/material/Container";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { requestPost } from "api/axios";
import { useSelector } from "react-redux";

const PostListItemComment = (props) => {
  const userStatus = useSelector(state => state.userStatus);
  const [commentList, setCommentList] = useState([]);
  const [isUpdate, setIsUpdate] = useState(1);
  const [isLoading, setLoading] = useState(true);

  const getComment = async () => {
    try {
      const response = await axios.get(
        `${urls.comment}readCommentByArticleIndex/${props.index}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },}
      );
      if (response.status === 204) {
        setCommentList([]);
      } else {
        setCommentList(response.data);
      }

      setLoading(false);
    } catch (event) {
      console.log("안됨");
    }
  };
  const viewCount = async () => {
    await requestPost(`${urls.article.getUserArticle}recent?article=${props.index}&user=${userStatus.userIndex}`)
  }
  const inputComment = () => {
    console.log("asdfasdf");
    setIsUpdate((target) => target + 1);
    console.log(isUpdate);
  };
  const check = () => {
    if (commentList !== []) {
      console.log('있습니다')
      
    } else {
      console.log('없습니다')
    }
  }
  useEffect(() => {
    {isLoading && 
      viewCount();
    }
    console.log(commentList);
    getComment();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isUpdate]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <Container>
      <PostListItemCommentInput
        index={props.index}
        inputComment={inputComment}
      />
      
        {commentList !== [] ? (
          <PostListItemCommentList commentList={commentList} />
          
        )
      : (
        <div>댓글이 없습니다.</div>
      )}
    </Container>
  );
};

export default PostListItemComment;
