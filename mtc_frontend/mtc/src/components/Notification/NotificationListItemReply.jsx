import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";
import { Link } from "react-router-dom";

const NotificationListItemReply = (props) => {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={2}>
          <Link to={`/user/${props.alarm.alarmReplyUserIndex}`}>
            <Avatar src={props.alarm.alarmReplyUserPictureSource}></Avatar>
          </Link>
        </Grid>
        <Grid item xs={8}>
          <div>댓글에 답글이 달렸습니다.</div>
          <div>
            <b>{props.alarm.alarmReplyContent}</b>
          </div>
        </Grid>
        <Grid item xs={2}>
          <Link to={`/post/${props.alarm.alarmReplyArticleIndex}`}>
            <Avatar
              src={props.alarm.alarmReplyArticlePictureSource}
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
export default NotificationListItemReply;