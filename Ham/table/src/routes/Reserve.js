import styles from "./reserve.module.scss";
import React, { useEffect, useState } from "react";
import gogiRestaurant from "../assets/gogiRestaurant.png";
import axios from "axios";
import { useReserve } from "../hooks/reservePageHook";
import moment from "moment";
import { useNavigate } from "react-router-dom";
import ReserveCard from "../components/reserveCard/ReserveCard";

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

  console.log(reserveData);

  return (
    <div className={styles.form}>
      <div className={styles.reserve_header}>
        <h1>예약내역</h1>
      </div>
      {!isLoading && (
        <div className={styles.reserve_cardList}>
          <>
            {reserveData?.content?.map((e, i) => {
              // e.map((e) => {
              //   <ReserveCard data={e} />;
              // });
            })}
          </>
        </div>
      )}
    </div>
  );
};

export default Reserve;
