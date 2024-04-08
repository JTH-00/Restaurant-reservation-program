import { useState } from "react";
import { Link } from "react-router-dom";

import styles from "./mainInfo.module.scss";
import goldStar from "../../../assets/goldStar.png";
import { categories } from "../../../contentData/categories";
import { restaurantImg } from "../../../contentData/restaurantInfo";
import ReserveModal from "../../modal/ReserveModal";
import TakeOutModal from "../../modal/TakeOutModal";

const MainInfo = ({ restaurantDetail, reviewCount }) => {
  const [reserveModalOpen, setReserveModal] = useState(false);
  const [takeOutModalOpen, setTakeOutModal] = useState(false);

  const getCurrentDay = () => {
    const days = ["sun", "mon", "tue", "wed", "thu", "fri", "sat"];
    const currentDate = new Date();
    const currentDayIndex = currentDate.getDay();
    return days[currentDayIndex];
  };

  const dayOfWeek = {
    sun: "일",
    mon: "월",
    tue: "화",
    wed: "수",
    thu: "목",
    fri: "금",
    sat: "토",
  };

  // 현재 요일
  const currentDay = getCurrentDay();
  return (
    <div>
      <div className={styles.mainInfo_wrapper}>
        <img src={restaurantImg.gogi}></img>
        <div className={styles.rightInfo_wrapper}>
          <div className={styles.top_wrapper}>
            <h2>매장 정보</h2>
            <div className={styles.line}></div>
            <div className={styles.subInfo_wrapper}>
              <h3>식당주소 :</h3>
              <p>{restaurantDetail.address}</p>
            </div>
            <div className={styles.subInfo_wrapper}>
              <h3>전화번호 :</h3>
              <p>{restaurantDetail.phone}</p>
            </div>
            <div className={styles.subInfo_wrapper}>
              <h3>영업시간 : {restaurantDetail.opentime}</h3>
              {/* <div className={styles.business_hours}>
              {restaurantOpenArray.map(([day, hours]) => {
                return (
                  <p
                    key={day}
                    style={{
                      fontWeight: day === currentDay ? "bold" : "normal",
                    }}
                  >
                    {dayOfWeek[day]}요일: {hours}
                  </p>
                );
              })}
            </div> */}
            </div>
            <div className={styles.subInfo_wrapper}>
              <h3>휴무일 :</h3>
              <p>{restaurantDetail.closeddays}</p>
            </div>
          </div>

          <div className={styles.button_wrapper}>
            <button onClick={() => setReserveModal(true)}>
              예약날짜 정하기
            </button>
            <button onClick={() => setTakeOutModal(true)}>포장하기</button>
          </div>
        </div>
        {reserveModalOpen && <ReserveModal setIsOpen={setReserveModal} />}
        {takeOutModalOpen && <TakeOutModal setIsOpen={setTakeOutModal} />}
      </div>
      <div className={styles.basicInfo_wrapoer}>
        <div className={styles.division}>
          <p>{restaurantDetail.category}</p>
          <p>•{restaurantDetail.address}</p>
        </div>
        <h2>{restaurantDetail.title}</h2>
        <div className={styles.total}>
          <img src={goldStar}></img>
          <h3>5.0</h3>
          <p>{reviewCount}명 평가</p>
          <Link to="/">리뷰보기</Link>
        </div>
      </div>
    </div>
  );
};

export default MainInfo;
