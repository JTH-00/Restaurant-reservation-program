import styles from "./signUp.module.scss";

const SignUp = () => {
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
          <input placeholder="이메일을 입력하세요"></input>
          <button>중복확인</button>
        </div>
        <div className={styles.input_wrapper}>
          <p>비밀번호</p>
          <input placeholder="비밀번호를 입력하세요"></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>비밀번호확인</p>
          <input placeholder="비밀번호를 다시 입력하세요"></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>이름</p>
          <input placeholder="이름을 입력하세요"></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>전화번호</p>
          <input placeholder="전화번호를 입력하세요"></input>
        </div>
        <button className={styles.signUp_button}>가입하기</button>
      </div>
    </div>
  );
};

export default SignUp;
