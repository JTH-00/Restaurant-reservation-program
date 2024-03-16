import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./boardDetail.module.scss";

const BoardDetail = () => {
  const [boardState, setBoardState] = useState("");

  const { eventId } = useParams();
  const { boardId } = useParams();

  const navigate = useNavigate();
  useEffect(() => {
    if (boardId) {
      setBoardState("공지사항");
    } else {
      setBoardState("이벤트");
    }
  }, []);

  return (
    <div className={styles.form}>
      <div className={styles.board_wrapper}>
        <header className={styles.board_header}>
          <h1>{boardState}</h1>
        </header>
        <body className={styles.board_body}>
          <div className={styles.board_title}>
            <h3>[{boardState}] 제목</h3>
            <p>2023-03-14</p>
          </div>
          <div className={styles.board_detail}>
            <p>
              따뜻한 물 많이 마시시고 따뜻하게 입으시고 배부르게 드시고
              건강하세요 -함승완올림-
            </p>
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUZZGRRLynAljQN5ITDlQuaxibxZ8M2aJntaLkzIUuQA&s"></img>
          </div>
        </body>
        <footer className={styles.board_footer}>
          <button onClick={() => navigate("/inform")}>목록</button>
        </footer>
      </div>
    </div>
  );
};

export default BoardDetail;
