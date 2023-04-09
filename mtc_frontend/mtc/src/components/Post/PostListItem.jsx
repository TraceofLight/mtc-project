// import { Swiper, SwiperSlide } from "swiper/react";
import Box from "@mui/material/Box";
// import Container from "@mui/material/Container";
import PostListItemPost from "./PostListItemPost";
import PostListItemComment from "./PostListItemComment";

// import "swiper/css";
// import "swiper/css/pagination";

// const PostListItem = (props) => {
//   return (
//     <Swiper
//       className="mySwiper2 swiper-v"
//       direction={"vertical"}
//       spaceBetween={0}
//     >
//       <SwiperSlide>
//         <PostListItemPost post={props.post} />
//       </SwiperSlide>

//       <SwiperSlide>
//         <Box sx={{ bgcolor: "#cfe8fc"}}>
//           <PostListItemComment index={props.id} />
//         </Box>
//       </SwiperSlide>
//     </Swiper>
//   );
// };

// export default PostListItem;

const PostListItem = (props) => {
  return (
    <>
      <PostListItemPost post={props.post} />
      <PostListItemComment index={props.post.articleIndex} />
    </>
  );
};
export default PostListItem;