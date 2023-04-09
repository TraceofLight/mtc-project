// import { useState } from "react";
import * as React from "react";
// import Backdrop from "@mui/material/Backdrop";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import ReportProblemOutlinedIcon from "@mui/icons-material/ReportProblemOutlined";
import Stack from "@mui/material/Stack";
import { useSelector } from "react-redux";
import DeleteIcon from "@mui/icons-material/Delete";
import urls from "api/url";
import { requestPost,requestDelete } from "api/axios";
import Button from "@mui/material/Button";

const style = {
  position: "absolute",
  bottom: "0%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 200,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 2,
};
function ReportModal(props) {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };
  const yesClose = async () => {
    const reportdata = {
      "reportReplyIndex": props.data.replyIndex,
      "reportDtype": "R",
      "reportUserIndex": props.userIndex
    }
    await requestPost(`${urls.report}`, reportdata)
    setOpen(false)
    props.handleClose()
  }

  return (
    <React.Fragment>
                <Stack
                  direction="row"
                  justifyContent="flex-start"
                  alignItems="flex-start"
                  spacing={2}
                  onClick={handleOpen}
                >
                  <ReportProblemOutlinedIcon />
                  &nbsp;&nbsp; 신고
                </Stack>
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-describedby="child-modal-description"
        closeAfterTransition
      >
        <Box sx={{ ...style, width: 200 }}>
          <p id="child-modal-description">
            정말 차단하시겠습니까?
          </p>
          <Box>
          <Button onClick={yesClose}>예</Button>
          <Button onClick={handleClose}>아니오</Button>
          </Box>
        </Box>
      </Modal>
    </React.Fragment>
  );
}
const PostReplyMoreButton = (props) => {
  const userStatus = useSelector((state) => state.userStatus);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {setOpen(true);
  console.log(props.data)};
  const handleClose = () => setOpen(false);
  const deleteReply = async () => {
    await requestDelete(`${urls.reply}deleteReplyByReplyIndex/${props.data.replyIndex}`)
    handleClose();
  }

  return (
    <>
      <span onClick={handleOpen}>
        <MoreVertIcon />
      </span>

      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        open={open}
        onClose={handleClose}
        closeAfterTransition
      >
        <Fade in={open}>
          <Box sx={style}>
            <Typography
              id="transition-modal-description"
              sx={{ mt: 1 }}
            ></Typography>
            <Typography id="transition-modal-description" sx={{ mt: 1 }}>
              {props.data.replyUserIndex === userStatus.userIndex ? (
                <Stack
                  direction="row"
                  justifyContent="flex-start"
                  alignItems="flex-start"
                  spacing={2}
                  onClick={deleteReply}
                >
                  <DeleteIcon />
                  &nbsp;&nbsp; 삭제
                </Stack>
              ) : (
                <ReportModal handleClose={handleClose} data={props.data} userIndex={userStatus.userIndex}/>
              )}
            </Typography>
          </Box>
        </Fade>
      </Modal>
    </>
  );
};

export default PostReplyMoreButton;
