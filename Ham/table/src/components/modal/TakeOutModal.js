import styles from "./takeOutModal.module.scss";
import { useState } from "react";
import gogiRestaurant from "../../assets/gogiRestaurant.png";
import add from "../../assets/add.png";
import minus from "../../assets/minus.png";

const TakeOutModal = ({ setIsOpen }) => {
  const [count, setCount] = useState(0);

  return (
    <div className={styles.modal_wrap}>
      <div className={styles.form}>
        <header>
          <h3>포장주문</h3>
        </header>
        <body className={styles.body_wrapper}>
          <img src={gogiRestaurant}></img>
          <div className={styles.body_info}>
            <p>[고기 • 구이]삼겹살집</p>
            <div className={styles.info_detail}>
              <span>삼겹살 (2인분) 32,000원</span>
              <img src={minus} onClick={() => setCount(count - 1)}></img>
              {count}
              <img src={add} onClick={() => setCount(count + 1)}></img>
            </div>
            <div className={styles.info_detail}>
              <span>삼겹살 (2인분) 32,000원</span>
              <img src={minus} onClick={() => setCount(count - 1)}></img>
              {count}
              <img src={add} onClick={() => setCount(count + 1)}></img>
            </div>
            <div className={styles.info_detail}>
              <span>삼겹살 (2인분) 32,000원</span>
              <img src={minus} onClick={() => setCount(count - 1)}></img>
              {count}
              <img src={add} onClick={() => setCount(count + 1)}></img>
            </div>
            <div className={styles.info_detail}>
              <span>삼겹살 (2인분) 32,000원</span>
              <img src={minus} onClick={() => setCount(count - 1)}></img>
              {count}
              <img src={add} onClick={() => setCount(count + 1)}></img>
            </div>
            <div className={styles.info_detail}>
              <span>삼겹살 (2인분) 32,000원</span>
              <img src={minus} onClick={() => setCount(count - 1)}></img>
              {count}
              <img src={add} onClick={() => setCount(count + 1)}></img>
            </div>
          </div>
        </body>
        <footer className={styles.footer_wrapper}>
          <div className={styles.button_wrapper}>
            <button
              className={styles.cancle_button}
              onClick={() => setIsOpen(false)}
            >
              취소
            </button>
            <button
              className={styles.confirm_button}
              onClick={() => setIsOpen(false)}
            >
              등록
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default TakeOutModal;
