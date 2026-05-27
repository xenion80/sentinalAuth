import { useSearchParams } from "react-router-dom";
import { useState } from "react";
import { resetPassword } from "../services/authService";

export default function ResetPassword() {

    const [searchParams] = useSearchParams();

    const token = searchParams.get("token");

    const [password, setPassword] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await resetPassword(token, password);

            alert("Password reset successful");

        } catch (err) {

    console.log(err.response?.data);

    alert(
        err.response?.data?.message ||
        "Reset failed"
    );
}
    };

    return (
        <div>

            <h1>Reset Password</h1>

            <form onSubmit={handleSubmit}>

                <input
                    type="password"
                    placeholder="New Password"
                    onChange={(e)=>setPassword(e.target.value)}
                />

                <button type="submit">
                    Reset Password
                </button>

            </form>

        </div>
    );
}