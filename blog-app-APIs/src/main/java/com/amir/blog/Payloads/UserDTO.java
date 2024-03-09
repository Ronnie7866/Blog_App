package com.amir.blog.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String about;
}
