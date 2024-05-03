import styles from "../../routes/reserve.module.scss";
import { useNavigate } from "react-router-dom";
import gogiRestaurant from "../../assets/gogiRestaurant.png";

const ReserveCard = ({ data }) => {
  const navigate = useNavigate();

  console.log("data", data);

  return (
    <div key={"i"} className={styles.reserve_card}>
      <img
        type="button"
        onClick={() => navigate("/restaurant/1")}
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
          <p>예약일시 : </p> <span>{"d"}</span>
        </div>
      </div>
      <div className={styles.btn_wrapper}>
        <button
          className={styles.update}
          onClick={() => navigate("/restaurant/1")}
        >
          상세 보기 {">"}
        </button>
        <button className={styles.delete}>예약 취소 {">"}</button>
      </div>
    </div>
  );
};

export default ReserveCard;
