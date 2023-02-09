import React, {useContext, useEffect, useState} from "react";
import {LoginSuccessContext, LoginSuccessContextType} from "../context/LoginSuccessContext";
import {Dialog, DialogContent, DialogTitle} from "@mui/material";

function LoginModal({history}) {
    const [isOpen, setIsOpen] = useState(false);
    const [timer, setTimer] = useState(3);
    const [title, setTitle] = useState("Login successful...");
    const {loggedIn, setLoggedIn, modalOpen, setModalOpen} =
        useContext(LoginSuccessContext) as LoginSuccessContextType;

    // useEffect(() => {
    //   setIsOpen(isModalOpen);
    // }, [isModalOpen]);

    const hideModal = () => {
        setIsOpen(false);
    };

    const modalLoaded = () => {
        setInterval(() => {
            if (timer > 0) {
                setTimer((prev) => prev - 1);
            }
        }, 1000);

        setTimeout(() => {
            //      setIsOpen(false);
            //      history.push("/");
            setModalOpen(false);
            history.push("/");
        }, 3000);
    };

    useEffect(() => {
        modalLoaded()
    }, [timer])

    return (
        <>
            <Dialog
                open={modalOpen}
                onClose={hideModal}

            >
                <DialogTitle>{title}</DialogTitle>
                <DialogContent>Redirecting... {timer}</DialogContent>
            </Dialog>
        </>
    );
}

export default LoginModal;

/*
   return (
    <>
      <Modal show={isOpen} onHide={hideModal} onEntered={modalLoaded}>
        <Modal.Header>
          <Modal.Title>{title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>Redirecting... {timer}</Modal.Body>
        <Modal.Footer></Modal.Footer>
      </Modal>
    </>
  );

 */
