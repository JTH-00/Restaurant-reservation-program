import styles from "../../routes/cart.module.scss";
import gogiRestaurant from "../../assets/gogiRestaurant.png";
import minus from "../../assets/minus.png";
import add from "../../assets/add.png";
import x from "../../assets/X.png";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const Card = ({
  resName,
  menuName,
  initialQuantity,
  price,
  setTotalPrice,
  cartmenuid,
  cartMenuDeleteHook,
  cartCountHook,
}) => {
  const addCountingQuantity = async (e) => {
    const response = await cartCountHook(1, cartmenuid);
  };
  const minusCountingQuantity = async (e) => {
    if (initialQuantity > 1) {
      const response = await cartCountHook(0, cartmenuid);
    } else {
      return null;
    }
  };

  const deleteCard = async () => {
    const response = await cartMenuDeleteHook(cartmenuid);
  };

  return (
    <>
      <div className={styles.contain_wrapper}>
        <input type="checkbox" id="check_btn" />
        <img src={gogiRestaurant}></img>
        <h1>[{resName}]</h1>
        <h1>{menuName}</h1>
        <img
          className={styles.btn}
          src={minus}
          type="button"
          name="minus"
          onClick={() => minusCountingQuantity()}
          style={{ width: "24px", height: "24px" }}
        ></img>
        <h2>{initialQuantity}</h2>
        <img
          src={add}
          type="button"
          name="add"
          onClick={() => addCountingQuantity()}
          style={{ width: "24px", height: "24px" }}
        ></img>
        <span>{initialQuantity * price.toLocaleString()}원</span>
        <img
          type="button"
          onClick={() => deleteCard()}
          src={x}
          style={{ width: "16px", height: "16px" }}
        ></img>
      </div>
    </>
  );
};

export default Card;
