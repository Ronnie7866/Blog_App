package com.amir.blog.Authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    private String name;

    private String password;

    private String about;

    private String email;

}
