import axios from "axios";
import React, { createContext, useState } from "react";

export const baseUrl = "http://localhost:8090";

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [price, setPrice] = useState(0);
  const [cartData, setCartData] = useState([]);
  const [cartId, setCartId] = useState("");

  const token = localStorage.getItem("token");

  const getCartListHook = async () => {
    try {
      const response = await axios.get("/api/user/cart", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setCartData(response.data.cartMenus);
      setCartId(response?.data.cartMenus[0]?.cart.cartid);
      setPrice(response.data.totalAmount);
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

  return (
    <CartContext.Provider
      value={{
        price,
        cartData,
        cartId,
        getCartListHook,
        cartAddMenuHook,
        cartCountHook,
        cartMenuDeleteHook,
        takeoutHook,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => React.useContext(CartContext);
