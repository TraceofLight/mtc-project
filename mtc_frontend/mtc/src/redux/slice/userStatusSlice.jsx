import { authenticationInstance } from "firebaseConfig";
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { requestGet } from "api/axios";

import urls from "api/url";

export const authenticationCheck = createAsyncThunk(
  "auth/authnticationCheck",
  async (_, { dispatch }) => {
    authenticationInstance.onAuthStateChanged(async (user) => {
      dispatch(SET_USER(user));
      const userUid = user?.uid === undefined ? null : user?.uid;
      const userInformation =
        userUid === null
          ? null
          : await requestGet(`${urls.accounts.getUserInformation}${userUid}`);
      dispatch(SET_USER_INDEX(userInformation?.userIndex));
      dispatch(SET_USER_NICKNAME(userInformation?.userNickname));
      dispatch(SET_USER_PROFILE(userInformation?.userProfilePictureSource));
    });
  }
);

const userStatusSlice = createSlice({
  name: "userStatus",
  initialState: {
    userToken: null,
    userIndex: null,
    userNickname: null,
    userProfilePictureSource: null,
  },
  reducers: {
    SET_USER: (state, action) => {
      state.userToken = action.payload;
    },
    SET_USER_INDEX: (state, action) => {
      state.userIndex = action.payload;
    },
    SET_USER_NICKNAME: (state, action) => {
      state.userNickname = action.payload;
    },
    SET_USER_PROFILE: (state, action) => {
      state.userProfilePictureSource = action.payload;
    },
  },
  extraReducers: {
    [authenticationCheck.fulfilled]: (state) => {
      // for keep status
    },
  },
});

export const { SET_USER, SET_USER_INDEX, SET_USER_NICKNAME, SET_USER_PROFILE } =
  userStatusSlice.actions;
export default userStatusSlice.reducer;
