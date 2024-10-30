package com.example.checkinrequestMS.HelpAPI.application.service;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;

public interface HelpDBAdapter {

    @JPASave
    public Long save(Help help);

    @JPARead
    public Help findById(Long id);

    String ETC_DISCRIMINATOR = "etc";
    String CHECK_IN_DISCRIMINATOR = "checkIn";
    String LINE_UP_DISCRIMINATOR = "lineUp";

}
