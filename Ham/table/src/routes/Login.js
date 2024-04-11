import { useState } from "react";
import axios from "axios";
import styles from "./login.module.scss";
import { useNavigate } from "react-router-dom";
import { useUser } from "../context/AuthContext";

const Login = () => {
  const { user, loginUser } = useUser();
  // console.log(user);

  const [input, setInput] = useState({
    email: "",
    password: "",
  });

  const { email, password } = input;

  const navigate = useNavigate();

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };

  const userLogin = (e) => {
    loginUser(email, password);
    navigate("/");
  };

  return (
    <div className={styles.form}>
      <h1>로그인</h1>
      <input
        type="text"
        name="email"
        value={email}
        onChange={checkInput}
        placeholder="아이디를 입력하세요"
      ></input>
      <input
        type="text"
        name="password"
        value={password}
        onChange={checkInput}
        placeholder="비밀번호를 입력하세요"
      ></input>
      <button className={styles.loginBtn} onClick={(e) => userLogin(e)}>
        로그인
      </button>
      <button className={styles.signUpBtn} onClick={() => navigate("/signup")}>
        회원가입
      </button>
    </div>
  );
};

export default Login;
