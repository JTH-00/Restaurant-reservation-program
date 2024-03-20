import axios from "axios";
import qs from "qs";

export const myPageHook = () => {
  const token = localStorage.getItem("token");
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
    } catch (err) {
      console.log("password", password);
      console.error(err);
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

  return { confirmUserHook, changePwHook };
};
