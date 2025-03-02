const {uploadFile} = require('@/file/fileUploadSequential');
const fs = require('fs');

describe('File Upload Tests', () => {
    test('should upload file to real server', async () => {

        const path = require('path');
        const filePath = path.join(__dirname, 'test-files', 'test.m4v');
        const fileBuffer = fs.readFileSync(filePath);
        const mockFile = new Blob([fileBuffer], {
            type: 'video/x-m4v'  // M4V 파일의 MIME 타입
        });
        mockFile.name = 'test.m4v';

        try {
            const result = await uploadFile(mockFile);
            console.log('Upload successful');
            console.log('Upload result:', result);

            expect(result).toBeDefined();
        } catch (error) {
            console.error('Upload failed:', error);
        }
    }, 30000);
});
