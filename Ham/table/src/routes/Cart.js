import React, { useState, useEffect } from "react";

import styles from "./cart.module.scss";
import cart from "../assets/cart.png";
import { useCartHook } from "../hooks/cartHook";
import Card from "../components/cart/Card";
import CartOption from "../components/cart/CartOption";
import { useUser } from "../context/AuthContext";

const Cart = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [isContain, setIsContain] = useState(false);
  const [cartData, setCartData] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [cardId, setCartId] = useState("");

  const { userId } = useUser();
  const { getCartListHook, cartCountHook, cartMenuDeleteHook, takeoutHook } =
    useCartHook();

  useEffect(() => {
    setIsLoading(true);
    getList();
  }, []);

  // console.log(cardId);

  const getList = async () => {
    const response = await getCartListHook();
    setCartData(response.cartMenus);
    setTotalPrice(response.totalAmount);
    setCartId(response?.cartMenus[0]?.cart.cartid);
    setIsLoading(false);
    setIsContain(true);
  };
  // console.log(cartData);

  if (isLoading) {
    return <div>로딩중입니다</div>;
  }
  return (
    <>
      {!isLoading && (
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
                <>
                  {cartData?.map((e, i) => {
                    return (
                      <Card
                        key={i}
                        cartmenuid={e.cartmenuid}
                        resName={e.menu.restaurant.title}
                        menuName={e.menu.name}
                        initialQuantity={e.menucount}
                        price={e.menu.price}
                        setTotalPrice={setTotalPrice}
                        cartMenuDeleteHook={cartMenuDeleteHook}
                        cartCountHook={cartCountHook}
                      />
                    );
                  })}
                </>
              )}
            </div>
            <CartOption
              totalPrice={totalPrice}
              cardId={cardId}
              takeoutHook={takeoutHook}
              userId={userId}
            />
          </div>
        </div>
      )}
    </>
  );
};

export default Cart;
