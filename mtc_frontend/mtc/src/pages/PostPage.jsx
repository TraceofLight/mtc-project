import PostListItem from "../components/Post/PostListItem";
import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { currentUser } from "firebaseConfig";
import { useNavigate } from "react-router-dom";
const PostPage = () => {
  const userStatus = useSelector(state => state.userStatus);
  const [isLoading, setLoading] = useState(true);
  const [post, setPost] = useState();
  const params = useParams();
  const navigate = useNavigate()
  const getPost = async () => {
    while (!currentUser || !currentUser.accessToken) {
      await new Promise((resolve) => setTimeout(resolve, 1000));
    }
    try {
      const response = await axios.get(
        `${urls.article.getUserArticle}${params.articleindex}?user=${userStatus.userIndex}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
          },
        }
      );
      setPost(response);
      setLoading(false);
    } catch (error) {
      console.log(error);
      navigate("/notfound")
    }
  };

  useEffect(() => {
    console.log("불러왔다");
    getPost();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <PostListItem post={post.data} />
    </div>
  );
};

export default PostPage;
