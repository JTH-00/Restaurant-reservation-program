import styles from "../../routes/block.module.scss";

const BlockTable = () => {
  return (
    <div className={styles.table_form}>
      <div className={styles.table_title}>
        <h3 style={{ width: "135px" }}>차단일자</h3>
        <h3 style={{ width: "765px" }}>가맹점명</h3>
      </div>

      <div className={styles.block_table}>
        <div className={styles.block_day}>
          <p>20230217</p>
        </div>
        <div className={styles.block_restaurant}>
          <p>고깃집 (버튼으로 활용해서 누르면 매장 가지게)</p>
          <button>차단 해제</button>
        </div>
      </div>
    </div>
  );
};

export default BlockTable;
