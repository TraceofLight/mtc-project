import Container from "@mui/system/Container";
import Button from "@mui/material/Button";
import Switch from "@mui/material/Switch";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";

import { requestGet, requestPut } from "api/axios";
import urls from "api/url";

const NotificationSettingPage = () => {
  const navigate = useNavigate();
  const userStatus = useSelector((state) => state.userStatus);
  const userIndex = userStatus.userIndex;

  const [notifyFollowSwitch, setNotifyFollowSwitch] = useState(null);
  const [notifyEvaluateSwitch, setNotifyEvaluateSwitch] = useState(null);
  const [notifyCommentSwitch, setNotifyCommentSwitch] = useState(null);
  const [notifyReplySwitch, setNotifyReplySwitch] = useState(null);

  useEffect(() => {
    const getSetting = async () => {
      const responseData = await requestGet(
        `${urls.setting.getSettingInformation}${userIndex}`
      );
      setNotifyFollowSwitch(!responseData.settingIgnoreFollow);
      setNotifyEvaluateSwitch(!responseData.settingIgnoreEvaluate);
      setNotifyCommentSwitch(!responseData.settingIgnoreComment);
      setNotifyReplySwitch(!responseData.settingIgnoreReply);
    };
    getSetting();
  }, [userIndex]);

  useEffect(() => {
    const notifySettingHandler = async () => {
      await requestPut(urls.setting.updateSetting, {
        settingIgnoreFollow: !notifyFollowSwitch,
        settingIgnoreEvaluate: !notifyEvaluateSwitch,
        settingIgnoreComment: !notifyCommentSwitch,
        settingIgnoreReply: !notifyReplySwitch,
        settingUserIndex: userIndex,
      });
    };
    if (
      notifyFollowSwitch !== null &&
      notifyEvaluateSwitch !== null &&
      notifyCommentSwitch !== null &&
      notifyReplySwitch !== null
    ) {
      notifySettingHandler();
    }
  }, [
    notifyFollowSwitch,
    notifyEvaluateSwitch,
    notifyCommentSwitch,
    notifyReplySwitch,
    userIndex,
  ]);

  const handleFollowSwitchChange = (event) => {
    setNotifyFollowSwitch(event.target.checked);
  };

  const handleEvaluateSwitchChange = (event) => {
    setNotifyEvaluateSwitch(event.target.checked);
  };

  const handleCommentSwitchChange = (event) => {
    setNotifyCommentSwitch(event.target.checked);
  };

  const handleReplySwitchChange = (event) => {
    setNotifyReplySwitch(event.target.checked);
  };

  return (
    <>
      <Container maxWidth="sm">
        <h2>알림 설정</h2>
        <br />
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          팔로우 알림
          <Switch
            checked={notifyFollowSwitch}
            onChange={handleFollowSwitchChange}
          />
        </div>
        <br />
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          평가 알림
          <Switch
            checked={notifyEvaluateSwitch}
            onChange={handleEvaluateSwitchChange}
          />
        </div>
        <br />
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          댓글 알림
          <Switch
            checked={notifyCommentSwitch}
            onChange={handleCommentSwitchChange}
          />
        </div>
        <br />
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          답글 알림
          <Switch
            checked={notifyReplySwitch}
            onChange={handleReplySwitchChange}
          />
        </div>
        <br />
        <Button onClick={() => navigate("/settings")}>뒤로 가기</Button>
      </Container>
    </>
  );
};

export default NotificationSettingPage;
