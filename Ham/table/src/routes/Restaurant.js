import React, { useEffect, useState } from "react";

import { useParams } from "react-router-dom";
import { restaurantDetail } from "../contentData/restaurantInfo";
import styles from "./restaurant.module.scss";
import UserReview from "../components/restaurant/review/UserReview";
import MainInfo from "../components/restaurant/info/MainInfo";
import logo from "../assets/logo.svg";
import logo1 from "../assets/logo1.png";
import RestaurantMenu from "../components/restaurant/menu/RestaurantMenu";
import IntroduceRes from "../components/restaurant/introduce/IntroduceRes";
import RealReview from "../components/restaurant/review/RealReview";
import axios from "axios";
import { useTimeStamp } from "../hooks/timeHook";
const Restaurant = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [categoryDto, setCategoryDto] = useState(null);
  const [menuDtoList, setMenuDtoList] = useState(null);
  const [restaurantDto, setRestaurantDto] = useState(null);
  const [reviewDto, setReviewDto] = useState(null);
  const [reviewCount, setReviewCount] = useState(null);

  const { id } = useParams();

  useEffect(() => {
    setIsLoading(true);
    getRestaurant();
  }, []);

  const getRestaurant = async () => {
    try {
      const response = await axios.get("/api/user/restaurant/1");
      setCategoryDto(response.data.menuCategoryDto);
      setMenuDtoList(response.data.menuDtoList);
      setRestaurantDto(response.data.restaurantDto);
      setReviewDto(response.data.reviewDto);
      setReviewCount(response.data.reviewDto.content.length);
      console.log(response);
      setIsLoading(false);
    } catch (err) {
      console.error(err);
    }
  };

  if (isLoading) {
    return <div>로딩중입니다</div>;
  }

  console.log("categoryDto", categoryDto);
  console.log("menuDtoList", menuDtoList);
  console.log("restaurantDto", restaurantDto);
  console.log("reviewDto", reviewDto);
  return (
    <div>
      {!isLoading && (
        <div className={styles.form}>
          <MainInfo
            restaurantDetail={restaurantDto}
            reviewCount={reviewCount}
          />
          <div className={styles.userReview_flex}>
            <UserReview restaurantReview={reviewDto} />
          </div>
          <div className={styles.logo}>
            <img width={75} height={75} src={logo}></img>
            <img
              className={styles.inner_logo}
              width={63.75}
              height={63.75}
              src={logo1}
            ></img>
          </div>
          <div className={styles.commend_wrapper}>
            <h2>TableSnap</h2>
            <p>이 추천하는 매장</p>
          </div>
          <h1>{restaurantDto.title}</h1>
          <RestaurantMenu realMenu={menuDtoList} menuCategories={categoryDto} />
          <IntroduceRes restaurantDetail={restaurantDetail} />
          <RealReview reviewCount={reviewCount} reviewDto={reviewDto} />
        </div>
      )}
    </div>
  );
};

export default Restaurant;
