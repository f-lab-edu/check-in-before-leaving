package com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Authenticated extends Ongoing{

    private Long helperId;
    private String photoPath;
    private boolean completed;

    public Authenticated(Long helperId, String photoPath) {
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = false;
    }
    public Authenticated(Long helperId, String photoPath, boolean completed) {
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public static Authenticated of(Long helperId, String photoPath, boolean completed) {
        if(completed)
            return new Authenticated(helperId, photoPath, completed);
        return new Authenticated(helperId, photoPath);
    }
    public void approved(){
        this.completed = true;
    }
}
