import React, { useState, useEffect } from "react";
import styles from "./main.module.scss";
import useDateInfo from "../hooks/dayHook";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import ReserveTable from "../component/table/ReserveTable";

const Main = () => {
  const [startOpen, setStartOpen] = useState(false);
  const [startValue, setStartValue] = useState(new Date());
  const [endValue, setEndValue] = useState(new Date());
  const [isActive, setIsActive] = useState("오늘");

  const { startDateInfo, setStartDate, endDateInfo, setEndDate } =
    useDateInfo();

  useEffect(() => {
    setStartDate(startValue);
    setEndDate(endValue);
  }, [startValue, endValue]);

  return (
    <div className={styles.form}>
      <div className={styles.reserve_header}>
        <h1>예약관리</h1>
      </div>
      <div className={styles.box_wrapper}>
        <div>
          <button
            className={
              isActive == "오늘" ? styles.active_btn : styles.default_btn
            }
            onClick={() => setIsActive("오늘")}
          >
            오늘
          </button>
          <button
            className={
              isActive == "내일" ? styles.active_btn : styles.default_btn
            }
            onClick={() => setIsActive("내일")}
          >
            내일
          </button>
          <button
            className={
              isActive == "3개월" ? styles.active_btn : styles.default_btn
            }
            onClick={() => setIsActive("3개월")}
          >
            3개월
          </button>
        </div>

        <div className={styles.chooseDate_wrapper}>
          <button onClick={() => setStartOpen(true)}>
            {startDateInfo.year} - {startDateInfo.month} - {startDateInfo.day}
          </button>
          ~
          <button onClick={() => setStartOpen(true)}>
            {endDateInfo.year} - {endDateInfo.month} - {endDateInfo.day}
          </button>
          <button className={styles.check_btn}>조회</button>
        </div>
      </div>
      {startOpen && (
        <div>
          <div className={styles.calendar_wrapper}>
            <Calendar onChange={setStartValue} value={startValue} />
            <Calendar onChange={setEndValue} value={endValue} />
          </div>
          <button onClick={() => setStartOpen(false)}>닫기</button>
        </div>
      )}
      <ReserveTable />
    </div>
  );
};

export default Main;
