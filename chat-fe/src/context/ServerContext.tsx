import React, {useState} from "react";
import Constants from "../constants/Constants";

export const ServerContext = React.createContext('');

export const ServerProvider = (props) => {
    const [server, setServer] = useState(Constants.SERVER);

    return (
        <ServerContext.Provider value={server}>
            {props.children}
        </ServerContext.Provider>
    );
}
