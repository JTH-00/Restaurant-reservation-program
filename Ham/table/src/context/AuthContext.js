import axios from "axios";
import React, { createContext, useState } from "react";

export const baseUrl = "http://localhost:8090";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [userId, setUserId] = useState(null);

  const token = localStorage.getItem("token");

  const signUpUser = async (email, password, username, callNumber) => {
    try {
      const response = await axios.post(`/api/user/signup`, {
        useremail: email,
        password: password,
        username: username,
        phone: callNumber,
      });
      loginUser(email, password);
      console.log(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const loginUser = async (email, password) => {
    try {
      const response = await axios.post(`/api/user/login`, {
        useremail: email,
        password: password,
      });
      if (response.status === 200) {
        setUser(response.data.username);
        localStorage.setItem("token", response.data.token);
        getUserId();
      }

      console.log(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const logoutUser = async () => {
    try {
      const response = await axios.post(
        `/api/user/logout`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      setUser(null);
      localStorage.clear();
    } catch (err) {
      console.error(err);
    }
  };

  const updateUser = async (username, phone) => {
    console.log(1);
    try {
      const response = await axios.post(
        `/api/user/mypage/info/modify`,
        {
          username: username,
          phone: phone,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status === 200) {
        setUser(response.data.username);
      }
      console.log(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getUserId = async () => {
    try {
      const response = await axios.get("/api/user/user", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUserId(response.data.userid);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <AuthContext.Provider
      value={{ user, userId, signUpUser, loginUser, logoutUser, updateUser }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useUser = () => React.useContext(AuthContext);
