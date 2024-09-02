package com.example.checkinrequestMS.HelpAPI.domain.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.progress.ProgressException;

import com.example.checkinrequestMS.HelpAPI.infra.fileIO.PhotoSaver;
import com.example.checkinrequestMS.HelpAPI.infra.db.progress.ProgressJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.progress.ProgressErrorCode.NO_PROGRESS;

@Service
@RequiredArgsConstructor
public class ProgressBusinessWriteService {

    private final ProgressJPARepository progressJPARepository;
    private final PhotoSaver photoSaver;

    @Transactional
    public void approveProgress(Long progressId) {
        Progress progress = progressJPARepository.findById(progressId).orElseThrow(
                () -> new ProgressException(NO_PROGRESS)
        );
        progress.approve();
        progressJPARepository.save(progress);
    }

    @Transactional
    public void addPhoto(Long progressId, MultipartFile photo) {
        Progress progress = progressJPARepository.findById(progressId).orElseThrow(
                () -> new ProgressException(NO_PROGRESS)
        );

        String photoPath = photoSaver.saveOnePhoto(progressId, photo);
        progress.setPhotoPath(photoPath);

        progressJPARepository.save(progress);
    }
}
