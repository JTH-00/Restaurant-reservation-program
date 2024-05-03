import axios from "axios";

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
      console.log(response.data);
      return response.data;
    } catch (err) {
      return "fail";
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
    const params = new URLSearchParams();
    params.append("currentPassword", currentPassword);
    params.append("newPassword", newPassword);
    params.append("newPasswordConfirm", newPasswordConfirm);

    try {
      const response = await axios.post(
        `/api/user/mypage/info/modify/password`,
        params,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      // alert(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  return { confirmUserHook, changePwHook, favoriteRestHook, reviewListHook };
};
