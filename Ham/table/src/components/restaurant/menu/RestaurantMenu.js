import styles from "./restaurantMenu.module.scss";
import React, { useState, useEffect } from "react";
import gogiRestaurant from "../../../assets/gogiRestaurant.png";
const RestaurantMenu = ({ realMenu, menuCategories }) => {
  const [currentIndex, setCurrentIndex] = useState("메인메뉴");
  const [currentMenu, setCurrentMenu] = useState([]);

  useEffect(() => {}, []);

  // console.log(currentIndex);
  // console.log("menuCategories", menuCategories);

  return (
    <div className={styles.menu_wrapper}>
      <div className={styles.menu_header}>
        <p>메뉴</p>
        <div className={styles.line}></div>
      </div>
      <div className={styles.menuCategories_wrapper}>
        {menuCategories.map((category, index) => {
          return (
            <div key={index}>
              <button
                className={
                  currentIndex == category.name
                    ? styles.active_button
                    : styles.default_button
                }
                onClick={() => setCurrentIndex(category.name)}
              >
                {category.name}
              </button>
            </div>
          );
        })}
      </div>
      <div className={styles.realMenu_wrapper}>
        {realMenu.map((menu, index) => {
          const formattedPrice = new Intl.NumberFormat("ko-KR").format(
            menu.price
          );

          return (
            menu?.categoryid?.name === currentIndex && (
              <div className={styles.detailMenu_wrapper}>
                <img src={gogiRestaurant} alt={menu.name}></img>
                <h3>{menu.name}™</h3>
                <p>{menu.content}</p>
                <h3>{new Intl.NumberFormat("ko-KR").format(menu.price)}원</h3>
              </div>
            )
          );
        })}
      </div>
    </div>
  );
};

export default RestaurantMenu;
