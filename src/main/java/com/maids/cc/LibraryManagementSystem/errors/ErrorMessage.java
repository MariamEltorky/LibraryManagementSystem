package com.maids.cc.LibraryManagementSystem.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**********************************************************************

 If there is an error, ErrorMessage will be shown clearly in postman.

 **********************************************************************/
@Getter
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private Date timeStamp;
    private String message;
    private String description;

}
