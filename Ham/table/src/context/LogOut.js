import axios from "axios";
import { useUser } from "./AuthContext";

const Logout = () => {
  const { logoutUser } = useUser();

  return (
    <div>
      <button onClick={(e) => logoutUser(e)}>로그아웃</button>
    </div>
  );
};

export default Logout;
