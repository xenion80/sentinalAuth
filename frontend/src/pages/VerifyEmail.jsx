import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { verifyEmail } from "../services/authService";

export default function VerifyEmail() {

    const [searchParams] = useSearchParams();

    useEffect(() => {

        const token = searchParams.get("token");

        verifyEmail(token)
            .then(() => {
                alert("Email verified");
            })
            .catch((err) => {

    console.log(err.response?.data);

    alert(
        err.response?.data ||
        "Verification failed"
    );
});

    }, []);

    return (
        <h1>Verifying Email...</h1>
    );
}