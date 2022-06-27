var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#chats").html("");
}

function connect() {
    stompClient = Stomp.client('ws://localhost:9000/ws');
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/errors', function (message) {
            console.log('Error occurred: ' + message.body);
        });
        stompClient.subscribe('/sub/v1/rooms/' + $("#room-id").val(), function (message) {
            showReceivedChat(JSON.parse(message.body).data.chat);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendChat() {
    stompClient.send("/pub/v1/rooms/" + $("#room-id").val() + "/chats", {}, JSON.stringify({
        'chat': $("#chat").val(),
    }));
}

function showReceivedChat(chat) {
    $("#chats").append("<tr><td>" + chat + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendChat();
    });
});
