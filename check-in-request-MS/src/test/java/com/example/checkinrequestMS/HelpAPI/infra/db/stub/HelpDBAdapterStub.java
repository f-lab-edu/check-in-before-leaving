package com.example.checkinrequestMS.HelpAPI.infra.db.stub;

import com.example.checkinrequestMS.HelpAPI.application.service.HelpDBAdapter;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@NoRepositoryBean
public class HelpDBAdapterStub implements HelpDBAdapter {

    private final Map<Long, Help> helpList = new HashMap<>();
    private final AtomicLong ids = new AtomicLong(1);

    @Override
    public Long save(Help help) {
        if(help.getId() != null && helpList.containsKey(help.getId())){
            helpList.replace(help.getId(), help);
            return help.getId();
        }
        Long id = ids.getAndIncrement();
        help = addId(help, id);
        helpList.put(id, help);
        return id;
    }
    private Help addId(Help help, Long id){
        if(help instanceof CheckIn) {
            return CheckInFixture.attachId((CheckIn) help, id);
        }else if(help instanceof LineUp){
            return LineUpFixture.attachId((LineUp) help, id);
        }else if(help instanceof Etc){
            return EtcFixture.attachId((Etc) help, id);
        }
        throw new RuntimeException("Help not found");
    }
    @Override
    public Help findById(Long id) {
        return helpList.get(id);
    }
}
