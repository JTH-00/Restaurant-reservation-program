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
  const [quantity, setQuantity] = useState(initialQuantity);

  useEffect(() => {}, []);

  const addCountingQuantity = async (e) => {
    try {
      const response = await cartCountHook(1, cartmenuid);
      setQuantity(quantity + 1);
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  };
  const minusCountingQuantity = async (e) => {
    try {
      const response = await cartCountHook(0, cartmenuid);
      setQuantity(quantity - 1);
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  };

  const deleteCard = async () => {
    console.log(1);
    try {
      const response = await cartMenuDeleteHook(cartmenuid);
    } catch (err) {
      console.error(err);
    }
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
        <h2>{quantity}</h2>
        <img
          src={add}
          type="button"
          name="add"
          onClick={() => addCountingQuantity()}
          style={{ width: "24px", height: "24px" }}
        ></img>
        <span>{quantity * price.toLocaleString()}원</span>
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
