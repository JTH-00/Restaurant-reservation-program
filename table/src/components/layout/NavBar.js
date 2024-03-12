import React, { useState } from "react";
import { categories, categoriesImg } from "../../contentData/categories";
import { Link } from "react-router-dom";

import styles from "./navBar.module.scss";
import vector from "../../assets/vector.svg";
import logo1 from "../../assets/logo.svg";
import logo2 from "../../assets/logo1.png";
import user from "../../assets/user.svg";
import bell from "../../assets/bell.png";
import category from "../../assets/categoryIcon.png";
import cart from "../../assets/cart.png";

const NavBar = () => {
  const [isHover, setIsHover] = useState(false);
  const [isCartOn, setIsCartOn] = useState(true);
  const userId = 1;

  return (
    <div className={styles.form}>
      <div className={styles.service_wrapper}>
        <Link to={"/signUp"} className={styles.redText}>
          회원가입
        </Link>
        <div className={styles.line}></div>
        <Link to={"/login"}>로그인</Link>
      </div>
      <div className={styles.navBar_wrapper}>
        <header className={styles.header_wrapper}>
          <a href="/" className={styles.logo_wrapper}>
            <img src={logo1} />
            <img
              className={styles.inner_logo}
              src={logo2}
              width={37.4}
              height={35.21}
            />
            <p>TableSnab</p>
          </a>
          <div className={styles.searchBar_wrapper}>
            <input type="text"></input>
            <button>
              <img src={vector}></img>
            </button>
          </div>
          <div className={styles.nav_wrapper}>
            <a href="/cart/:userId" className={styles.cart_inner}>
              <img src={cart} width={30} />
              {isCartOn && <span>2</span>}
            </a>
            <a>
              <img src={bell} width={30} />
            </a>
            <a href={`/myPage/${userId}`}>
              <img src={user} width={30} />
            </a>
          </div>
        </header>
        <div className={styles.ul_wrapper}>
          {isHover === false ? (
            <button
              className={styles.defaultBtn}
              onMouseEnter={() => setIsHover(true)}
              onMouseLeave={() => setIsHover(false)}
            >
              <img src={category} width={20} />
              카테고리
            </button>
          ) : (
            <button className={styles.hoverBtn}>
              <img src={category} width={20} />
              카테고리
            </button>
          )}

          <a>공지사항/이벤트</a>
          <a href={`/mypage/use/${userId}}`}>이용내역</a>
          <a href={`/mypage/reserve/${userId}`}>예약내역</a>
        </div>

        {isHover && (
          <div
            className={styles.categories_wrapper}
            onMouseEnter={() => setIsHover(true)}
            onMouseLeave={() => setIsHover(false)}
          >
            {categories.map((e, i) => {
              return (
                <a>
                  <img src={categoriesImg[i]}></img>
                  {e}
                </a>
              );
            })}
          </div>
        )}
      </div>

      <div className={styles.box_shadow}></div>
    </div>
  );
};

export default NavBar;
