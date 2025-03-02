const uploadFileParallel = (file) => {
    const chunkSize = 1024 * 1024; // 1MB
    const totalChunks = Math.ceil(file.size / chunkSize);

    // 각 청크를 전송하는 Promise 배열 생성
    const uploadPromises = Array.from({length: totalChunks}, (_, i) => {
        const start = i * chunkSize;
        const end = Math.min(start + chunkSize, file.size);
        const chunk = file.slice(start, end);

        const formData = new FormData();
        formData.append("file", chunk);
        formData.append("chunkNumber", i);
        formData.append("totalChunks", totalChunks);
        formData.append("filename", file.name);

        return fetch("http://localhost:8086/upload/chunk", {
            method: "POST",
            body: formData
        }).then(response => {
            console.log(`Chunk ${i} uploaded with status: ${response.status}`);
            return response;
        });
    });

    // 모든 청크 업로드 완료 대기
    return Promise.all(uploadPromises);
};

module.exports = {
    uploadFileParallel
};