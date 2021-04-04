var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#Question").show();
    }
    else {
        $("#Question").hide();
    }
    $("#questions").html("");
}

function connect() {
    console.log("trying to connect");
    var socket = new SockJS('/mySocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/questions', function (questionDTO) {
            showQuestion(questionDTO);
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

function sendQuestionRequest() {
    if(stompClient==null){
        alert("Connect to websocket first")
        return
    }
    stompClient.send("/app/question", {}, JSON.stringify({
        'topic': $("#topic").val(),
        'difficulty':$("#difficulty").val()
    }));
}



function showQuestion(questionDTO) {
    var question = JSON.parse(questionDTO.body);
    $("#questions").empty();
    $("#questions").append(
        ("<tr>"+
            "<td>" +  String(question.id) + "</td>"+
            "<td>" +  question.text + "</td>"+
            "<td>" +  question.answer + "</td>"+
            "</tr>"));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#request" ).click(function() { console.log("request") ;sendQuestionRequest(); });
});

