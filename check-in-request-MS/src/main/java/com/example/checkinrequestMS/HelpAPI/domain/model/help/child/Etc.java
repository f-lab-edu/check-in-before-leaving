package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Etc extends Help {

    private String contents;

    @Builder(access = AccessLevel.PROTECTED)
    protected Etc(Long id, Long helpRegisterId, String title, String placeId, Long reward, LocalDateTime start, LocalDateTime end, String contents, Progress progress) {
        super(id, helpRegisterId, title, start, end, placeId, reward, progress);
        this.contents = contents;
    }

    public static Etc of(EtcRegisterDTO dto, Progress progress) {
        return Etc.builder()
                .helpRegisterId(dto.getHelpRegisterId())
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(dto.getEnd())
                .placeId(dto.getPlaceId())
                .progress(progress)
                .reward(dto.getReward())
                .contents(dto.getContents())
                .build();
    }

    public static Etc from(EtcJPAEntity japEntity) {
        return Etc.builder()
                .helpRegisterId(japEntity.getHelpRegisterId())
                .title(japEntity.getTitle())
                .start(japEntity.getStart())
                .end(japEntity.getEnd())
                .placeId(japEntity.getPlaceId())
                .progress(Progress.from(japEntity.getProgressVO()))
                .reward(japEntity.getReward())
                .contents(japEntity.getContents())
                .build();
    }

}
