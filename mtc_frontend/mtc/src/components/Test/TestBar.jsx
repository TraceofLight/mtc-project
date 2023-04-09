import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Logo from "./Logo";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";
import SearchIcon from "@mui/icons-material/Search";
import EditIcon from "@mui/icons-material/Edit";
import Notification from "./Notification";
import { Link, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
import SettingsIcon from "@mui/icons-material/Settings";
import { width } from "@mui/system";

const TestBar = () => {
  const userStatus = useSelector((state) => state.userStatus);
  const location = useLocation();
  return (
    <>
      <AppBar position="sticky" sx={{ backgroundColor: "white", opacity: 0.9 }}>
        <Toolbar>
          <Logo />
          <Grid container justifyContent="flex-end">
            <Stack direction="row" spacing={0.1}>
              <Box component="span" sx={{ p: 2 }}>
                <Link to="/search">
                  <SearchIcon sx={{ color: "black" }} />
                </Link>
              </Box>

              <Box component="span" sx={{ p: 2 }}>
                <Link to="/post/new">
                  {/* <EditIcon sx={{ color: "black" }} /> */}
                  <img
                    src={require("../../assets/icons/Logo_Add_Article.png")}
                    width={22}
                  ></img>
                </Link>
              </Box>

              <Box component="span" sx={{ p: 2 }}>
                <Link to="/notification">
                  <Notification />
                </Link>
              </Box>

              <IconButton>
                {location.pathname === `/user/${userStatus.userIndex}` ? (
                  <Link to="/settings">
                    <SettingsIcon sx={{ color: "black" }}/>
                  </Link>
                ) : (
                  <Link to={`/user/${userStatus.userIndex}`}>
                    <Avatar
                      alt="buriburi"
                      src={userStatus.userProfilePictureSource}
                    />
                  </Link>
                )}
              </IconButton>
            </Stack>
          </Grid>
        </Toolbar>
      </AppBar>
    </>
  );
};

export default TestBar;
