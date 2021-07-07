package com.example.mall.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegist {
    @NotBlank
    //用于String 判断空格
//    @NotNull //用于集合
//    @NotNull
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
