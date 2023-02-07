var stompClient = null;

let accessToken = null;
const parseJwt = (token) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/connect');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log(frame)
        stompClient.subscribe("/user/user/topic/messages", onMessageReceived)
    }, function (error) {
        window.alert(error)
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    console.log("send message")
    let from = "Long"
    stompClient.send("/app/chat", {},
        JSON.stringify({'from': from, 'content': 'content'}));
}

function join() {
    stompClient.send("/ws/secured/join", {},
        JSON.stringify({
            'userId': $("#name").val(),
            'cif': 'acb123',
            'name': $("#name").val(),
            'topic': $("#name").val()
        }));
}


function login() {
    var keyCloakAuth = fetch(
        'http://10.20.1.44:8888/realms/umee-cms/protocol/openid-connect/token',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'client_id': 'umee-cms-backend-client',
                'username': $("#username").val(),
                'password': $("#password").val(),
                'grant_type': 'password',
                'client_secret': 'zTuStq6qtNqVsdTVoBFPhT5HW6tKxkW1'
            })
        }
    )
    keyCloakAuth.then(async (r) => {
        const body = await r.json();
        console.log(body);
        accessToken = body?.data?.access_token;
    }).catch(async (error) => {
        window.alert(error)
    })
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log("received message", message)

    var messageElement = document.createElement('li');

    if (message.type === 'newUser') {
        messageElement.classList.add('event-data');
        message.content = message.sender + ' has joined the chat';
    } else if (message.type === 'Leave') {
        messageElement.classList.add('event-data');
        message.content = message.sender + ' has left the chat';
    } else {
        messageElement.classList.add('message-data');

        var element = document.createElement('i');
        var text = document.createTextNode(message.sender[0]);
        element.appendChild(text);

        messageElement.appendChild(element);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    document.querySelector('#messageList').appendChild(messageElement);
    document.querySelector('#messageList').scrollTop = document
        .querySelector('#messageList').scrollHeight;

}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#join").click(function () {
        join();
    });
    $("#login").click(function () {
        login();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
});
