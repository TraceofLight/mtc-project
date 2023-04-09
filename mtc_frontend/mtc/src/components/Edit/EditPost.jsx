import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import TagInput from "./TagInput";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormLabel from "@mui/material/FormLabel";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { getAuth } from "firebase/auth";
import { useSelector } from "react-redux";
import { useEffect,useState } from "react";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { requestGet, requestDelete } from "api/axios";
// import { usePrompt } from './Blocker';
// import TagList from "./TagList";
// css
import "./EditPost.css";

import { useParams } from "react-router-dom";

const EditPost = () => {
  const userStatus = useSelector((state) => state.userStatus);
  const [isLoading, setIsLaoding] = useState(true);
  const [visible, setVisible] = useState(true);
  const [tagList, setTagList] = useState([]);
  const [article, setArticle] = useState([]);
  const Navigate = useNavigate();
  const AuthenticationInstance = getAuth();
  console.log(AuthenticationInstance.currentUser);

  const tagChange = (tags) => {
    setTagList(tags);
    console.log(tagList);
  };
  const params = useParams();
  const onVisible = () => {
    setVisible(true);
  };

  const offVisible = () => {
    setVisible(false);
  };
  const deletePost = async () => {
    await requestDelete(`${urls.article.getUserArticle}${params.articleindex}?user=${userStatus.userIndex}`);
    Navigate("/");
  };
  const editPost = async () => {
    try {
      let url = `${urls.article.getUserArticle}?article=`;
      tagList.map((tag, index) => (url += `&hashtagList=${tag}`));
      url += `&user=${userStatus.userIndex}&visible=${visible}`;

      const response = await axios.post(url, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },
      });
      console.log(response);
      Navigate(`/post/${response.data.articleIndex}`);
    } catch (error) {
      alert(error.message);
    }
  };
  useEffect(() => {
    const getArticle = async () => {
      const response = await requestGet(
        `${urls.article.getUserArticle}${params.articleindex}?user=${userStatus.userIndex}`
      );
      setArticle(response);
      setTagList(response.hashtagList)
    };
    getArticle();
    setIsLaoding(false)
  }, []);
  if (isLoading) {
 return <div></div>
  } else {
  return (
    <>
      <Grid container>
        <Box
          clone
          mt={5}
          component="form"
          sx={{
            "& > :not(style)": { m: 1, width: "80%" },
            marginLeft: 3,
          }}
          noValidate
          autoComplete="off"
        >
          <TextField disabled id="standard-basic" label="제목" defaultValue={article.articleTitle} variant="standard"/>
            

          <div id="InputBox">
            <IconButton
              color="#6DCEF5"
              aria-label="upload picture"
              component="label"
              sx={{ marginRight: 6 }}
            >
              <img id="imgId" src={article.articlePictureSource} alt="hi" />
            </IconButton>
          </div>

          <TagInput tagChange={tagChange} />
        </Box>

        <Box clone mt={4} sx={{ margin: "auto" }}>
          <Stack direction="row" spacing={1} justifyContent="center">
            <div id="OpenDiv">공개여부</div>
            <FormLabel id="demo-row-radio-buttons-group-label"></FormLabel>
            <RadioGroup
              row
              defaultValue="visible"
              aria-labelledby="demo-row-radio-buttons-group-label"
              name="row-radio-buttons-group"
            >
              &nbsp; &nbsp;
              <FormControlLabel
                value="visible"
                control={
                  <Radio
                    sx={{
                      color: "#6DCEF5",
                      "&.Mui-checked": {
                        color: "#6DCEF5",
                      },
                    }}
                  />
                }
                onClick={onVisible}
                label="공개"
              />
              <FormControlLabel
                value="invisible"
                control={
                  <Radio
                    sx={{
                      color: "#6DCEF5",
                      "&.Mui-checked": {
                        color: "#6DCEF5",
                      },
                    }}
                  />
                }
                onClick={offVisible}
                label="비공개"
              />
            </RadioGroup>
          </Stack>
        </Box>
      </Grid>
      <Grid container marginX={"auto"} mt={5}>
        <Grid item xs={3}></Grid>
        <Grid item xs={3}>
          <Box>
            <Button
              onClick={editPost}
              disableRipple
              prev
              variant="contained"
              style={{
                backgroundColor: "#6DCEF5",
                color: "#11111",
              }}
            >
              &nbsp; &nbsp; 수정 &nbsp; &nbsp;
            </Button>
          </Box>
        </Grid>
        <Grid item xs={3}>
          <Box>
            <Button
              onClick={deletePost}
              disableRipple
              prev
              variant="contained"
              style={{
                backgroundColor: "#ff0000",
                color: "#11111",
              }}
            >
              &nbsp; &nbsp; 삭제 &nbsp; &nbsp;
            </Button>
          </Box>
        </Grid>
      </Grid>
    </>
  );}
};

export default EditPost;
