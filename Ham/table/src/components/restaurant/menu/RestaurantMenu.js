import styles from "./restaurantMenu.module.scss";
import React, { useState, useEffect } from "react";

const RestaurantMenu = ({ realMenu, menuCategories }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [currentMenu, setCurrentMenu] = useState([]);
  let a = realMenu.mainMenu;
  let b = realMenu.sideMenu;
  let c = realMenu.etcMenu;
  let d = realMenu.drink;
  useEffect(() => {
    if (menuCategories[currentIndex] === "메인메뉴") {
      setCurrentMenu(a);
    } else if (menuCategories[currentIndex] === "사이드메뉴") {
      setCurrentMenu(b);
    } else if (menuCategories[currentIndex] === "기타메뉴") {
      setCurrentMenu(c);
    } else if (menuCategories[currentIndex] === "주류 및 음료") {
      setCurrentMenu(d);
    }
  }, [currentIndex, a, b, c, d, menuCategories]);

  const menuCategoriesOnClick = (i) => {
    setCurrentIndex(i);
  };

  return (
    <div className={styles.menu_wrapper}>
      <div className={styles.menu_header}>
        <p>메뉴</p>
        <div className={styles.line}></div>
      </div>
      <div className={styles.menuCategories_wrapper}>
        {menuCategories.map((e, i) => {
          return (
            <div>
              <button
                className={
                  i === currentIndex
                    ? styles.active_button
                    : styles.default_button
                }
                key={i}
                onClick={() => menuCategoriesOnClick(i)}
              >
                {e}
              </button>
            </div>
          );
        })}
      </div>
      <h2>{menuCategories[currentIndex]}</h2>
      <div className={styles.realMenu_wrapper}>
        {currentMenu.map((e, i) => {
          return (
            <div className={styles.detailMenu_wrapper} key={i}>
              <img src={e[1]}></img>
              <h3>{e[0]}™</h3>
              <p>
                겉은 바삭 육즙 가득한 부드러운 속살이 환상적인 건강한 치킨
                비비큐의 시그니처 메뉴 후라이드의 대명사 황금올리브치킨™
              </p>
              <h3>30,000원</h3>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default RestaurantMenu;
