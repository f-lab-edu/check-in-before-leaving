package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "etc")
public class EtcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc_id", nullable = false)
    @Getter
    private Long id;

    @Getter
    private String contents;

    @Embedded
    private HelpDetailEntity helpEntity;

    @Embedded
    private ProgressEntity progressEntity;

    @Builder
    protected EtcEntity(@NonNull Long id, @NonNull Etc etc) {
        Etc.DTO dto = Etc.DTO.getDTO(etc);
        this.id = id;
        this.contents = etc.getContents();
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    private EtcEntity(@NonNull Boolean isRegister, @NonNull Etc etc) {
        Etc.DTO dto = Etc.DTO.getDTOForCreation(etc);
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    //Business
    public static EtcEntity register(Etc etc) {
        return new EtcEntity(true, etc);
    }

    public Etc update(Etc etc) {
        this.contents = etc.getContents();
        this.helpEntity = HelpDetailEntity.from(Etc.DTO.getDTO(etc));
        return returnDomainModel();
    }

    public Etc returnDomainModel() {
        Etc.DTO dto = Etc.DTO.builder()
                .id(this.getId())
                .contents(this.getContents())
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
        return Etc.from(dto);
    }
}
