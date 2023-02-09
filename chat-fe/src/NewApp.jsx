import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {ServerProvider} from "./context/ServerContext";
import {LoginSuccessProvider} from "./context/LoginSuccessContext";
import {routerMeta} from "./router";

const router = createBrowserRouter(routerMeta)

function App() {
    return (
        <ServerProvider>
            <LoginSuccessProvider>
                <React.Fragment>
                    <RouterProvider router={router}>

                        {/*<Routes>*/}
                        {/*    <Route path="/log-in">*/}
                        {/*        <Login/>*/}
                        {/*    </Route>*/}
                        {/*    <Route path="/simple">*/}
                        {/*        <Simple/>*/}
                        {/*    </Route>*/}
                        {/*</Routes>*/}
                    </RouterProvider>
                </React.Fragment>
            </LoginSuccessProvider>
        </ServerProvider>
    )
}

export default App;