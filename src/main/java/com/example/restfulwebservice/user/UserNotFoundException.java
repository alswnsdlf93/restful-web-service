package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code 값으로 에러 확인할 수 있다.
// 2xx : Ok
// 4xx : Client 관련 오류
// 5xx : Server 관련 오류
@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
