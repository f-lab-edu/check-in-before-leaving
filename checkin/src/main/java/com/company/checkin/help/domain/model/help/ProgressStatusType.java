package com.company.checkin.help.domain.model.help;

import lombok.Getter;

@Getter
public enum ProgressStatusType {
    CREATED {
        public ProgressStatus.Created getStatus() {
            return ProgressStatus.Created.getInstance();
        }
    }, STARTED {
        public ProgressStatus.Started getStatus() {
            return ProgressStatus.Started.getInstance();
        }
    }, AUTHENTICATED {
        public ProgressStatus.Authenticated getStatus() {
            return ProgressStatus.Authenticated.getInstance();
        }
    }, COMPLETED {
        public ProgressStatus.Completed getStatus() {
            return ProgressStatus.Completed.getInstance();
        }
    };

    abstract public ProgressStatus getStatus();
}
