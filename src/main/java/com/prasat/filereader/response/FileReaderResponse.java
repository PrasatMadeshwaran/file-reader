package com.prasat.filereader.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileReaderResponse {

    private static final long serialVersionUID = 1L;

    protected Integer status;

    protected String responseMessage;

    protected Object resourceData;
}
