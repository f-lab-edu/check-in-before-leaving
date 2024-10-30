// Import the functions you need from the SDKs you need
import {initializeApp} from "https://www.gstatic.com/firebasejs/10.13.2/firebase-app.js";
import {getMessaging, getToken} from "https://www.gstatic.com/firebasejs/10.13.2/firebase-messaging.js";
// TODO: Add SDKs for Firebase products that you want to use

// Initialize Firebase
var app;
var messaging;

function initializeFirebase() {
    getFirebaseConfig();
}

function getFirebaseConfig() {
    fetch('http://localhost:8082/info', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response => response.json())
        .then(data => {
            app = initializeApp(data);
            messaging = getMessaging(app);
            // check();
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

export function getFCMDeviceToken(validKey) {
    getToken(messaging, {vapidKey: validKey}).then((currentToken) => {
        if (currentToken) {
            sendFCMDeviceTokenToServer(currentToken);
        } else {
            console.log('No registration token available. Request permission to generate one.');
        }
    });
}

function check() {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('/firebase-messaging-sw.js')
            .then(function (registration) {
                console.log('Service Worker registered with scope:', registration.scope);
            }).catch(function (err) {
            console.log('Service Worker registration failed:', err);
        });
    }
}

function sendFCMDeviceTokenToServer(token) {
    const tokenData = {
        token: token
    };
    console.log(tokenData.token)

    fetch('http://localhost:8080/token', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tokenData)
    })
        .then(response => response.text())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

initializeFirebase();

