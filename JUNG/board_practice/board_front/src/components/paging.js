import styles from "./Paging.module.css";

const Paging = () => {
  return (
    <div className={styles.paging}>
      <div className={styles.div}>
        <div className={styles.num}>1</div>
      </div>
      <div className={styles.div}>
        <div className={styles.num}>2</div>
      </div>
      <div className={styles.div}>
        <div className={styles.num}>3</div>
      </div>
      <div className={styles.div}>
        <div className={styles.num}>4</div>
      </div>
      <div className={styles.div}>
        <div className={styles.num}>5</div>
      </div>
      <img className={styles.nextIcon} alt="" src="/next.svg" />
    </div>
  );
};

export default Paging;