import Navbar from "./NavBar";
import { Outlet } from "react-router-dom";
import Footer from "./Footer";

const Layout = () => {
  return (
    <>
      <Navbar />
      <div className="layout">
        <Outlet />
      </div>
      <Footer />
    </>
  );
};

export default Layout;
