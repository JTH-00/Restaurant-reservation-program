import { useState } from "react";

import styles from "./mainInfo.module.scss";
import goldStar from "../../../assets/goldStar.png";
import { categories } from "../../../contentData/categories";
import { restaurantImg } from "../../../contentData/restaurantInfo";
import ReserveModal from "../../modal/ReserveModal";

const MainInfo = ({ restaurantDetail }) => {
  const [reserveModalOpen, setReserveModal] = useState(false);
  const [takeOutModal, setTakeOutModal] = useState(false);

  const restaurantOpenArray = Object.entries(restaurantDetail.restaurantOpen);

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
          <h2>매장 정보</h2>
          <div className={styles.line}></div>
          <div className={styles.subInfo_wrapper}>
            <h3>식당주소 :</h3>
            <p>{restaurantDetail.detailAddress}</p>
          </div>
          <div className={styles.subInfo_wrapper}>
            <h3>전화번호 :</h3>
            <p>{restaurantDetail.callNumber}</p>
          </div>
          <div className={styles.subInfo_wrapper}>
            <h3>영업시간 :</h3>
            <div className={styles.business_hours}>
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
            </div>
          </div>
          <div className={styles.subInfo_wrapper}>
            <h3>휴무일 :</h3>
            <p>{restaurantDetail.restaurantClose}</p>
          </div>
          <div className={styles.button_wrapper}>
            <button onClick={() => setReserveModal(true)}>
              예약날짜 정하기
            </button>
            <button onClick={() => setTakeOutModal(true)}>포장하기</button>
          </div>
        </div>
        {reserveModalOpen && <ReserveModal />}
      </div>
      <div className={styles.basicInfo_wrapoer}>
        <div className={styles.division}>
          <p>{categories[2]}</p>
          <p>•{restaurantDetail.address}</p>
        </div>
        <h2>{restaurantDetail.restaurantName}</h2>
        <div className={styles.total}>
          <img src={goldStar}></img>
          <h3>5.0</h3>
          <p>{restaurantDetail.reviewCounter}명 평가</p>
          <a href="/">리뷰보기</a>
        </div>
      </div>
    </div>
  );
};

export default MainInfo;
