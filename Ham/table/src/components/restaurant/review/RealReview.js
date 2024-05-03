import React, { useState } from "react";
import styles from "./realReview.module.scss";
import goldStar from "../../../assets/goldStar.png";
import order from "../../../assets/order.png";
import flower from "../../../assets/flower.png";
import like from "../../../assets/like.png";
import { restaurantImg } from "../../../contentData/restaurantInfo";
import Modal from "../../modal/Modal";

import { getDayMinuteCounter } from "../../../hooks/timeHook";

const RealReview = ({ reviewCount, reviewDto }) => {
  const [isOpen, setIsOpen] = useState(false);

  // const { timeAgo } = useTimeStamp();

  const imgArr = [restaurantImg.gogi, restaurantImg.cafe];
  const modalData = [
    "신고",
    "고기•구이",
    "삼겹살집",
    "700px",
    "700px",
    imgArr[0],
  ];
  console.log(reviewDto, 12341242);

  return (
    <div className={styles.realReview_wrapper}>
      <div className={styles.realReview_header}>
        <h3>
          <img src={goldStar}></img>리얼 리뷰
        </h3>
        <span>{reviewCount}명 평가</span>
        <span>{reviewCount}명 리뷰</span>
        <div className={styles.order_wrapper}>
          <img src={order}></img>
          <select>
            <option>추천순</option>
            <option>최신순</option>
            <option>평점 높은순</option>
            <option>평점 낮은순</option>
          </select>
        </div>
      </div>
      <div className={styles.line}></div>
      {reviewDto.content.map((e) => {
        return (
          <div className={styles.userReview_wrapper}>
            <aside style={{ display: "flex" }}>
              <div className={styles.circle}></div>

              <p style={{ marginLeft: "10px", marginTop: "10px" }}>
                {e.user.username}
              </p>
            </aside>
            <div className={styles.reviewDetail_wrapper}>
              <img
                style={{ width: "20px", height: "20px" }}
                src={goldStar}
              ></img>
              <img
                style={{ width: "20px", height: "20px" }}
                src={goldStar}
              ></img>
              <img
                style={{ width: "20px", height: "20px" }}
                src={goldStar}
              ></img>
              <img
                style={{ width: "20px", height: "20px" }}
                src={goldStar}
              ></img>
              <img
                style={{ width: "20px", height: "20px" }}
                src={goldStar}
              ></img>
              <span style={{ color: "gray" }}>
                {getDayMinuteCounter(e.date)}
              </span>
              <div className={styles.detailImg}>
                {imgArr.map((e, i) => {
                  // 첫 번째 이미지에 대한 조건부 클래스 지정
                  const imgClass = i === 0 ? styles.firstImg : null;
                  return <img key={i} src={e} className={imgClass}></img>;
                })}
              </div>
              <span>주문메뉴:</span>
              {e.paymentMenuDtos?.map((menu) => {
                return (
                  <span style={{ marginLeft: "10px" }}>
                    {menu.menu.name} : {menu.count}
                  </span>
                );
              })}

              <p>{e.content}</p>
            </div>
            <button
              className={styles.report_button}
              onClick={(e) => setIsOpen(true)}
            >
              신고하기
            </button>
            {isOpen && <Modal setIsOpen={setIsOpen} modalData={modalData} />}
          </div>
        );
      })}
    </div>
  );
};

export default RealReview;
