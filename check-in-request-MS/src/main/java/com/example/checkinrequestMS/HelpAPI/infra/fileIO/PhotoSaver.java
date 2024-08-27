package com.example.checkinrequestMS.HelpAPI.infra.fileIO;

import com.example.checkinrequestMS.HelpAPI.infra.exceptions.fileIO.FileIOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.example.checkinrequestMS.HelpAPI.infra.exceptions.fileIO.FileIOErrorCode.*;

@Service
@Slf4j
public class PhotoSaver {

    private static String PROPERTY_ROOT = "user.home";
    private static String FOLDER_NAME = "photo_for_authentication";

    public String saveOnePhoto(Long progressId, MultipartFile photo) {

        //fixme: 진행ID를 가지고 파일을 저장하는 방식이 문제가 없을까요?
        //       암호화를 살짝 고민 했지만 민감한 정보는 아니라고 생각하여 사용해도 괜찮지 않을까 생각했습니다.
        Path directoryPath = getPath(progressId);

        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                log.debug("디렉토리 생성: {}", directoryPath);
            }
            Path targetPath = directoryPath.resolve(photo.getOriginalFilename());
            Files.copy(photo.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();

        }catch (NullPointerException e){
            throw new FileIOException(NULL_POINTER_WHILE_FILE_NAME, e);
        }
        catch (IOException e) {
            throw new FileIOException(IO_EXCEPTION_WHILE_SAVING_PHOTO, e);
        }
    }
    private Path getPath(Long progressId){
        try {
            String desktopPath = System.getProperty(PROPERTY_ROOT).toString();
            Path directoryPath = Paths.get(desktopPath, FOLDER_NAME, progressId.toString());
            return directoryPath;
        }catch(NullPointerException e){
            throw new FileIOException(NULL_POINTER_WHILE_GETTING_PATH, e);
        }
    }



}
