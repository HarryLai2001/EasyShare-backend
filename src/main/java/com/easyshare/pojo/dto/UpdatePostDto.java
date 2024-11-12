package com.easyshare.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePostDto {
    @NotNull(message = "帖子id不能为空")
    private Long id;
    @NotBlank(message = "内容不能为空")
    private String content;
}
