package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_in")
public class CheckInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_in_id", nullable = false)
    private Long id;

    @Embedded
    private HelpDetailEntity helpEntity;

    @Embedded
    private ProgressEntity progressEntity;

    @Builder
    protected CheckInEntity(@NonNull Long id, @NonNull CheckIn checkIn) {
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);
        this.id = id;
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    protected CheckInEntity(CheckIn checkIn, Boolean isRegister) {
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    public static CheckInEntity register(CheckIn checkIn) {
        return new CheckInEntity(checkIn, true);
    }

    public CheckIn update(CheckIn checkIn) {
        this.helpEntity = HelpDetailEntity.from(CheckIn.DTO.getDTO(checkIn));
        return returnDomainEntity();
    }

    public CheckIn returnDomainEntity() {
        //fixme: dto 빠지는 경우.
        CheckIn.DTO dto = CheckIn.DTO.builder()
                .id(this.getId())
                .helpRegisterId(this.getHelpEntity().getHelpRegisterId())
                .title(this.getHelpEntity().getTitle())
                .start(this.getHelpEntity().getStart())
                .end(this.getHelpEntity().getEnd())
                .placeId(this.getHelpEntity().getPlaceId())
                .reward(this.getHelpEntity().getReward())
                .helperId(this.getProgressEntity().getHelperId())
                .status(this.getProgressEntity().getStatus())
                .photoPath(this.getProgressEntity().getPhotoPath())
                .completed(this.getProgressEntity().isCompleted())
                .build();
        return CheckIn.from(dto);
    }

    public static CheckInEntity from(CheckIn checkIn) {
        return CheckInEntity.builder()
                .id(checkIn.getId())
                .checkIn(checkIn)
                .build();
    }

}
