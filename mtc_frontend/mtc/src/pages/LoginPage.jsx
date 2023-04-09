import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Box from "@mui/system/Box";

import { useNavigate } from "react-router-dom";
import { useState } from "react";
import {
  signInWithPopup,
  GoogleAuthProvider,
  signInWithEmailAndPassword,
} from "firebase/auth";

import { authenticationInstance } from "firebaseConfig";
import { requestGet, requestFormData } from "api/axios";

import urls from "api/url";
import { Grid, Stack } from "@mui/material";

const LoginPage = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleGoogleLogin = async () => {
    const provider = new GoogleAuthProvider();
    try {
      await signInWithPopup(authenticationInstance, provider);
      const user = authenticationInstance.currentUser;

      const isRegistered = await requestGet(
        `${urls.accounts.userCheck}${user.uid}`
      );

      if (!isRegistered) {
        const credentials = new FormData();
        credentials.append("userUid", user.uid);
        credentials.append("userNickname", user.email);
        await requestFormData(urls.accounts.register, credentials);
      }
      navigate("/");
    } catch (error) {
      alert(error.message);
    }
  };

  const handleLogin = async () => {
    try {
      await signInWithEmailAndPassword(authenticationInstance, email, password);
      navigate("/");
    } catch (error) {
      alert(error.message);
    }
  };

  const handleKeyUp = (event) => {
    if (event.key === "Enter") {
      handleLogin();
    }
  };

  return (
    <Container maxWidth="sm">
      {/* 로고 시작 */}
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          alignItems: "center",
          justifyContent: "center",
          marginTop: 5,
          marginBottom: 5,
        }}
      >
        <Box>
          <img
            src={require("../assets/images/main_logo.png")}
            width="200"
            height="200"
          ></img>
        </Box>
      </Box>
      {/* 로고 끝 */}
      <br />
      <Box>
        <Grid container spacing={3}>
          <Grid item xs={2}></Grid>
          <Grid item xs={8}>
            <Stack spacing={2}>
              <TextField
                id="email"
                label="E - mail"
                inputProps={{ maxLength: 100 }}
                onChange={(event) => setEmail(event.target.value)}
              />
              <TextField
                id="password"
                label="Password"
                type="password"
                inputProps={{ maxLength: 30 }}
                onChange={(event) => setPassword(event.target.value)}
                onKeyUp={handleKeyUp}
              />
              <Button
                onClick={handleLogin}
                variant="contained"
                style={{ backgroundColor: "#6DCEF5" }}
              >
                LOGIN
              </Button>
              <Button onClick={handleGoogleLogin}>
                <img
                  src={require("../assets/images/google.png")}
                  alt="google logo"
                  width={"20"}
                />
                <p>&nbsp; Google Login</p>
              </Button>
              <hr></hr>
              <Button
                variant="outlined"
                onClick={() => navigate("/pw-recovery")}
              >
                패스워드 찾기
              </Button>
              <Button variant="outlined" onClick={() => navigate("/register")}>
                가입하기
              </Button>
            </Stack>
          </Grid>
          <Grid item xs={2}></Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default LoginPage;
