import axios from "axios";
import { useUser } from "./AuthContext";

const Logout = () => {
  const { logoutUser } = useUser();

  return (
    <div>
      <button onClick={(e) => logoutUser(e)}>마감하기</button>
    </div>
  );
};

export default Logout;
