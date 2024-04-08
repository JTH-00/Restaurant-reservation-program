import axios from "axios";

export const useReserve = () => {
  const token = localStorage.getItem("token");

  const userReservedHook = async () => {
    try {
      const response = await axios.get(`/api/user/mypage/reserve`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  };

  const getRestaurant = async () => {
    try {
      const response = await axios.get("/api/user/restaurant/1");
      return response.data.restaurantDto;
    } catch (err) {
      return err.response.data;
    }
  };

  return {
    userReservedHook,
    getRestaurant,
  };
};
