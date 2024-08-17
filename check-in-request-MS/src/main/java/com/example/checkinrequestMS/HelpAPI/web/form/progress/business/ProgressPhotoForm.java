package com.example.checkinrequestMS.HelpAPI.web.form.progress.business;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
public class ProgressPhotoForm {

    @NotNull
    private Long progressId;

}
