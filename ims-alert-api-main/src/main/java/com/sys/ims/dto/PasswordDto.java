package com.sys.ims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sys.ims.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
    @NotNull(groups = { UpdatePassword.class },message = "Password required")
    private String oldPassword;
    @Pattern(groups = { UpdatePassword.class },
            regexp = Constants.REGEXP_FOR_PASSWORD, message = "Password must be atleast 6 maximum 14 charaters contianing numerics, alphabets and special charaters.")
    @NotNull(groups = { UpdatePassword.class },message = "Password required")
    private String newPassword;
    public interface CreatePassword {}
    public interface UpdatePassword {}
}
