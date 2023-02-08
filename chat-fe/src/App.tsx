import React, {useState} from 'react';
import './assets/scss/App.css';
import logo from "./assets/images/logo.svg";
import axios from "axios";

function App() {
    const [chats, setChats] = useState([])
    axios.get('').then(value => {
        console.log(value.data)
    })

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <p>
                    Edit <code>src/App.tsx</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
            </header>
        </div>
    );
}

export default App;
