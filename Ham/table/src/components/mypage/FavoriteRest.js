import React, { useState, useEffect } from "react";
import gogiRestaurant from "../../assets/gogiRestaurant.png";
import warning from "../../assets/warning.png";
import goldStar from "../../assets/goldStar.png";
import styles from "../../routes/myPage.module.scss";
import { myPageHook } from "../../hooks/myPage";

const FavoriteRest = () => {
  const [isItem, setIsItem] = useState(false);
  const [dataList, setDataList] = useState(null);

  const { favoriteRestHook } = myPageHook();

  useEffect(() => {
    favorite();
  }, []);

  const favorite = async () => {
    try {
      const data = await favoriteRestHook();
      if (data) {
        setDataList(data);
        setIsItem(true);
        console.log(data);
      } else {
        setIsItem(false);
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      {isItem == false ? (
        <div className={styles.noneItem_wrapper}>
          <img src={warning}></img>
          <p>찜한 매장이 존재하지 않습니다</p>
        </div>
      ) : (
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
            <p>
              <img
                src={goldStar}
                style={{ width: "16px", height: "16px" }}
              ></img>
              4.9 (13)
            </p>
            <span>경기도 부천시 원미구 심곡동 부일로 442</span>
          </div>
          <div className={styles.btn_wrapper}>
            <button className={styles.delete}>삭제</button>
            <button className={styles.order}>주문하기</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default FavoriteRest;
