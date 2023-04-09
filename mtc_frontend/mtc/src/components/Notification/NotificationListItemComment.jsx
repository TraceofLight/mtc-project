import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";
import { Link } from "react-router-dom";

const NotificationListItemComment = (props) => {
  console.log(props);
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={2}>
          <Link to={`/user/${props.alarm.alarmCommentUserIndex}`}>
            <Avatar src={props.alarm.alarmCommentUserPictureSource}></Avatar>
          </Link>
        </Grid>
        <Grid item xs={8}>
          <div>
            {props.alarm.alarmCommentUserNickname}님이 댓글을 달았습니다.
          </div>
          <div>
            <b>{props.alarm.alarmCommentContent}</b>
          </div>
        </Grid>
        <Grid item xs={2}>
          <Link to={`/post/${props.alarm.alarmCommentArticleIndex}`}>
            <Avatar
              src={props.alarm.alarmCommentArticlePictureSource}
              variant="square"
            >
              게시글 사진
            </Avatar>
          </Link>
        </Grid>
      </Grid>
    </Box>
  );
};
export default NotificationListItemComment;
