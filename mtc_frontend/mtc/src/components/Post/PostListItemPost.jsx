import UnderNavigationBar from "components/UnderNavigationBar/UnderNavigationBar";
import { useSelector } from "react-redux";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import { Link } from "react-router-dom";

const PostListItemPost = (props) => {
  const userStatus = useSelector((state) => state.userStatus);
  const tagList = props.post.hashtagList.map((tag) => <>#{tag}&nbsp;&nbsp;</>);

  return (
    <>
      <Box sx={{ height: "10vh" }}>
        <Stack direction="row" spacing={3} sx={{ margin: "4vw" }}>
          <Link to={`/user/${props.post.userIndex}`}>
          <Avatar
            src={props.post.userPictureSource}
            sx={{ width: "3em", height: "3em" }}
          />
          </Link>
          <Stack>
            <div style={{ fontSize: "1.5em", fontWeight: 800 }}>
              {props.post.articleTitle}
            </div>
            <Stack direction="row">
              <div style={{ color: "gray" }}>{tagList}</div>
            </Stack>
          </Stack>
        </Stack>
        {/* <Grid container spacing={1} sx={{marginTop:"5%"}}>
        <Grid item xs={2.5}>
          
        </Grid>
        <Grid item xs={9}>
          
        </Grid>
      </Grid> */}
      </Box>
      <Box sx={{ height: "70vh", position: "relative", marginBottom: "5%" }}>
        <img
          src={props.post.articlePictureSource}
          alt=""
          style={{ width: "100%", height: "100%", objectFit: "contain" }}
        />
        <Box
          sx={{ position: "absolute", bottom: "5%", right: "5%", zIndex: 1 }}
        >
          <UnderNavigationBar post={props.post} />
        </Box>
      </Box>
    </>
  );
};

export default PostListItemPost;
