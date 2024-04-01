import styles from "./NavBar.module.css";

const NavBar = () => {
  return (
    <div className={styles.navbar}>
      <div className={styles.user}>
        <div className={styles.div1}>회원가입</div>
        <div className={styles.div2}>로그인</div>
        <img className={styles.userChild} alt="" src="/line-1.svg" />
      </div>
      <div className={styles.logo}>
        <div className={styles.logoInner}>
          <div className={styles.groupWrapper}>
            <div className={styles.groupWrapper}>
              <img className={styles.groupChild} alt="" src="/group-2.svg" />
              <img className={styles.image2Icon} alt="" src="/image-2@2x.png" />
            </div>
          </div>
        </div>
        <div className={styles.tablesnap}>TableSnap</div>
      </div>
      <div className={styles.search}>
        <div className={styles.searchChild} />
        <img className={styles.vectorIcon} alt="" src="/vector.svg" />
      </div>
      <img className={styles.icon} alt="" src="/icon.svg" />
      <div className={styles.click}>
        <b className={styles.link}>카테고리</b>
        <b className={styles.link}>예약내역</b>
        <div className={styles.link}>이용내역</div>
        <b className={styles.link}>공지사항/이벤트</b>
      </div>
      <div className={styles.line} />
    </div>
  );
};

export default NavBar;