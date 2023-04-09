import { useState, useEffect } from "react";
import axios from "axios";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import Paper from '@mui/material/Paper';
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";

const TagList = (props) => {
  const [tagList, setTagList] = useState([]);


  const searchTagList = async () => {
    try {
      const response = await axios.get(
        `${urls.article.getUserArticle}hashtag?keyword=${props.keyword}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },}
      );
      setTagList(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const ViewTagList = tagList.map((tag) => (
    <>
    <ListItem key={tag.id}>
      <ListItemButton onClick={() => props.TagSearch(tag.tagname)}>
        <ListItemText primary={"#" + tag.tagname} />
      </ListItemButton>
    </ListItem>
    <Divider  sx={{ width:"85%", margin:"auto"}} />
    </>
  ));

  useEffect(() => {
    console.log(props.keyword);
    searchTagList();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.keyword]);

  return (
    <div style={{ display: 'flex', justifyContent: 'center'}}>
    <Paper sx={{ width: '85%' }} elevation={0}>
      {tagList && <List component="nav">{ViewTagList}</List>}
    </Paper>
    </div>
  );
};
export default TagList;
