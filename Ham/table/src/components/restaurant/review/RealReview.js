import React, { useState } from "react";
import styles from "./realReview.module.scss";
import goldStar from "../../../assets/goldStar.png";
import order from "../../../assets/order.png";
import flower from "../../../assets/flower.png";
import like from "../../../assets/like.png";
import { restaurantImg } from "../../../contentData/restaurantInfo";
import Modal from "../../modal/Modal";

const RealReview = () => {
  const [isOpen, setIsOpen] = useState(false);

  const imgArr = [restaurantImg.gogi, restaurantImg.cafe];
  const modalData = [
    "신고 작성",
    "고기•구이",
    "삼겹살집",
    "700px",
    "700px",
    imgArr[0],
  ];

  return (
    <div className={styles.realReview_wrapper}>
      <div className={styles.realReview_header}>
        <h3>
          <img src={goldStar}></img>리얼 리뷰
        </h3>
        <span>123명 평가</span>
        <span>123명 리뷰</span>
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
      <div className={styles.userReview_wrapper}>
        <aside style={{ display: "flex" }}>
          <div className={styles.circle}></div>
          <p style={{ marginLeft: "10px", marginTop: "10px" }}>함승완</p>
        </aside>
        <div className={styles.reviewDetail_wrapper}>
          <img style={{ width: "20px", height: "20px" }} src={goldStar}></img>
          <img style={{ width: "20px", height: "20px" }} src={goldStar}></img>
          <img style={{ width: "20px", height: "20px" }} src={goldStar}></img>
          <img style={{ width: "20px", height: "20px" }} src={goldStar}></img>
          <img style={{ width: "20px", height: "20px" }} src={goldStar}></img>
          <span style={{ color: "gray" }}>2개월 전</span>
          <div className={styles.detailImg}>
            {imgArr.map((e, i) => {
              // 첫 번째 이미지에 대한 조건부 클래스 지정
              const imgClass = i === 0 ? styles.firstImg : null;
              return <img key={i} src={e} className={imgClass}></img>;
            })}
          </div>
          <span>주문메뉴:</span> <span>삼겹살,목살</span>
          <p>
            인생 최고의 삼겹살 이였습니다.다음에도 이용할 예정입니다.부천
            심곡에서 고기드실분은 여기로 오세요. 제가 아는 삼겹살집 중에서
            가성비 좋고 최고였습니다. 다들 즐거운 식사 하시길 바랍니다
          </p>
        </div>
        <button
          className={styles.report_button}
          onClick={(e) => setIsOpen(true)}
        >
          신고하기
        </button>
        {isOpen && <Modal setIsOpen={setIsOpen} modalData={modalData} />}
      </div>
    </div>
  );
};

export default RealReview;
