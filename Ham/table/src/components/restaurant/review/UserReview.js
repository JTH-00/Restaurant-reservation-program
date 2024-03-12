import styles from "./userReview.module.scss";
import reviewMark from "../../../assets/reviewMark.png";

const UserReview = ({ restaurantReview }) => {
  const reviewDate = restaurantReview.date;
  let myDate = new Date(reviewDate);

  const writeDay = {
    year: myDate.getFullYear(),
    month: myDate.getMonth(),
    day: myDate.getDate(),
  };
  const month =
    String(writeDay.month).length == 1 ? "0" + String(writeDay.month) : null;
  const day =
    String(writeDay.month).length == 1 ? "0" + String(writeDay.day) : null;
  console.log(reviewDate);

  return (
    <div className={styles.reviewBox_wrapper}>
      <div className={styles.userReview}>
        <img src={reviewMark}></img>
        <p>{restaurantReview.review}</p>
      </div>
      <div className={styles.userDetail}>
        <h3>{restaurantReview.username}</h3>
        <p>
          {writeDay.year}-{month}-{day}
        </p>
      </div>
    </div>
  );
};
export default UserReview;
