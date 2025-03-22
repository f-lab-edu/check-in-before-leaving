package com.company.checkin.help.infra.adapter.db.entity.help.checkin;

import com.company.checkin.help.domain.model.help.checkin.CheckIn2;
import com.company.checkin.help.infra.adapter.db.entity.help.HelpDetailEntity;
import com.company.checkin.help.infra.adapter.db.entity.help.ProgressEntity2;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_in")
public class CheckInEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_in_id", nullable = false)
    @Getter
    private Long id;

    @Embedded
    private HelpDetailEntity helpEntity;

    @Embedded
    private ProgressEntity2 progressEntity;

    private CheckInEntity2(@NonNull Boolean isRegister, @NonNull CheckIn2 checkIn) {
        CheckIn2.DTO dto = CheckIn2.DTO.getDTOForCreation(checkIn);
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity2.register(dto);
    }

    public static CheckInEntity2 register(@NonNull CheckIn2 checkIn) {
        return new CheckInEntity2(true, checkIn);
    }

    public CheckIn2 update(@NonNull CheckIn2 checkIn) {
        this.helpEntity = HelpDetailEntity.from(CheckIn2.DTO.getDTO(checkIn));
        return returnDomainModel();
    }

    public CheckIn2 start(@NonNull CheckIn2 checkIn) {
        this.progressEntity = this.progressEntity.start(CheckIn2.DTO.getDTO(checkIn));
        return returnDomainModel();
    }

    public CheckIn2 returnDomainModel() {
        //check: dto 빠지는 경우.
        CheckIn2.DTO dto = CheckIn2.DTO.builder()
                .id(Objects.requireNonNull(this.getId()))
                .helpRegisterId(this.helpEntity.getHelpRegisterId())
                .title(this.helpEntity.getTitle())
                .start(this.helpEntity.getStart())
                .end(this.helpEntity.getEnd())
                .placeId(this.helpEntity.getPlaceId())
                .reward(this.helpEntity.getReward())
                .helperId(this.progressEntity.getHelperId())
                .photoPath(this.progressEntity.getPhotoPath())
                .completed(this.progressEntity.isCompleted())
                .status(this.progressEntity.getStatusType().getStatus())
                .build();
        return CheckIn2.from(dto);
    }


}
