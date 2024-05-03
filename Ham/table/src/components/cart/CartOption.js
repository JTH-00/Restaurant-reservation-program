import { useState } from "react";
import styles from "../../routes/cart.module.scss";

const CartOption = ({ totalPrice, cardId, takeoutHook, userId }) => {
  const takeout = async () => {
    const response = await takeoutHook(cardId, userId);
    console.log(response);
  };

  return (
    <div className={styles.rightSide_wrapper}>
      <div className={styles.option_wrapper}>
        <div className={styles.inner_wrapper}>
          <span>총 주문금액</span>
          <span style={{ marginRight: "25px" }}>
            {totalPrice.toLocaleString()}원
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
            {totalPrice.toLocaleString()}원
          </span>
        </div>
        <button onClick={() => takeout()}>결제하기</button>
      </div>
    </div>
  );
};

export default CartOption;
