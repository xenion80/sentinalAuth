import { Link } from "react-router-dom";

export default function Home() {

    return (

        <div>

            <h1>SentinalAuth</h1>

            <Link to="/auth/login">
                Login
            </Link>

            <br />

            <Link to="/auth/register">
                Register
            </Link>
            <br />
            <Link to="/auth/forgot-password">
                Forgot Password
            </Link>


        </div>
    );
}