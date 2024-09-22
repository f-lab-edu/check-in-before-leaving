package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Etc<T extends Progress> extends Help<T> {

    private String contents;

    @Builder(access = AccessLevel.PROTECTED)
    protected Etc(Long id, Long helpRegisterId, String title, String placeId, Long reward, T progress, LocalDateTime start, LocalDateTime end, String contents) {
        super(id, helpRegisterId, title, start, end, placeId, reward, progress);
        this.contents = contents;
    }

    public static <T extends Progress> Etc<T> of(EtcRegisterDTO dto, T progress) {
        return Etc.<T>builder()
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
                .reward(japEntity.getReward())
                .contents(japEntity.getContents())
                .build();
    }

}
