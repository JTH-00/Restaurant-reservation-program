import styles from "./myPage.module.scss";
import { useParams } from "react-router-dom";
import React, { useState } from "react";

import gogiRestaurant from "../assets/gogiRestaurant.png";
import goldStar from "../assets/goldStar.png";
import warning from "../assets/warning.png";
import arrow from "../assets/arrow.png";
import PasswordModal from "../components/modal/PasswordModal";

const MyPage = () => {
  const [isItem, setIsItem] = useState(false);
  const [isReviewItem, setIsReviewItem] = useState(false);
  const [activeButton, setActiveButton] = useState("찜한 매장");
  const [confirmUser, setConfirmUser] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);

  const { userId } = useParams();

  const handleClick = (buttonName) => {
    setActiveButton(buttonName);
  };

  console.log(activeButton);

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
              {isItem == true ? (
                <div className={styles.noneItem_wrapper}>
                  <img src={warning}></img>
                  <p>{activeButton}이 존재하지 않습니다</p>
                </div>
              ) : (
                <div className={styles.ddip_card}>
                  <img
                    src={gogiRestaurant}
                    style={{
                      marginLeft: "15px",
                      marginTop: "20px",
                      width: "100px",
                      height: "100px",
                    }}
                  ></img>
                  <div className={styles.card_content}>
                    <h3>삼겹살집</h3>
                    <p>
                      <img
                        src={goldStar}
                        style={{ width: "16px", height: "16px" }}
                      ></img>
                      4.9 (13)
                    </p>
                    <span>경기도 부천시 원미구 심곡동 부일로 442</span>
                  </div>
                  <div className={styles.btn_wrapper}>
                    <button className={styles.delete}>삭제</button>
                    <button className={styles.order}>주문하기</button>
                  </div>
                </div>
              )}
            </div>
          )}
          {activeButton == "리뷰 내역" && (
            <div>
              {isReviewItem == false ? (
                <div className={styles.noneItem_wrapper}>
                  <img src={warning}></img>
                  <p>{activeButton}이 존재하지 않습니다</p>
                </div>
              ) : (
                <div className={styles.ddip_card}>
                  <img
                    src={gogiRestaurant}
                    style={{
                      marginLeft: "15px",
                      marginTop: "20px",
                      width: "100px",
                      height: "100px",
                    }}
                  ></img>
                  <div className={styles.card_content}>
                    <h3>삼겹살집</h3>
                    <div style={{ display: "flex" }}>
                      <img
                        src={goldStar}
                        style={{ width: "16px", height: "16px" }}
                      ></img>
                      <img
                        src={goldStar}
                        style={{ width: "16px", height: "16px" }}
                      ></img>
                      <img
                        src={goldStar}
                        style={{ width: "16px", height: "16px" }}
                      ></img>
                      <img
                        src={goldStar}
                        style={{ width: "16px", height: "16px" }}
                      ></img>
                    </div>

                    <span>리뷰내용</span>
                  </div>
                  <div className={styles.btn_wrapper}>
                    <button className={styles.update}>리뷰 수정</button>
                    <button className={styles.delete}>리뷰 삭제</button>
                  </div>
                </div>
              )}
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
                  <input placeholder="비밀번호를 입력해주세요."></input>
                  <button onClick={(e) => setConfirmUser(true)}>확인</button>
                </div>
              ) : (
                <div className={styles.infoUpdate}>
                  <div className={styles.userInfo_wrapper}>
                    <p>email</p>
                    <span>fake@email.com</span>
                  </div>
                  <div className={styles.userInfo_wrapper}>
                    <p>비밀번호</p>
                    <button onClick={(e) => setModalOpen(true)}>
                      비밀번호 변경
                    </button>
                  </div>
                  <div className={styles.userInfo_wrapper}>
                    <p>이름</p>
                    <input placeholder="함승완"></input>
                  </div>
                  <div className={styles.userInfo_wrapper}>
                    <p>전화번호</p>
                    <input placeholder="전화번호"></input>
                  </div>
                  <button className={styles.update_button}>
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
