package com.quanlyphattu.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DoiMatKhauDto {
    @NotNull
    private Integer id;
    @NotNull
    private String email;
    @NotNull
    private String matKhauCu;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Mật khẩu phải có tối thiểu 8 ký tự có cả chữ hoa chữ thường và ký tự đặc biệt")
    private String matKhauMoi;
}
