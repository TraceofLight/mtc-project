import { Link } from "react-router-dom";
import logo from "../../assets/images/logo.png";
// import Box from "@mui/material/Box";

const Logo = () => {
  return (
    <Link to="/">
      {/* <Box> */}
      <img src={logo} alt="hi" height="44" sx={{ mt: "auto" }} />
      {/* </Box> */}
    </Link>
  );
};

export default Logo;
