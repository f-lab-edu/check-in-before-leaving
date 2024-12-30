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

    @Builder(access = AccessLevel.PROTECTED)
    protected EtcEntity(@NonNull Long id, @NonNull Etc etc) {
        this.id = id;
        this.contents = etc.getContents();
        this.helpEntity = HelpDetailEntity.toDB(etc.getHelpDetail());
        this.progressEntity = ProgressEntity.from(etc.getProgress());
    }

    protected EtcEntity(Etc etc, boolean isRegister) {
        this.contents = etc.getContents();
        this.helpEntity = HelpDetailEntity.toDB(etc.getHelpDetail());
        this.progressEntity = ProgressEntity.from(etc.getProgress());
    }

    public static EtcEntity register(Etc etc) {
        return new EtcEntity(etc, true);
    }

    public static EtcEntity toDB(Etc etc) {
        return EtcEntity.builder()
                .id(etc.getId())
                .etc(etc)
                .build();
    }

    //for test
    public static EtcEntity createForTest() {
        return EtcEntity.builder()
                .id(1L)
                .etc(Etc.createForTest())
                .build();
    }

    public static EtcEntity createForTestWithoutId() {
        return EtcEntity.builder()
                .etc(Etc.createForTest())
                .build();
    }

}
