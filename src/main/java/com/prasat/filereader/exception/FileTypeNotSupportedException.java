package com.prasat.filereader.exception;

public class FileTypeNotSupportedException extends RuntimeException {

    public FileTypeNotSupportedException(String fileFormat, String expectedFileType) {
        super("File type: " + fileFormat +" is not supported, please upload "+expectedFileType+" type.");
    }
}
