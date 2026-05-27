import { useState } from "react";
import { forgotPassword } from "../services/authService";

export default function ForgotPassword() {

    const [email, setEmail] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await forgotPassword(email);

            alert("Reset email sent");

        } catch (err) {

    console.log(err.response?.data);

    alert(
        err.response?.data?.message ||
        "Forgot password failed"
    );
}
    };

    return (
        <div>

            <h1>Forgot Password</h1>

            <form onSubmit={handleSubmit}>

                <input
                    type="email"
                    placeholder="Email"
                    onChange={(e) => setEmail(e.target.value)}
                />

                <button type="submit">
                    Send Reset Link
                </button>

            </form>

        </div>
    );
}