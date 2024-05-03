import DaumPostcodeEmbed from "react-daum-postcode";
import styles from "./addressModal.module.scss";
const postCodeStyle = {
  width: "400px",
  height: "400px",
}; // 스타일 정의 code

const AddressModal = ({ setIsOpen, getAddress }) => {
  const handleComplete = (data) => {
    console.log("data", data);
    getAddress(data.sigungu + " " + data.bname);
    setIsOpen(false);
  };
  return (
    <div className={styles.modal_wrap}>
      <header className={styles.header_wrap}>
        <h2>주소 입력</h2>
      </header>
      <div className={styles.location_wrap}>
        <DaumPostcodeEmbed style={postCodeStyle} onComplete={handleComplete} />
      </div>
      <footer className={styles.footer_wrap}>
        <button onClick={(e) => setIsOpen(false)}>취소</button>
      </footer>
    </div>
  );
};

export default AddressModal;
