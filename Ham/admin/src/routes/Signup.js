import { useState } from "react";
import styles from "./signup.module.scss";
import { useUser } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const SignUp = () => {
  const { user, signUpUser } = useUser();
  console.log(user);
  const [uploadImgUrl, setUploadImgUrl] = useState("");
  const [input, setInput] = useState({
    ceoNumber: "",
    loginId: "",
    password: "",
    confirmPw: "",
    username: "",
    callNumber: "",
  });

  const { ceoNumber, loginId, password, confirmPw, username, callNumber } =
    input;
  const navigate = useNavigate();

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };

  const onClickSignUp = () => {
    if (password == confirmPw) {
      signUpUser(ceoNumber, password, username, callNumber, uploadImgUrl);
      // navigate("/");
    } else {
      alert("비밀번호가 일치하지 않습니다.");
      setInput("");
    }
  };
  const imgOnchange = (e) => {
    const { files } = e.target;
    const uploadFile = files[0];
    const reader = new FileReader();
    reader.readAsDataURL(uploadFile);
    reader.onloadend = () => {
      setUploadImgUrl(reader.result);
    };
  };

  return (
    <div className={styles.form}>
      <div className={styles.signUp_wrapper}>
        <div className={styles.SignUp_header}>
          <h1>사업자 회원가입</h1>
        </div>
        <div className={styles.line}></div>
        <div className={styles.input_wrapper}>
          <p>사업자 번호</p>
          <input
            name="ceoNumber"
            value={ceoNumber}
            onChange={checkInput}
            placeholder="사업자번호를 입력하세요"
          ></input>
          <button>중복확인</button>
        </div>
        <div className={styles.input_wrapper}>
          <p>아이디</p>
          <input
            name="loginId"
            value={loginId}
            onChange={checkInput}
            placeholder="아이디를 입력하세요"
          ></input>
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
        <div className={styles.imgInput_wrapper}>
          <p>사업자 등록증 사진</p>
          <input
            name="photo"
            type="file"
            value={uploadImgUrl}
            onChange={imgOnchange}
          ></input>
        </div>
        <button
          className={styles.signUp_button}
          onClick={() => onClickSignUp()}
        >
          가입하기
        </button>
      </div>
    </div>
  );
};

export default SignUp;
