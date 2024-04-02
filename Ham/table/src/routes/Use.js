import React, { useState, useEffect } from "react";
import styles from "./use.module.scss";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import useDateInfo from "../hooks/dayHook";
import imgCal from "../assets/imgCal.png";
import gogiRestaurant from "../assets/gogiRestaurant.png";
import Modal from "../components/modal/Modal";
import axios from "axios";

const modalData = [
  "신고",
  "고기•구이",
  "삼겹살집",
  "700px",
  "700px",
  gogiRestaurant,
];
const reviewModalData = [
  "리뷰",
  "고기•구이",
  "삼겹살집",
  "700px",
  "700px",
  gogiRestaurant,
];

const Use = () => {
  const [startOpen, setStartOpen] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [reviewModalOpen, setReviewModalOpen] = useState(false);

  const [startValue, setStartValue] = useState(new Date());
  const [endValue, setEndValue] = useState(new Date());

  const { startDateInfo, setStartDate, endDateInfo, setEndDate } =
    useDateInfo();

  useEffect(() => {
    setStartDate(startValue);
    setEndDate(endValue);
  }, [startValue, endValue]);
  useEffect(() => {
    getUsedData();
  }, []);

  const getUsedData = async () => {
    const token = localStorage.getItem("token");
    try {
      const response = await axios.get("/api/user/mypage/use", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(response.data, 123);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className={styles.form}>
      {modalOpen && <Modal setIsOpen={setModalOpen} modalData={modalData} />}
      {reviewModalOpen && (
        <Modal setIsOpen={setReviewModalOpen} modalData={reviewModalData} />
      )}
      <div className={styles.used_header}>
        <h1>이용내역</h1>
      </div>
      <div className={styles.use_header}>
        <select>
          <option>전체</option>
          <option>예약</option>
          <option>포장</option>
        </select>
        <div className={styles.day_wrapper}>
          <div className={styles.detailDay_wrapper}>
            <button onClick={() => setStartOpen(true)}>
              {startDateInfo.year} - {startDateInfo.month} - {startDateInfo.day}
            </button>
            <img src={imgCal}></img>
          </div>
          ~
          <div className={styles.detailDay_wrapper}>
            <button onClick={() => setStartOpen(true)}>
              {endDateInfo.year} - {endDateInfo.month} - {endDateInfo.day}
            </button>
            <img src={imgCal}></img>
          </div>
        </div>
      </div>
      <div style={{ display: "flex" }}>
        {startOpen && (
          <div className={styles.calendar_wrapper}>
            <p>시작일</p>
            <Calendar
              className={styles.react_calendar}
              onChange={setStartValue}
              value={startValue}
            />
            <button
              className={styles.calendar_button}
              onClick={() => setStartOpen(false)}
            >
              닫기
            </button>
          </div>
        )}
        {startOpen && (
          <div
            style={{ marginLeft: "10px" }}
            className={styles.calendar_wrapper}
          >
            <p>종료일</p>
            <Calendar
              className={styles.react_calendar}
              onChange={setEndValue}
              value={endValue}
            />
          </div>
        )}
      </div>
      <div className={styles.cardList_wrapper}>
        <div className={styles.useCard}>
          <div className={styles.card_header}>
            <span>구매확정완료</span>
            <a onClick={() => setReviewModalOpen(true)}>리뷰작성</a>
          </div>
          <div className={styles.body_wrapper}>
            <div className={styles.card_img}>
              <img src={gogiRestaurant}></img>
            </div>
            <div className={styles.card_info}>
              <p>2024.02.18결제</p>
              <p>고깃집</p>
              <p style={{ fontSize: "15px", fontWeight: "bold" }}>32,000원</p>
              <a>매장정보{">"} </a>
            </div>
            <button onClick={() => setModalOpen(true)}>신고하기</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Use;
