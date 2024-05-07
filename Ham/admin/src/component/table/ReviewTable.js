import styles from "./reviewTable.module.scss";
import dropdown from "../../assets/dropdown.png";

const ReviewTable = () => {
  return (
    <div className={styles.form}>
      <div className={styles.review_title}>
        <p style={{ width: "136.5px" }}>리뷰일자</p>
        <p style={{ width: "130px" }}>작성자</p>
        <p style={{ width: "633.5px" }}>리뷰내용</p>
      </div>
      <div className={styles.reviewDetail_wrapper}>
        <div className={styles.day}>20230217</div>
        <div className={styles.user}>함승완</div>
        <div className={styles.detail}>
          리뷰내용
          <img src={dropdown}></img>
        </div>
      </div>
      <div className={styles.answer_wrapper}>
        <h4>답글달기</h4>
        <textarea></textarea>
        <div className={styles.answer_btn}>
          <button>수정</button>
          <button>삭제</button>
        </div>
      </div>
    </div>
  );
};

export default ReviewTable;
