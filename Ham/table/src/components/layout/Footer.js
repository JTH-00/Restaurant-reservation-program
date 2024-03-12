import React from "react";
import { Link } from "react-router-dom";
import style from "./footer.module.scss";

const Footer = () => {
  return (
    <footer>
      <div className={style.inner}>
        <nav className={style.nav}>
          <Link to="" className={style.item}>
            이용약관
          </Link>
          <Link to="" className={style.item}>
            서비스 운영 정책
          </Link>
          <Link to="" className={style.item}>
            개인정보처리방침
          </Link>
          <Link to="" className={style.item}>
            책임의 한계와 법적고지
          </Link>
          <Link to="" className={style.item}>
            글 권리 보호
          </Link>
          <Link to="" className={style.item}>
            게시물 중단 요청 서비스
          </Link>
        </nav>
        <address className={style.address}>
          <Link to="/" className={style.logo}>
            TableSnap
          </Link>
          <span className={style.address_text}>Copyright ©</span>
          <strong className={style.member}>식당 예약 프로그램</strong>
          <span className={style.address_text}>All Rights Reserved.</span>
        </address>
      </div>
    </footer>
  );
};

export default Footer;
