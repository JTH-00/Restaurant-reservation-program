import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./component/layout/Layout";
import Main from "./routes/Main";
import BoardDetail from "./routes/BoardDetail";
import BoradWrite from "./routes/BoardWrite";
import Approved from "./routes/Approved";
import Block from "./routes/Block";
import Report from "./routes/Report";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      { path: "/", element: <Main /> },
      { path: "/board/inform/:boardId", element: <BoardDetail /> },
      { path: "/board/write", element: <BoradWrite /> },
      { path: "/approved", element: <Approved /> },
      { path: "/block", element: <Block /> },
      { path: "/report", element: <Report /> },
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
