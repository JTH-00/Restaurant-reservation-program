import styles from "./Menu.module.css";
import React from "react";

const Menu = () => {
  return (
    <div className={styles.menu}>
      <h2 className={styles.header}>고객센터</h2>
      <div className={styles.menuItem}>쇼핑 FAQ</div>
      <hr className={styles.line} />
      <div className={styles.menuItem}>공지사항</div>
      <hr className={styles.line} />
    </div>
  );
};

export default Menu;