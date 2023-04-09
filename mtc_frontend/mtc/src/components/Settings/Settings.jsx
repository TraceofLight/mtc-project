import Button from "@mui/material/Button";

import logoutIcon from "assets/icons/Logo_Logout.png";
import userIcon from "assets/icons/Logo_User.png";
import blockUserIcon from "assets/icons/Logo_Block_User.png";
import alarmIcon from "assets/icons/Logo_Alarm.png";
import graphIcon from "assets/icons/Logo_Graph.png";
import backIcon from "assets/icons/Logo_Back.png";
import followUserIcon from "assets/icons/Logo_Follow_User.png";

import { useNavigate } from "react-router-dom";
import { authenticationInstance } from "firebaseConfig";
import Stack from "@mui/material/Stack";
// import { color, fontSize, textAlign } from "@mui/system";
const Settings = () => {
  const navigate = useNavigate();

  const logoutHandler = async () => {
    try {
      await authenticationInstance.signOut();
      navigate("/login", { replace: true });
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <>
      <Stack mt={4} justifyContent="flex-start" alignItems="flex-start">
        <Stack mt={3}>
          <div>
            &nbsp;&nbsp;
            <Button
              onClick={logoutHandler}
              style={{ color: "black", fontSize: "15px" }}
            >
              <img src={logoutIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;로그아웃
            </Button>
          </div>
        </Stack>

        <Stack mt={2}>
          <div>
            &nbsp;&nbsp;
            <Button
              onClick={() => navigate("/follow-list")}
              style={{ color: "black", fontSize: "15px" }}
            >
              <img src={followUserIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;팔로우 리스트
            </Button>
          </div>
        </Stack>

        <Stack mt={2}>
          <div>
            &nbsp;&nbsp;
            <Button
              onClick={() => navigate("/block-list")}
              style={{ color: "black", fontSize: "15px" }}
            >
              <img src={blockUserIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;차단 리스트
            </Button>
          </div>
        </Stack>

        <Stack mt={2}>
          <div>
            &nbsp;&nbsp;
            <Button
              onClick={() => navigate("/notification-setting")}
              style={{ color: "black", fontSize: "15px" }}
            >
              {" "}
              <img src={alarmIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;알림 설정
            </Button>
          </div>
        </Stack>

        <Stack mt={2}>
          <div>
            &nbsp;&nbsp;
            <Button
              onClick={() => navigate("/my-page")}
              style={{ color: "black", fontSize: "15px" }}
            >
              <img src={userIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;My Page
            </Button>
          </div>
        </Stack>

        <Stack mt={2}>
          <div>
            {/* 게시물 통계 url 연결해야 됨   */}
            &nbsp;&nbsp;
            <Button
              onClick={() => navigate()}
              style={{ color: "black", fontSize: "15px" }}
            >
              <img src={graphIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;게시물 통계
            </Button>
          </div>
        </Stack>

        <Stack mt={2}>
          <div>
            &nbsp;&nbsp;
            <Button
              onClick={() => navigate("/")}
              style={{ color: "black", fontSize: "15px" }}
            >
              <img src={backIcon} style={{ width: "20px" }} alt="" />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;돌아가기
            </Button>
          </div>
        </Stack>
      </Stack>
    </>
  );
};

export default Settings;
