import NotificationListItemArticle from "./NotificationListItemArticle";
import NotificationListItemComment from "./NotificationListItemComment";
import NotificationListItemFollow from "./NotificationListItemFollow";
import NotificationListItemReply from "./NotificationListItemReply";

const NotificationListItem = (props) => {

  const alarmType = props.alarm.alarmDtype

  switch (alarmType) {
    case 'C':
    return (
      <NotificationListItemComment alarm={props.alarm} />
    );
    case 'R':
      return (
        <NotificationListItemReply alarm={props.alarm} />
      );
    case 'F':
      return (
        <NotificationListItemFollow alarm={props.alarm} />
      );
    case 'A':
      return (
        <NotificationListItemArticle alarm={props.alarm}/>
      );
    default:
      return (
        <div>대충 로딩 애니메이션</div>
      )
  }; 

};
export default NotificationListItem;