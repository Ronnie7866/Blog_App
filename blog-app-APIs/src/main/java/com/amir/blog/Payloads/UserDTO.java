package com.amir.blog.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Integer id;

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum of 4 characters")
    private String name;

    @Email(message = "Your email address is not Valid")
    @Pattern(regexp = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b")
    private String email;

    @NotEmpty
    @Size(min = 8,max = 12, message = "Password must be minimum of 8 characters and max of 12 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=.*$).{8,}$")
    private String password;

    @NotEmpty
    private String about;
}
