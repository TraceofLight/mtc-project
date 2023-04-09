import * as React from "react";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";
import Input from "@mui/material/Input";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import axios from "axios";
import { useSelector } from "react-redux";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";

const ariaLabel = { "aria-label": "description" };

const PostListItemCommentInput = (props) => {
  const [comment, setComment] = React.useState("");
  const userStatus = useSelector((state) => state.userStatus);
  const commentRef = React.useRef();

  const TypeComment = (event) => {
    console.log(event.target.value);
    setComment(event.target.value);
  };

  const RequestComment = async () => {
    try {
      const response = await axios.post(
        `${urls.comment}create`,
        {
          commentArticleIndex: props.index,
          commentContent: comment,
          commentUserIndex: userStatus.userIndex,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
          },
        }
      );
      console.log(response);
      props.inputComment();
    } catch (error) {
      alert(error.message);
    }
  };

  const PostComment = () => {
    if (!comment.trim()) {
      alert("댓글을 입력해주세요.");
    } else {
      RequestComment();
    }

    setComment("");
    commentRef.current.focus();
  };

  return (
    <Grid container spacing={2} alignItems="end">
      <Grid item xs={9}>
        <Stack direction="row" spacing={2}>
          <Avatar
            alt="#"
            src={userStatus.userProfilePictureSource}
            sx={{ width: "2em", height: "2em" }}
          />
          <Input
            placeholder="댓글 입력"
            inputProps={ariaLabel}
            onChange={TypeComment}
            ref={commentRef}
            value={comment}
            sx={{ width: "100%" }}
          />
        </Stack>
      </Grid>
      <Grid item xs={3}>
        <Button
          variant="contained"
          onClick={PostComment}
          disableRipple
          size="small"
          style={{
            backgroundColor: "#6DCEF5",
            color: "#11111",
          }}
        >
          작성
        </Button>
      </Grid>
    </Grid>
  );
};

export default PostListItemCommentInput;
