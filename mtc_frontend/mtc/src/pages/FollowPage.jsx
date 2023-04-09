import Container from "@mui/system/Container";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Avatar from "@mui/material/Avatar";

import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";

import { requestDelete, requestGet } from "api/axios";
import urls from "api/url";
import Box from "@mui/system/Box";
import { Grid, Stack } from "@mui/material";

const FollowPage = () => {
  const navigate = useNavigate();

  const userStatus = useSelector((state) => state.userStatus);
  const myIndex = userStatus.userIndex;

  const [followData, setFollowData] = useState([]);
  const [filteredFollowData, setFilteredFollowData] = useState([]);
  const [searchText, setSearchText] = useState("");

  useEffect(() => {
    const getFollowData = async () => {
      const responseData = await requestGet(
        `${urls.relation.getFollowList}${myIndex}`
      );
      if (responseData) {
        setFollowData([...responseData]);
      }
    };
    getFollowData();
  }, [myIndex]);

  const unfollowButtonClickHandler = (myIndex) => () => {
    unfollowHandler(myIndex);
  };

  const unfollowHandler = async (params) => {
    const responseData = await requestDelete(urls.relation.unfollowUser, {
      followUserIndex: myIndex,
      followTargetIndex: params,
    });
    if (!responseData.startsWith("Request failed")) {
      setFollowData((prevData) =>
        prevData.filter((data) => data.userIndex !== params)
      );
      setFilteredFollowData((prevData) =>
        prevData.filter((data) => data.userIndex !== params)
      );
    }
  };

  const handleSearch = (e) => {
    const searchValue = e.target.value;
    setSearchText(searchValue);
    if (searchValue !== "") {
      setFilteredFollowData(
        followData.filter(
          (data) =>
            data.userNickname
              .toLowerCase()
              .indexOf(searchValue.toLowerCase()) !== -1
        )
      );
    } else {
      setFilteredFollowData(followData);
    }
  };

  return (
    <>
      <Container maxWidth="sm">
        <br />

        <Box sx={{ display: "flex" }}>
          <Button onClick={() => navigate("/settings")}>
            <img src={require("../assets/icons/Logo_Back.png")} width={"20"} />
          </Button>
          <h2>팔로우</h2>
        </Box>
        <TextField
          label="Search Users"
          value={searchText}
          onChange={handleSearch}
        />
        {(searchText === "" ? followData : filteredFollowData).length === 0 && (
          <div> 팔로우한 유저가 없습니다. </div>
        )}
        {(searchText === "" ? followData : filteredFollowData).map(
          (data, i) => (
            <div key={i} style={{ marginTop: "30px" }}>
              <Box
                sx={{
                  display: "flex",
                  alignContent: "center",
                  justifyContent: "space-around",
                }}
                onClick={() => navigate(`/user/${data.userIndex}`)}
              >
                <Stack
                  direction="row"
                  spacing={2}
                  sx={{
                    display: "flex",
                    width: 200,
                    alignContent: "center",
                  }}
                >
                  <Avatar>
                    <img
                      src={data.userPictureSource}
                      alt=""
                      style={{
                        width: "100%",
                        height: "100%",
                        objectFit: "cover",
                      }}
                    />
                  </Avatar>
                  {/* <p style={{ margin: "auto 20px" }}>{data.userNickname}</p> */}
                  <p
                    style={{
                      margin: "auto 20px",
                    }}
                  >
                    {data.userNickname}
                  </p>
                </Stack>
                <Button
                  sx={{ height: "30px", borderRadius: "20%" }}
                  variant="contained"
                  onClick={unfollowButtonClickHandler(data.userIndex)}
                >
                  삭제
                </Button>
              </Box>
            </div>
          )
        )}
      </Container>
    </>
  );
};

export default FollowPage;
