const {uploadFile} = require('@/file/fileUploadSequential');
const {uploadFileParallel} = require('@/file/fileUploadParallel');

describe('File Upload Tests', () => {
    beforeEach(() => {
        global.fetch = jest.fn();
        global.FormData = jest.fn(() => ({
            append: jest.fn()
        }));
        global.Blob = jest.fn(() => ({}));
    });

    test('should upload file in chunks', async () => {
        const mockFile = {
            size: 3 * 1024 * 1024, // 3MB
            name: 'test.txt',
            slice: jest.fn((start, end) => new Blob(['chunk']))
        };

        global.fetch.mockImplementation(() =>
            Promise.resolve({
                status: 206,
                ok: true
            })
        );

        await uploadFile(mockFile);
        await new Promise(process.nextTick);

        const expectedChunks = Math.ceil(mockFile.size / (1024 * 1024));
        expect(fetch).toHaveBeenCalledTimes(expectedChunks);
        console.log('Expected chunks:', expectedChunks);
        console.log('Actual fetch calls:', fetch.mock.calls.length);
        console.log('Fetch calls:', fetch.mock.calls);
    });
});

