import axios from "axios";
import qs from "qs";

export const myPageHook = () => {
  const token = localStorage.getItem("token");

  const favoriteRestHook = async () => {
    try {
      const response = await axios.get("/api/user/mypage/like", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.data.length < 0) {
        return response.data;
      } else {
        return null;
      }
    } catch (err) {
      console.error(err);
      throw err;
    }
  };

  const reviewListHook = async () => {
    try {
      const response = await axios.get(`/api/user/mypage/review`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.data.length <= 1) {
        return response.data;
      } else {
        return null;
      }
    } catch (err) {
      console.log("fail");
      console.error(err);
    }
  };

  const confirmUserHook = async (password) => {
    try {
      const response = await axios.post(
        `/api/user/mypage/info`,
        {
          password: password,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      return response; // response 반환
    } catch (err) {
      alert(err.response.data);
      throw err; // 에러 재throw
    }
  };

  const changePwHook = async (
    currentPassword,
    newPassword,
    newPasswordConfirm
  ) => {
    axios.defaults.paramsSerializer = (params) => {
      return qs.stringify(params);
    };
    const params = {
      currentPassword: currentPassword,
      newPassword: newPassword,
      newPasswordConfirm: newPasswordConfirm,
    };
    try {
      const response = await axios.post(
        `/api/user/mypage/info/modify/password`,
        { params },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      // alert(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  return { confirmUserHook, changePwHook, favoriteRestHook, reviewListHook };
};
