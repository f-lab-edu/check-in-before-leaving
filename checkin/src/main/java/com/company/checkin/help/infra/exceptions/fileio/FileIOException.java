package com.company.checkin.help.infra.exceptions.fileio;

import com.company.checkin.common.exception.types.InfraException;

public class FileIOException extends InfraException {

    public FileIOException(FileIOErrorCode error) {
        super(error.getDetail());
    }

    public FileIOException(FileIOErrorCode error, Exception e) {
        super(error.getDetail(), e);
    }
}
