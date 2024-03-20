import React, { useState } from "react";
import styles from "./restaurantList.module.scss";
import KakaoMap from "../components/KakaoMap";
import { categories, viveCategories } from "../contentData/categories";
import donkkasRestaurant from "../assets/donkkasRestaurant.png";
import goldstar from "../assets/goldStar.png";
import heart from "../assets/heart.png";

const RestaurantList = () => {
  const [activeButton, setActiveButton] = useState("전체");
  const [viveActiveButton, setviveActiveButton] = useState("");

  console.log(viveActiveButton);

  const handleClick = (buttonName) => {
    setActiveButton(buttonName);
  };

  const viveClick = (buttonName) => {
    setviveActiveButton(buttonName);
  };
  return (
    <div className={styles.form}>
      <div className={styles.list_header}>
        <h1>부천심곡매장 4개</h1>
      </div>
      <div className={styles.list_wrapper}>
        <aside className={styles.aside_wrapper}>
          <KakaoMap mapWidth={288} mapHeight={172} />
          <div className={styles.category_all}>
            <h3>카테고리</h3>
            <div
              className={
                activeButton === "전체"
                  ? styles.active
                  : styles.category_wrapper
              }
            >
              <button name="전체" onClick={() => handleClick("전체")}></button>
              <span>전체</span>
            </div>
            {categories.map((e) => {
              return (
                <div
                  className={
                    activeButton == e ? styles.active : styles.category_wrapper
                  }
                >
                  <button name={e} onClick={() => handleClick(e)}></button>
                  <span>{e}</span>
                </div>
              );
            })}
          </div>

          <div className={styles.vive_all}>
            <h3>분위기</h3>
            <div className={styles.vive_wrapper}>
              {Array(Math.ceil(viveCategories.length / 3)) // 3개씩 묶어서 줄 수 계산
                .fill()
                .map((e, index) => (
                  <div key={index} className={styles.vive_row}>
                    {viveCategories
                      .slice(index * 3, (index + 1) * 3)
                      .map((category) => (
                        <button
                          className={
                            viveActiveButton === category
                              ? styles.active_vive
                              : styles.default_vive
                          }
                          key={category}
                          name={category}
                          onClick={() => viveClick(category)}
                        >
                          {category}
                        </button>
                      ))}
                  </div>
                ))}
            </div>
          </div>
        </aside>
        <div className={styles.right_side}>
          <div className={styles.restaurant_card}>
            <img src={donkkasRestaurant} width={277} height={200}></img>
            <div className={styles.card_info}>
              <p>일식 • 부천 심곡동</p>
              <h3>돈까스집</h3>
              <p>운영시간: 10:00 ~ 22:00</p>
              <img
                width={20}
                height={20}
                src={goldstar}
                style={{ marginTop: "12px" }}
              ></img>
              <span style={{ fontSize: "14px", fontWeight: "bold" }}>4.5</span>
              <span>(6)</span>
              <p style={{ color: "black" }}>정성이 깃든 돈가스 전문점</p>
            </div>
            <div>
              <img src={heart}></img>
            </div>
          </div>
          <div className={styles.line}></div>
        </div>
      </div>
    </div>
  );
};

export default RestaurantList;
