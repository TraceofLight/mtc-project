import { useState, useEffect } from "react";
import axios from "axios";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import { useSelector } from "react-redux";
import SearchListItem from "./SearchListItem"
import { NavLink } from "react-router-dom";
import Paper from '@mui/material/Paper';
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { currentUser } from "firebaseConfig";
const SearchList = (props) => {
  const [postList, setPostList] = useState([]);
  const userStatus = useSelector(state => state.userStatus);
  let url;
  if (props.search === "tag") {
    url = `${urls.article.getUserArticle}search/hashtag?hashtag`
  } else {
    url = `${urls.article.getUserArticle}search/title?keyword`
  }

  const searchPostList = async () => {
    while (!currentUser || !currentUser.accessToken) {
      await new Promise((resolve) => setTimeout(resolve, 1000));
    }
    try {
      
      const response = await axios.get(
        `${url}=${props.keyword}&user=${userStatus.userIndex}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${currentUser.accessToken}`,

        },}
      );
      console.log(response.data);
      setPostList(response.data.articleViewDtoList);
    } catch (error) {
      console.log(error);
      console.log(currentUser.accessToken);
      console.log('asdfasdfasdf');
    }
  };

  const getPostPage = () => {
    console.log('이동');
  }

  const ViewPostList = postList.map((post) => (
    <>
    <NavLink to={`/post/${post.articleIndex}`} style={{ textDecoration: 'none' }}>
    <ListItem key={post.id}>
      
      <ListItemButton onClick={getPostPage}>
        <SearchListItem post={post} />
      </ListItemButton>
    </ListItem>
    </NavLink>
    <Divider  sx={{ width:"85%", margin:"auto"}} />
    </>
  ));

  useEffect(() => {
    console.log(props.keyword);
    searchPostList();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.keyword]);

  return (
    <div style={{ display: 'flex', justifyContent: 'center'}}>
    <Paper sx={{ width: '85%' }} elevation={0}>
      {postList && <List component="nav">{ ViewPostList}</List>}
    </Paper>
    </div>
  );
};
export default SearchList;
