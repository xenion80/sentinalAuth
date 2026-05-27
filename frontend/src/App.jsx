import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";
import VerifyEmail from "./pages/VerifyEmail";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/auth/login"
                    element={<Login />}
                />

                <Route
                    path="/auth/register"
                    element={<Register />}
                />

                <Route
                    path="/auth/forgot-password"
                    element={<ForgotPassword />}
                />

                <Route
                    path="/auth/reset-password"
                    element={<ResetPassword />}
                />

                <Route
                    path="/auth/verify-email"
                    element={<VerifyEmail />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;