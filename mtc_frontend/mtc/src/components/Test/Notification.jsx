import Badge from '@mui/material/Badge';
import NotificationsIcon from '@mui/icons-material/Notifications';
import { useState,useEffect } from 'react';
import axios from 'axios';
import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import urls from "api/url";
import { authenticationInstance } from "firebaseConfig";

const Notification = () => {
  const location = useLocation();
  const [alarmCount,setAlarmCount] = useState(0)
  const userStatus = useSelector((state) => state.userStatus);

  const getAlarm = async ()=> {
    try {
      const response = await axios.get(
        `${urls.alarm.notification}countAlarm/${userStatus.userIndex}`,
        {headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authenticationInstance.currentUser.accessToken}`,
        },}
        )
        setAlarmCount(response.data)
    } catch (error) {
      console.log('안됨...')
    }
   
  };
  useEffect(() => {
    console.log(location.pathname);
    getAlarm();
     
  }, [location]);

  return (
    <>
    {
      alarmCount === 0 || location.pathname === "/notification"
      ?<NotificationsIcon sx={{color:"black"}}/>
      :<Badge badgeContent={alarmCount} color="error" max={10}>
      <NotificationsIcon sx={{color:"black"}}/>
      </Badge>
    }
    </>
  );
};
export default Notification; 