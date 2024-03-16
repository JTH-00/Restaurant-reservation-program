import { useState } from "react";
import axios from "axios";
import styles from "./signUp.module.scss";

const SignUp = () => {
  const [input, setInput] = useState({
    email: "",
    password: "",
    confirmPw: "",
    username: "",
    callNumber: "",
  });

  const [userLogin, setUserLogin] = useState(false);

  const { email, password, confirmPw, username, callNumber } = input;

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };

  const userSignUp = async () => {
    try {
      const response = await axios.post("/api/user/signup", {
        email,
        password,
        username,
        callNumber,
      });
      setUserLogin(true);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className={styles.form}>
      <div className={styles.signUp_wrapper}>
        <div className={styles.SignUp_header}>
          <h1>
            회원가입 <a>사업자 회원가입</a>
          </h1>
        </div>
        <div className={styles.line}></div>
        <div className={styles.input_wrapper}>
          <p>이메일</p>
          <input
            name="email"
            value={email}
            onChange={checkInput}
            placeholder="이메일을 입력하세요"
          ></input>
          <button>중복확인</button>
        </div>
        <div className={styles.input_wrapper}>
          <p>비밀번호</p>
          <input
            name="password"
            value={password}
            onChange={checkInput}
            placeholder="비밀번호를 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>비밀번호확인</p>
          <input
            name="confirmPw"
            value={confirmPw}
            onChange={checkInput}
            placeholder="비밀번호를 다시 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>이름</p>
          <input
            name="username"
            value={username}
            onChange={checkInput}
            placeholder="이름을 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>전화번호</p>
          <input
            name="callNumber"
            value={callNumber}
            onChange={checkInput}
            placeholder="전화번호를 입력하세요"
          ></input>
        </div>
        <button className={styles.signUp_button} onClick={() => userSignUp}>
          가입하기
        </button>
      </div>
    </div>
  );
};

export default SignUp;
