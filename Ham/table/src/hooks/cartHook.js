import axios from "axios";

export const useCartHook = () => {
  const token = localStorage.getItem("token");

  const getCartListHook = async () => {
    try {
      const response = await axios.get("/api/user/cart", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      return response.data;
    } catch (err) {
      return err.response.data;
    }
  };

  const cartAddMenuHook = async (addmenu, menuid) => {
    const params = new URLSearchParams();
    params.append("addmenu", addmenu);
    params.append("menuid", menuid);
    try {
      const response = await axios.post("/api/user/cart", params, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      console.error(err);
    }
  };

  const cartCountHook = async (count, menuid) => {
    const params = new URLSearchParams();
    params.append("cartmenuid", menuid);
    params.append("increase", count);
    try {
      const response = await axios.put(
        "/api/user/cart/updatemenucount",
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },

          params: params,
        }
      );

      return getCartListHook();
    } catch (err) {
      return err;
    }
  };

  const cartMenuDeleteHook = async (cartmenuid) => {
    const params = new URLSearchParams();
    params.append("cartmenuid", cartmenuid);
    try {
      const response = await axios.delete("/api/user/cart/removemenu", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: params,
      });
      getCartListHook();
      return response.data;
    } catch (err) {
      return err.response.data;
    }
  };

  const takeoutHook = async (cartid, userid) => {
    const params = new URLSearchParams();
    params.append("cartid", cartid);
    console.log(cartid, userid);
    try {
      const response = await axios.post(
        `api/user/restaurant/takeout/${userid}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
        { params: params }
      );
      return response.data;
    } catch (err) {
      return err;
    }
  };

  return {
    getCartListHook,
    cartAddMenuHook,
    cartCountHook,
    cartMenuDeleteHook,
    takeoutHook,
  };
};
