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
        this.id = id;
        this.helpEntity = HelpDetailEntity.toDB(lineUp.getHelpDetail());
        this.progressEntity = ProgressEntity.from(lineUp.getProgress());
    }

    protected LineUpEntity(LineUp lineUp, Boolean isRegister) {
        this.helpEntity = HelpDetailEntity.toDB(lineUp.getHelpDetail());
        this.progressEntity = ProgressEntity.from(lineUp.getProgress());
    }

    public static LineUpEntity toDB(LineUp lineUp) {
        return LineUpEntity.builder()
                .id(lineUp.getId())
                .lineUp(lineUp)
                .build();
    }

    public static LineUpEntity register(LineUp lineUp) {
        return new LineUpEntity(lineUp, true);
    }

    //for test
    public static LineUpEntity createForTest() {
        return LineUpEntity.builder()
                .id(1L)
                .lineUp(LineUp.createForTest())
                .build();
    }


}
