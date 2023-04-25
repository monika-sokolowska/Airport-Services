import LoginPage from "./pages/LoginPage/LoginPage";
import SharedLayout from "./pages/Dashboard/SharedLayout";
import Error from "./pages/Error/Error";
import ProtectedRoute from "./pages/ProtectedRoute/ProtectedRoute";
import { Provider } from "react-redux";
import { store } from "./store";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route
            index
            path="/"
            element={<Navigate replace to="/login" />}></Route>
          <Route index path="/login" element={<LoginPage />}></Route>
          <Route
            path="/home"
            element={
              <ProtectedRoute>
                <SharedLayout />
              </ProtectedRoute>
            }></Route>
          <Route path="*" element={<Error />}></Route>
        </Routes>
        <ToastContainer
          position="top-center"
          autoClose={1000}
          pauseOnHover={false}
          progress={undefined}
          hideProgressBar
        />
      </BrowserRouter>
    </Provider>
  );
}

export default App;
