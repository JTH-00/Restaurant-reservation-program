import styles from "./reserve.module.scss";
import React, { useEffect, useState } from "react";
import gogiRestaurant from "../assets/gogiRestaurant.png";
import axios from "axios";
import { useReserve } from "../hooks/reservePageHook";
import moment from "moment";
import { useNavigate } from "react-router-dom";

const Reserve = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [reserveData, setReserveData] = useState([]);

  const navigate = useNavigate();
  useEffect(() => {
    setIsLoading(true);
    reserved();
  }, []);

  const { userReservedHook, getRestaurant } = useReserve();

  const reserved = async () => {
    const response = await userReservedHook();
    const restaurantInfo = await getRestaurant();
    setReserveData(response);
    setIsLoading(false);
  };

  if (isLoading) {
    return <div>로딩중입니다</div>;
  }

  return (
    <div className={styles.form}>
      <div className={styles.reserve_header}>
        <h1>예약내역</h1>
      </div>
      {!isLoading && (
        <div className={styles.reserve_cardList}>
          <>
            {reserveData.map((e, i) => {
              return (
                <div key={i} className={styles.reserve_card}>
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
                      <p>주소 : </p>{" "}
                      <span>경기도 부천시 원미구 심곡동 부일로 442</span>
                    </div>
                    <div className={styles.content_detail}>
                      <p>예약일시 : </p>{" "}
                      <span>
                        {e.visittime} {e.visitcustomers}명
                      </span>
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
            })}
          </>
        </div>
      )}
    </div>
  );
};

export default Reserve;
