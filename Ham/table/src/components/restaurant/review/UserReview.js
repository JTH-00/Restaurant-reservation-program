import styles from "./userReview.module.scss";
import reviewMark from "../../../assets/reviewMark.png";
import useDateInfo from "../../../hooks/dayHook";
import moment from "moment";

const UserReview = ({ restaurantReview }) => {
  // const reviewDate = restaurantReview.date;
  // let myDate = new Date(reviewDate);

  // const writeDay = {
  //   year: myDate.getFullYear(),
  //   month: myDate.getMonth(),
  //   day: myDate.getDate(),
  // };

  // const month =
  //   String(writeDay.month).length == 1 ? "0" + String(writeDay.month) : null;
  // const day =
  //   String(writeDay.month).length == 1 ? "0" + String(writeDay.day) : null;
  // console.log(reviewDate);
  let time = restaurantReview.content.map((e) => {
    const reviewTime = moment(e.date).format("YYYY-MM-DD");
    return (
      <div className={styles.reviewBox_wrapper}>
        <div className={styles.userReview}>
          <img src={reviewMark}></img>
          <p>{e.content}</p>
        </div>
        <div className={styles.userDetail}>
          <h3>{e.user.username}</h3>
          <p>{reviewTime}</p>
        </div>
      </div>
    );
  });

  return <div>{time}</div>;
};
export default UserReview;
