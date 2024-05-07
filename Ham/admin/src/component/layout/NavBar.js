import React, { useState } from "react";
import { Link } from "react-router-dom";
import styles from "./navbar.module.scss";
import logo1 from "../../assets/logo.svg";
import logo2 from "../../assets/logo1.png";
import userImg from "../../assets/user.svg";
import LogOut from "../../context/LogOut";

const NavBar = () => {
  let user = false;
  return (
    <div className={styles.form}>
      {user ? (
        <div className={styles.service_wrapper}>
          <p className={styles.redText}>{user} 님</p>
          <LogOut />
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
          <div className={styles.switch_wrapper}>
            <span>영업 임시 중단</span>
            <input role="switch" type="checkbox" />
          </div>
          <div className={styles.nav_wrapper}>
            <Link to={`/myPage/${user}`}>
              <img src={userImg} width={30} />
            </Link>
          </div>
        </header>
      </div>

      <div className={styles.ul_wrapper}>
        <Link to={`/storeManage`}>가게관리</Link>
        <Link to={"/"}>예약관리</Link>
        <Link to={`/reviewManage`}>리뷰관리</Link>
        <Link to={`/alarm`}>알림</Link>
      </div>
      <div className={styles.box_shadow}></div>
    </div>
  );
};

export default NavBar;
