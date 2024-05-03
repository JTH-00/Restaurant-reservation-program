import axios from "axios";

export const useInform = () => {
  const token = localStorage.getItem("token");

  const informListHook = async () => {
    try {
      const response = await axios.get("api/user/board/notice", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      console.error(err);
    }
  };

  const eventListHook = async () => {
    try {
      const response = await axios.get("api/user/board/event", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      console.error(err);
    }
  };

  const noticeDetailHook = async (id) => {
    console.log(id);
    try {
      const response = await axios.get(`/api/user/board/notice/detail/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      console.error(err);
    }
  };

  const eventDetailHook = async (id) => {
    try {
      const response = await axios.get(`/api/user/board/event/detail/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      console.error(err);
    }
  };

  return { informListHook, eventListHook, noticeDetailHook, eventDetailHook };
};
