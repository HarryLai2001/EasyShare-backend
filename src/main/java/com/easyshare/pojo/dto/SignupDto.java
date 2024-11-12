package com.easyshare.pojo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupDto {
    @Length(min = 6, max = 16, message = "用户名长度为6到16位字符")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message = "用户名只允许字母，数字，下划线，减号")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+", message = "邮箱格式不正确")
    private String email;

    @Length(min = 8, max = 16, message = "密码长度为8到16位字符")
    @Pattern(regexp = "^\\S*(?=\\S{8,16})(?=\\S*\\d)(?=\\S*[A-Z])(?=\\S*[a-z])\\S*$", message = "密码包含至少1个大写字母，1个小写字母，1个数字")
    private String password;
}
