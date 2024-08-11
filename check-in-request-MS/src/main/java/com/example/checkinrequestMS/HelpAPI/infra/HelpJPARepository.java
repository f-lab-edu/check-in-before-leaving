package com.example.checkinrequestMS.HelpAPI.infra;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HelpJPARepository extends JpaRepository<Help, Long> {

}
