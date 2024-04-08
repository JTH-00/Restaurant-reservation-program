import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./boardDetail.module.scss";
import { useInform } from "../hooks/informHook";

const BoardDetail = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [boardState, setBoardState] = useState("");
  const [boardDetail, setBoardDetail] = useState(null);

  const { noticeDetailHook, eventDetailHook } = useInform();
  const { eventId } = useParams();
  const { boardId } = useParams();

  const navigate = useNavigate();
  useEffect(() => {
    setIsLoading(true);
    if (boardId) {
      noticeDetail();
      setBoardState("공지사항");
    } else {
      setBoardState("이벤트");
      eventDetail();
    }
    setIsLoading(false);
  }, []);

  const noticeDetail = async () => {
    try {
      const data = await noticeDetailHook(boardId);

      setBoardDetail(data);
    } catch (err) {
      console.error(err);
    }
  };

  const eventDetail = async () => {
    try {
      const data = await eventDetailHook(eventId);

      setBoardDetail(data);
      console.log(data);
    } catch (err) {
      console.error(err);
    }
  };

  if (isLoading) {
    return <div>로딩중입니다..</div>;
  }

  return (
    <div className={styles.form}>
      {!isLoading && (
        <div className={styles.board_wrapper}>
          <header className={styles.board_header}>
            <h1>{boardState}</h1>
          </header>
          <body className={styles.board_body}>
            <div className={styles.board_title}>
              <h3>
                [{boardState}] {boardDetail?.title}
              </h3>
              <p>{boardDetail?.date}</p>
            </div>
            <div className={styles.board_detail}>
              <p>{boardDetail?.content}</p>
              <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUZZGRRLynAljQN5ITDlQuaxibxZ8M2aJntaLkzIUuQA&s"></img>
            </div>
          </body>
          <footer className={styles.board_footer}>
            <button onClick={() => navigate("/inform")}>목록</button>
          </footer>
        </div>
      )}
    </div>
  );
};

export default BoardDetail;
