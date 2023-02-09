import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import {ServerContext} from "../context/ServerContext";
import LoginModal from "./LoginModal";
import {LoginSuccessContext, LoginSuccessContextType} from "../context/LoginSuccessContext";
import {Link} from "react-router-dom";
import {Alert, Snackbar} from "@mui/material";
import Constants from "../constants/Constants";

/*setCookie("token", response.headers.authorization, {
    path: "/",
    expires: new Date(2030, 1, 1)
})*/
// const [cookies, setCookie, removeCookie] = useCookies(["token"]);

export const Login = (props) => {
    const [userName, setUsername] = useState("");
    const [passWord, setPassword] = useState("");
    const server = useContext(ServerContext);
    const {loggedIn, setLoggedIn, modalOpen, setModalOpen} =
        useContext(LoginSuccessContext) as LoginSuccessContextType;
    const [error, showError] = useState(false)

    /// const [modalOpen, setModalOpen] = useState(false);


    useEffect(() => {
        if (loggedIn) {
            console.log(loggedIn + " already logged in , redirecting!");
            setModalOpen(true);
        }
    }, [loggedIn]);

    function validateForm() {
        return userName.length > 0 && passWord.length > 0;
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        axios({
            method: "post",
            url: `${server}/login`,
            data: {
                username: userName,
                password: passWord,
            },
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                console.log("Login sent!");
                console.log(response);
                console.log(
                    "JWT TOKEN :" + JSON.stringify(response.headers.authorization)
                );
                localStorage.setItem("TokenJWT", response.headers.authorization);
                console.log(localStorage.getItem("TokenJWT"));
                setModalOpen(true);
                setLoggedIn(true);
            })
            .catch((err) => {
                console.log(err);
                showError(true)
            });
    };

    const closeError = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }

        showError(false);
    };

    return (
        <>
            <Snackbar
                open={error}
                autoHideDuration={Constants.HIDE_DURATION}
                onClose={closeError}
            >
                <Alert severity="error"> Login failed </Alert>
            </Snackbar>

            <div className="container mt-5 ">
                <form className="g-3 needs-validation " onSubmit={handleSubmit}>
                    <div className="row">
                        <div className="col-md-4 mx-auto">
                            <label className="form-label" htmlFor="username">
                                Username or EMail
                            </label>
                            <input
                                autoFocus
                                id="username"
                                className="form-control"
                                type="text"
                                value={userName}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4 mx-auto">
                            <label className="form-label" htmlFor="password">
                                Password
                            </label>
                            <input
                                id="password"
                                className="form-control"
                                type="password"
                                value={passWord}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4 d-grid gap-2 d-md-block mx-auto mt-2">
                            <button
                                className="btn btn-primary px-4"
                                type="submit"
                                disabled={!validateForm()}
                            >
                                Login
                            </button>

                            <Link
                                className="btn btn-primary float-end px-4"
                                to={`/signUp`}
                                role="button"
                            >
                                Sign Up
                            </Link>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4 mx-auto mt-2">
                            <Link className="text-decoration-none" to="/passwordResetRequest">
                                Forgot Password?
                            </Link>
                        </div>
                    </div>
                </form>
                <LoginModal history={props.history}/>
            </div>
        </>
    );
};
