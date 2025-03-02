const {uploadFileParallel} = require('@/file/fileUploadParallel');


test('should upload file in parallel', async () => {
    const content = Buffer.alloc(3 * 1024 * 1024);
    const mockFile = new Blob([content], {
        type: 'text/plain'
    });
    mockFile.name = 'test.txt';

    try {
        const results = await uploadFileParallel(mockFile);
        console.log('All chunks uploaded successfully');
        expect(results.length).toBe(3); // 3개의 청크가 모두 전송되었는지 확인
    } catch (error) {
        console.error('Upload failed:', error);
        throw error;
    }
}, 30000);