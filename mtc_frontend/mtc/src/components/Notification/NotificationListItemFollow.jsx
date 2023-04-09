import { useState, useEffect } from "react";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import axios from "axios";
import { useSelector } from "react-redux";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";


const NotificationListItemFollow = (props) => {
  const userStatus = useSelector(state => state.userStatus);
  const [isFollow, setIsFollow] = useState(false);

  const FollowRequest = async () => {
    try {
      const response = await axios.post(
        urls.relation.followUser,
        {
          followTargetIndex: props.alarm.alarmFollowUserIndex,
          followUserIndex: userStatus.userIndex,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
          }},
      );
      console.log(response)
      setIsFollow(true);
    } catch (error) {
      console.log(error);
    }
  };

  const cancelFollowRequest = async () => {
    try {
      const response = await axios.delete(
        urls.relation.unfollowUser,
        {
          data: {
            followTargetIndex: props.alarm.alarmFollowUserIndex,
            followUserIndex: userStatus.userIndex,
          },
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
            },
        }
      );
      console.log(response)
      setIsFollow(false);
    } catch (error) {
      console.log(error);
      console.log(userStatus.userIndex)
    }
  };

  useEffect(() => {
    const followCheck = async () => {
      try {
        const response = await axios.get(
          `${urls.relation.checkFollow}?target-index=${props.alarm.alarmFollowUserIndex}&user-index=${userStatus.userIndex}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
            },
          },
        )
        setIsFollow(response.data)
      } catch (error) {
        console.log(error);
      }
    }
    followCheck();
  }, []);
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={2}>
          <Avatar src="#"></Avatar>
        </Grid>
        <Grid item xs={8}>
          <div>
            <b>{props.alarm.alarmFollowUserNickname}</b>님이 회원님을 팔로우 했습니다.
          </div>
        </Grid>
        <Grid item xs={2}>
          {isFollow ? (
            <Button variant="outlined" onClick={cancelFollowRequest}>
              팔로잉
            </Button>
          ) : (
            <Button variant="contained" onClick={FollowRequest}>
              팔로우
            </Button>
          )}
        </Grid>
      </Grid>
    </Box>
  );
};
export default NotificationListItemFollow;
