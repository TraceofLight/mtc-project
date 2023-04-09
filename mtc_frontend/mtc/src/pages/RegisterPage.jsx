import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import "./style/Register.css";

import { useRef, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import { authenticationInstance } from "firebaseConfig";
import { updateProfile, createUserWithEmailAndPassword } from "firebase/auth";
import { emailCheck } from "components/EmailCheck";

import { requestFormData, requestUserNickname } from "api/axios";
import urls from "api/url";

import addPicture from "assets/images/addPicture.png";

const RegisterPage = () => {
  const navigate = useNavigate();
  const imageRef = useRef();

  const [image, setImage] = useState(addPicture);
  const [imageFile, setImageFile] = useState(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [name, setName] = useState("");
  const [nickname, setNickname] = useState("");

  const [isValidForm, setIsValidForm] = useState(false);

  const [isValidPassword, setIsValidPassword] = useState(false);
  const [passwordValidationWord, setPasswordValidationWord] = useState("");
  const [isValidEmail, setIsValidEmail] = useState(true);
  const [emailValidationWord, setEmailValidationWord] = useState("");
  const [isValidNickname, setIsValidNickname] = useState(null);
  const [nicknameValidationWord, setNicknameValidationWord] = useState("");

  authenticationInstance.languageCode = "ko";

  // 폼 전체 유효성 검사
  useEffect(() => {
    if (isValidEmail && isValidPassword && isValidNickname) {
      setIsValidForm(true);
    } else {
      setIsValidForm(false);
    }
  }, [isValidEmail, isValidPassword, isValidNickname]);

  // 이메일 유효성 검사
  useEffect(() => {
    if (email === "") {
      setIsValidEmail(false);
      setEmailValidationWord("");
    } else {
      if (emailCheck(email)) {
        setIsValidEmail(true);
        setEmailValidationWord("");
      } else {
        setIsValidEmail(false);
        setEmailValidationWord("이메일 형식에 맞지 않습니다.");
      }
    }
  }, [email]);

  // 비밀번호 유효성 검사
  useEffect(() => {
    if (password.length === 0 || confirmPassword.length === 0) {
      setPasswordValidationWord("Password: ");
      setIsValidPassword(false);
    } else if (password === confirmPassword) {
      setPasswordValidationWord("Password: 일치");
      setIsValidPassword(true);
    } else {
      setPasswordValidationWord("Password: 불일치");
      setIsValidPassword(false);
    }
  }, [password, confirmPassword]);

  useEffect(() => {
    const nicknameCheckHandler = async () => {
      const checkResult = await requestUserNickname(
        `${urls.accounts.nicknameCheck}${nickname}`
      );
      if (checkResult) {
        setIsValidNickname(true);
        setNicknameValidationWord("사용이 가능한 닉네임입니다.");
      } else {
        setIsValidNickname(false);
        setNicknameValidationWord("사용이 불가능한 닉네임입니다.");
      }
    };

    if (nickname === "") {
      setIsValidNickname(false);
      setNicknameValidationWord("");
    } else if (emailCheck(nickname)) {
      setIsValidNickname(false);
      setNicknameValidationWord("이메일 형식의 닉네임은 사용이 불가능합니다.");
    } else {
      nicknameCheckHandler();
    }
  }, [nickname]);

  const handleRegister = async () => {
    if (isValidPassword) {
      await createUserWithEmailAndPassword(
        authenticationInstance,
        email,
        password
      );

      const user = authenticationInstance.currentUser;
      await updateProfile(user, {
        displayName: name,
      });

      const credentials = new FormData();
      credentials.append("userMultipartFile", imageFile);
      credentials.append("userUid", user.uid);
      credentials.append("userNickname", nickname);
      await requestFormData(urls.accounts.register, credentials);

      navigate("/need-verification");
    } else {
      alert("비밀번호가 일치하지 않습니다.");
    }
  };

  const handleKeyUp = (event) => {
    if (event.key === "Enter") {
      handleRegister();
    }
  };

  const handleImage = async (event) => {
    const file = event.target.files[0];
    const validImageTypes = ["image/jpeg", "image/png", "image/gif"];

    if (file && file.size <= 2000000 && validImageTypes.includes(file.type)) {
      setImageFile(file);
      setImage(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        setImage(reader.result);
      };
    } else if (!validImageTypes.includes(file.type)) {
      alert("이미지 파일이 아닙니다.");
    } else {
      alert("프로필 사진은 2MB 이하로 가능합니다.");
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ display: "flex", flexDirection: "row", alignItems: "center" }}>
        <Box sx={{ mr: 2 }}>
          <Avatar>
            <LockOutlinedIcon />
          </Avatar>
        </Box>
        <h1 id="registText">회원가입</h1>
      </Box>
      <hr />
      <br />
      <Box>
        <IconButton
          color="primary"
          aria-label="upload picture"
          component="label"
        >
          <input
            hidden
            accept="image/*"
            type="file"
            onChange={handleImage}
            ref={imageRef}
          />
          <img src={image} alt="hi" width="50%" />
        </IconButton>
      </Box>
      <div>
        <TextField
          name="email"
          label="New Email Address"
          autoComplete="email"
          autoFocus
          required
          onChange={(event) => setEmail(event.target.value)}
        ></TextField>
      </div>
      <div>{emailValidationWord}</div>
      <br />
      <div>
        <TextField
          name="password"
          label="Password"
          autoComplete="current-password"
          type="password"
          required
          onChange={(event) => setPassword(event.target.value)}
        ></TextField>
      </div>
      <br />
      <div>
        <TextField
          name="password"
          label="Confirm Password"
          autoComplete="current-password"
          type="password"
          required
          onChange={(event) => setConfirmPassword(event.target.value)}
        ></TextField>
      </div>
      <br />
      <div>{passwordValidationWord}</div>
      <br />
      <div>
        <TextField
          name="name"
          label="이름"
          autoComplete="name"
          autoFocus
          required
          onChange={(event) => setName(event.target.value)}
        ></TextField>
      </div>
      <br />
      <div>
        <TextField
          name="nickname"
          label="닉네임"
          autoFocus
          required
          onKeyUp={handleKeyUp}
          onChange={(event) => setNickname(event.target.value)}
        ></TextField>
      </div>
      <div>{nicknameValidationWord}</div>
      <br />
      {isValidForm && (
        <Button
          variant="contained"
          onClick={handleRegister}
          style={{
            width: "100px",
            minWidth: "100px",
            backgroundColor: "#6DCEF5",
          }}
        >
          회원가입
        </Button>
      )}
      {!isValidForm && (
        <Button
          variant="contained"
          disabled
          style={{ width: "100px", minWidth: "100px", marginBottom: 5 }}
        >
          회원가입
        </Button>
      )}
      <br />

      <Button
        variant="outlined"
        onClick={() => navigate(-1)}
        style={{
          width: "100px",
          minWidth: "100px",
        }}
      >
        Back
      </Button>
    </Container>
  );
};

export default RegisterPage;
