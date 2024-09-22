class LocationTracker {
    constructor(apiUrl) {
        this.apiUrl = apiUrl;
        this.intervalSet = null;
    }

    start() {
        this.intervalSet = setInterval(() => {
            this.getAndSendLocation();
        }, 10000); // 60000 밀리초 = 1분
    }

    stop() {
        if (this.intervalSet) {
            clearInterval(this.intervalSet);
            this.intervalSet = null;
        }
    }

    getAndSendLocation() {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const locationData = {
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude,
                    timestamp: new Date().toISOString()
                };
                this.sendLocationToServer(locationData);
            },
            (error) => {
                console.error('Error getting location:', error.message);
            }
        );
    }

    sendLocationToServer(locationData) {
        fetch(this.apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(locationData)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Location sent successfully:', data);
            })
            .catch(error => {
                console.error('Error sending location:', error);
            });
    }
}

const tracker = new LocationTracker('http:///localhost:8080/track');
tracker.start(); // 추적 시작
