import * as React from "react";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import MenuIcon from "@mui/icons-material/Menu";
import ReplyOutlinedIcon from "@mui/icons-material/ReplyOutlined";
import ReportProblemOutlinedIcon from "@mui/icons-material/ReportProblemOutlined";
import Stack from "@mui/material/Stack";
import { useSelector } from "react-redux";
import EditIcon from "@mui/icons-material/Edit";
import BlockIcon from "@mui/icons-material/Block";
import Button from "@mui/material/Button";
import { requestPost } from "api/axios";
import urls from "api/url";
import { useNavigate } from "react-router-dom";

const style = {
  position: "absolute",
  bottom: "0%",
  left: "45%",
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
      reportArticleIndex: props.post.articleIndex,
      reportDtype: "A",
      reportUserIndex: props.userIndex,
    };
    await requestPost(`${urls.report}`, reportdata);
    setOpen(false);
    props.handleClose();
  };

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
          <p id="child-modal-description">정말 신고하시겠습니까?</p>
          <Box>
            <Button onClick={yesClose}>예</Button>
            <Button onClick={handleClose}>아니오</Button>
          </Box>
        </Box>
      </Modal>
    </React.Fragment>
  );
}

function ChildModal(props) {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };
  const yesClose = async () => {
    const blockdata = {
      blockTargetIndex: props.post.userIndex,
      blockUserIndex: props.userIndex,
    };
    await requestPost(`${urls.relation.blockUser}`, blockdata);
    setOpen(false);
    props.handleClose();
  };

  return (
    <React.Fragment>
      <Stack
        onClick={handleOpen}
        direction="row"
        justifyContent="flex-start"
        alignItems="flex-start"
        spacing={2}
      >
        <BlockIcon />
        &nbsp;&nbsp; 차단
      </Stack>
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-describedby="child-modal-description"
        closeAfterTransition
      >
        <Box sx={{ ...style, width: 200 }}>
          <p id="child-modal-description">정말 차단하시겠습니까?</p>
          <Box>
            <Button onClick={yesClose}>예</Button>
            <Button onClick={handleClose}>아니오</Button>
          </Box>
        </Box>
      </Modal>
    </React.Fragment>
  );
}
const HamburgerButton = (props) => {
  const navigate = useNavigate();
  const navigateEdit = () => {
    navigate(`/edit/${props.post.articleIndex}`)
  }
  const handleShareClick = async () => {
    if (navigator.share) {
      try {
        await navigator.share({
          title: "ㅁㅌㅊ???",
          url: window.location.href,
        });
      } catch (error) {
        console.error("Error sharing:", error);
      }
    } else {
      const el = document.createElement("textarea");
      el.value = window.location.href;
      document.body.appendChild(el);
      el.select();
      document.execCommand("copy");
      document.body.removeChild(el);
      alert("URL이 복사되었습니다.");
    }
  };

  const [open, setOpen] = React.useState(false);
  const userStatus = useSelector((state) => state.userStatus);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <Box>
      <Fab aria-label="add" onClick={handleOpen} size="medium" sx={{ backgroundColor: 'white'}}>
        <MenuIcon />
      </Fab>

      <Modal
        aria-describedby="transition-modal-description"
        open={open}
        onClose={handleClose}
        closeAfterTransition
      >
        <Fade in={open}>
          <Box sx={style}>
            <Typography id="transition-modal-description" sx={{ mt: 1 }}>
              <Stack
                direction="row"
                justifyContent="flex-start"
                alignItems="flex-start"
                spacing={2}
                onClick={handleShareClick}
              >
                <ReplyOutlinedIcon />
                &nbsp;&nbsp; 공유
              </Stack>
            </Typography>
            <Typography id="transition-modal-description" sx={{ mt: 1 }}>
              {props.post.userIndex === userStatus.userIndex ? (
                <Stack
                  direction="row"
                  justifyContent="flex-start"
                  alignItems="flex-start"
                  spacing={2}
                  onClick={navigateEdit}
                >
                  <EditIcon />
                  &nbsp;&nbsp; 수정
                </Stack>
              ) : (
                <ReportModal
                  handleClose={handleClose}
                  post={props.post}
                  userIndex={userStatus.userIndex}
                />
              )}
            </Typography>
            {props.post.userIndex !== userStatus.userIndex && (
              <Typography id="transition-modal-description" sx={{ mt: 1 }}>
                <ChildModal
                  handleClose={handleClose}
                  post={props.post}
                  userIndex={userStatus.userIndex}
                />
              </Typography>
            )}
          </Box>
        </Fade>
      </Modal>
    </Box>
  );
};

export default HamburgerButton;
