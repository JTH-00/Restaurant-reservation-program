import { useState, useEffect } from "react";
import Calendar from "react-calendar";
import styles from "../../routes/block.module.scss";
import "react-calendar/dist/Calendar.css";
import useDateInfo from "../../hooks/dayHook";
const ReportCheck = () => {
  const [startOpen, setStartOpen] = useState(false);
  const [startValue, setStartValue] = useState(new Date());
  const [endValue, setEndValue] = useState(new Date());
  const [isActive, setIsActive] = useState("3개월");

  const { startDateInfo, setStartDate, endDateInfo, setEndDate } =
    useDateInfo();

  useEffect(() => {
    setStartDate(startValue);
    setEndDate(endValue);
  }, [startValue, endValue]);

  return (
    <div>
      <h1>신고조회</h1>
      <div className={styles.title_box}>
        <div className={styles.choose_date}>
          <button
            className={
              isActive == "3개월" ? styles.active_btn : styles.default_btn
            }
            name="3개월"
            onClick={() => setIsActive("3개월")}
          >
            3개월
          </button>
          <button
            className={
              isActive == "6개월" ? styles.active_btn : styles.default_btn
            }
            name="6개월"
            onClick={() => setIsActive("6개월")}
          >
            6개월
          </button>
          <button
            className={
              isActive == "1년" ? styles.active_btn : styles.default_btn
            }
            name="1년"
            onClick={() => setIsActive("1년")}
          >
            1년
          </button>
        </div>
        <div className={styles.day_wrapper}>
          <button onClick={() => setStartOpen(true)}>
            {startDateInfo.year} - {startDateInfo.month} - {startDateInfo.day}
          </button>{" "}
          ~{" "}
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
    </div>
  );
};

export default ReportCheck;
