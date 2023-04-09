import { useEffect } from "react";
import { Navigate, useOutlet } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { authenticationCheck } from "redux/slice/userStatusSlice";

const PrivateRoute = ({ needLogin }) => {
  const outlet = useOutlet();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(authenticationCheck());
  }, [dispatch]);

  const userStatus = useSelector((state) => state.userStatus);
  const userToken = userStatus.userToken;
  const emailVerified = userToken?.emailVerified;
  const phoneNumber = userToken?.phoneNumber;

  if (window.location.href === "http://www.sangta.ch/register") {
    return outlet;
  } else if (userToken === null) {
    // 로그인 요구 정보를 확인
    return needLogin ? (
      // 로그인이 필요한 경우라면 로그인 페이지로 이동
      <Navigate to="/login" replace={true} />
    ) : (
      // 로그인이 필요하지 않은 페이지라면 그대로 이동
      outlet
    );

    // 로그인 정보가 존재할 때
  } else {
    // 이메일 혹은 휴대폰이 미확인된 경우
    if (emailVerified === false || phoneNumber === null) {
      // 안내 페이지로 이동
      return <Navigate to="/need-verification" replace={true} />;
      // 이메일이 정상 확인된 경우
    } else {
      // 로그인이 필요한 페이지라면 그대로 이동, 아니라면 메인 페이지로 이동
      return needLogin ? outlet : <Navigate to="/" replace={true} />;
    }
  }
};

export default PrivateRoute;
