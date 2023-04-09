import Container from "@mui/system/Container";
import Button from "@mui/material/Button";
import NotFound from "../assets/images/NotFound.png"

import { useNavigate } from "react-router-dom";

const NoMatchPage = () => {
  const navigate = useNavigate();
  return (
    <>
      <Container maxWidth="sm">
        <div>&nbsp;</div>
        <img src={NotFound} alt="" style={{width:"100%"}}/>
        <div>&nbsp;</div>
        <Button onClick={() => navigate("/")}>홈으로 돌아가기</Button>
      </Container>
    </>
  );
};

export default NoMatchPage;
