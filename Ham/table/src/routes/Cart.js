import React, { useState } from "react";

import styles from "./cart.module.scss";
import cart from "../assets/cart.png";
import gogiRestaurant from "../assets/gogiRestaurant.png";
import add from "../assets/add.png";
import minus from "../assets/minus.png";
import x from "../assets/X.png";
const Cart = () => {
  const [isContain, setIsContain] = useState(true);
  const [count, setCount] = useState(1);
  const [price, setPrice] = useState(23000);

  let no = 1231232;
  let a = no.toLocaleString();
  console.log(a);

  return (
    <div className={styles.form}>
      <div className={styles.cart_header}>
        <h1>장바구니</h1>
        <div className={styles.line}></div>
      </div>
      <div className={styles.cart_body}>
        <div className={styles.aside_wrapper}>
          {isContain === false ? (
            <div className={styles.none_wrapper}>
              <img src={cart}></img>
              <p>장바구니가 비었습니다.</p>
              <button>쇼핑하러 가기</button>
            </div>
          ) : (
            <div className={styles.contain_wrapper}>
              <input type="checkbox" id="check_btn" />
              <img src={gogiRestaurant}></img>
              <h1>삼겹살200g (1인분)</h1>
              <img
                className={styles.btn}
                src={minus}
                type="button"
                onClick={() => setCount(count - 1)}
                style={{ width: "24px", height: "24px" }}
              ></img>
              <h2>{count}</h2>
              <img
                src={add}
                type="button"
                onClick={() => setCount(count + 1)}
                style={{ width: "24px", height: "24px" }}
              ></img>
              <span>{price.toLocaleString()}원</span>
              <img src={x} style={{ width: "16px", height: "16px" }}></img>
            </div>
          )}
        </div>
        <div className={styles.rightSide_wrapper}>
          <div className={styles.option_wrapper}>
            <div className={styles.inner_wrapper}>
              <span>총 주문금액</span>
              <span style={{ marginRight: "25px" }}>
                {price.toLocaleString()}원
              </span>
            </div>
            <div className={styles.inner_wrapper}>
              <span>할인 금액</span>
              <span style={{ marginRight: "25px" }}>0원</span>
            </div>
            <div className={styles.inner_wrapper}>
              <span>배송비</span>
              <span style={{ marginRight: "25px" }}>0원</span>
            </div>
          </div>
          <div className={styles.total_wrapper}>
            <div className={styles.inner_wrapper}>
              <span>총 결제 금액</span>
              <span style={{ marginRight: "25px" }}>
                {price.toLocaleString()}원
              </span>
            </div>
            <button>결제하기</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;
