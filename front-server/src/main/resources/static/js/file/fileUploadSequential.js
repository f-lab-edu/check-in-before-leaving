const uploadFile = (file) => {
    const chunkSize = 1024 * 1024; // 1MB
    const totalChunks = Math.ceil(file.size / chunkSize);
    let currentChunk = 0;

    const sendNextChunk = () => {
        const start = currentChunk * chunkSize;
        const end = Math.min(start + chunkSize, file.size);
        const chunk = file.slice(start, end);

        const formData = new FormData();
        formData.append("file", chunk);
        formData.append("chunkNumber", currentChunk);
        formData.append("totalChunks", totalChunks);
        formData.append("filename", file.name);

        fetch("http://localhost:8086/upload/chunk", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (response.status === 206) {
                    currentChunk++;
                    if (currentChunk < totalChunks) {
                        sendNextChunk();
                    }
                } else if (response.status === 200) {
                    console.log("Upload completed");
                }
            })
            .catch(error => {
                console.error("Upload failed:", error);
            });
    };

    sendNextChunk();
};
module.exports = {uploadFile};
