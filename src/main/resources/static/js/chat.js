// src/main/resources/static/js/chat.js

let stompClient = null;

// Function to connect to the WebSocket server
function connect() {
  const socket = new SockJS("/ws"); // Connect to the "/ws" endpoint
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
}

// Callback when connected
function onConnected() {
  // Subscribe to the "/topic/public" destination
  stompClient.subscribe("/topic/public", onMessageReceived);

  // Notify the server that a new user has joined
  const chatMessage = {
    sender: getUsername(),
    type: "JOIN",
    content: "has joined the chat",
  };
  stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));
}

// Callback when connection fails
function onError(error) {
  console.error(
    "Could not connect to WebSocket server. Please refresh this page to try again!",
    error
  );
}

// Function to send a message
function sendMessage() {
  const messageContent = document.getElementById("message").value.trim();
  if (messageContent && stompClient) {
    const chatMessage = {
      sender: getUsername(),
      type: "CHAT",
      content: messageContent,
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    document.getElementById("message").value = "";
  }
}

// Function to handle received messages
function onMessageReceived(payload) {
  const message = JSON.parse(payload.body);
  const messageArea = document.getElementById("messageArea");

  const messageElement = document.createElement("div");
  messageElement.classList.add("message");

  if (message.type === "JOIN" || message.type === "LEAVE") {
    messageElement.innerHTML = `<i>${message.sender} ${message.content} at ${message.time}</i>`;
  } else {
    messageElement.innerHTML = `
            <span class="sender">${message.sender}</span>
            <span class="time">${message.time}</span>
            <div class="content">${message.content}</div>
        `;
  }

  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}

// Utility function to get the username
function getUsername() {
  // For simplicity, prompt the user for a username. In production, retrieve it from the authenticated user.
  let username = sessionStorage.getItem("username");
  if (!username) {
    username = prompt("Enter your username:");
    if (username) {
      sessionStorage.setItem("username", username);
    } else {
      username = "Anonymous";
    }
  }
  return username;
}

// Initialize the connection when the page loads
document.addEventListener("DOMContentLoaded", (event) => {
  connect();
});
