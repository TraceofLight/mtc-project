import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";

import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router";
import { useSelector } from "react-redux";

import { requestDelete, requestGet, requestPost } from "api/axios";
import urls from "api/url";

import "./style/UserPage.css";
import goodIcon from "assets/icons/Good_Icon.png";
import badIcon from "assets/icons/Bad_Icon.png";
import viewIcon from "assets/icons/View_Icon.png";

const UserPage = () => {
  const params = useParams();
  const navigate = useNavigate();

  const userStatus = useSelector((state) => state.userStatus);
  const myIndex = userStatus.userIndex;

  // 대상 유저의 index, 닉네임, 프로필 사진
  const targetIndex = params.userIndex;
  const [targetNickname, setTargetNickname] = useState("");
  const [targetProfileSource, setTargetProfileSource] = useState(null);

  // 해당 유저 관련 정보
  const [isFollow, setIsFollow] = useState(null);
  const [articleCount, setArticleCount] = useState(null);
  const [followerCount, setFollowerCount] = useState(null);

  // 세부 페이지 여부
  const [showDetail, setShowDetail] = useState(false);

  // 각 세부페이지 작동 확인
  const [showWrite, setShowWrite] = useState(false);
  const [showEvaluate, setShowEvaluate] = useState(false);
  const [showRecent, setShowRecent] = useState(false);

  // 작성한 게시물
  const [pageData, setPageData] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(null);

  // 평가한 게시물
  const [evaluateData, setEvaluateData] = useState([]);
  const [evaluatePage, setEvaluatePage] = useState(0);
  const [totalEvaluatePage, setTotalEvaluatePage] = useState(null);

  // 최근에 본 게시물
  const [viewRecentData, setViewRecentData] = useState([]);
  const [recentViewPage, setRecentViewPage] = useState(0);
  const [totalRecentViewPage, setTotalRecentViewPage] = useState(null);

  // index를 받아서 닉네임으로 반환하는 effect
  useEffect(() => {
    const findTargetNickname = async () => {
      const responseData = await requestGet(
        `${urls.accounts.getUserInformationByIndex}${targetIndex}`
      );

      if (typeof responseData === String) {
        navigate("/wrong-page");
      } else {
        setTargetNickname(responseData.userNickname);
        setTargetProfileSource(responseData.userProfilePictureSource);
        setArticleCount(responseData.userArticleCount);
        setFollowerCount(responseData.userFollowCount);
      }
    };
    findTargetNickname();
  });

  // 작성 목록을 받아오는 effect
  useEffect(() => {
    const userArticleInformation = async () => {
      const responseData = await requestGet(
        `${urls.article.getUserArticle}?pageNumber=${page}&pageSize=15&viewer=${myIndex}&writer=${targetIndex}`
      );
      console.log(responseData, page);
      setPageData([...responseData.articleViewDtoList]);
      setTotalPage(parseInt(responseData.totalPage) - 1);
    };
    userArticleInformation();
  }, [page, myIndex, targetIndex]);

  // 평가한 게시물을 반환하는 effect
  useEffect(() => {
    const getEvaluateData = async () => {
      const responseData = await requestGet(
        `${urls.article.getEvaluateLog}?pageNumber=${evaluatePage}&pageSize=15&user=${myIndex}`
      );
      setEvaluateData([...responseData.articleViewDtoList]);
      setTotalEvaluatePage(parseInt(responseData.totalPage) - 1);
    };
    if (myIndex === parseInt(targetIndex)) {
      getEvaluateData();
    }
  }, [evaluatePage, myIndex, targetIndex]);

  // 최근 본 게시물을 반환하는 effect
  useEffect(() => {
    const getRecentViewData = async () => {
      const responseData = await requestGet(
        `${urls.article.getViewLog}?pageNumber=${recentViewPage}&pageSize=15&user=${myIndex}`
      );
      setViewRecentData([...responseData.articleViewDtoList]);
      setTotalRecentViewPage(parseInt(responseData.totalPage) - 1);
    };
    if (myIndex === parseInt(targetIndex)) {
      getRecentViewData();
    }
  }, [recentViewPage, myIndex, targetIndex]);

  // 팔로우 여부를 반환하는 effect
  useEffect(() => {
    const followChecker = async () => {
      const responseData = await requestGet(
        `${urls.relation.checkFollow}?target-index=${targetIndex}&user-index=${myIndex}`
      );
      setIsFollow(responseData);
    };
    followChecker();
  });

  // 팔로우
  const followHandler = async () => {
    await requestPost(urls.relation.followUser, {
      followUserIndex: myIndex,
      followTargetIndex: targetIndex,
    });
    setIsFollow(true);
    setFollowerCount((count) => count + 1);
  };

  // 언팔로우
  const unfollowHandler = async () => {
    await requestDelete(urls.relation.unfollowUser, {
      followUserIndex: myIndex,
      followTargetIndex: targetIndex,
    });
    setIsFollow(false);
    setFollowerCount((count) => count - 1);
  };

  // 더 보기 버튼 관리
  const viewWriteHandler = () => {
    setShowDetail(true);
    setShowWrite(true);
    setPage(0);
  };

  const viewEvaluateHandler = () => {
    setShowDetail(true);
    setShowEvaluate(true);
  };

  const viewRecentHandler = () => {
    setShowDetail(true);
    setShowRecent(true);
  };

  const detailCloseHandler = () => {
    setShowDetail(false);
    setShowWrite(false);
    setShowEvaluate(false);
    setShowRecent(false);
    setPage(0);
    setEvaluatePage(0);
    setRecentViewPage(0);
  };

  // 본인 페이지
  if (myIndex === parseInt(targetIndex)) {
    return (
      <>
        <Container maxWidth="sm">
          <h2>{targetNickname}</h2>
          <Grid container spacing={2} justifyContent="space-between">
            <Grid item xs={4}>
              {targetProfileSource === "" && (
                <Avatar
                  style={{
                    width: "100px",
                    height: "100px",
                    top: "-20px",
                    left: "10px",
                  }}
                ></Avatar>
              )}
              {targetProfileSource !== "" && (
                <Avatar
                  style={{
                    width: "100px",
                    height: "100px",
                    top: "-20px",
                    left: "10px",
                  }}
                >
                  <img
                    src={targetProfileSource}
                    alt="profile"
                    style={{
                      width: "100%",
                      height: "100%",
                      objectFit: "cover",
                    }}
                  />
                </Avatar>
              )}
            </Grid>
            <Grid item xs={6}>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-around",
                  marginBottom: 3,
                }}
              >
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <div style={{ fontSize: 16 }}>작성글</div>
                  <div style={{ fontSize: 16 }}>{articleCount}</div>
                </Box>
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <div style={{ fontSize: 16 }}>팔로워</div>
                  <div style={{ fontSize: 16 }}>{followerCount}</div>
                </Box>
              </Box>
            </Grid>
          </Grid>
          <br />
          <hr />

          {/* 기본 출력 페이지 */}
          {!showDetail && (
            <>
              <h3>최근에 작성한 게시물</h3>
              {/* 최근 작성 게시물 */}
              {pageData.length === 0 ? (
                <div className="empty-article">
                  최근에 작성한 게시물이 없습니다.
                </div>
              ) : (
                <>
                  <div className="post-figure">
                    {pageData.slice(0, 3).map((data, i) => (
                      <div
                        key={i}
                        style={{ padding: "16px" }}
                        onClick={() => navigate(`/post/${data.articleIndex}`)}
                      >
                        <Avatar style={{ width: "90px", height: "90px" }}>
                          <img
                            src={data.articlePictureSource}
                            alt=""
                            style={{
                              width: "100%",
                              height: "100%",
                              objectFit: "cover",
                            }}
                          />
                        </Avatar>
                      </div>
                    ))}
                  </div>
                  <Button onClick={viewWriteHandler}>더 보기</Button>
                </>
              )}

              <hr />

              {/* 평가한 게시물 */}
              <h3>최근에 평가한 게시물</h3>
              {evaluateData.length === 0 ? (
                <div className="empty-article">
                  최근에 평가한 게시물이 없습니다.
                </div>
              ) : (
                <>
                  <div className="post-figure">
                    {evaluateData.slice(0, 3).map((data, i) => (
                      <div
                        key={i}
                        style={{ padding: "16px" }}
                        onClick={() => navigate(`/post/${data.articleIndex}`)}
                      >
                        <Avatar style={{ width: "90px", height: "90px" }}>
                          <img
                            src={data.articlePictureSource}
                            alt=""
                            style={{
                              width: "100%",
                              height: "100%",
                              objectFit: "cover",
                            }}
                          />
                        </Avatar>
                      </div>
                    ))}
                  </div>
                  <Button onClick={viewEvaluateHandler}>더 보기</Button>
                </>
              )}
              <hr />

              {/* 최근에 본 게시물 */}
              <h3>최근에 본 게시물</h3>
              {viewRecentData.length === 0 ? (
                <div className="empty-article">
                  최근에 본 게시물이 없습니다.
                </div>
              ) : (
                <>
                  <Button onClick={viewRecentHandler}>더 보기</Button>
                  <div className="post-figure">
                    {viewRecentData.slice(0, 3).map((data, i) => (
                      <div
                        key={i}
                        style={{ padding: "16px" }}
                        onClick={() => navigate(`/post/${data.articleIndex}`)}
                      >
                        <Avatar style={{ width: "90px", height: "90px" }}>
                          <img
                            src={data.articlePictureSource}
                            alt=""
                            style={{
                              width: "100%",
                              height: "100%",
                              objectFit: "cover",
                            }}
                          />
                        </Avatar>
                      </div>
                    ))}
                  </div>
                </>
              )}

              <hr />
            </>
          )}
          {showWrite && <h3>최근에 작성한 게시물</h3>}
          {showEvaluate && <h3>최근에 평가한 게시물</h3>}
          {showRecent && <h3>최근에 본 게시물</h3>}

          {/* 작성글 세부 */}

          {pageData.length === 0 && showWrite && (
            <div className="empty-article">작성한 글이 없습니다.</div>
          )}
          {showWrite &&
            pageData.length !== 0 &&
            pageData.map((data, j) => (
              <div
                key={j}
                style={{ padding: "16px" }}
                onClick={() => navigate(`/post/${data.articleIndex}`)}
              >
                <div className="each-post-information">
                  <Avatar style={{ width: "100px", height: "100px" }}>
                    <img src={data.articlePictureSource} alt="" />
                  </Avatar>
                  <div className="each-post-data-container">
                    <div>
                      <div>{data.articleTitle}</div>
                      {data.hashtagList.map((hashtag, index) => (
                        <span key={index}>#{hashtag} </span>
                      ))}
                    </div>
                    <div className="good-bad-view-icons">
                      <img
                        className="post-information-icon"
                        src={viewIcon}
                        alt=""
                      />
                      <span>{data.articleHit}</span>
                      <img
                        className="post-information-icon"
                        src={goodIcon}
                        alt=""
                      />
                      <span className="goodCount">{data.goodCount}</span>
                      <img
                        className="post-information-icon"
                        src={badIcon}
                        alt=""
                      />
                      <span className="badCount">{data.badCount}</span>
                    </div>
                  </div>
                </div>
                <br />
                <hr />
              </div>
            ))}
          {showWrite && page > 0 && (
            <Button onClick={() => setEvaluatePage((page) => page - 1)}>
              이전 페이지
            </Button>
          )}
          {showWrite && page < totalPage && (
            <Button onClick={() => setEvaluatePage((page) => page + 1)}>
              다음 페이지
            </Button>
          )}

          {/* 최근에 본 게시글 세부 */}

          {viewRecentData.length === 0 && showRecent && (
            <div className="empty-article">평가한 글이 없습니다.</div>
          )}
          {showRecent &&
            viewRecentData.length !== 0 &&
            viewRecentData.map((data, j) => (
              <div
                key={j}
                style={{ padding: "16px" }}
                onClick={() => navigate(`/post/${data.articleIndex}`)}
              >
                <div className="each-post-information">
                  <Avatar style={{ width: "100px", height: "100px" }}>
                    <img src={data.articlePictureSource} alt="" />
                  </Avatar>
                  <div className="each-post-data-container">
                    <div>
                      <div>{data.articleTitle}</div>
                      {data.hashtagList.map((hashtag, index) => (
                        <span key={index}>#{hashtag} </span>
                      ))}
                    </div>
                    <div className="good-bad-view-icons">
                      <img
                        className="post-information-icon"
                        src={viewIcon}
                        alt=""
                      />
                      <span>{data.articleHit}</span>
                      <img
                        className="post-information-icon"
                        src={goodIcon}
                        alt=""
                      />
                      <span className="goodCount">{data.goodCount}</span>
                      <img
                        className="post-information-icon"
                        src={badIcon}
                        alt=""
                      />
                      <span className="badCount">{data.badCount}</span>
                    </div>
                  </div>
                </div>
                <br />
                <hr />
              </div>
            ))}
          {showRecent && recentViewPage > 0 && (
            <Button onClick={() => setEvaluatePage((page) => page - 1)}>
              이전 페이지
            </Button>
          )}
          {showRecent && recentViewPage < totalRecentViewPage && (
            <Button onClick={() => setEvaluatePage((page) => page + 1)}>
              다음 페이지
            </Button>
          )}

          {/* 최근에 평가한 게시글 세부 */}

          {evaluateData.length === 0 && showEvaluate && (
            <div className="empty-article">최근에 본 글이 없습니다.</div>
          )}
          {showEvaluate &&
            evaluateData.length !== 0 &&
            evaluateData.map((data, j) => (
              <div
                key={j}
                style={{ padding: "16px" }}
                onClick={() => navigate(`/post/${data.articleIndex}`)}
              >
                <div className="each-post-information">
                  <Avatar style={{ width: "100px", height: "100px" }}>
                    <img src={data.articlePictureSource} alt="" />
                  </Avatar>
                  <div className="each-post-data-container">
                    <div>
                      <div>{data.articleTitle}</div>
                      {data.hashtagList.map((hashtag, index) => (
                        <span key={index}>#{hashtag} </span>
                      ))}
                    </div>
                    <div className="good-bad-view-icons">
                      <img
                        className="post-information-icon"
                        src={viewIcon}
                        alt=""
                      />
                      <span>{data.articleHit}</span>
                      <img
                        className="post-information-icon"
                        src={goodIcon}
                        alt=""
                      />
                      <span className="goodCount">{data.goodCount}</span>
                      <img
                        className="post-information-icon"
                        src={badIcon}
                        alt=""
                      />
                      <span className="badCount">{data.badCount}</span>
                    </div>
                  </div>
                </div>
                <br />
                <hr />
              </div>
            ))}
          {showEvaluate && evaluatePage > 0 && (
            <Button onClick={() => setEvaluatePage((page) => page - 1)}>
              이전 페이지
            </Button>
          )}
          {showEvaluate && evaluatePage < totalEvaluatePage && (
            <Button onClick={() => setEvaluatePage((page) => page + 1)}>
              다음 페이지
            </Button>
          )}

          {/* 세부 페이지 뒤로 가기 */}
          {showDetail && (
            <div className="right-button-container">
              <Button onClick={detailCloseHandler} className="right-button">
                뒤로 가기
              </Button>
            </div>
          )}
        </Container>
      </>
    );
  } else {
    // 다른 사람 페이지
    return (
      <>
        <Container maxWidth="sm">
          <h2>{targetNickname}</h2>
          <Grid container spacing={2} justifyContent="space-between">
            <Grid item xs={4}>
              <Avatar
                style={{
                  width: "100px",
                  height: "100px",
                  top: "-20px",
                  left: "10px",
                }}
              >
                <img
                  src={targetProfileSource}
                  alt="profile"
                  style={{ width: "100%", height: "100%", objectFit: "cover" }}
                />
              </Avatar>
            </Grid>
            <Grid item xs={6}>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-around",
                  marginBottom: 3,
                }}
              >
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <div style={{ fontSize: 16 }}>작성글</div>
                  <div style={{ fontSize: 16 }}>{articleCount}</div>
                </Box>
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <div style={{ fontSize: 16 }}>팔로워</div>
                  <div style={{ fontSize: 16 }}>{followerCount}</div>
                </Box>
              </Box>
              <Box sx={{ float: "right", marginRight: 2 }}>
                {parseInt(targetIndex) !== myIndex && !isFollow && (
                  <Button variant="outlined" onClick={followHandler}>
                    팔로우
                  </Button>
                )}
                {parseInt(targetIndex) !== myIndex && isFollow && (
                  <Button variant="contained" onClick={unfollowHandler}>
                    팔로우 해제
                  </Button>
                )}
              </Box>
            </Grid>
          </Grid>

          <hr />
          <div>
            {pageData
              .reduce(
                (acc, item) => {
                  const index = acc.length - 1;
                  if (acc[index].length < 3) {
                    acc[index].push(item);
                  } else {
                    acc.push([item]);
                  }
                  return acc;
                },
                [[]]
              )
              .map((row, i) => (
                <div key={i} style={{ display: "flex" }}>
                  {row.map((data, j) => (
                    <div
                      key={j}
                      style={{ padding: "16px" }}
                      onClick={() => navigate(`/post/${data.articleIndex}`)}
                    >
                      <Avatar style={{ width: "95px", height: "95px" }}>
                        <img
                          src={data.articlePictureSource}
                          alt=""
                          style={{
                            width: "100%",
                            height: "100%",
                            objectFit: "cover",
                          }}
                        />
                      </Avatar>
                    </div>
                  ))}
                </div>
              ))}
          </div>
          {pageData.length === 0 && <div>작성한 글이 없습니다.</div>}
          {pageData.length !== 0 && (
            <div>
              {page + 1} 페이지 / {totalPage + 1} 페이지
            </div>
          )}

          {page > 0 && (
            <Button onClick={() => setPage((page) => page - 1)}>
              이전 페이지
            </Button>
          )}
          {page < totalPage && (
            <Button onClick={() => setPage((page) => page + 1)}>
              다음 페이지
            </Button>
          )}
        </Container>
      </>
    );
  }
};

export default UserPage;
