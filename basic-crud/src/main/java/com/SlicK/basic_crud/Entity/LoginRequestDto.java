package com.SlicK.basic_crud.Entity;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}