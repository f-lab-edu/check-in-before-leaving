document.getElementById('uploadButton').addEventListener('click', () => {
    const file = document.getElementById('fileInput').files[0];
    if (file) {
        uploadFile(file);
    } else {
        alert('파일을 선택해 주세요.');
    }
});

const uploadFile = (file) => {

    if (!file) {
        alert('파일을 선택해 주세요.');
        return;
    }

    const chunkSize = 200 * 1024 * 1024;
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

        //fixme: 성능 향상위한 부분 재전송 로직 추가하기.
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

//check: module 있으면 node.js 로직으로 인식해서 import가 불가. -> 추후 공존 법 모색.
//module.exports = {uploadFile};
