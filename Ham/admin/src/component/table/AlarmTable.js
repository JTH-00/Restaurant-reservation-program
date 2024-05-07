import { useState } from "react";
import styles from "./alarmTable.module.scss";

const AlarmTable = () => {
  const [redCircle, setRedCircle] = useState(true);
  console.log(redCircle);
  return (
    <div className={styles.form}>
      <div className={styles.alarm_title}>
        <p style={{ width: "276px" }}>방문일시</p>
        <p style={{ width: "279px" }}>분류</p>
        <p style={{ width: "345px" }}>예약자</p>
      </div>
      <div
        className={styles.alarm_detail}
        type="button"
        onClick={() => setRedCircle(false)}
      >
        <div className={styles.left}>
          {redCircle && <div className={styles.redCircle} />}20230229 18:30PM
        </div>
        <div className={styles.mid}>리뷰</div>
        <div className={styles.right}>
          <p>함승완 외 1명</p> <button>리뷰보기</button>
        </div>
      </div>
      {/* <div className={styles.alarm_detail}>
        <div className={styles.left}>20230229 18:30PM</div>
        <div className={styles.mid}>포장</div>
        <div className={styles.right}>함승완</div>
      </div>
      <div className={styles.alarm_detail}>
        <div className={styles.left}>20230229 18:30PM</div>
        <div className={styles.mid}>예약</div>
        <div className={styles.right}>
          함승완 외 1명 <button>거부</button>
        </div>
      </div> */}
    </div>
  );
};

export default AlarmTable;
