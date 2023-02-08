import React, {useEffect, useRef, useState} from 'react';
import './assets/scss/App.css';
import axios from "axios";
import SockJsClient from 'react-stomp'

const WS_URL = 'ws://localhost:8080/ws/connect'
const HOST = 'http://localhost:8080'

function isUserEvent(message: any) {
    let evt = JSON.parse(message.data);
    return evt.type === 'userevent';
}

function isDocumentEvent(message: any) {
    let evt = JSON.parse(message.data);
    return evt.type === 'contentchange';
}

const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};

function connectWs() {

}

function App() {
    const [token, setToken] = useState('')
    const [meetingTopic, setMeetingTopic] = useState('');
    const [agentStatusTopic, setAgentStatusTopic] = useState('');
    const wsClient = useRef(null);

    useEffect(() => {
        console.log('use affect')
        if (token) {
            setMeetingTopic("/topic/agent." + parseJwt(token).sub);
            setAgentStatusTopic("/topic/agentState." + parseJwt(token).sub);
        }
    }, [token])

    return (
        <>
            <div className="container-fluid">
                {
                    token ?
                        <>
                            <EditorSection token={token} client={wsClient}/>
                            <SockJsClient url='http://localhost:8080/ws/connect/'
                                          headers={{'Authorization': 'Bearer ' + token}}
                                          topics={[meetingTopic, agentStatusTopic]}
                                          onConnect={() => {
                                              console.log("connected");
                                          }}
                                          onDisconnect={() => {
                                              console.log("Disconnected");
                                          }}
                                          onMessage={(msg) => {
                                              console.log(msg);
                                          }}
                                          ref={(client) => {
                                              wsClient.current = client
                                          }}
                                          debug={true}/>
                        </>
                        : <LoginSection onLogin={setToken}/>
                }
            </div>
        </>
    );
}

function LoginSection({onLogin}: { onLogin: React.Dispatch<React.SetStateAction<string>> }) {
    const [username, setUsername] = useState('');
    // async function logInUser() {
    //     var params = new URLSearchParams();
    //     params.append('username', 'root')
    //     params.append('password', 'root')
    //
    //     console.log(params)
    //     let rs = await axios.post<any>(
    //         HOST + '/login',
    //         params,
    //         {
    //             headers: {
    //                 "Content-Type": "application/x-www-form-urlencoded",
    //             }
    //         }
    //     )
    //     console.log(rs)
    //     onLogin && onLogin(rs.data.access_token);
    // }

    async function logInUser2() {
        let rs = await axios.post<any>(
            HOST + '/cms/login',
            {userIdentity: "duyth", password: 'P@ssword789'},
            {
                headers: {
                    "Content-Type": "application/json"
                }
            }
        )
        console.log(rs)
        onLogin(rs.data.data.access_token);
    }

    return (
        <div className="account">
            <div className="account__wrapper">
                <div className="account__card">
                    <div className="account__profile">
                        <p className="account__name">Hello, user!</p>
                        <p className="account__sub">Join to edit the document</p>
                    </div>
                    <input name="username" onInput={(e: any) => setUsername(e.target.value)} className="form-control"/>
                    <button
                        type="button"
                        onClick={() => logInUser2()}
                        className="btn btn-primary account__btn">Join
                    </button>
                </div>
            </div>
        </div>
    );
}

// function History() {
//     console.log('history');
//     const {lastJsonMessage} = useWebSocket(WS_URL, {
//         share: true,
//         filter: isUserEvent
//     });
//     const activities = lastJsonMessage?.data.userActivity || [];
//     return (
//         <ul>
//             {activities.map((activity, index) => <li key={`activity-${index}`}>{activity}</li>)}
//         </ul>
//     );
// }

// function Users() {
//     const {lastJsonMessage} = useWebSocket(WS_URL, {
//         share: true,
//         filter: isUserEvent
//     });
//     const users = Object.values(lastJsonMessage?.data.users || {});
//     return users.map(user => (
//         <div key={user.username}>
//             <p>{user.username}</p>
//         </div>
//     ));
// }

function EditorSection({token, client}: { token: string, client: SockJsClient }) {

    return (
        <div className="main-content">
            <button onClick={() =>
                client.current.sendMessage(
                    "/app/secured/join",
                    JSON.stringify({cif: parseJwt(token).sub})
                )
                // client.current.client.publish({
                //     destination: "/app/secured/join",
                //     body: JSON.stringify({
                //         cif: parseJwt(token).sub,
                //     })
                // })
            }>JOIN
            </button>
            Editor section
            {/*<div className="document-holder">*/}
            {/*    <div className="currentusers">*/}
            {/*        <Users/>*/}
            {/*    </div>*/}
            {/*    <Document/>*/}
            {/*</div>*/}
            {/*<div className="history-holder">*/}
            {/*    <History/>*/}
            {/*</div>*/}
        </div>
    );
}

// function Document() {
//     const {lastJsonMessage, sendJsonMessage} = useWebSocket(WS_URL, {
//         share: true,
//         filter: isDocumentEvent
//     });
//
//     let html = lastJsonMessage?.data.editorContent || '';
//
//     function handleHtmlChange(e) {
//         sendJsonMessage({
//             type: 'contentchange',
//             content: e.target.value
//         });
//     }
//
//     return (
//         <DefaultEditor value={html} onChange={handleHtmlChange}/>
//     );
// }

export default App;
