package com.example.checkinrequestMS.HelpAPI.web.dto.progress;


import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProgressDTO {

    // fixme: Progress 엔티티에서는 Help 엔티티를 참조하고 있지만
    //  ProgressDTO가 조회 되는 경우는 보통 Help 관련 엔티티를 조회할때 같이 조회 될것 같고
    //  단독으로 조회 되더라도 Help자체에 대한 정보라기 보다 진행 상황에 관한 정보가 필요할것 같아서
    //  (Help 에서 상태를 새로고침등)
    //  Help 관련 정보는 ProgressDTO에서 뺏는데 이렇게 선택적으로 사용해도 괜찮을까요?
    private Long id;
    private Long helperId;
    private String picturePath;
    private boolean isCompleted;

    public static ProgressDTO from(Progress progress) {
        return ProgressDTO.builder()
                .id(progress.getId())
                .helperId(progress.getHelperId())
                .picturePath(progress.getPicturePath())
                .isCompleted(progress.isCompleted())
                .build();
    }

}
