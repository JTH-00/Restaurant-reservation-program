import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./component/layout/Layout";
import Main from "./routes/Main";
import SignUp from "./routes/Signup";
import Login from "./routes/Login";
import StoreManage from "./routes/StoreManage";
import ReviewManange from "./routes/ReviewManage";
import Alarm from "./routes/Alarm";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      { path: "/", element: <Main /> },
      { path: "/signUp", element: <SignUp /> },
      { path: "/login", element: <Login /> },
      { path: "/storeManage", element: <StoreManage /> },
      { path: "/reviewManage", element: <ReviewManange /> },
      { path: "/alarm", element: <Alarm /> },
    ],
  },
]);

function App() {
  return (
    <div>
      <RouterProvider router={router}></RouterProvider>
    </div>
  );
}

export default App;
