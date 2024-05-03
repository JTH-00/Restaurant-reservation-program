import React, { useState } from "react";
import { categories, viveCategories } from "../contentData/categories";
import styles from "./storeManage.module.scss";
import AddressModal from "../component/modal/AddressModal";
import add from "../assets/add.png";
import minus from "../assets/minus.png";
import AddMenu from "../component/storeManage/AddMenu";
const StoreManage = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [choiceAddress, setChoiceAddress] = useState("주소를 입력하세요");
  const [resCategories, setResCategories] = useState([]);
  const [cooktime, setCooktime] = useState(5);

  const [input, setInput] = useState({
    resName: "",
    resNumber: "",
    dayOff: "",
    intro: "",
  });

  const { resName, resNumber, dayOff, intro } = input;

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };
  const handleCategoriesChange = (updatedCategories) => {
    setResCategories(updatedCategories);
  };

  const getAddress = (e) => {
    setChoiceAddress(e);
  };

  const addClick = () => {
    setCooktime(cooktime + 5);
  };
  const minClick = () => {
    if (cooktime > 5) {
      setCooktime(cooktime - 5);
    }
  };

  const cooktimeOnChange = (e) => {
    const cooktime = Number(e.target.value);
    if (Number.isNaN(cooktime)) return;
    if (cooktime === 0) return;
    setCooktime(cooktime);
  };

  console.log("resCategories", 111, resCategories);

  return (
    <div className={styles.form}>
      <div className={styles.storeManage_header}>
        <h1>가게관리</h1>
      </div>
      <div className={styles.storeManage_body}>
        <div className={styles.input_wrapper}>
          <p>매장이름</p>
          <input
            name="resName"
            onChange={checkInput}
            placeholder="매장이름을 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>카테고리</p>
          <select>
            {categories.map((e) => {
              return <option>{e}</option>;
            })}
          </select>
        </div>
        <div className={styles.input_wrapper}>
          <p>가게 전화번호</p>
          <input
            name="resNumber"
            onChange={checkInput}
            placeholder="가게 전화번호를 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>도시</p>
          <button onClick={() => setIsOpen(true)}>{choiceAddress}</button>
          {isOpen && (
            <AddressModal setIsOpen={setIsOpen} getAddress={getAddress} />
          )}
        </div>
        <div className={styles.input_wrapper}>
          <p>분위기</p>
          <select>
            {viveCategories.map((e) => {
              return <option>{e}</option>;
            })}
          </select>
        </div>
        <div className={styles.input_wrapper}>
          <p>휴무일</p>
          <input
            name="dayOff"
            onChange={checkInput}
            placeholder="휴무일을 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>매장소개</p>
          <input
            name="intro"
            onChange={checkInput}
            placeholder="매장소개를 입력하세요"
          ></input>
        </div>
        <div className={styles.input_wrapper}>
          <p>조리시간</p>
          <div className={styles.cooktime_warpper}>
            <img src={minus} type="button" onClick={() => minClick()}></img>
            <input
              name="userPw"
              onChange={cooktimeOnChange}
              value={cooktime}
            ></input>
            <img src={add} type="button" onClick={() => addClick()}></img>
          </div>
        </div>
        <AddMenu
          setResCategories={setResCategories}
          handleCategoriesChange={handleCategoriesChange}
        />
      </div>
      <button className={styles.submit_button}>가게 등록</button>
    </div>
  );
};

export default StoreManage;
