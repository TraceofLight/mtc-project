import axios from "axios";

import { currentUser } from "firebaseConfig";

const requestPost = async (url, data = null) => {
  while (!currentUser || !currentUser.accessToken) {
    await new Promise((resolve) => setTimeout(resolve, 1000));
  }

  const requestInput = JSON.stringify(data);

  try {
    const responseData = await axios({
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${currentUser.accessToken}`,
      },
      method: "post",
      url: url,
      data: requestInput,
    });
    return responseData.data;

    // 에러 반환
  } catch (error) {
    return error.message;
  }
};

const requestGet = async (url) => {
  while (!currentUser || !currentUser.accessToken) {
    await new Promise((resolve) => setTimeout(resolve, 1000));
  }

  try {
    const responseData = await axios({
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${currentUser.accessToken}`,
      },
      method: "get",
      url: url,
    });
    return responseData.data;
  } catch (error) {
    return error.message;
  }
};

const requestUserNickname = async (url) => {
  try {
    const responseData = await axios({
      headers: {
        "Content-type": "application/json",
      },
      method: "get",
      url: url,
    });
    return responseData.data;

    // 에러 반환
  } catch (error) {
    return error.message;
  }
};

const requestPutFormData = async (url, formData) => {
  while (!currentUser || !currentUser.accessToken) {
    await new Promise((resolve) => setTimeout(resolve, 1000));
  }

  try {
    const responseData = await axios({
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${currentUser.accessToken}`,
      },
      method: "put",
      url: url,
      data: formData,
    });
    return responseData.data;

    // 에러 반환
  } catch (error) {
    return error.message;
  }
};

const requestFormData = async (url, formdata) => {
  try {
    const responseData = await axios({
      headers: { "Content-Type": "multipart/form-data" },
      method: "post",
      url: url,
      data: formdata,
    });
    return responseData.data;

    // 에러 반환
  } catch (error) {
    return error.message;
  }
};

const requestPut = async (url, data) => {
  while (!currentUser || !currentUser.accessToken) {
    await new Promise((resolve) => setTimeout(resolve, 1000));
  }

  try {
    const responseData = await axios({
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${currentUser.accessToken}`,
      },
      method: "put",
      url: url,
      data: data,
    });
    return responseData.data;

    // 에러 반환
  } catch (error) {
    return error.message;
  }
};

const requestDelete = async (url, data = null) => {
  while (!currentUser || !currentUser.accessToken) {
    await new Promise((resolve) => setTimeout(resolve, 1000));
  }

  try {
    const responseData = await axios({
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${currentUser.accessToken}`,
      },
      method: "delete",
      url: url,
      data: data,
    });
    return responseData.data;

    // 에러 반환
  } catch (error) {
    return error.message;
  }
};

export {
  requestPost,
  requestGet,
  requestPut,
  requestPutFormData,
  requestFormData,
  requestUserNickname,
  requestDelete,
};
