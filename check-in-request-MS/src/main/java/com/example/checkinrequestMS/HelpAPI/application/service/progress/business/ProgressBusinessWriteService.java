//package com.example.checkinrequestMS.HelpAPI.application.service.progress.business;
//
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
//import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;
//import com.example.checkinrequestMS.HelpAPI.infra.fileIO.PhotoSaver;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//
//@Service
//@RequiredArgsConstructor
//public class ProgressBusinessWriteService {
//
//    private final HelpDBAdapter helpDBAdapter;
//    private final PhotoSaver photoSaver;
//
//    @Transactional
//    public Long approveProgress(Long helpId) {
//        Help help = helpDBAdapter.findById(helpId);
//        help.approve();
//        return helpDBAdapter.save(help);
//    }
//
//    @Transactional
//    public Long addPhoto(Long helpId, MultipartFile photo) {
//        Help help = helpDBAdapter.findById(helpId);
//        String photoPath = photoSaver.saveOnePhoto(helpId, photo);
//        help.authenticate(photoPath);
//
//        return helpDBAdapter.save(help);
//    }
//}
