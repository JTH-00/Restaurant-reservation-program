import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./restaurantList.module.scss";
import KakaoMap from "../components/KakaoMap";
import { categories, viveCategories } from "../contentData/categories";
import donkkasRestaurant from "../assets/donkkasRestaurant.png";
import goldstar from "../assets/goldStar.png";
import heart from "../assets/heart.png";
import { useUser } from "../context/AuthContext";
import AddressModal from "../components/modal/AddressModal";

const RestaurantList = () => {
  const [activeButton, setActiveButton] = useState("전체");
  const [viveActiveButton, setviveActiveButton] = useState("");
  const [resList, setResList] = useState([]);
  const [choiceAddress, setChoiceAddress] = useState("서울");
  const [isOpen, setIsOpen] = useState(false);

  const token = localStorage.getItem("token");

  const { userId } = useUser();

  useEffect(() => {
    getRes();
  }, []);

  const getRes = async () => {
    try {
      const response = await axios.get("/api/user/restaurant/list", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(response.data, 123123123);
      setResList(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const ddipRes = async (restaurantid) => {
    try {
      const response = await axios.post(
        `/api/user/restaurant/list/favorite/${restaurantid}`,
        { restaurantid: 1 },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getAddress = (e) => {
    setChoiceAddress(e);
  };

  const handleClick = (buttonName) => {
    setActiveButton(buttonName);
  };

  const viveClick = (buttonName) => {
    setviveActiveButton(buttonName);
  };

  const list = resList.map((e) => {
    return (
      <div className={styles.restaurant_card}>
        <img src={donkkasRestaurant} width={277} height={200}></img>
        <div className={styles.card_info}>
          <p>
            {e.category} • {e.address}
          </p>
          <h3>{e.title}</h3>
          <p>
            운영시간: {e.opentime} ~ {e.closetime}{" "}
          </p>
          <img
            width={20}
            height={20}
            src={goldstar}
            style={{ marginTop: "12px" }}
          ></img>
          <span style={{ fontSize: "14px", fontWeight: "bold" }}>4.5</span>
          <span>(6)</span>
          <p style={{ color: "black" }}> {e.content}</p>
        </div>
        <div>
          <img onClick={() => ddipRes(e.restaurantid)} src={heart}></img>
        </div>
      </div>
    );
  });

  return (
    <div className={styles.form}>
      <div className={styles.list_header}>
        <h1>{choiceAddress} 4개</h1>
      </div>
      <div className={styles.list_wrapper}>
        <aside className={styles.aside_wrapper}>
          {isOpen && (
            <AddressModal setIsOpen={setIsOpen} getAddress={getAddress} />
          )}
          <button
            onClick={(e) => setIsOpen(true)}
            className={styles.choice_address}
          >
            {choiceAddress}
          </button>
          <div className={styles.category_all}>
            <h3>카테고리</h3>
            <div
              className={
                activeButton === "전체"
                  ? styles.active
                  : styles.category_wrapper
              }
            >
              <button name="전체" onClick={() => handleClick("전체")}></button>
              <span>전체</span>
            </div>
            {categories.map((e) => {
              return (
                <div
                  className={
                    activeButton == e ? styles.active : styles.category_wrapper
                  }
                >
                  <button name={e} onClick={() => handleClick(e)}></button>
                  <span>{e}</span>
                </div>
              );
            })}
          </div>

          <div className={styles.vive_all}>
            <h3>분위기</h3>
            <div className={styles.vive_wrapper}>
              {Array(Math.ceil(viveCategories.length / 3)) // 3개씩 묶어서 줄 수 계산
                .fill()
                .map((e, index) => (
                  <div key={index} className={styles.vive_row}>
                    {viveCategories
                      .slice(index * 3, (index + 1) * 3)
                      .map((category) => (
                        <button
                          className={
                            viveActiveButton === category
                              ? styles.active_vive
                              : styles.default_vive
                          }
                          key={category}
                          name={category}
                          onClick={() => viveClick(category)}
                        >
                          {category}
                        </button>
                      ))}
                  </div>
                ))}
            </div>
          </div>
        </aside>
        <div className={styles.right_side}>
          {list}
          <div className={styles.line}></div>
        </div>
      </div>
    </div>
  );
};

export default RestaurantList;
