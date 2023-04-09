import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";

const NotificationListItemArticle = (props) => {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={2}>
          <Avatar src={props.alarm.alarmArticlePictureSource} variant="square"></Avatar>
        </Grid>
        <Grid item xs={9}>
          <Box sx={{my:"auto"}}>
          
          <div style={{margintop:"auto"}}><b>당신의 '{props.alarm.alarmArticleTitle}' 게시물이</b></div>
          <div><b>{props.alarm.alarmArticleMaxGood} ㅅㅌㅊ 돌파!!!</b></div>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};
export default NotificationListItemArticle;