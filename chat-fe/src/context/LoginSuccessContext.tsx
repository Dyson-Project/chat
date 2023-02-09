import React, {useContext, useEffect, useState} from "react";
import {ServerContext} from "./ServerContext";
import axios from "axios";

export type LoginSuccessContextType = {
    loggedIn: boolean;
    setLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
    customerFK
    setCustomerFK
    loginWithJWTSuccess
    setLoginWithJWTSuccess
    modalOpen: boolean;
    setModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export const LoginSuccessContext = React.createContext<LoginSuccessContextType | null>(null);

export const LoginSuccessProvider = (props) => {
    const server = useContext(ServerContext);
    const [loggedIn, setLoggedIn] = useState(false);
    const [customerFK, setCustomerFK] = useState(null);
    const [loginWithJWTSuccess, setLoginWithJWTSuccess] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);

    useEffect(() => {
        if (localStorage.getItem("TokenJWT")) {
            checkAuthorization();
        }
    }, [loggedIn]);

    const checkAuthorization = () => {
        axios
            .get(`${server}/auth/checkJWT`, {
                headers: {
                    Authorization: localStorage.getItem("TokenJWT"),
                },
            })
            .then((res) => {
                console.log(res.status);
                if (res.status === 200) {
                    console.log("logged in");

                    var FK = res.headers.cookie.replace("custFK=", "");
                    console.log("FK: " + FK);
                    // sessionStorage.setItem("custFK", FK);
                    setCustomerFK(FK);
                    setLoggedIn(true);
                    setLoginWithJWTSuccess(true);
                }
            })
            .catch((err) => console.log(err));
    };

    return (
        <LoginSuccessContext.Provider
            value={{
                loggedIn,
                setLoggedIn,
                customerFK,
                setCustomerFK,
                loginWithJWTSuccess,
                setLoginWithJWTSuccess,
                modalOpen,
                setModalOpen,
            }}
        >
            {props.children}
        </LoginSuccessContext.Provider>
    );
};
