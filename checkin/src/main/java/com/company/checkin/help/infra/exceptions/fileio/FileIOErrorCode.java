package com.company.checkin.help.infra.exceptions.fileio;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileIOErrorCode {
    NULL_POINTER_WHILE_GETTING_PATH("파일 경로를 가져오는 중 null pointer 발생"),
    NULL_POINTER_WHILE_FILE_NAME("파일 이름을 가져오는 중 null pointer 발생"),
    IO_EXCEPTION_WHILE_SAVING_PHOTO("사진 저장 중 IO 예외 발생");

    private final String detail;
}
