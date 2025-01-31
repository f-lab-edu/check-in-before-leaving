package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "line_up")
public class LineUpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_up", nullable = false)
    private Long id;

    @Embedded
    private HelpDetailEntity helpEntity;

    @Embedded
    private ProgressEntity progressEntity;

    @Builder(access = AccessLevel.PROTECTED)
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

    //Business
    public static LineUpEntity register(LineUp lineUp) {
        return new LineUpEntity(lineUp, true);
    }

    public LineUp update(LineUp lineUp) {
        this.helpEntity = HelpDetailEntity.from(LineUp.DTO.getDTO(lineUp));
        return returnDomainEntity();
    }

    public LineUp returnDomainEntity() {
        LineUp.DTO dto = LineUp.DTO.builder()
                .id(this.getId())
                .helpRegisterId(this.getHelpEntity().getHelpRegisterId())
                .title(this.getHelpEntity().getTitle())
                .start(this.getHelpEntity().getStart())
                .end(this.getHelpEntity().getEnd())
                .placeId(this.getHelpEntity().getPlaceId())
                .reward(this.getHelpEntity().getReward())
                .status(this.getProgressEntity().getStatus())
                .helperId(this.getProgressEntity().getHelperId())
                .photoPath(this.getProgressEntity().getPhotoPath())
                .completed(this.getProgressEntity().isCompleted())
                .build();
        return LineUp.from(dto);
    }

    public static LineUpEntity from(LineUp lineUp) {
        return LineUpEntity.builder()
                .id(lineUp.getId())
                .lineUp(lineUp)
                .build();
    }


    //for test
    public static LineUpEntity createForTest() {
        return LineUpEntity.builder()
                .id(1L)
                .lineUp(LineUp.createForTest())
                .build();
    }


}
