package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpDetailEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "etc")
public class EtcEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc_id", nullable = false)
    private Long id;

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

    protected EtcEntity(Etc etc, boolean isRegister) {
        Etc.DTO dto = Etc.DTO.getDTO(etc);
        this.contents = etc.getContents();
        this.helpEntity = HelpDetailEntity.from(dto);
        this.progressEntity = ProgressEntity.from(dto);
    }

    //Business
    public static EtcEntity register(Etc etc) {
        return new EtcEntity(etc, true);
    }

    public Etc update(Etc etc) {
        this.contents = etc.getContents();
        this.helpEntity = HelpDetailEntity.from(Etc.DTO.getDTO(etc));
        return returnDomainEntity();
    }

    public Etc returnDomainEntity() {
        Etc.DTO dto = Etc.DTO.builder()
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
                .contents(this.getContents())
                .build();
        return Etc.from(dto);
    }

    public static EtcEntity from(Etc etc) {
        return EtcEntity.builder()
                .id(etc.getId())
                .etc(etc)
                .build();
    }
}
