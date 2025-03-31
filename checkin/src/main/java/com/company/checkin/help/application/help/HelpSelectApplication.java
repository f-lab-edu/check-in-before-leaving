package com.company.checkin.help.application.help;

import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HelpSelectApplication {

    private final CheckInService checkInService;
    private final LineUpService lineUpService;
    private final EtcService etcService;

    @Cacheable(cacheNames = "help_searched", key = "'checkIn_' + #id")
    public CheckInSelectDTO selectCheckIn(@NonNull Long id) {
        return CheckInSelectDTO.from(checkInService.findOne(id));
    }

    @Cacheable(cacheNames = "help_searched", key = "'lineUp_' + #id")
    public LineUp.DTO selectLineUp(@NonNull Long id) {
        return lineUpService.findOne(id);
    }

    @Cacheable(cacheNames = "help_searched", key = "'etc_' + #id")
    public Etc.DTO selectEtc(@NonNull Long id) {
        return etcService.findOne(id);
    }

    @Getter
    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    public static final class CheckInSelectDTO {
        
        private final Long id;
        private final Long helpRegisterId;
        private final String title;
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final String placeId;
        private final Long reward;
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;

        public static CheckInSelectDTO from(CheckIn.DTO dto) {
            return new CheckInSelectDTO(dto.getId(), dto.getHelpRegisterId(), dto.getTitle(), dto.getStart(), dto.getEnd(), dto.getPlaceId(), dto.getReward(), dto.getHelperId().orElse(null), dto.getPhotoPath().orElse(null), dto.isCompleted());
        }
    }

}
