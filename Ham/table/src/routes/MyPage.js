import styles from "./myPage.module.scss";
import { useParams } from "react-router-dom";
import React, { useState, useEffect } from "react";

import arrow from "../assets/arrow.png";
import PasswordModal from "../components/modal/PasswordModal";
import axios from "axios";
import { myPageHook } from "../hooks/myPage";
import { useUser } from "../context/AuthContext";
import FavoriteRest from "../components/mypage/FavoriteRest";
import ReviewList from "../components/mypage/ReviewList";

const MyPage = () => {
  const [isItem, setIsItem] = useState(false);
  const [isReviewItem, setIsReviewItem] = useState(false);
  const [activeButton, setActiveButton] = useState("찜한 매장");
  const [confirmUser, setConfirmUser] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [myEmail, setMyEmail] = useState("");
  const [myName, setMyName] = useState("");
  const [myPhone, setMyPhone] = useState("");

  const [input, setInput] = useState({
    userPw: "",
    username: "",
    phone: "",
  });
  const { userPw, username, phone } = input;
  const { userId } = useParams();
  const { confirmUserHook } = myPageHook();
  const { updateUser } = useUser();

  const token = localStorage.getItem("token");

  const checkInput = (e) => {
    const { name, value } = e.target;
    setInput({
      ...input,
      [name]: value,
    });
  };

  const confirmHandle = async () => {
    const response = await confirmUserHook(userPw);
    console.log(response);
    setConfirmUser(true);
    getUserData();
  };

  const updateHandle = () => {
    updateUser(username, phone);
  };

  const getUserData = async () => {
    try {
      const response = await axios.get("/api/user/user", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMyEmail(response.data.useremail);
      setMyName(response.data.username);
      setMyPhone(response.data.phone);
    } catch (err) {
      console.error(err);
    }
  };

  const handleClick = (buttonName) => {
    setActiveButton(buttonName);
  };

  return (
    <div className={styles.form}>
      <div className={styles.myPage_wrapper}>
        <aside className={styles.aside_wrapper}>
          <h1>마이페이지</h1>
          <div className={styles.category_wrapper}>
            <button
              className={activeButton === "찜한 매장" ? styles.active : ""}
              onClick={() => handleClick("찜한 매장")}
            >
              <span style={{ marginLeft: "10px" }}>찜한 매장</span>
              <img
                style={{ marginRight: "10px" }}
                src={arrow}
                alt="arrow"
              ></img>
            </button>
          </div>
          <div className={styles.category_wrapper}>
            <button
              className={activeButton === "리뷰 내역" ? styles.active : ""}
              onClick={() => handleClick("리뷰 내역")}
            >
              <span style={{ marginLeft: "10px" }}>리뷰 내역</span>
              <img
                style={{ marginRight: "10px" }}
                src={arrow}
                alt="arrow"
              ></img>
            </button>
          </div>
          <div className={styles.category_wrapper}>
            <button
              className={activeButton === "개인 정보 수정" ? styles.active : ""}
              onClick={() => handleClick("개인 정보 수정")}
            >
              <span style={{ marginLeft: "10px" }}>개인 정보 수정</span>
              <img
                style={{ marginRight: "10px" }}
                src={arrow}
                alt="arrow"
              ></img>
            </button>
          </div>
        </aside>
        <div className={styles.myPageInfo_wrapper}>
          {activeButton == "개인 정보 수정" ? (
            <>
              <p>{activeButton}</p>
              <div className={styles.line}></div>
            </>
          ) : (
            <>
              <p>{activeButton} 목록 (0)</p>
              <div className={styles.line}></div>
            </>
          )}

          {activeButton == "찜한 매장" && (
            <div>
              <FavoriteRest />
            </div>
          )}
          {activeButton == "리뷰 내역" && (
            <div>
              <ReviewList />
            </div>
          )}
          {activeButton == "개인 정보 수정" && (
            <div>
              {confirmUser === false ? (
                <div className={styles.updateUser_wrapper}>
                  <h2>비밀번호 재확인</h2>
                  <p>
                    회원님의 정보를 안전하게 보호하기 위해 비밀번호를 다시 한번
                    확인해주세요.
                  </p>
                  <input
                    name="userPw"
                    onChange={checkInput}
                    placeholder="비밀번호를 입력해주세요."
                  ></input>
                  <button onClick={(e) => confirmHandle()}>확인</button>
                </div>
              ) : (
                <div className={styles.infoUpdate}>
                  <div className={styles.userInfo_wrapper}>
                    <p>email</p>
                    <span>{myEmail}</span>
                  </div>
                  <div className={styles.userInfo_wrapper}>
                    <p>비밀번호</p>
                    <button onClick={(e) => setModalOpen(true)}>
                      비밀번호 변경
                    </button>
                  </div>
                  <div className={styles.userInfo_wrapper}>
                    <p>이름</p>
                    <input
                      name="username"
                      onChange={checkInput}
                      placeholder={myName}
                    ></input>
                  </div>
                  <div className={styles.userInfo_wrapper}>
                    <p>전화번호</p>
                    <input
                      name="phone"
                      onChange={checkInput}
                      placeholder={myPhone}
                    ></input>
                  </div>
                  <button
                    className={styles.update_button}
                    onClick={(e) => updateHandle()}
                  >
                    회원정보 수정
                  </button>
                </div>
              )}
            </div>
          )}
          {modalOpen && <PasswordModal setModalOpen={setModalOpen} />}
        </div>
      </div>
    </div>
  );
};

export default MyPage;
