import * as React from "react";
import Backdrop from "@mui/material/Backdrop";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import MenuIcon from "@mui/icons-material/Menu";
import ChatOutlinedIcon from "@mui/icons-material/ChatOutlined";
import ReplyOutlinedIcon from "@mui/icons-material/ReplyOutlined";
import ReportProblemOutlinedIcon from "@mui/icons-material/ReportProblemOutlined";
import Stack from "@mui/material/Stack";
import { useSelector } from "react-redux";
import EditIcon from "@mui/icons-material/Edit";
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

const HamburgerButton = (props) => {
  const userStatus = useSelector((state) => state.userStatus);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => 
  {console.log(`이거봐봐이거${userStatus.userIndex}`)
  console.log(typeof userStatus.userIndex);
  console.log(typeof props.post.userIndex);setOpen(true);};
  const handleClose = () => setOpen(false);
  
  return (
    <Box>
      <Fab color="secondary" aria-label="add" onClick={handleOpen}>
        <MenuIcon />
      </Fab>

      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <Box sx={style}>
            <Typography id="transition-modal-description" sx={{ mt: 1 }}>
              <Stack
                direction="row"
                justifyContent="flex-start"
                alignItems="flex-start"
                spacing={2}
              >
                <ChatOutlinedIcon />
                &nbsp;&nbsp; 댓글
              </Stack>
            </Typography>
            <Typography id="transition-modal-description" sx={{ mt: 1 }}>
              <Stack
                direction="row"
                justifyContent="flex-start"
                alignItems="flex-start"
                spacing={2}
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
                >
                  <EditIcon />
                  &nbsp;&nbsp; 수정
                </Stack>
              ) : (
                <Stack
                  direction="row"
                  justifyContent="flex-start"
                  alignItems="flex-start"
                  spacing={2}
                >
                  <ReportProblemOutlinedIcon />
                  &nbsp;&nbsp; 신고
                </Stack>
              )}
            </Typography>
          </Box>
        </Fade>
      </Modal>
    </Box>
  );
};

export default HamburgerButton;
