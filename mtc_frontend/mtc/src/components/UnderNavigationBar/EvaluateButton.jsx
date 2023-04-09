// import Fab from "@mui/material/Fab";
// import Box from "@mui/material/Box";
// import { useState } from "react";
// import { useSelector } from "react-redux";
// import axios from "axios";
// import Avatar from "@mui/material/Avatar";
// import "./EvaluateButton.css";

// const EvaluateButton = (props) => {
//   console.log("asdfasdfasdfasdfasdfasdf");
//   console.log(props.post);
//   console.log("asdfasdfasdfasdfasdfasdf");
//   // const [evaluate,setEvaluate] = useState(props.article.evaluateValue)
//   // const [good,setGood] = useState(props.article.goodCount)
//   // const [soso,setSoso] = useState(props.article.sosoCount)
//   // const [bad, setBad] = useState(props.article.badCount)

//   const [evaluate, setEvaluate] = useState(props.post.evaluateValue);
//   const [good, setGood] = useState(props.post.goodCount);
//   const [soso, setSoso] = useState(props.post.sosoCount);
//   const [bad, setBad] = useState(props.post.badCount);
//   const userStatus = useSelector((state) => state.userStatus);

//   const goodButton = (
//     <>
//       <Avatar
//         className="evaluatebutton"
//         sx={{
//           width: "100%",
//           height: "100%",
//           fontSize: "6em",
//           fontWeight: 400,
//           color: "black",
//           bgcolor: "white",
//         }}
//       >
//         ㅅ
//       </Avatar>
//       <div style={{ position: "absolute", bottom: -1, color: "black" }}>
//         {good}
//       </div>
//     </>
//   );

//   const goodButtonSelected = (
//     <>
//       <Avatar
//         className="evaluatebutton"
//         sx={{
//           width: "100%",
//           height: "100%",
//           fontSize: "6em",
//           fontWeight: 400,
//           color: "white",
//           bgcolor: "#6DCEF5",
//         }}
//       >
//         ㅅ
//       </Avatar>
//       <div style={{ position: "absolute", bottom: -1, color: "white" }}>
//         {good}
//       </div>
//     </>
//   );

//   const sosoButton = (
//     <>
//       <Avatar
//         className="evaluatebutton"
//         sx={{
//           width: "100%",
//           height: "100%",
//           fontSize: "6em",
//           fontWeight: 400,
//           color: "black",
//           bgcolor: "white",
//         }}
//       >
//         ㅍ
//       </Avatar>
//       <div style={{ position: "absolute", bottom: -1, color: "black" }}>
//         {soso}
//       </div>
//     </>
//   );

//   const sosoButtonSelected = (
//     <>
//       <Avatar
//         className="evaluatebutton"
//         sx={{
//           width: "100%",
//           height: "100%",
//           fontSize: "6em",
//           fontWeight: 400,
//           color: "white",
//           bgcolor: "#6DCEF5",
//         }}
//       >
//         ㅅ
//       </Avatar>
//       <div style={{ position: "absolute", bottom: -1, color: "white" }}>
//         {soso}
//       </div>
//     </>
//   );

//   const badButton = (
//     <>
//       <Avatar
//         className="evaluatebutton"
//         sx={{
//           width: "100%",
//           height: "100%",
//           fontSize: "6em",
//           fontWeight: 400,
//           color: "black",
//           bgcolor: "white",
//         }}
//       >
//         ㅎ
//       </Avatar>
//       <div style={{ position: "absolute", bottom: -1, color: "black" }}>
//         {bad}
//       </div>
//     </>
//   );

//   const badButtonSelected = (
//     <>
//       <Avatar
//         className="evaluatebutton"
//         sx={{
//           width: "100%",
//           height: "100%",
//           fontSize: "6em",
//           fontWeight: 400,
//           color: "white",
//           bgcolor: "#6DCEF5",
//         }}
//       >
//         ㅎ
//       </Avatar>
//       <div style={{ position: "absolute", bottom: -1, color: "white" }}>
//         {bad}
//       </div>
//     </>
//   );

//   // const changeEvaluate = () => {
//   //   switch (evaluate) {
//   //     case 'GOOD':
//   //       setGood((target) => target - 1);
//   //       break;
//   //     case 'SOSO':
//   //       setSoso((target) => target - 1);
//   //       break;
//   //     case 'BAD':
//   //       setBad((target) => target - 1);
//   //       break;
//   //     default:
//   //       break;
//   //   }
//   // }

//   const postEvaluate = async (evaluate) => {
//     try {
//       const response = await axios.post(
//         `http://localhost:8080/article/evaluate?articleIndex=${props.post.articleIndex}&evaluateType=${evaluate}&userIndex=${userStatus.userIndex}`
//       );
//       console.log(response);
//       console.log("통신");
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   const goodToSoso = () => {
//     setGood((target) => target - 1);
//     setSoso((target) => target + 1);
//     setEvaluate("SOSO");
//     postEvaluate("SOSO");
//   };

//   const goodToBad = () => {
//     setGood((target) => target - 1);
//     setBad((target) => target + 1);
//     setEvaluate("BAD");
//     postEvaluate("BAD");
//   };

//   const goodToNone = () => {
//     setGood((target) => target - 1);
//     setEvaluate("NONE");
//     postEvaluate("NONE");
//   };

//   const sosoToGood = () => {
//     setGood((target) => target + 1);
//     setSoso((target) => target - 1);
//     setEvaluate("GOOD");
//     postEvaluate("GOOD");
//   };

//   const sosoToBad = () => {
//     setBad((target) => target + 1);
//     setSoso((target) => target - 1);
//     setEvaluate("BAD");
//     postEvaluate("BAD");
//   };

//   const sosoToNone = () => {
//     setSoso((target) => target - 1);
//     setEvaluate("NONE");
//     postEvaluate("NONE");
//   };

//   const badToGood = () => {
//     setGood((target) => target + 1);
//     setBad((target) => target - 1);
//     setEvaluate("GOOD");
//     postEvaluate("GOOD");
//   };

//   const badToSoso = () => {
//     setSoso((target) => target + 1);
//     setBad((target) => target - 1);
//     setEvaluate("SOSO");
//     postEvaluate("SOSO");
//   };

//   const badToNone = () => {
//     setBad((target) => target - 1);
//     setEvaluate("NONE");
//     postEvaluate("NONE");
//   };

//   const noneToGood = () => {
//     setGood((target) => target + 1);
//     setEvaluate("GOOD");
//     postEvaluate("GOOD");
//   };

//   const noneToSoso = () => {
//     setSoso((target) => target + 1);
//     setEvaluate("SOSO");
//     postEvaluate("SOSO");
//   };

//   const noneToBad = () => {
//     setBad((target) => target + 1);
//     setEvaluate("BAD");
//     postEvaluate("BAD");
//   };

//   switch (evaluate) {
//     case "GOOD":
//       return (
//         <>
//           <Fab color="primary" onClick={goodToNone}>
//             <Box>
//               <div>상</div>
//               <div>{good}</div>
//             </Box>
//           </Fab>
//           <Fab onClick={goodToSoso}>
//             <Box>
//               <div>중</div>
//               <div>{soso}</div>
//             </Box>
//           </Fab>
//           <Fab onClick={goodToBad}>
//             <Box>
//               <div>하</div>
//               <div>{bad}</div>
//             </Box>
//           </Fab>
//         </>
//       );
//     case "SOSO":
//       return (
//         <>
//           <Fab onClick={sosoToGood}>
//             <Box>
//               <div>상</div>
//               <div>{good}</div>
//             </Box>
//           </Fab>
//           <Fab color="primary" onClick={sosoToNone}>
//             <Box>
//               <div>중</div>
//               <div>{soso}</div>
//             </Box>
//           </Fab>
//           <Fab onClick={sosoToBad}>
//             <Box>
//               <div>하</div>
//               <div>{bad}</div>
//             </Box>
//           </Fab>
//         </>
//       );
//     case "BAD":
//       return (
//         <>
//           <Fab onClick={badToGood}>
//             <Box>
//               <div>상</div>
//               <div>{good}</div>
//             </Box>
//           </Fab>
//           <Fab onClick={badToSoso}>
//             <Box>
//               <div>중</div>
//               <div>{soso}</div>
//             </Box>
//           </Fab>
//           <Fab color="primary" onClick={badToNone}>
//             <Box>
//               <div>하</div>
//               <div>{bad}</div>
//             </Box>
//           </Fab>
//         </>
//       );
//     default:
//       return (
//         <>
//           <Fab onClick={noneToGood}>
//             <Box>
//               <div>상</div>
//               <div>{good}</div>
//             </Box>
//           </Fab>
//           <Fab onClick={noneToSoso}>
//             <Box>
//               <div>중</div>
//               <div>{soso}</div>
//             </Box>
//           </Fab>
//           <Fab onClick={noneToBad}>
//             <Box>
//               <div>하</div>
//               <div>{bad}</div>
//             </Box>
//           </Fab>
//         </>
//       );
//   }
// };

// export default EvaluateButton;

import Fab from "@mui/material/Fab";
import Box from "@mui/material/Box";
import { useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";
import Avatar from "@mui/material/Avatar";
import "./EvaluateButton.css";
import Typography from "@mui/material/Typography";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import { currentUser } from "firebaseConfig";
import { requestPost } from "api/axios";
const EvaluateButton = (props) => {
  console.log("asdfasdfasdfasdfasdfasdf");
  console.log(props.post);
  console.log("asdfasdfasdfasdfasdfasdf");
  // const [evaluate,setEvaluate] = useState(props.article.evaluateValue)
  // const [good,setGood] = useState(props.article.goodCount)
  // const [soso,setSoso] = useState(props.article.sosoCount)
  // const [bad, setBad] = useState(props.article.badCount)

  const [evaluate, setEvaluate] = useState(props.post.evaluateValue);
  const [good, setGood] = useState(props.post.goodCount);
  const [soso, setSoso] = useState(props.post.sosoCount);
  const [bad, setBad] = useState(props.post.badCount);
  const userStatus = useSelector((state) => state.userStatus);

  const goodButton = (
    <>
      <Typography
        className="evaluatebutton"
        sx={{
          width: "100%",
          height: "100%",
          fontSize: "6em",
          fontFamily:"dongle",
          fontWeight: 400,
          color: "black",
          position: "relative",
          bottom: "63%",
        }}
      >
        ㅅ
      </Typography>
      <div className="evaluatecount" style={{ position: "absolute", bottom: -7, color: "black" }}>
        {good}
      </div>
    </>
  );

  const goodButtonSelected = (
    <>
      <Typography
        className="evaluatebutton"
        sx={{
          width: "100%",
          height: "100%",
          fontFamily:"dongle",
          fontSize: "6em",
          fontWeight: 400,
          color: "white",
          position: "relative",
          bottom: "63%",
        }}
      >
        ㅅ
      </Typography>
      <div className="evaluatecount" style={{ position: "absolute", bottom: -7, color: "white" }}>
        {good}
      </div>
    </>
  );

  const sosoButton = (
    <>
      <Typography
        className="evaluatebutton"
        sx={{
          width: "100%",
          height: "100%",
          fontFamily:"dongle",
          fontSize: "6em",
          fontWeight: 400,
          color: "black",
          position: "relative",
          bottom: "63%",
        }}
      >
        ㅍ
      </Typography>
      <div className="evaluatecount" style={{ position: "absolute", bottom: -7, color: "black" }}>
        {soso}
      </div>
    </>
  );

  const sosoButtonSelected = (
    <>
      <Typography
        className="evaluatebutton"
        sx={{
          width: "100%",
          height: "100%",
          fontFamily:"dongle",
          fontSize: "6em",
          fontWeight: 400,
          color: "white",
          position: "relative",
          bottom: "63%",
        }}
      >
        ㅍ
      </Typography>
      <div className="evaluatecount" style={{ position: "absolute", bottom: -7, color: "white" }}>
        {soso}
      </div>
    </>
  );

  const badButton = (
    <>
      <Typography
        className="evaluatebutton"
        sx={{
          width: "100%",
          height: "100%",
          fontSize: "6em",
          fontFamily:"dongle",
          fontWeight: 400,
          color: "black",
          position: "relative",
          bottom: "63%",
        }}
      >
        ㅎ
      </Typography>
      <div className="evaluatecount" style={{ position: "absolute", bottom: -7, color: "black" }}>
        {bad}
      </div>
    </>
  );

  const badButtonSelected = (
    <>
      <Typography
        className="evaluatebutton"
        sx={{
          width: "100%",
          height: "100%",
          fontSize: "6em",
          fontFamily:"dongle",
          fontWeight: 400,
          color: "white",
          position: "relative",
          bottom: "63%",
        }}
      >
        ㅎ
      </Typography>
      <div className="evaluatecount" style={{ position: "absolute", bottom: -7, color: "white" }}>
        {bad}
      </div>
    </>
  );

  // const changeEvaluate = () => {
  //   switch (evaluate) {
  //     case 'GOOD':
  //       setGood((target) => target - 1);
  //       break;
  //     case 'SOSO':
  //       setSoso((target) => target - 1);
  //       break;
  //     case 'BAD':
  //       setBad((target) => target - 1);
  //       break;
  //     default:
  //       break;
  //   }
  // }



  const postEvaluate = async (evaluate) => {
    requestPost(`${urls.article.getUserArticle}evaluate?article=${props.post.articleIndex}&evaluateType=${evaluate}&user=${userStatus.userIndex}`)
    // while (!currentUser || !currentUser.accessToken) {
    //   await new Promise((resolve) => setTimeout(resolve, 1000));
    // }
  
    // try {
    //   const response = await axios.post(
    //     `${urls.article.getUserArticle}evaluate?article=${props.post.articleIndex}&evaluateType=${evaluate}&user=${userStatus.userIndex}`,
    //     {headers: {
    //       "Content-Type": "application/json",
    //       Authorization: `Bearer ${currentUser.accessToken}`,
    //     },}
    //   );
    //   console.log(response);
    //   console.log("통신");
    // } catch (error) {
    //   console.log(error);
    //   console.log(authenticationInstance.currentUser.accessToken)
    // }
  };

  const goodToSoso = () => {
    setGood((target) => target - 1);
    setSoso((target) => target + 1);
    setEvaluate("SOSO");
    postEvaluate("SOSO");
  };

  const goodToBad = () => {
    setGood((target) => target - 1);
    setBad((target) => target + 1);
    setEvaluate("BAD");
    postEvaluate("BAD");
  };

  const goodToNone = () => {
    setGood((target) => target - 1);
    setEvaluate("NONE");
    postEvaluate("NONE");
  };

  const sosoToGood = () => {
    setGood((target) => target + 1);
    setSoso((target) => target - 1);
    setEvaluate("GOOD");
    postEvaluate("GOOD");
  };

  const sosoToBad = () => {
    setBad((target) => target + 1);
    setSoso((target) => target - 1);
    setEvaluate("BAD");
    postEvaluate("BAD");
  };

  const sosoToNone = () => {
    setSoso((target) => target - 1);
    setEvaluate("NONE");
    postEvaluate("NONE");
  };

  const badToGood = () => {
    setGood((target) => target + 1);
    setBad((target) => target - 1);
    setEvaluate("GOOD");
    postEvaluate("GOOD");
  };

  const badToSoso = () => {
    setSoso((target) => target + 1);
    setBad((target) => target - 1);
    setEvaluate("SOSO");
    postEvaluate("SOSO");
  };

  const badToNone = () => {
    setBad((target) => target - 1);
    setEvaluate("NONE");
    postEvaluate("NONE");
  };

  const noneToGood = () => {
    setGood((target) => target + 1);
    setEvaluate("GOOD");
    postEvaluate("GOOD");
  };

  const noneToSoso = () => {
    setSoso((target) => target + 1);
    setEvaluate("SOSO");
    postEvaluate("SOSO");
  };

  const noneToBad = () => {
    setBad((target) => target + 1);
    setEvaluate("BAD");
    postEvaluate("BAD");
  };

  switch (evaluate) {
    case "GOOD":
      return (
        <>
          <Fab sx={{ bgcolor:"#6DCEF5" }} onClick={goodToNone} size="medium" disableRipple>
            {goodButtonSelected}
          </Fab>
          <Fab sx={{ bgcolor: "white" }} onClick={goodToSoso} size="medium" disableRipple>
            {sosoButton}
          </Fab>
          <Fab sx={{ bgcolor: "white" }} onClick={goodToBad} size="medium" disableRipple>
            {badButton}
          </Fab>
        </>
      );
    case "SOSO":
      return (
        <>
          <Fab sx={{ bgcolor: "white" }} onClick={sosoToGood} size="medium" disableRipple>
            {goodButton}
          </Fab>
          <Fab sx={{ bgcolor:"#6DCEF5" }} onClick={sosoToNone} size="medium" disableRipple>
            {sosoButtonSelected}
          </Fab>
          <Fab sx={{ bgcolor: "white" }} onClick={sosoToBad} size="medium" disableRipple>
            {badButton}
          </Fab>
        </>
      );
    case "BAD":
      return (
        <>
          <Fab sx={{ bgcolor: "white" }} onClick={badToGood} size="medium" disableRipple>
            {goodButton}
          </Fab>
          <Fab sx={{ bgcolor: "white" }} onClick={badToSoso} size="medium" disableRipple>
            {sosoButton}
          </Fab>
          <Fab sx={{ bgcolor:"#6DCEF5" }} onClick={badToNone} size="medium" disableRipple>
            {badButtonSelected}
          </Fab>
        </>
      );
    default:
      return (
        <>
          <Fab sx={{ bgcolor: "white" }} onClick={noneToGood} size="medium" disableRipple>
            {goodButton}
          </Fab>
          <Fab sx={{ bgcolor: "white" }} onClick={noneToSoso} size="medium" disableRipple>
            {sosoButton}
          </Fab>
          <Fab sx={{ bgcolor: "white" }} onClick={noneToBad} size="medium" disableRipple>
            {badButton}
          </Fab>
        </>
      );
  };
};

export default EvaluateButton;
