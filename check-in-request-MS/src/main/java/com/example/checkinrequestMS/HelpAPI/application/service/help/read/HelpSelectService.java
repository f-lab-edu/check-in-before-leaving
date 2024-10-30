package com.example.checkinrequestMS.HelpAPI.application.service.help.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpSelectService {

    private final HelpDBAdapter helpDBAdapter;

    public Help selectHelp(Long id) {
        return  helpDBAdapter.findById(id);
    }

}
