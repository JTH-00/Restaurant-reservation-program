import styles from "./passwordModal.module.scss";
import { myPageHook } from "../../hooks/myPage";
import React, { useState } from "react";

const PasswordModal = ({ setModalOpen }) => {
  const [input, setInput] = useState({
    currentPassword: "",
    newPassword: "",
    newPasswordConfirm: "",
  });

  const { currentPassword, newPassword, newPasswordConfirm } = input;
  const { changePwHook } = myPageHook();

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };

  const changePwHandle = () => {
    changePwHook(currentPassword, newPassword, newPasswordConfirm);
    setModalOpen(false);
  };

  return (
    <div className={styles.modal_wrap}>
      <div className={styles.modal_detail}>
        <header>
          <h2>비밀번호 변경 </h2>
        </header>
        <body>
          <div className={styles.input_wrapper}>
            <p>현재 비밀번호</p>
            <input
              name="currentPassword"
              onChange={checkInput}
              placeholder="현재 비밀번호를 입력해 주세요"
            ></input>
          </div>
          <div className={styles.input_wrapper}>
            <p>새 비밀번호</p>
            <input
              name="newPassword"
              onChange={checkInput}
              placeholder="바꿀 비밀번호를 입력해 주세요"
            ></input>
          </div>
          <div className={styles.input_wrapper}>
            <p>새 비밀번호 확인</p>
            <input
              name="newPasswordConfirm"
              onChange={checkInput}
              placeholder="비밀번호를 다시 입력해 주세요"
            ></input>
          </div>
        </body>
        <button
          className={styles.del_button}
          onClick={(e) => changePwHandle(e)}
        >
          취소
        </button>
        <button className={styles.update_button} onClick={changePwHandle}>
          비밀번호변경
        </button>
      </div>
    </div>
  );
};

export default PasswordModal;
