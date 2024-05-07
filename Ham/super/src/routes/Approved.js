import { useState } from "react";

import styles from "./approved.module.scss";
import dropdown from "../assets/dropdown.png";

const Approved = () => {
  const [isDropdown, setIsDropdown] = useState(false);

  return (
    <div className={styles.form}>
      <h1>가게승인</h1>
      <div className={styles.approved_box}>
        <div className={styles.approved_title}>
          <p style={{ width: "135px" }}>승인요청일자</p>
          <p style={{ width: "235px" }}>가맹점명</p>
          <p style={{ width: "130px" }}>카테고리</p>
          <p style={{ width: "110px" }}>분위기</p>
          <p style={{ width: "290px" }}>전화번호</p>
        </div>
        <div className={styles.approved_innerBox}>
          <div className={styles.first}>20230217</div>
          <div className={styles.second}>고깃집</div>
          <div className={styles.third}>고기•구이</div>
          <div className={styles.four}>분위기</div>
          <div className={styles.five}>
            02 - 1234 -1234
            <button>가게승인</button>
            <img
              src={dropdown}
              onClick={() =>
                isDropdown == false ? setIsDropdown(true) : setIsDropdown(false)
              }
            />
          </div>
        </div>
        {isDropdown && (
          <div className={styles.dropdown_wrapper}>
            <div className={styles.dropdown_title}>
              <p>사업자 번호 : {"12312312"}</p> <p>{"경기도 부천시"}</p>
            </div>
            <div className={styles.dropdown_detail}>
              <p>
                식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개식당소개
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Approved;
