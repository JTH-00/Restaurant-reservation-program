import React, { useState, useEffect } from "react";

import styles from "./main.module.scss";
import { restaurantArray } from "../contentData/restaurantInfo";
import goldStar from "../assets/goldStar.png";
import grayStar from "../assets/grayStar.png";
import { imageData } from "../contentData/imageData";
import { Link } from "react-router-dom";
import { useUser } from "../context/AuthContext";
import axios from "axios";

const Main = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const { userId } = useUser();

  useEffect(() => {
    const intervalId = setInterval(nextSlide, 5000);
    return () => clearInterval(intervalId); // 컴포넌트가 언마운트되면 인터벌 제거
  }, []);

  useEffect(() => {
    getRest();
  }, []);
  const nextSlide = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === imageData.length - 1 ? 0 : prevIndex + 1
    );
  };

  let starScore = restaurantArray.map((e) => {
    const starScoreArr = [];

    for (let i = 0; i < e[3]; i++) {
      starScoreArr.push(<img width={16} height={16} src={goldStar}></img>);
    }
    for (let i = 0; i < e[4]; i++) {
      starScoreArr.push(<img width={16} height={16} src={grayStar}></img>);
    }
    return starScoreArr;
  });

  const getRest = async () => {
    try {
      const response = await axios.get("/api/main");
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  };

  const RestaurantList = ({ title, subTitle }) => {
    return (
      <div>
        <div className={styles.info_Wrapper}>
          <h2>{title}</h2>
          <p>{subTitle}</p>
        </div>
        <div className={styles.restaurantList_wrapper}>
          {restaurantArray.map((restaurant, i) => (
            <div className={styles.restaurant_wrapper} key={restaurant[0]}>
              <Link to={`/restaurant/${restaurant[5]}`}>
                <h3>
                  {restaurant[0]} ({restaurant[1]})
                </h3>
                {starScore[i]}
                <img
                  className={styles.restaurantImg}
                  src={restaurant[2]}
                  alt={restaurant[0]}
                />
              </Link>
            </div>
          ))}
        </div>
        <div className={styles.restaurantList_wrapper}>
          {restaurantArray.map((restaurant, i) => (
            <div className={styles.restaurant_wrapper} key={restaurant[0]}>
              <Link to={`/restaurant/${restaurant[5]}`}>
                <h3>
                  {restaurant[0]} ({restaurant[1]})
                </h3>
                {starScore[i]}
                <img
                  className={styles.restaurantImg}
                  src={restaurant[2]}
                  alt={restaurant[0]}
                />
              </Link>
            </div>
          ))}
        </div>
      </div>
    );
  };

  return (
    <main className={styles.form}>
      <div className={styles.slide_wrapper}>
        <img src={imageData[currentIndex]} alt={`slide-${currentIndex}`} />
        {imageData.map((e, i) => {
          return (
            <button
              className={
                i === currentIndex
                  ? styles.active_button
                  : styles.default_button
              }
              key={i}
              onClick={() => setCurrentIndex(i)}
            ></button>
          );
        })}
      </div>
      <RestaurantList title="TableSnap" subTitle="의 맛집추천!" />
      <RestaurantList title="TableSnap" subTitle="과 함께하는 다른 맛집들" />
    </main>
  );
};

export default Main;
