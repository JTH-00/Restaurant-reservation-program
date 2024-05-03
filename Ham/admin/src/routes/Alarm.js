import AlarmTable from "../component/table/AlarmTable";
import styles from "./alarm.module.scss";

const Alarm = () => {
  return (
    <div className={styles.form}>
      <div className={styles.alarm_title}>
        <h1>알림</h1>
      </div>
      <div className={styles.alarm_category}>
        <select>
          <option>전체</option>
          <option>예약</option>
          <option>포장</option>
          <option>리뷰</option>
        </select>
      </div>
      <AlarmTable />
    </div>
  );
};

export default Alarm;
