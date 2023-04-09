import NavigationBar from "../components/NavigationBar/NavigationBar";
import Container from "@mui/system/Container";
import Button from "@mui/material/Button";

import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { sendEmailVerification } from "firebase/auth";

import { authenticationInstance } from "firebaseConfig";

const NeedVerificationPage = () => {
  const navigate = useNavigate();

  const userStatus = useSelector((state) => state.userStatus);
  const userToken = userStatus.userToken;
  const emailVerified = userToken?.emailVerified;
  const phoneNumber = userToken?.phoneNumber;

  const [emailVerificationButton, setEmailVerificationButton] = useState(true);
  const [phoneNumberVerification, setPhoneNumberVerification] = useState(true);
  const [emailSent, setEmailSent] = useState(false);

  useEffect(() => {
    if (emailVerified === true) {
      setEmailVerificationButton(false);
    }
  }, [emailVerified]);

  useEffect(() => {
    if (phoneNumber !== null && phoneNumber !== undefined) {
      setPhoneNumberVerification(false);
    }
  }, [phoneNumber]);

  useEffect(() => {
    if (
      emailVerified === true &&
      phoneNumber !== null &&
      phoneNumber !== undefined
    ) {
      navigate("/");
    }
  }, [emailVerified, phoneNumber, navigate]);

  const sendEmailHandler = async () => {
    try {
      const user = authenticationInstance.currentUser;
      await sendEmailVerification(user);
      alert("이메일이 발송되었습니다.");
      setEmailSent(true);
    } catch (error) {
      alert(error.message);
    }
  };

  const moveLoginHandler = async () => {
    await authenticationInstance.signOut();
    navigate("/login");
  };

  const movePhoneVericationHandler = () => {
    navigate("/phone-verification");
  };

  return (
    <>
      <Container maxWidth="sm">
        <NavigationBar />
        <h1>회원가입이 완료되었습니다.</h1>
        <h1>이메일 및 휴대폰 인증</h1>
        {emailVerificationButton && !emailSent && (
          <Button onClick={sendEmailHandler}>인증 메일 보내기</Button>
        )}
        {emailVerificationButton && emailSent && (
          <Button disabled>메일 발송 완료</Button>
        )}
        {!emailVerificationButton && <Button disabled>이메일 인증 완료</Button>}
        {phoneNumberVerification && (
          <Button onClick={movePhoneVericationHandler}>휴대폰 인증하기</Button>
        )}
        {!phoneNumberVerification && <Button disabled>휴대폰 인증 완료</Button>}
        <Button onClick={moveLoginHandler}>로그인 화면으로 이동</Button>
      </Container>
    </>
  );
};

export default NeedVerificationPage;
