package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "line_up")
public class LineUpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_up", nullable = false)
    @Getter
    private Long id;

    @Embedded
    private HelpDetailEntity helpEntity;

    @Embedded
    private ProgressEntity progressEntity;

    @Builder
    protected LineUpEntity(@NonNull Long id, @NonNull LineUp lineUp) {
        LineUp.DTO dto = LineUp.DTO.getDTO(lineUp);
        this.id = id;
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    protected LineUpEntity(LineUp lineUp, Boolean isRegister) {
        LineUp.DTO dto = LineUp.DTO.getDTO(lineUp);
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    private LineUpEntity(@NonNull Boolean isRegister, @NonNull LineUp lineUp) {
        LineUp.DTO dto = LineUp.DTO.getDTOForCreation(lineUp);
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    //Business
    public static LineUpEntity register(LineUp lineUp) {
        return new LineUpEntity(true, lineUp);
    }

    public LineUp update(LineUp lineUp) {
        this.helpEntity = HelpDetailEntity.from(LineUp.DTO.getDTO(lineUp));
        return returnDomainModel();
    }

    public LineUp returnDomainModel() {
        LineUp.DTO dto = LineUp.DTO.builder()
                .id(this.getId())
                .helpRegisterId(this.helpEntity.getHelpRegisterId())
                .title(this.helpEntity.getTitle())
                .start(this.helpEntity.getStart())
                .end(this.helpEntity.getEnd())
                .placeId(this.helpEntity.getPlaceId())
                .reward(this.helpEntity.getReward())
                .helperId(this.progressEntity.getHelperId())
                .status(this.progressEntity.getStatus())
                .photoPath(this.progressEntity.getPhotoPath())
                .completed(this.progressEntity.isCompleted())
                .build();
        return LineUp.from(dto);
    }

}
