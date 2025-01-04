package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Getter
public final class Etc {

    private final Long id;
    private final String contents;
    private final HelpDetail helpDetail;
    private final Progress progress;

    @Builder(access = AccessLevel.PRIVATE)
    private Etc(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull String contents) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
        this.contents = contents;
    }

    private Etc(@NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull String contents, @NonNull Boolean isRegister) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
        this.contents = contents;
    }

    //Business
    public static Etc register(EtcService.Registration dto) {
        return new Etc(HelpDetail.register(dto), Progress.DEFAULT, dto.getContents(), true);
    }

    public Etc update(EtcService.Update dto) {
        return Etc.builder()
                .id(this.id)
                .helpDetail(HelpDetail.update(dto))
                .progress(this.progress)
                .contents(dto.getContents())
                .build();
    }

    public Etc start(Long helperId) {
        return Etc.builder()
                .id(this.id)
                .helpDetail(this.helpDetail)
                .progress(this.progress.registerHelper(helperId))
                .build();
    }

    public static Etc transferFrom(EtcEntity entity) {
        return Etc.builder()
                .id(entity.getId())
                .contents(entity.getContents())
                .helpDetail(HelpDetail.toDomain(entity.getHelpEntity()))
                .progress(Progress.toDomain(entity.getProgressEntity()))
                .build();
    }

    //for Test
    public static Etc createForTest() {
        return Etc.builder()
                .id(1L)
                .contents("contents")
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

    public static Etc createForTestWithoutId() {
        return Etc.builder()
                .contents("contents")
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

}
