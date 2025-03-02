package com.company.checkin.help.domain.model.alarm;


import com.company.checkin.help.infra.adapter.rest.okhttp.MSClient;

public record HelpAlarmEvent(
        Long helpRegisterId,
        MSClient.AlarmForm alarmForm
) {
}
