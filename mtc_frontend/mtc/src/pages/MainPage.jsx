import { useState, useEffect } from "react";
import PostList from "components/Post/PostList";
import { useSelector } from "react-redux";
import MainMenu from "components/MainMenu";
import { requestGet } from "api/axios";
import urls from "api/url";
import { useNavigate } from "react-router-dom";
const MainPage = () => {
  const navigate = useNavigate();
  const userStatus = useSelector((state) => state.userStatus);
  const [mainList, setMainList] = useState([]);
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
       
    const getMainList = async () => {
      while (!userStatus.userIndex) {
        await new Promise((resolve) => setTimeout(resolve, 1000));
      }
      const responsed = await requestGet(
        `${urls.article.getUserArticle}main?page=0&size=50&user=${userStatus.userIndex}`
      );

      console.log('메인메인');
      console.log(responsed.articleViewDtoList);
      console.log('메인메인');
      setMainList([...responsed]);
    };
    getMainList();
    
    setIsLoading(false);
    console.log("asdfasdf")
  }, []);

  if (isLoading) {
    return(
      <div>로딩중</div>
    )
  } else {
    return (
      <>
        <MainMenu setMain={setMain} setHot={setHot} setRecent={setRecent}/>
        <PostList postList={mainList}/>
      </>
    );
  };
};

export default MainPage;
