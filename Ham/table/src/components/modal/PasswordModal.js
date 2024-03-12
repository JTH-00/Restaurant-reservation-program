import styles from "./passwordModal.module.scss";

const PasswordModal = ({ setModalOpen }) => {
  return (
    <div className={styles.modal_wrap}>
      <div className={styles.modal_detail}>
        <header>
          <h2>비밀번호 변경 </h2>
        </header>
        <body>
          <div className={styles.input_wrapper}>
            <p>현재 비밀번호</p>
            <input placeholder="현재 비밀번호를 입력해 주세요"></input>
          </div>
          <div className={styles.input_wrapper}>
            <p>새 비밀번호</p>
            <input placeholder="바꿀 비밀번호를 입력해 주세요"></input>
          </div>
          <div className={styles.input_wrapper}>
            <p>새 비밀번호 확인</p>
            <input placeholder="비밀번호를 다시 입력해 주세요"></input>
          </div>
        </body>
        <button
          className={styles.del_button}
          onClick={(e) => setModalOpen(false)}
        >
          취소
        </button>
        <button className={styles.update_button}>비밀번호변경</button>
      </div>
    </div>
  );
};

export default PasswordModal;
