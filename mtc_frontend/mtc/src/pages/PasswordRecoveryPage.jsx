import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Container from "@mui/system/Container";

import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { authenticationInstance } from "firebaseConfig";
import { sendPasswordResetEmail } from "firebase/auth";

const PasswordRecoveryPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [emailSent, setEmailSent] = useState(false);

  const findPasswordHandler = async () => {
    try {
      await sendPasswordResetEmail(authenticationInstance, email);
      setEmailSent(true);
      alert("새로운 비밀번호로 변경할 수 있는 이메일을 발송했습니다.");
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <>
      <Container maxWidth="sm">
        <h1>비밀번호 찾기</h1>
        <div>
          <TextField
            id="email"
            label="E - mail"
            inputProps={{ maxLength: 100 }}
            onChange={(event) => setEmail(event.target.value)}
          />
        </div>
        {!emailSent && (
          <Button onClick={findPasswordHandler}>
            비밀 번호 변경 메일 보내기
          </Button>
        )}
        {emailSent && <Button disabled>메일 발송 완료</Button>}
        <br />
        <Button onClick={() => navigate("/login")}> 뒤로 가기 </Button>
      </Container>
    </>
  );
};

export default PasswordRecoveryPage;
