package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
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

    @Builder(access = AccessLevel.PROTECTED)
    protected CheckInEntity(@NonNull Long id, @NonNull CheckIn checkIn) {
        this.id = id;
        this.helpEntity = HelpDetailEntity.toDB(checkIn.getHelpDetail());
        this.progressEntity = ProgressEntity.from(checkIn.getProgress());
    }

    protected CheckInEntity(CheckIn checkIn, Boolean isRegister) {
        this.helpEntity = HelpDetailEntity.toDB(checkIn.getHelpDetail());
        this.progressEntity = ProgressEntity.from(checkIn.getProgress());
    }

    public static CheckInEntity register(CheckIn checkIn) {
        return new CheckInEntity(checkIn, true);
    }

    public static CheckInEntity toDB(CheckIn checkIn) {
        return CheckInEntity.builder()
                .id(checkIn.getId())
                .checkIn(checkIn)
                .build();
    }

    //for test
    public static CheckInEntity createForTest() {
        return CheckInEntity.builder()
                .id(1L)
                .checkIn(CheckIn.createForTest())
                .build();
    }


    public CheckIn update(CheckInService.Update dto) {
        this.id = dto.getHelpId();
        this.helpEntity = this.helpEntity.update(dto);
        return CheckIn.toDomain(this);
    }
}
