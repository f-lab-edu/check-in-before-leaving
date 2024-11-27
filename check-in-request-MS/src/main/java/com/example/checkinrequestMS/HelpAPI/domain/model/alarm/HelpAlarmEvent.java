package com.example.checkinrequestMS.HelpAPI.domain.model.alarm;

import com.example.checkinrequestMS.HelpAPI.infra.MSClient.MSClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record HelpAlarmEvent(
        Long helpRegisterId,
        MSClient.AlarmForm alarmForm
) {
}
