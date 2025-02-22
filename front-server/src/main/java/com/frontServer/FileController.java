package com.frontServer;

import com.frontServer.infra.FileChunkUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileChunkUploader fileChunkUploader;

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload/chunk")
    public ResponseEntity<String> uploadChunk(
            @RequestParam("file") MultipartFile chunk,
            @RequestParam("chunkNumber") int chunkNumber,
            @RequestParam("totalChunks") int totalChunks,
            @RequestParam("filename") String filename) {


        return fileChunkUploader.chunkUpload(chunk, chunkNumber, totalChunks, filename)
                ? ResponseEntity.ok().body("Upload completed")
                : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("Chunk uploaded");
    }


}
