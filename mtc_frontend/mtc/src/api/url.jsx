// const HOST = "http://localhost:8080/";
const HOST = "https://www.sangta.ch/";
// const HOST = "http://70.12.247.178:8080/";

const USER = "user/";
const ARTICLE = "article/";
const RELATION = "relation/";
const ALARM = "alarm/";
const COMMENT = "comment/";
const REPLY = "reply/";
const SETTING = "setting/";
const REPORT = "report/create/";

const urls = {
  accounts: {
    login: HOST + USER + "login/",
    logout: HOST + USER + "logout/",
    register: HOST + USER + "register/",
    getUserInformation: HOST + USER + "information/",
    getUserInformationByIndex: HOST + USER + "information/index/",
    nicknameCheck: HOST + USER + "nicknameCheck/",
    updateProfile: HOST + USER + "updateProfile/",
    updateNickname: HOST + USER + "updateNickname/",
    userCheck: HOST + USER + "userCheck/",
    userDelete: HOST + USER + "delete/",
  },
  article: {
    getUserArticle: HOST + ARTICLE,
    getEvaluateLog: HOST + ARTICLE + "evaluate/", // 평가한 게시물 로그 조회
    getViewLog: HOST + ARTICLE + "recent/", // 평가한 게시물 로그 조회
  },
  relation: {
    checkFollow: HOST + RELATION + "checkFollow/",
    followUser: HOST + RELATION + "createfollow/",
    unfollowUser: HOST + RELATION + "deletefollow/",
    getFollowList: HOST + RELATION + "selectfollowlist/",
    blockUser: HOST + RELATION + "createblock/",
    unblockUser: HOST + RELATION + "deleteblock/",
    getBlockList: HOST + RELATION + "selectblocklist/",
  },
  setting: {
    getSettingInformation: HOST + SETTING + "selectsetting/",
    updateSetting: HOST + SETTING + "updatesetting/",
  },
  alarm: {
    notification: HOST + ALARM,
  },
  comment: HOST + COMMENT,
  reply: HOST + REPLY,
  report: HOST + REPORT,
};

export default urls;
