import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import addPicture from "../../assets/icons/Logo_Add_Picture.png";
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
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
// import { usePrompt } from './Blocker';
// import TagList from "./TagList";
// css
import "./CreatePost.css";

const CreatePost = ({ parentsFunction }) => {
  // usePrompt('내용이 사라져요', true);
  const userStatus = useSelector((state) => state.userStatus);
  const [title, setTitle] = React.useState("");
  const [image, setImage] = React.useState(addPicture);
  const [imageFile, setImageFile] = React.useState("");
  const [visible, setVisible] = React.useState(true);
  const imageRef = React.useRef();
  const [tagList, setTagList] = React.useState([]);
  const Navigate = useNavigate();
  const AuthenticationInstance = getAuth();

  console.log(AuthenticationInstance.currentUser);

  const tagChange = (tags) => {
    setTagList(tags);
    console.log(tagList);
  };

  const onVisible = () => {
    setVisible(true);
    parentsFunction();
  };

  const offVisible = () => {
    setVisible(false);
    parentsFunction();
  };

  const typeTitle = (event) => {
    setTitle(event.target.value);
    console.log(title);
    parentsFunction();
  };

  const saveImgFile = () => {
    const file = imageRef.current.files[0];
    if (file.size <= 5*1024*1024) {
      setImageFile(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        setImage(reader.result);
        console.log("asdfsdafsadfsadfsadf");
        console.log(imageFile);
        console.log("asdfsdafsadfsadfsadf");
        console.log(image);
        parentsFunction();
      };
    } else {
      alert("사진은 5MB 이하로 가능합니다.");
    }
  };

  const checkPost = async () => {
    try {
      const formData = new FormData();
      formData.append("multipartFile", imageFile);

      let url = `${urls.article.getUserArticle}?title=${title}`;
      tagList.map((tag, index) => (url += `&hashtagList=${tag}`));
      url += `&user=${userStatus.userIndex}&visible=${visible}`;

      const response = await axios.post(url, formData, {
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
          <TextField
            id="standard-basic"
            label="제목"
            variant="standard"
            onChange={typeTitle}
          />

          <div id="InputBox">
            <IconButton
              color="#6DCEF5"
              aria-label="upload picture"
              component="label"
              sx={{ marginRight: 6 }}
            >
              <input
                hidden
                accept="image/*"
                type="file"
                onChange={saveImgFile}
                ref={imageRef}
              />
              <img id="imgId" src={image} alt="hi" />
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
      <Grid container mt={5}>
        <Grid item xs={8} />
        <Grid item xs={4}>
          <Box>
            <Button
              onClick={checkPost}
              disableRipple
              prev
              variant="contained"
              style={{
                backgroundColor: "#6DCEF5",
                color: "#11111",
              }}
            >
              &nbsp; &nbsp; 작성 &nbsp; &nbsp;
            </Button>
          </Box>
        </Grid>
      </Grid>
    </>
  );
};

export default CreatePost;
