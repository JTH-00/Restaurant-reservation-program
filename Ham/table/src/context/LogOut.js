import { useUser } from "./AuthContext";

const Logout = () => {
  const { user, updateUser } = useUser();

  const handleLogOut = (e) => {
    updateUser(null);
  };

  return (
    <div>
      <button onClick={(e) => handleLogOut()}>로그아웃</button>
    </div>
  );
};

export default Logout;
