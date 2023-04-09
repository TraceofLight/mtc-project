import axios from "axios";
import { useState, useEffect } from "react";
import NotificationList from "../components/Notification/NotificationList"
import { useSelector } from "react-redux"
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import DeleteIcon from '@mui/icons-material/Delete';
import Paper from '@mui/material/Paper';
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";
import Logo_NoAlarm from "../assets/icons/Logo_NoAlarm.png"
import "./style/Alarm.css"
 
const NotificationPage = () => {
  const [alarmList, setAlarmList] = useState([])
  const userStatus = useSelector(state => state.userStatus);

  const veiwAlarm = async () => {
    try {
      const response = await axios.get(
        `${urls.alarm.notification}selectAlarm/${userStatus.userIndex}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },}
      )
      setAlarmList(response.data) 
    } catch(error) {
      console.log(error)
    }
  };

  const deleteAlarm = async () => {
    try{
      const response = await axios.delete(
        `${urls.alarm.notification}deleteAlarmAll/${userStatus.userIndex}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },}
      )
      setAlarmList([])
    } catch(error) {
      console.log(error)
    }
  };
  useEffect(() => {
    veiwAlarm()
  }, []);

  if (alarmList.length === 0) {
    return (
      <>
      <h2>알람 없음</h2>

      <div id="wrap">
          <div class="center">
              <img src={Logo_NoAlarm}></img>
          </div>
      </div>                   
               
      </>
    ) 
  } else {
    return (
      <>
      <h2>알람</h2>
    <NotificationList alarmList={alarmList} />
    <Paper sx={{ position: 'fixed', bottom: 0, left: 0, right: 0 }} elevation={3}>
    <BottomNavigation
        showLabels

      >
        <BottomNavigationAction label="알람 모두 삭제" icon={<DeleteIcon />} disableRipple onClick={deleteAlarm}/>

      </BottomNavigation>
      </Paper>
    </>
    );
    
  };

  // return (
  //   <>
  //   {alarmList === []
  //   ?
  //   :<NotificationList alarmList={alarmList} />
  //   }
  //   </>

  // );
};
export default NotificationPage;