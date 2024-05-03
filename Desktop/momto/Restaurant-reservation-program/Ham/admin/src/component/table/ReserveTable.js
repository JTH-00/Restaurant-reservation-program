import styles from "./reserveTable.module.scss";

const ReserveTable = ({}) => {
  //props받을땐 반드시 {}형태로 받아야됨
  return (
    <div className={styles.form}>
      <div className={styles.reserve_title}>
        <h3 style={{ width: "276px" }}>예약일시</h3>
        <h3 style={{ width: "279px" }}>방문일시</h3>
        <h3 style={{ width: "345px" }}>예약자</h3>
      </div>
      <div className={styles.reserve_detail}>
        <div className={styles.left}>
          <p>2024-03-23</p>
        </div>
        <div className={styles.mid}>
          <p>2024-03-23</p>
        </div>
        <div className={styles.right}>
          <p>함승왕</p>
        </div>
      </div>
    </div>
  );
};

export default ReserveTable;
