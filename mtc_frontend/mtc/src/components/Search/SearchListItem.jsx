import Avatar from "@mui/material/Avatar";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import VisibilityIcon from "@mui/icons-material/Visibility";

const SearchListItem = (props) => {
  const tagList = props.post.hashtagList.map((tag) => <>#{tag}&nbsp;&nbsp;</>);
  console.log(props);

  return (
    <>
      <Grid container spacing={3}>
        <Grid item xs={4}>
          <Avatar
            src={props.post.articlePictureSource}
            variant="rounded"
            sx={{ width: "4em", height: "4em" }}
          />
        </Grid>
        <Grid item xs={8}>
          <Stack>
            <div style={{ fontSize: "1.1em" }}>
              <b style={{ color: "black" }}>{props.post.articleTitle}</b>
            </div>
            <div style={{ color: "gray" }}>{tagList}</div>
            <div>
              <Stack
                direction="row"
                spacing={1}
                alignItems="center"
                marginTop={0.8}
              >
                <Avatar
                  sx={{ width: "0.8em", height: "0.8em", bgcolor: "#6DCEF5" }}
                >
                  <b>ㅅ</b>
                </Avatar>
                <div style={{ color: "black" }}>{props.post.goodCount}&nbsp;&nbsp;</div>
                <Avatar
                  sx={{ width: "0.8em", height: "0.8em", bgcolor: "#6DCEF5" }}
                >
                  <b>ㅎ</b>
                </Avatar>
                <div style={{ color: "black" }}>{props.post.badCount}&nbsp;&nbsp;</div>
                <VisibilityIcon sx={{ width: "0.8em", height: "0.8em", color: "black"}} />
                <div style={{ color: "black" }}>{props.post.articleHit}</div>
              </Stack>
            </div>
          </Stack>
        </Grid>
      </Grid>
    </>
  );
};
export default SearchListItem;
