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
const HamburgerMenu = () => {
  return (
    <Box>
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
            <Typography id="transition-modal-description">
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
            <Typography id="transition-modal-description">
              <ReplyOutlinedIcon />
            </Typography>
            <Typography id="transition-modal-description">
              <ReportProblemOutlinedIcon />
            </Typography>
          </Box>
        </Fade>
      </Modal>
    </Box>
  );
};

export default HamburgerMenu;
