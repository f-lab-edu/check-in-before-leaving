package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

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

    public static CheckInEntity register(CheckIn checkIn) {
        return new CheckInEntity(true, checkIn);
    }

    public CheckIn update(CheckIn checkIn) {
        this.helpEntity = HelpDetailEntity.from(CheckIn.DTO.getDTO(checkIn));
        return returnDomainModel();
    }

    public CheckIn returnDomainModel() {
        //fixme: dto 빠지는 경우.
        CheckIn.DTO dto = CheckIn.DTO.builder()
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
        return CheckIn.from(dto);
    }


}
