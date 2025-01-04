package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class CheckIn {

    public static final String CHECK_IN_TITLE = "체크인 요청";

    private final Long id;
    private final HelpDetail helpDetail;
    private final Progress progress;


    @Builder(access = AccessLevel.PRIVATE)
    private CheckIn(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private CheckIn(@NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull Boolean isRegister) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static CheckIn register(CheckInService.Registration dto) {
        return new CheckIn(HelpDetail.register(dto), Progress.DEFAULT, true);
    }

    public CheckIn update(CheckInService.Update dto) {
        return CheckIn.builder()
                .id(this.id)
                .helpDetail(HelpDetail.update(dto))
                .progress(this.progress)
                .build();
    }

    public CheckIn start(Long helperId) {
        return CheckIn.builder()
                .id(this.id)
                .helpDetail(this.helpDetail)
                .progress(this.progress.registerHelper(helperId))
                .build();
    }

    public static CheckIn transferFrom(CheckInEntity entity) {
        return CheckIn.builder()
                .id(entity.getId())
                .helpDetail(HelpDetail.toDomain(entity.getHelpEntity()))
                .progress(Progress.toDomain(entity.getProgressEntity()))
                .build();
    }


    //for Test
    public static CheckIn createForTest() {
        return CheckIn.builder()
                .id(1L)
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

}
