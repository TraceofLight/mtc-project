import NotificationListItem from "./NotificationListItem";
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';

const NotificationList = (props) => {

  const veiwNotifications = props.alarmList.map((alarm) =>
  <ListItem alignItems="flex-start">
    <NotificationListItem key={alarm} alarm={alarm} />
  </ListItem>
  )
  return (
    <List>
    {veiwNotifications}
    </List>
  );
};
export default NotificationList;