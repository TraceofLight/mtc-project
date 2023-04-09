import Box from "@mui/material/Box";
import Container from "@mui/system/Container";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import { authenticationInstance } from "firebaseConfig";
import {
  PhoneAuthProvider,
  RecaptchaVerifier,
  updatePhoneNumber,
} from "firebase/auth";

const PhoneVerificationPage = () => {
  const navigate = useNavigate();

  const [phoneNumber, setPhoneNumber] = useState("");
  const [verificationCode, setVerificationCode] = useState("");
  const [verificationId, setVerificationId] = useState("");
  const [showToggle, setShowToggle] = useState(false);
  const [isValid, setIsValid] = useState(false);

  useEffect(() => {
    if (phoneNumber.length === 11) {
      setIsValid(true);
    }
  }, [phoneNumber]);

  const phoneVerificationHandler = async () => {
    const appVerifier = new RecaptchaVerifier(
      "recaptcha-container",
      {},
      authenticationInstance
    );

    const provider = new PhoneAuthProvider(authenticationInstance);

    try {
      if (isValid) {
        const verificationResponse = await provider.verifyPhoneNumber(
          `+82${phoneNumber.substring(1)}`,
          appVerifier
        );
        setVerificationId(verificationResponse);
        setShowToggle(!showToggle);
        alert("Code Sent");
      } else {
        alert("Invalid Phone Number");
      }
    } catch (error) {
      alert(error.message);
    }
  };

  const handleValidation = async () => {
    try {
      const phoneCredential = PhoneAuthProvider.credential(
        verificationId,
        verificationCode
      );
      await updatePhoneNumber(
        authenticationInstance.currentUser,
        phoneCredential
      );
      alert("휴대폰 인증이 완료되었습니다.");
      navigate("/need-verification");

      // 에러 체크
    } catch (error) {
      alert(error.message);
      window.location.reload();
    }
  };

  return (
    <>
      <>
        <Container maxWidth="sm">
          <h1>휴대폰 인증하기</h1>
          <Box sx={{ display: showToggle ? "none" : "block" }}>
            <div>
              <TextField
                id="phoneNumber"
                label="전화번호"
                inputProps={{ maxLength: 11 }}
                onChange={(event) => setPhoneNumber(event.target.value)}
              />
            </div>
            <br />
            <div id="recaptcha-container"></div>
            <Button onClick={phoneVerificationHandler}>전화번호 인증</Button>
          </Box>

          <Box sx={{ display: showToggle ? "block" : "none" }}>
            <div>
              <TextField
                id="OTP"
                label="Enter Your OTP Code"
                inputProps={{ maxLength: 11 }}
                onChange={(event) => setVerificationCode(event.target.value)}
              />
            </div>
            <Button onClick={handleValidation}>Submit</Button>
          </Box>
          <Button onClick={() => navigate(-1)}>뒤로 가기</Button>
        </Container>
      </>
    </>
  );
};

export default PhoneVerificationPage;
