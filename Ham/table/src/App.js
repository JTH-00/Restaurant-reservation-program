import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./components/layout/Layout";
import Main from "./routes/Main";
import Login from "./routes/Login";
import SignUp from "./routes/SignUp";
import Restaurant from "./routes/Restaurant";
import Cart from "./routes/Cart";
import MyPage from "./routes/MyPage";
import Use from "./routes/Use";
import Reserve from "./routes/Reserve";
import { AuthProvider } from "./context/AuthContext";
import Inform from "./routes/Inform";
import BoardDetail from "./routes/BoardDetail";
import RestaurantList from "./routes/RestaurantList";
import { CartProvider } from "./context/CartContext";
const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "/",
        element: <Main />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/signup",
        element: <SignUp />,
      },
      {
        path: "/restaurant/:id",
        element: <Restaurant />,
      },
      {
        path: "/cart/:user",
        element: (
          <CartProvider>
            <Cart />
          </CartProvider>
        ),
      },
      {
        path: "/myPage/:user",
        element: <MyPage />,
      },
      {
        path: "/mypage/use/:user",
        element: <Use />,
      },
      {
        path: "/mypage/reserve/:user",
        element: <Reserve />,
      },
      {
        path: "/inform",
        element: <Inform />,
      },
      {
        path: "/board/inform/:boardId",
        element: <BoardDetail />,
      },
      {
        path: "/board/event/:eventId",
        element: <BoardDetail />,
      },
      {
        path: "/restaurantList/:categoryId",
        element: <RestaurantList />,
      },
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
