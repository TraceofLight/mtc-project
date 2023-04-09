import { Link } from "react-router-dom";
import logo from "../../assets/images/logo.png";
import Avatar from '@mui/material/Avatar';

// import Box from "@mui/material/Box";

const Logo = () => {
  return (
    <Link to="/">
      {/* <Box> */}
      <Avatar alt="mtc" src={logo} />
      {/* </Box> */}
    </Link>
  );
};

export default Logo;
