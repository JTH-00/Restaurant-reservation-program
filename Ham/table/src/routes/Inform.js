import React, { useState } from "react";
import styles from "./inform.module.scss";
import { Link } from "react-router-dom";

const Inform = () => {
  const [isEventSelected, setIsEventSelected] = useState(false);
  const [isNoticeSelected, setIsNoticeSelected] = useState(true);

  const handleEventClick = () => {
    setIsEventSelected(true);
    setIsNoticeSelected(false);
  };

  const handleNoticeClick = () => {
    setIsEventSelected(false);
    setIsNoticeSelected(true);
  };
  let boardId = 1;
  let eventId = 1;
  return (
    <div className={styles.form}>
      <div className={styles.inform_wrapper}>
        <aside className={styles.aside_wrapper}>
          <h1>커뮤니티</h1>

          <button
            className={isEventSelected ? styles.selected : ""}
            onClick={handleEventClick}
          >
            <p>이벤트</p>
          </button>
          <button
            className={isNoticeSelected ? styles.selected : ""}
            onClick={handleNoticeClick}
          >
            <p>공지사항</p>
          </button>
        </aside>
        <div className={styles.rightSide_wrapper}>
          <div className={styles.rightSide_header}>
            {isEventSelected && <h2>이벤트</h2>}
            {isNoticeSelected && <h2>공지사항</h2>}
          </div>
          <div className={styles.rightSide_body}>
            {isNoticeSelected && (
              <div className={styles.board_card}>
                <Link
                  to={`/board/inform/${boardId}`}
                  className={styles.title_text}
                >
                  [카테고리] 공지사항
                </Link>
                <span>2023.04.10</span>
              </div>
            )}
            {isEventSelected && (
              <div className={styles.board_card}>
                <Link
                  to={`/board/event/${eventId}`}
                  className={styles.title_text}
                >
                  [카테고리] 이벤트
                </Link>
                <span>2023.04.10</span>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Inform;
