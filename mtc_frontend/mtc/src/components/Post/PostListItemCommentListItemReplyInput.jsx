// import * as React from "react";
// import Box from "@mui/material/Box";
// import Input from "@mui/material/Input";
// import Avatar from "@mui/material/Avatar";
// import buriburi from "../../assets/images/buriburi.jpg";
// import Button from "@mui/material/Button";

// const ariaLabel = { "aria-label": "description" };

// const PostListItemCommentListItemReplyInput = () => {
//   const [reply, setReply] = React.useState("")

//   const TypeReply = (event) => {
//     console.log(event.target.value)
//     setReply(event.target.value)
//   }

//   const PostReply = () => {
//     console.log(reply)
//   }


//   return (
//     <Box sx={{ "& > :not(style)": { mb: 2 } }}>
//       <Box sx={{ display: "flex", alignItems: "flex-end" }}>
//         <Box marginLeft={5} marginRight={2}>
//           <Avatar alt="#" src={buriburi} sx={{ width: 24, height: 24 }} />
//         </Box>
//         <Box
//           sx={{
//             "& > :not(style)": { m: 1 },
//           }}
//           noValidate
//           autoComplete="off"
//         >
//           <Input placeholder="답글 입력" inputProps={ariaLabel} onChange={TypeReply} />
//           <Button variant="contained" href="#contained-buttons"  onClick={PostReply} disableRipple>
//             작성
//           </Button>
//         </Box>
//       </Box>
//     </Box>
//   );
// };

// export default PostListItemCommentListItemReplyInput;

import Stack from '@mui/material/Stack';
import Grid from '@mui/material/Grid';
import * as React from "react";
import Box from "@mui/material/Box";
import Input from "@mui/material/Input";
import Avatar from "@mui/material/Avatar";
import buriburi from "../../assets/images/buriburi.jpg";
import Button from "@mui/material/Button";
import axios from "axios";
import { useSelector } from "react-redux";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";

const ariaLabel = { "aria-label": "description" };

const PostListItemCommentListItemReplyInput = (props) => {
  const userStatus = useSelector((state) => state.userStatus);
  const [reply, setReply] = React.useState("")

  const replyRef = React.useRef();

  const TypeReply = (event) => {
    console.log(event.target.value);
    setReply(event.target.value);
  };

  const RequestReply = async () => {
    try {
      const response = await axios.post(
        `${urls.reply}create`,
        {
          replyCommentIndex: props.index,
          replyContent: reply,
          replyUserIndex: userStatus.userIndex,
        },{
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
          },
        }
      );
      console.log(response);
      props.inputReply();
    } catch (error) {
      alert(error.message);
    } 
  };

  const PostReply = () => {
    if (!reply.trim()) {
      alert("답글을 입력해주세요.")
    } else {
      RequestReply()
    }
    
    setReply('');
    replyRef.current.focus();
  };


  return (
    <Grid container spacing={2} alignItems="end">
        <Grid item xs={9}>
        <Stack direction="row" spacing={2}>
          <Avatar alt="#" src={userStatus.userProfilePictureSource} sx={{ width: "1.5em", height: "1.5em" }} />
          <Input placeholder="답글 입력" inputProps={ariaLabel} onChange={TypeReply} ref={replyRef} value={reply} sx={{ width: "100%" }}/>
        </Stack>
        </Grid>
        <Grid item xs={3}>
          <Button variant="contained" onClick={PostReply} disableRipple size="small" style={{
              backgroundColor: "#6DCEF5",
              color : "#11111"
            }}>
            작성
          </Button>
        </Grid>
        
      
    </Grid>
  );
};

export default PostListItemCommentListItemReplyInput;
