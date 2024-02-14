import styles from "./BoardList.module.css";

const BoardList = () => {
  return (
    <div className={styles.boardlist}>
      <BoardItem
        title="[주의] 가짜 롯데온 사이트를 조심하세요!!"
        date="2023.04.10"
      />
      <BoardItem
        title="[롯데쇼핑] 전자금융거래 이용약관 개정 안내 (시행일 : 2023.11.06)"
        date="2023.10.05"
      />
      <BoardItem 
        title="[롯데쇼핑] 롯데오너스 롯데ON 무료배송쿠폰 적용 조건 일부 변경 안내"
        date="2023.07.30"
      />
      <BoardItem 
        title="[롯데쇼핑] 롯데ON 무료배송쿠폰 적용 조건 일부 변경 안내"
        date="2023.07.27"
      />
      <BoardItem 
        title="[롯데쇼핑] 구매회원 이용약관 개정 안내 (시행일 : 2023.07.01)"
        date="2023.05.16"
      />
      <BoardItem 
        title="[롯데ON] 롯데오너스 그린카 제휴 혜택 종료 안내"
        date="2023.03.23"
      />
      <BoardItem 
        title="[롯데마트/롯데슈퍼프레시] 정기배송 자동결제 서비스 제공자 및 정기과금 약관 변경 안내"
        date="2023.02.20"
      />
      <BoardItem 
        title="[롯데ON] 롯데오너스 정기결제대행사 변경 안내"
        date="2023.02.14"
      />
      <BoardItem 
        title="개인정보처리방침 개정 안내 (시행일 : 2024.01.18)"
        date="2024.01.17"
      />
      <BoardItem 
        title="개인정보처리방침 개정 안내 (시행일 : 2023.12.21)"
        date="2023.12.20"
      />
    </div>
  );
};

const BoardItem = ({ title, date }) => {
  return (
    <div className={styles.board}>
      <div className={styles.boardname}>{title}</div>
      <div className={styles.boarddate}>{date}</div>
      <div className={styles.line} />
    </div>
  );
};

export default BoardList;