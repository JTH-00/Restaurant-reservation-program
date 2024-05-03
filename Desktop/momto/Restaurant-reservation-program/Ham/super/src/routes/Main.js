import React, { useState, useEffect } from "react";
import styles from "./main.module.scss";
import { Link, useNavigate } from "react-router-dom";
import { useInform } from "../hooks/informHook";

const Main = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [isEventSelected, setIsEventSelected] = useState(false);
  const [isNoticeSelected, setIsNoticeSelected] = useState(true);
  const [informList, setInformList] = useState(null);

  const navigate = useNavigate();

  const { informListHook, eventListHook } = useInform();

  useEffect(() => {
    setIsLoading(true);
    if (isNoticeSelected) {
      inform();
    } else {
      event();
    }
    setIsLoading(false);
  }, [isEventSelected, isNoticeSelected]);

  const inform = async () => {
    try {
      const response = await informListHook();
      setInformList(response);
    } catch (error) {
      console.error(error);
    }
  };
  const event = async () => {
    try {
      const response = await eventListHook();
      setInformList(response);
    } catch (error) {
      console.error(error);
    }
  };

  const handleEventClick = () => {
    setIsEventSelected(true);
    setIsNoticeSelected(false);
  };

  const handleNoticeClick = () => {
    setIsEventSelected(false);
    setIsNoticeSelected(true);
  };

  if (isLoading) {
    return <div>로딩중입니다..</div>;
  }

  return (
    <div className={styles.form}>
      {!isLoading && (
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
              {isEventSelected && (
                <div className={styles.right_title}>
                  <h2>이벤트</h2>
                  <button onClick={() => navigate(`/board/write`)}>
                    글쓰기
                  </button>
                </div>
              )}
              {isNoticeSelected && (
                <div className={styles.right_title}>
                  <h2>공지사항</h2>
                  <button onClick={() => navigate(`/board/write`)}>
                    글쓰기
                  </button>
                </div>
              )}
            </div>
            <div className={styles.rightSide_body}>
              {isNoticeSelected && (
                <div>
                  {informList?.map((e) => {
                    return (
                      <div className={styles.board_card}>
                        <Link
                          to={`/board/inform/${e.noticeid}`}
                          className={styles.title_text}
                        >
                          {e.title}
                        </Link>
                        <span>{e.date}</span>
                      </div>
                    );
                  })}
                </div>
              )}
              {isEventSelected && (
                <div>
                  {informList?.map((e) => {
                    return (
                      <div className={styles.board_card}>
                        <Link
                          to={`/board/event/${e.eventid}`}
                          className={styles.title_text}
                        >
                          {e.title}
                        </Link>
                        <span>{e.date}</span>
                      </div>
                    );
                  })}
                </div>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Main;
