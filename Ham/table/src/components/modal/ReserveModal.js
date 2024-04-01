import { useState, useEffect } from "react";
import useDateInfo from "../../hooks/dayHook";
import styles from "./reserveModal.module.scss";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";

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
      <header>
        <h3>예약주문</h3>
      </header>
      <div className={styles.line}></div>
      <body>
        <img></img>
        <p>[고기 • 구이] 삼겹살집</p>
        <button>
          {startDateInfo.year} - {startDateInfo.month} - {startDateInfo.day}
        </button>
        <span>
          <p>18:30PM</p> 해당 예약일이 맞습니까?
        </span>
        {openCal && <Calendar onChange={setValue} value={value} />}
      </body>
      <div className={styles.line}></div>
      <footer>
        <button onClick={() => setIsOpen(true)}>취소</button>
        <button onClick={() => setIsOpen(true)}>등록</button>
      </footer>
    </div>
  );
};

export default ReserveModal;
