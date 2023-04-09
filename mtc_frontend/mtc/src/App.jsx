import { Route, Routes } from "react-router-dom";
import MainPage from "pages/MainPage";
import MyPagePage from "pages/MyPagePage";
import SettingsPage from "pages/SettingsPage";
import LoginPage from "pages/LoginPage";
import PasswordRecoveryPage from "pages/PasswordRecoveryPage";
import NeedVerificationPage from "pages/NeedVerificationPage";
import NoMatchPage from "pages/NoMatchPage";
import BlockPage from "pages/BlockPage";
import FollowPage from "pages/FollowPage";
import NotificationSettingPage from "pages/NotificationSettingPage";
import UserPage from "pages/UserPage";
import CreatePage from "pages/CreatePage";
import PhoneVerificationPage from "pages/PhoneVerificationPage";
import RegisterPage from "pages/RegisterPage";
import PostPage from "pages/PostPage";
import TestPage from "pages/TestPage";
import TestBar from "components/Test/TestBar";
import NotificationPage from "pages/NotificationPage";
import SearchPage from "pages/SearchPage";
import EditPage from "pages/EditPage";
import HotPage from "pages/HotPage"
import PrivateRoute from "PrivateRoute";
import RecentPage from "pages/RecentPage";
const App = () => {
  return (
    <>
      <TestBar />
      <Routes>
        <Route path="*" element={<NoMatchPage />}></Route>
        <Route path="/need-verification" element={<NeedVerificationPage />} />
        <Route path="/phone-verification" element={<PhoneVerificationPage />} />
        <Route element={<PrivateRoute needLogin={false} />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/pw-recovery" element={<PasswordRecoveryPage />} />
          <Route path="/test" element={<TestPage />} />
        </Route>

        <Route element={<PrivateRoute needLogin={true} />}>
          <Route path="/" element={<MainPage />} />
          <Route path="/settings" element={<SettingsPage />} />
          <Route path="/my-page" element={<MyPagePage />} />
          <Route path="/block-list" element={<BlockPage />} />
          <Route path="/follow-list" element={<FollowPage />} />
          <Route
            path="/phone-verification"
            element={<PhoneVerificationPage />}
          />
          <Route
            path="/notification-setting"
            element={<NotificationSettingPage />}
          />
          <Route path="/user/:userIndex" element={<UserPage />} />
          <Route path="/post/new" element={<CreatePage />} />
          <Route path="/post/:articleindex" element={<PostPage />} />
          <Route path="/notification" element={<NotificationPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/edit/:articleindex" element={<EditPage />}/>
          <Route path="/hot" element={<HotPage />} />
          <Route path="/recent" element={<RecentPage />} />
        </Route>
      </Routes>
    </>
  );
};

export default App;
