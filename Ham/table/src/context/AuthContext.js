import axios from "axios";
import React, { createContext, useState } from "react";

export const baseUrl = "http://localhost:8090";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

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
      setUser(response.data.username);
      localStorage.setItem("token", response.data.token);
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
      setUser(response.data.username);
      console.log(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <AuthContext.Provider
      value={{ user, signUpUser, loginUser, logoutUser, updateUser }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useUser = () => React.useContext(AuthContext);
