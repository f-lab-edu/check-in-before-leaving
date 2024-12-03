package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Getter
public final class LineUp {

    public static final String CHECK_IN_TITLE = "체크인 요청";

    private final Long id;
    private final HelpDetail helpDetail;
    private final Progress progress;

    @Builder(access = AccessLevel.PRIVATE)
    private LineUp(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private LineUp(@NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull Boolean isRegister) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    public LineUp start(Long helperId) {
        return LineUp.builder()
                .id(this.id)
                .helpDetail(this.helpDetail)
                .progress(this.progress.registerHelper(helperId))
                .build();
    }

    public static LineUp register(LineUpService.Registration dto) {
        return new LineUp(HelpDetail.registerLineUp(dto), Progress.DEFAULT, true);
    }

    public static LineUp toDomain(LineUpEntity entity) {
        return LineUp.builder()
                .id(entity.getId())
                .helpDetail(HelpDetail.toDomain(entity.getHelpEntity()))
                .progress(Progress.toDomain(entity.getProgressEntity()))
                .build();
    }


    //for Test
    public static LineUp createForTest() {
        return LineUp.builder()
                .id(1L)
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

}
