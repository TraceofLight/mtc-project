import Container from "@mui/system/Container";
import Button from "@mui/material/Button";
import Avatar from "@mui/material/Avatar";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Badge from "@mui/material/Badge";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import { useState, useEffect, useRef } from "react";
import Modal from "react-modal";
import EditImage from "../assets/icons/Logo_EditImage.png";
import Stack from "@mui/material/Stack";

import urls from "api/url";
import {
  requestUserNickname,
  requestDelete,
  requestPut,
  requestPutFormData,
} from "api/axios";

import { emailCheck } from "components/EmailCheck";
import { authenticationInstance } from "firebaseConfig";
import { sendPasswordResetEmail, deleteUser } from "firebase/auth";

const UserPage = () => {
  const navigate = useNavigate();
  const imageRef = useRef();

  const userStatus = useSelector((state) => state.userStatus);
  const userToken = userStatus.userToken;
  const nickname = userStatus.userNickname;
  const userIndex = userStatus.userIndex;

  const handleImage = async (event) => {
    const file = event.target.files[0];
    const validImageTypes = ["image/jpeg", "image/png", "image/gif"];

    if (file && file.size <= 2000000 && validImageTypes.includes(file.type)) {
      const profileImage = new FormData();
      profileImage.append("userMultipartFile", file);
      await requestPutFormData(urls.accounts.updateProfile, profileImage);
      window.location.reload();
    } else if (!validImageTypes.includes(file.type)) {
      alert("이미지 파일이 아닙니다.");
    } else {
      alert("프로필 사진은 2MB 이하로 가능합니다.");
    }
  };

  // 모달을 통한 닉네임 변경
  const [nicknameModalIsOpen, setNicknameModalIsOpen] = useState(false);
  const [changeNickname, setChangeNickname] = useState("");
  const [nicknameValidationWord, setNicknameValidationWord] = useState("");
  const [isValidNickname, setIsValidNickname] = useState(null);

  const openChangeNicknameModal = () => {
    setNicknameModalIsOpen(true);
  };

  const closeChangeNicknameModal = () => {
    setNicknameModalIsOpen(false);
  };

  const popupModalHandler = () => {
    openChangeNicknameModal();
  };

  const sendChangePasswordEmailHandler = async () => {
    try {
      await sendPasswordResetEmail(authenticationInstance, userToken.email);
      alert("송신 완료");
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    const nicknameCheckHandler = async () => {
      try {
        const checkResult = await requestUserNickname(
          `${urls.accounts.nicknameCheck}${changeNickname}`
        );
        if (checkResult) {
          setIsValidNickname(true);
          setNicknameValidationWord("사용이 가능한 닉네임입니다.");
        } else {
          setIsValidNickname(false);
          setNicknameValidationWord("사용이 불가능한 닉네임입니다.");
        }
      } catch (error) {
        console.log(error.message);
      }
    };

    if (changeNickname === "") {
      setIsValidNickname(false);
      setNicknameValidationWord("");
    } else if (emailCheck(changeNickname)) {
      setIsValidNickname(false);
      setNicknameValidationWord("이메일 형식의 닉네임은 사용이 불가능합니다.");
    } else {
      nicknameCheckHandler();
    }
  }, [changeNickname]);

  const changeNicknameHandler = async () => {
    try {
      await requestPut(urls.accounts.updateNickname, {
        userNickname: changeNickname,
        userUid: userToken?.uid,
      });
      window.location.reload();
      closeChangeNicknameModal();
    } catch (error) {
      console.log(error);
    }
  };

  const [deleteUserModalIsOpen, setDeleteUserModalIsOpen] = useState(false);

  const openDeleteUserModal = () => {
    setDeleteUserModalIsOpen(true);
  };

  const closeDeleteUserModal = () => {
    setDeleteUserModalIsOpen(false);
  };

  const deleteAccountHandler = async () => {
    const user = authenticationInstance.currentUser;
    try {
      await requestDelete(`${urls.accounts.userDelete}${userIndex}`);
      await deleteUser(user);
      navigate("/login");
    } catch (error) {
      alert("다시 로그인한 이후 시도해주세요.");
      authenticationInstance.signOut();
      navigate("");
    }
  };

  return (
    <>
      <Container maxWidth="sm">
        <Grid
          container
          mt={5}
          spacing={0}
          direction="column"
          alignItems="center"
        >
          <Grid item xs={12} mt={4}></Grid>
          <Grid
            item
            xs={12}
            mt={4}
            style={{
              margin: "auto 0px",
              justifyContent: "center",
              felxWrap: "wrap",
              alignItems: "center",
              alignContent: "center",
              display: "flex",
              flexDirection: "column",
              height: "250px",
              width: "250px",
              position: "relative",
            }}
          >
            {userStatus.userProfilePictureSource === "" ? (
              <Avatar style={{ width: "250px", height: "250px" }}></Avatar>
            ) : (
              <Avatar
                style={{ width: "250px", height: "250px" }}
                src={userStatus.userProfilePictureSource}
              ></Avatar>
            )}
          </Grid>
          <Grid>
            <Badge
              style={{ position: "absolute", top: "320px", left: "250px" }}
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
              <img src={EditImage} style={{ width: "40px" }} />
            </Badge>
          </Grid>
        </Grid>
        <Grid container>
          <Grid item xs={1}></Grid>
          <Grid item xs={11}>
            <Grid item xs={12} mt={4}>
              아이디{" "}
            </Grid>
            <Grid item xs={12} mt={1}>
              {userToken.email}
            </Grid>
            <Grid item xs={12} mt={3}>
              비밀번호
              <Button onClick={sendChangePasswordEmailHandler}>변경</Button>
            </Grid>

            <Grid item xs={12} mt={2}>
              닉네임
            </Grid>
            <Grid item xs={12} mt={1}>
              {nickname}{" "}
              <Button onClick={popupModalHandler}>닉네임 변경</Button>
            </Grid>
            <Grid item xs={12} style={{ textAlign: "right" }}></Grid>
            <Grid item xs={12} mt={2}>
              이름
            </Grid>
            <Grid item xs={12} mt={1}>
              {" "}
              {userToken.displayName}
            </Grid>
            <Grid item xs={12} mt={3}>
              전화번호
            </Grid>
            <Grid item xs={12} mt={1}>
              {" "}
              {userToken.phoneNumber}
            </Grid>
          </Grid>
          <Grid container mt={5}>
            <Grid item xs={7}></Grid>
            <Grid item xs={5}>
              <Button onClick={() => navigate("/settings")}>돌아가기</Button>
              <Button color="error" onClick={openDeleteUserModal}>
                탈퇴하기
              </Button>
            </Grid>
          </Grid>
        </Grid>

        {/* 여기서부터 모달 */}

        <Modal
          style={{ zIndex: "10000" }}
          isOpen={nicknameModalIsOpen}
          onRequestClose={closeChangeNicknameModal}
          contentLabel="Change Nickname Modal"
        >
          <h1>닉네임 변경</h1>
          <TextField
            name="nickname"
            label="닉네임"
            autoFocus
            required
            onChange={(event) => setChangeNickname(event.target.value)}
          ></TextField>
          <div>{nicknameValidationWord}</div>
          {isValidNickname && (
            <Button onClick={changeNicknameHandler}>닉네임 변경하기</Button>
          )}
          {!isValidNickname && <Button disabled>닉네임 변경하기</Button>}
          <Button onClick={closeChangeNicknameModal}>뒤로 가기</Button>
        </Modal>

        <Modal
          isOpen={deleteUserModalIsOpen}
          onRequestClose={closeDeleteUserModal}
          contentLabel="Delete User Modal"
        >
          <h1>정말 탈퇴하시겠습니까?</h1>
          <Button color="error" onClick={deleteAccountHandler}>
            탈퇴하기
          </Button>
          <Button onClick={closeDeleteUserModal}>뒤로 가기</Button>
        </Modal>
      </Container>
    </>
  );
};

export default UserPage;
