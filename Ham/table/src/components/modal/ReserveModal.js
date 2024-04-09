import { useState, useEffect } from "react";
import useDateInfo from "../../hooks/dayHook";
import styles from "./reserveModal.module.scss";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import imgCal from "../../assets/imgCal.png";
import gogiRestaurant from "../../assets/gogiRestaurant.png";

const ReserveModal = ({ setIsOpen }) => {
  const [openCal, setOpenCal] = useState(false);
  const [value, setValue] = useState(new Date());

  const { startDateInfo, setStartDate, endDateInfo, setEndDate } =
    useDateInfo();

  useEffect(() => {
    setStartDate(value);
  }, [value]);

  return (
    <div className={styles.modal_wrap}>
      <div className={styles.form}>
        <header className={styles.reserve_header}>
          <h3>예약주문</h3>
        </header>
        <body className={styles.reserve_body}>
          <img
            style={{
              width: "75px",
              height: "75px",
              borderRadius: "10px",
              marginTop: "5px",
            }}
            src={gogiRestaurant}
          ></img>
          <div className={styles.reserve_info}>
            <p>[고기 • 구이] 삼겹살집</p>
            <div className={styles.info_detail}>
              <button onClick={() => setOpenCal(true)}>
                {startDateInfo.year} - {startDateInfo.month} -
                {startDateInfo.day}
                <img src={imgCal}></img>
              </button>
              <p>
                <span>18:30PM</span> 해당 예약일이 맞습니까?
              </p>
            </div>
          </div>
        </body>
        {openCal && (
          <div className={styles.calender_warpper}>
            <Calendar onChange={setValue} value={value} />
            <button onClick={() => setOpenCal(false)}>닫기</button>
          </div>
        )}
        <footer className={styles.footer_wrapper}>
          <button
            className={styles.cancle_button}
            onClick={() => setIsOpen(false)}
          >
            취소
          </button>
          <button
            className={styles.confirm_button}
            onClick={() => setIsOpen(false)}
          >
            등록
          </button>
        </footer>
      </div>
    </div>
  );
};

export default ReserveModal;
