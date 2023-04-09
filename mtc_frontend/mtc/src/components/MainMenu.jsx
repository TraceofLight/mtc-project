import Box from "@mui/material/Box";
import { emphasize, styled } from "@mui/material/styles";
// import Breadcrumbs from "@mui/material/Breadcrumbs";
import SwitchAccessShortcutIcon from '@mui/icons-material/SwitchAccessShortcut';
import Chip from "@mui/material/Chip";
import HomeIcon from "@mui/icons-material/Home";
import WhatshotIcon from "@mui/icons-material/Whatshot";
import { Button } from "@mui/material";

const StyledBreadcrumb = styled(Chip)(({ theme }) => {
  const backgroundColor =
    theme.palette.mode === "light"
      ? theme.palette.grey[100]
      : theme.palette.grey[800];
  return {
    backgroundColor,
    height: theme.spacing(3),
    color: theme.palette.text.primary,
    fontWeight: theme.typography.fontWeightRegular,
    "&:hover, &:focus": {
      backgroundColor: emphasize(backgroundColor, 0.06),
    },
    "&:active": {
      boxShadow: theme.shadows[1],
      backgroundColor: emphasize(backgroundColor, 0.12),
    },
  };
});

const MainMenu = (props) => {
  return (
    <Box
      sx={{
        marginX: "20%",
        marginY: "2%",
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      {/* <Breadcrumbs aria-label="breadcrumb">
        <StyledBreadcrumb label="메인" icon={<HomeIcon fontSize="small" />} onClick={props.setMain} />
        <StyledBreadcrumb label="인기" icon={<WhatshotIcon fontSize="small" />} onClick={props.setHot}/>
    
      </Breadcrumbs> */}
      <Button startIcon={<HomeIcon />} onClick={props.setMain}>
        메인
      </Button>
      <Button startIcon={<WhatshotIcon />} onClick={props.setHot}>
        인기
      </Button>
      <Button startIcon={<SwitchAccessShortcutIcon />} onClick={props.setRecent}>
        최신
      </Button>
    </Box>
  );
};
export default MainMenu;
