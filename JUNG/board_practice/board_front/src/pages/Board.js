import Paging from "../components/paging";
import BoardList from "../components/boardlist";
import Menu from "../components/menu";
import NavBar from "../components/navbar";
import styles from "./Board.module.css";

const Board = () => {
  return (
    <div className={styles.board}>
      <NavBar />
      <Menu />
      <BoardList />
      <Paging />
    </div>
  );
};

export default Board;