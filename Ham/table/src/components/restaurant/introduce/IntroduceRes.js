import styles from "./introduceRes.module.scss";
import arrow from "../../../assets/arrow.png";
import copy from "../../../assets/copy.png";
import KakaoMap from "../../KakaoMap";

const IntroduceRes = ({ restaurantDetail }) => {
  const handleCopyClipBoard = async (text) => {
    try {
      await navigator.clipboard.writeText(text);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className={styles.introduce_wrapper}>
      <div className={styles.line}></div>
      <div className={styles.miniInfo_wrapper}>
        <span>매장소개</span>
        <p>24시간 영업하는 고깃집 입니다.</p>
        <p>매장 내 금연</p>
      </div>

      <div className={styles.miniInfo_wrapper}>
        <span>매장소개</span>
        <p>예약일 기준 2일전 100% 환불</p>
        <p>예약일 기준 1일전 50% 환불</p>
        <p>예약일 기준 당일 환불 불가</p>
      </div>

      <div className={styles.miniInfo_wrapper}>
        <span>원산지</span>
        <p>삼겹살 : 미국산</p>
        <p>목살 : 미국산</p>
        <p>대패삼겹살 : 미국산</p>
      </div>
      <div className={styles.line}></div>

      <div className={styles.ceoInfo_wrapper}>
        <button>
          <h4>판매자 정보</h4>
          <img src={arrow}></img>
        </button>
      </div>
      <div className={styles.line}></div>
      <span>위치</span>
      <KakaoMap
        mapWidth={"1198px"}
        mapHeight={"478px"}
        restaurantAddress={restaurantDetail.detailAddress}
        restaurantName={restaurantDetail.restaurantName}
      />
      <div className={styles.addressInfo_wrapper}>
        <h5>{restaurantDetail.detailAddress}</h5>
        <button
          onClick={() =>
            handleCopyClipBoard(`${restaurantDetail.detailAddress}`)
          }
        >
          <img src={copy}></img>주소복사
        </button>
      </div>
      <div className={styles.takeTime}>
        <p>부천역 도보 10분</p>
        <p>부천역 차량 5분</p>
      </div>
    </div>
  );
};

export default IntroduceRes;
