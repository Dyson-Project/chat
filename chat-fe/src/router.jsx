import {Simple} from "./pages/Simple";
import {NavBar} from "./components/NavBar";

export const routerMeta = [
    {
        path: "/",
        element: <NavBar/>,
        children: [
            {
                path: "/profile",
                opts: {basename: "Profile"},
                element: <Simple/>,
            },
            {
                path: "/profile2",
                opts: {basename: "Profile2"},
                element: <Simple/>,
            },
            {
                path: "/profile3",
                opts: {basename: "Profile3"},
                element: <Simple/>,
            }
        ]
    }
]