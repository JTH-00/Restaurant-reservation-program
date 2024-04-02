import React, { useState, useEffect } from "react";
import { categories, categoriesImg } from "../../contentData/categories";
import { Link } from "react-router-dom";
import { useUser } from "../../context/AuthContext";
import styles from "./navBar.module.scss";
import vector from "../../assets/vector.svg";
import logo1 from "../../assets/logo.svg";
import logo2 from "../../assets/logo1.png";
import userImg from "../../assets/user.svg";
import bell from "../../assets/bell.png";
import category from "../../assets/categoryIcon.png";
import cart from "../../assets/cart.png";
import Logout from "../../context/LogOut";

const NavBar = () => {
  const { user, userId, setTokenUser } = useUser();

  useEffect(() => {
    setTokenUser();
  }, []);

  const [isHover, setIsHover] = useState(false);
  const [isCartOn, setIsCartOn] = useState(true);
  return (
    <div className={styles.form}>
      {user ? (
        <div className={styles.service_wrapper}>
          <p className={styles.redText}>{user} 님</p>
          <Logout />
        </div>
      ) : (
        <div className={styles.service_wrapper}>
          <Link to={"/signUp"} className={styles.redText}>
            회원가입
          </Link>
          <div className={styles.line}></div>
          <Link to={"/login"} style={{ marginLeft: "12px" }}>
            로그인
          </Link>
        </div>
      )}

      <div className={styles.navBar_wrapper}>
        <header className={styles.header_wrapper}>
          <Link to="/" className={styles.logo_wrapper}>
            <img src={logo1} />
            <img
              className={styles.inner_logo}
              src={logo2}
              width={37.4}
              height={35.21}
            />
            <p>TableSnab</p>
          </Link>
          <div className={styles.searchBar_wrapper}>
            <input type="text"></input>
            <button>
              <img src={vector}></img>
            </button>
          </div>
          <div className={styles.nav_wrapper}>
            <Link to={`/cart/${user}`} className={styles.cart_inner}>
              <img src={cart} width={30} />
              {isCartOn && <span>2</span>}
            </Link>
            <a>
              <img src={bell} width={30} />
            </a>
            <Link to={`/myPage/${user}`}>
              <img src={userImg} width={30} />
            </Link>
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

          <Link to={"/inform"}>공지사항/이벤트</Link>
          <Link to={`/mypage/use/${user}`}>이용내역</Link>
          <Link to={`/mypage/reserve/${user}`}>예약내역</Link>
        </div>

        {isHover && (
          <div
            className={styles.categories_wrapper}
            onMouseEnter={() => setIsHover(true)}
            onMouseLeave={() => setIsHover(false)}
          >
            {categories.map((e, i) => {
              return (
                <Link to={`restaurantList/${i}`}>
                  <img src={categoriesImg[i]} key={i}></img>
                  {e}
                </Link>
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
