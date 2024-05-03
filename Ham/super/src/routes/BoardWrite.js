import { useState, useEffect } from "react";
import styles from "./boardWrite.module.scss";
const BoardWrite = () => {
  return (
    <div className={styles.form}>
      <div className={styles.board_title}>
        <div className={styles.category_wrapper}>
          <h3>카테고리선택:</h3>
          <select>
            <option>공지사항</option>
            <option>이벤트</option>
          </select>
        </div>
        <div className={styles.writing_title}>
          <input placeholder="제목을 입력하세요."></input>
        </div>
      </div>
      <div className={styles.writing_detail}>
        <textarea></textarea>
        <div className={styles.writing_button}>
          <input type="file"></input>
          <button>취소</button>
          <button>확인</button>
        </div>
      </div>
    </div>
  );
};

export default BoardWrite;
