package com.libertex.qa.challenge.response;

public enum ResultCode {

    // success
    Ok,

    // exceptions
    UserAlreadyExists,
    IncorrectParameter,
    ConstraintViolation,

    // general
    Unauthorized,
    UnexpectedError

}
