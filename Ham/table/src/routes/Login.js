import styles from "./login.module.scss";

const Login = () => {
  return (
    <div className={styles.form}>
      <h1>로그인</h1>
      <input type="text" placeholder="아이디를 입력하세요"></input>
      <input type="text" placeholder="비밀번호를 입력하세요"></input>
      <button className={styles.loginBtn}>로그인</button>
      <button className={styles.signUpBtn}>회원가입</button>
    </div>
  );
};

export default Login;
