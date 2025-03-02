importScripts(
    "https://www.gstatic.com/firebasejs/9.7.0/firebase-app-compat.js"
);
importScripts(
    "https://www.gstatic.com/firebasejs/9.7.0/firebase-messaging-compat.js"
);

const firebaseApp = firebase.initializeApp({
    apiKey: "AIzaSyATunf9xLnnNQhnWL9fyMLNB8vuuH5hA6E",
    authDomain: "push-test-4d79d.firebaseapp.com",
    projectId: "push-test-4d79d",
    storageBucket: "push-test-4d79d.appspot.com",
    messagingSenderId: "774177630402",
    appId: "1:774177630402:web:964f3389c17cce902d335b"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage(function (payload) {
    const notification = payload.notification;

    console.log(
        "[firebase-messaging-sw.js] Received background message ",
        notification
    );

    const notificationTitle = notification.title;
    const notificationOptions = {
        body: notification.body,
        icon: "/firebase-logo.png", // 루트 경로 기준으로 접근
    };

    //self.registration.showNotification(notificationTitle, notificationOptions);
});

// if ('serviceWorker' in navigator) {
//     navigator.serviceWorker.register('/firebase-messaging-sw.js')
//         .then(function (registration) {
//             console.log('Service Worker registered with scope:', registration.scope);
//         }).catch(function (err) {
//         console.log('Service Worker registration failed:', err);
//     });
// }

