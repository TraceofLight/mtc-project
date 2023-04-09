import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Avatar from "@mui/material/Avatar";
import Logo from "./Logo";
import buriburi from "../../assets/images/buriburi.jpg";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";

export default function ButtonAppBar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            // size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <Logo />
          </IconButton>
          <Grid container justifyContent="flex-end">
            <Stack direction="row" spacing={1}>
              <Box component="span" sx={{ p: 2 }}>
                {/* <Icon path={mdiPencil} size={1} /> */}
              </Box>
              <Box component="span" sx={{ p: 2 }}>
                {/* <Icon path={mdiMagnify} size={1} /> */}
              </Box>

              <Box component="span" sx={{ p: 2 }}>
                {/* <Icon path={mdiBellOutline} size={1} /> */}
              </Box>

              <IconButton>
                <Avatar alt="buriburi" src={buriburi} />
              </IconButton>
            </Stack>
          </Grid>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
