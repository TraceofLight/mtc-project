// import { Swiper, SwiperSlide } from "swiper/react";
// import PostListItem from "./PostListItem";
// // import Box from '@mui/material/Box';
// // import Container from '@mui/material/Container';

// // import "swiper/css";
// // import "swiper/css/pagination";
// // import "./Post.css";

// const PostList = () => {
//   const postsList = [
//     {
//       "userIndex": 1,
//       "userNickname": "존시나",
//       "userPictureSource": "",
//       "articleIndex": 2,
//       "articleTitle": "ㅁㄴㅇㄹ",
//       "articlePictureSource": "https://s3.ap-northeast-2.amazonaws.com/mtc-test-1/article/bdd48693-2a17-4c16-9d69-bbcca0c4cafc.gif",
//       "articleHit": 0,
//       "hashtagList": [],
//       "goodCount": 3,
//       "sosoCount": 1,
//       "badCount": 0,
//       "evaluateValue": "GOOD"
//     },
//     {
//       "userIndex": 1,
//       "userNickname": "존시나",
//       "userPictureSource": "",
//       "articleIndex": 6,
//       "articleTitle": "ㅁㄴㅇㄻㄴㅇㄹ",
//       "articlePictureSource": "https://s3.ap-northeast-2.amazonaws.com/mtc-test-1/article/7352d081-79ab-4160-ae0a-648b66b4d7de.png",
//       "articleHit": 0,
//       "hashtagList": [],
//       "goodCount": 0,
//       "sosoCount": 0,
//       "badCount": 0,
//       "evaluateValue": "NONE"
//     },
//     {
//       "userIndex": 4,
//       "userNickname": "4번",
//       "userPictureSource": "",
//       "articleIndex": 7,
//       "articleTitle": "ㅁㄴㅇㄻㄴㅇㄹ",
//       "articlePictureSource": "https://s3.ap-northeast-2.amazonaws.com/mtc-test-1/article/632b47a9-3069-4ea9-94f9-040311ca5b6d.png",
//       "articleHit": 0,
//       "hashtagList": [],
//       "goodCount": 0,
//       "sosoCount": 0,
//       "badCount": 0,
//       "evaluateValue": "NONE"
//     },
//     {
//       "userIndex": 4,
//       "userNickname": "4번",
//       "userPictureSource": "",
//       "articleIndex": 8,
//       "articleTitle": "ㅁㄴㅇㄹ",
//       "articlePictureSource": "https://s3.ap-northeast-2.amazonaws.com/mtc-test-1/article/64355aed-73bf-43a7-8798-4f6ffb7066fe.png",
//       "articleHit": 0,
//       "hashtagList": [
//         "ㅁㄴㅇ",
//         "ㅁㄴㅇㄹ"
//       ],
//       "goodCount": 0,
//       "sosoCount": 0,
//       "badCount": 0,
//       "evaluateValue": "NONE"
//     },
//     {
//       "userIndex": 5,
//       "userNickname": "조조립제",
//       "userPictureSource": "",
//       "articleIndex": 10,
//       "articleTitle": "ㅁㄴㅇㄹ",
//       "articlePictureSource": "https://s3.ap-northeast-2.amazonaws.com/mtc-test-1/article/530e4734-2d84-42a9-a685-ddb66664f35f.png",
//       "articleHit": 0,
//       "hashtagList": [
//         "파이어베이스"
//       ],
//       "goodCount": 0,
//       "sosoCount": 0,
//       "badCount": 0,
//       "evaluateValue": "NONE"
//     }
//   ];

//   const postView = postsList.map((post) => (
//     <SwiperSlide>
//       <PostListItem post={post} />
//     </SwiperSlide>
//   ));
//   return (
//     <>
//       <Swiper
//         className="mySwiper swiper-h"
//         spaceBetween={50}
//         pagination={{
//           clickable: true,
//         }}
//       >
//         {postView}
//       </Swiper>
//     </>
//   );
// };

// export default PostList;

import Box from "@mui/material/Box";

import React, { useState } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import PostListItemPost from "./PostListItemPost";
import PostListItemComment from "./PostListItemComment";

const settings = {
  dots: false,
  infinite: false,
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 1,
  lazyLoad: "ondemand",
  
};

const PostList = (props) => {
  

  console.log(props.hotList)
  return (
    <>
    <Slider {...settings}>
      {props.postList.map((item, index) => (
        <div key={index}>
          <Box sx={{position: "relative"}}>
          <PostListItemPost post={item}/>
          <PostListItemComment index={item.articleIndex} />
          </Box>
        </div>
      ))}
    </Slider>
    </>
  );
};

export default PostList;
