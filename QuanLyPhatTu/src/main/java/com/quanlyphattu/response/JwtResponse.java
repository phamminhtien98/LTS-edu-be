package com.quanlyphattu.response;

import com.quanlyphattu.model.PhatTu;
import lombok.Data;
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private PhatTu phatTu;

    public JwtResponse(String token, PhatTu phatTu) {
        this.token = token;
        this.phatTu = phatTu;
    }
}
