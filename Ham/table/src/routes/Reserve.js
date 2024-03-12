import styles from "./reserve.module.scss";

import gogiRestaurant from "../assets/gogiRestaurant.png";

const Reserve = () => {
  return (
    <div className={styles.form}>
      <div className={styles.reserve_header}>
        <h1>예약내역</h1>
        <div className={styles.line}></div>
      </div>

      <div className={styles.reserve_cardList}>
        <div className={styles.reserve_card}>
          <img
            src={gogiRestaurant}
            style={{
              marginLeft: "50px",
              marginTop: "53px",
              width: "100px",
              height: "100px",
            }}
          ></img>
          <div className={styles.card_content}>
            <h3>삼겹살집</h3>
            <div className={styles.content_detail}>
              <p>주소 : </p> <span>경기도 부천시 원미구 심곡동 부일로 442</span>
            </div>
            <div className={styles.content_detail}>
              <p>예약일시 : </p> <span>2월 17일 6:00PM 인원 2명</span>
            </div>
          </div>
          <div className={styles.btn_wrapper}>
            <button className={styles.update}>상세 보기 {">"}</button>
            <button className={styles.delete}>예약 취소 {">"}</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Reserve;
