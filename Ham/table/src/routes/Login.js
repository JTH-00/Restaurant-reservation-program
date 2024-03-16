import { useState } from "react";
import axios from "axios";
import styles from "./login.module.scss";
import { useNavigate } from "react-router-dom";
import { useUser } from "../context/AuthContext";

const Login = () => {
  const { user, updateUser } = useUser();
  console.log(user);

  const [input, setInput] = useState({
    email: "",
    password: "",
  });

  const [isUserLogin, setIsUserLogin] = useState(false);
  const [userData, setUserData] = useState({});

  const { email, password } = input;

  const navigate = useNavigate();

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };

  const userLogin = async () => {
    try {
      const response = await axios.post("/api/user/login", {
        email,
        password,
      });
      setIsUserLogin(true);
    } catch (err) {
      console.error(err);
    }
  };

  const temporaryLogin = (e) => {
    //임시로그인
    const loggedIn = {
      id: 1,
      username: "hamsuengwan",
    };
    updateUser(loggedIn);
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
      <button className={styles.loginBtn} onClick={(e) => temporaryLogin(e)}>
        로그인
      </button>
      <button className={styles.signUpBtn} onClick={() => navigate("/signup")}>
        회원가입
      </button>
    </div>
  );
};

export default Login;
