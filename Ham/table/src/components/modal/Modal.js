import React, { useState, useEffect } from "react";

import styles from "./modal.module.scss";
import axios from "axios";
import goldStar from "../../assets/goldStar.png";
import grayStar from "../../assets/grayStar.png";

const Modal = ({ modalData, setIsOpen }) => {
  const [status, setStatus] = useState(modalData[0]);
  const [input, setInput] = useState("");
  const [showImages, setShowImages] = useState([]);
  const [rating, setRating] = useState(0);

  const date = new Date();

  const a = [
    [modalData[5], "음식의 맛"],
    [modalData[5], "실제 음식의 사진"],
    [modalData[5], "매장의 분위기"],
  ];

  const token = localStorage.getItem("token");
  const onChangeHandle = (e) => {
    setInput(e.target.value);
  };

  const handleStarClick = (index) => {
    setRating(index + 1);
  };
  // 이미지 상대경로 저장
  const handleAddImages = (event) => {
    const imageLists = event.target.files;
    let imageUrlLists = [...showImages];

    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length > 10) {
      imageUrlLists = imageUrlLists.slice(0, 10);
    }

    setShowImages(imageUrlLists);
  };

  const postReview = async (e) => {
    const formData = new FormData();

    try {
      const jsonData = {
        content: input,
        scope: rating,
      };
      formData.append(
        "reviewDto",
        new Blob([JSON.stringify(jsonData)], { type: "application/json" })
      );

      if (showImages.length > 0) {
        for (const file of showImages) {
          console.log(file);
          formData.append("files", [file]);
        }
      }
      const response = await axios.post(
        "/api/user/mypage/use/write/review",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/file",
          },
          params: { paymentid: "1" },
        }
      );
      console.log(response.data);
    } catch (err) {
      console.log(formData);
      console.error(err);
    }
  };
  const postReport = async () => {
    const formData = new FormData();
    const jsonData = {
      content: input,
    };

    formData.append(
      "reportRestaurantDto",
      new Blob([JSON.stringify(jsonData)], { type: "application/json" })
    );
    if (showImages.length > 0) {
      for (const file of showImages) {
        formData.append("files", file);
      }
    }

    try {
      const response = await axios.post(
        "/api/user/report/restaurant/1",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className={styles.modal_wrap}>
      <div
        className={styles.modal_detail}
        style={{ width: modalData[3], height: modalData[4] }}
      >
        <header className={styles.header_wrap}>
          <p>{modalData[0]} 작성</p>
        </header>
        <div className={styles.body_wrapper}>
          <div className={styles.line}></div>
          <div className={styles.restaurant}>
            <img src={modalData[5]}></img>
            <span>[{modalData[1]}]</span>
            <span>{modalData[2]}</span>
          </div>
          <div className={styles.line}></div>
          {modalData[0] == "신고" ? (
            <div>
              <div className={styles.info_header}>
                <h3>{modalData[0]}작성 은 이렇게 써주세요</h3>
              </div>
              <div className={styles.info_body}>
                <div className={styles.info_text}>
                  <p>
                    매장의 불편을 느꼈던
                    <em>음식의 맛 • 매장의 서비스 • 매장의 분위기</em>
                    등을 설명해주세요
                  </p>
                </div>
              </div>
              <div className={styles.infoImg_wrapper}>
                {a.map((e) => {
                  return (
                    <div className={styles.infoImg}>
                      <img src={e[0]}></img>
                      <p>{e[1]}</p>
                    </div>
                  );
                })}
              </div>
            </div>
          ) : (
            //리뷰모달
            <div>
              <div className={styles.info_header}>
                <h3>{modalData[0]}는 이렇게 써주세요</h3>
              </div>
              <div className={styles.info_body}>
                <div className={styles.info_text}>
                  <p>
                    <em style={{ color: "#ff3114" }}>
                      음식의 맛 • 매장의 서비스 • 매장의 분위기
                    </em>
                    등을 설명해주세요 좋았던 점, 아쉬웠던 점도 솔직하게
                    얘기해주세요
                  </p>
                </div>
              </div>
              <div className={styles.infoImg_wrapper}>
                {a.map((e) => {
                  return (
                    <div className={styles.infoImg}>
                      <img src={e[0]}></img>
                      <p>{e[1]}</p>
                    </div>
                  );
                })}
              </div>
            </div>
          )}
          {status === "리뷰" && (
            <div className={styles.star_wrapper}>
              {[...Array(5)].map((_, index) => (
                <img
                  key={index}
                  src={index < rating ? goldStar : grayStar}
                  style={{ width: "20px", height: "20px", cursor: "pointer" }}
                  onClick={() => handleStarClick(index)}
                />
              ))}
            </div>
          )}

          <div className={styles.userInput_wrapper}>
            <p>{status} 내용</p>
            <textarea
              onChange={onChangeHandle}
              className={styles.user_input}
            ></textarea>
          </div>
          <div className={styles.userInput_wrapper}>
            <p>사진첨부</p>
            <label htmlFor="input-file" onChange={handleAddImages}>
              <input type="file" id="input-file" multiple />
            </label>
          </div>
          <div></div>
          <div className={styles.imgInfo_wrapper}>
            <p>•사진은 최대 8장까지, 30MB 이하의 이미지만 업로드 가능합니다.</p>
            <p>
              •상품과 무관하거나,반복되는 동일 단어/문장을 사용하여 후기로 볼 수
              없는 글, 판매자와 고객의 후기 이용을 방해한다고 판단되는 경우,
              구매 음식을 구분할 수 없는 전체 사진, 화면캡쳐, 음란 및
              부적절하거나 불법적인 내용은 통보없이 삭제될 수 있습니다.
            </p>
            <p>
              •전화번호, 이메일, 주소, 계좌번호 등 개인정보가 노출되지 않도록
              주의해주세요.
            </p>
          </div>
        </div>
        <footer className={styles.footer_wrap}>
          <button
            className={styles.cancle_button}
            onClick={(e) => setIsOpen(false)}
          >
            취소
          </button>
          <button
            className={styles.confirm_button}
            formEncType="multipart/form-data"
            onClick={status === "신고" ? postReport : postReview}
          >
            등록
          </button>
        </footer>
      </div>
    </div>
  );
};

export default Modal;
