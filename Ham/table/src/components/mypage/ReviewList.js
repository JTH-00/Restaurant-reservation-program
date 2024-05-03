import React, { useState, useEffect } from "react";
import styles from "../../routes/myPage.module.scss";
import gogiRestaurant from "../../assets/gogiRestaurant.png";
import warning from "../../assets/warning.png";
import goldStar from "../../assets/goldStar.png";
import { myPageHook } from "../../hooks/myPage";

const ReviewList = () => {
  const [isReviewItem, setIsReviewItem] = useState(false);
  const [dataList, setDataList] = useState(null);

  const { reviewListHook } = myPageHook();

  useEffect(() => {
    review();
  }, []);

  const review = async () => {
    const data = await reviewListHook();
    setDataList(data);
    setIsReviewItem(true);
  };

  console.log("dataList", dataList);
  return (
    <div>
      {isReviewItem == false ? (
        <div className={styles.noneItem_wrapper}>
          <img src={warning}></img>
          <p>리뷰가 존재하지 않습니다</p>
        </div>
      ) : (
        <>
          {dataList?.map((e) => {
            return (
              <div className={styles.ddip_card}>
                <img
                  src={gogiRestaurant}
                  style={{
                    marginLeft: "15px",
                    marginTop: "20px",
                    width: "100px",
                    height: "100px",
                  }}
                ></img>
                <div className={styles.card_content}>
                  <h3>삼겹살집</h3>
                  <div style={{ display: "flex" }}>
                    <img
                      src={goldStar}
                      style={{ width: "16px", height: "16px" }}
                    ></img>
                    <img
                      src={goldStar}
                      style={{ width: "16px", height: "16px" }}
                    ></img>
                    <img
                      src={goldStar}
                      style={{ width: "16px", height: "16px" }}
                    ></img>
                    <img
                      src={goldStar}
                      style={{ width: "16px", height: "16px" }}
                    ></img>
                  </div>
                  <span>{e.content}</span>
                </div>
                <div className={styles.btn_wrapper}>
                  <button className={styles.update}>리뷰 수정</button>
                  <button className={styles.delete}>리뷰 삭제</button>
                </div>
              </div>
            );
          })}
        </>
      )}
    </div>
  );
};

export default ReviewList;
