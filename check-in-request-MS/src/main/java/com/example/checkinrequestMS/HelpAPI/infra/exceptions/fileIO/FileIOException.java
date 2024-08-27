package com.example.checkinrequestMS.HelpAPI.infra.exceptions.fileIO;

import com.example.checkinrequestMS.common.exception.types.InfraException;

public class FileIOException extends InfraException {

        public FileIOException(FileIOErrorCode error) {
            super(error.getDetail());
        }

        public FileIOException(FileIOErrorCode error, Exception e) {
            super(error.getDetail(), e);
        }
}
