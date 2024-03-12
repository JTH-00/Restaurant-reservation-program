import { useParams } from "react-router-dom";
import { categories } from "../contentData/categories";
import {
  restaurantDetail,
  restaurantReview,
  realMenu,
  menuCategories,
} from "../contentData/restaurantInfo";
import goldStar from "../assets/goldStar.png";
import styles from "./restaurant.module.scss";
import UserReview from "../components/restaurant/review/UserReview";
import MainInfo from "../components/restaurant/info/MainInfo";
import logo from "../assets/logo.svg";
import logo1 from "../assets/logo1.png";
import RestaurantMenu from "../components/restaurant/menu/RestaurantMenu";
import IntroduceRes from "../components/restaurant/introduce/IntroduceRes";
import RealReview from "../components/restaurant/review/RealReview";

const Restaurant = () => {
  let { id } = useParams();
  return (
    <div className={styles.form}>
      <MainInfo restaurantDetail={restaurantDetail} />
      <div className={styles.userReview_flex}>
        <UserReview restaurantReview={restaurantReview} />
        <UserReview restaurantReview={restaurantReview} />
        <UserReview restaurantReview={restaurantReview} />
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
      <h1>{restaurantDetail.restaurantName}</h1>
      <RestaurantMenu realMenu={realMenu} menuCategories={menuCategories} />
      <IntroduceRes restaurantDetail={restaurantDetail} />
      <RealReview />
    </div>
  );
};

export default Restaurant;
