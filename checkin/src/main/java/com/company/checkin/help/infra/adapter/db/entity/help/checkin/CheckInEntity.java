package com.company.checkin.help.infra.adapter.db.entity.help.checkin;

import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.infra.adapter.db.entity.help.HelpDetailEntity;
import com.company.checkin.help.infra.adapter.db.entity.help.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_in")
public class CheckInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_in_id", nullable = false)
    @Getter
    private Long id;

    @Embedded
    private HelpDetailEntity helpEntity;

    @Embedded
    private ProgressEntity progressEntity;

    @Builder
    private CheckInEntity(@NonNull Long id, @NonNull CheckIn checkIn) {
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);
        this.id = id;
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    private CheckInEntity(@NonNull Boolean isRegister, @NonNull CheckIn checkIn) {
        CheckIn.DTO dto = CheckIn.DTO.getDTOForCreation(checkIn);
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    public static CheckInEntity register(@NonNull CheckIn checkIn) {
        return new CheckInEntity(true, checkIn);
    }

    public CheckIn update(@NonNull CheckIn checkIn) {
        this.helpEntity = HelpDetailEntity.from(CheckIn.DTO.getDTO(checkIn));
        this.progressEntity = ProgressEntity.from(CheckIn.DTO.getDTO(checkIn)); //added
        return returnDomainModel();
    }

    public CheckIn returnDomainModel() {
        //check: dto 빠지는 경우.
        CheckIn.DTO dto = CheckIn.DTO.builder()
                .id(Objects.requireNonNull(this.getId()))
                .helpRegisterId(this.helpEntity.getHelpRegisterId())
                .title(this.helpEntity.getTitle())
                .start(this.helpEntity.getStart())
                .end(this.helpEntity.getEnd())
                .placeId(this.helpEntity.getPlaceId())
                .reward(this.helpEntity.getReward())
                .helperId(this.progressEntity.getHelperId())
                .status(this.progressEntity.getStatusType().getStatus())
                .photoPath(this.progressEntity.getPhotoPath())
                .completed(this.progressEntity.isCompleted())
                .build();
        return CheckIn.from(dto);
    }


}
