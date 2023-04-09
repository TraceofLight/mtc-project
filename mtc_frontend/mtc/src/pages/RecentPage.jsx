import { useState, useEffect } from "react";
import PostList from "components/Post/PostList";
import { useSelector } from "react-redux";
import MainMenu from "components/MainMenu";
import { requestGet } from "api/axios";
import urls from "api/url";
import { useNavigate } from "react-router-dom";
const RecentPage = () => {
  const navigate = useNavigate();
  const userStatus = useSelector((state) => state.userStatus);

  const [recentList, setRecentList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const setHot = () => {

    console.log("핫");
    navigate("/Hot")
  };

  const setMain = () => {

    console.log("메인");
    navigate("/")
  };

  const setRecent = () => {

    navigate("/recent")
  };

  useEffect(() => {
    
    const getRecentList = async () => {
      while (!userStatus.userIndex) {
        await new Promise((resolve) => setTimeout(resolve, 1000));
      }

      const response = await requestGet(
        `${urls.article.getUserArticle}list?page=0&size=30&user=${userStatus.userIndex}`
      );
      
    setRecentList([...response.articleViewDtoList])
    }

    getRecentList();
    setIsLoading(false);
  }, []);

  if (isLoading) {
    return(
      <div>로딩중</div>
    )
  } else {
    return (
      <>
        <MainMenu setMain={setMain} setHot={setHot} setRecent={setRecent}/>
        <PostList postList={recentList}/>
      </>
    );
  };
};

export default RecentPage;