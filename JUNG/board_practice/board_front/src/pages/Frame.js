import Paging from "../components/paging";
import BoardList from "../components/boardlist";
import Menu from "../components/menu";
import NavBar from "../components/navbar";
import styles from "./Frame.module.css";

const Frame = () => {
  return (
    <div className={styles.frame}>
      <NavBar />
      <Menu />
      <BoardList />
      <Paging />
    </div>
  );
};

export default Frame;