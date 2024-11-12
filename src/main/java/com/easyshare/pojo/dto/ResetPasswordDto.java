package com.easyshare.pojo.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ResetPasswordDto {
    @NotBlank(message = "原始密码不能为空")
    private String oldPassword;
    @Length(min = 8, max = 16, message = "密码长度为8到16位字符")
    @Pattern(regexp = "^\\S*(?=\\S{8,16})(?=\\S*\\d)(?=\\S*[A-Z])(?=\\S*[a-z])\\S*$", message = "密码包括至少1个大写字母，1个小写字母，1个数字")
    private String newPassword;
}
