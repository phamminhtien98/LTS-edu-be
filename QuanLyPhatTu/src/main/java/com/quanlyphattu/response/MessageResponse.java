package com.quanlyphattu.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageResponse {
    private List<String> message;
    private String emailTonTai;
    private String soDienThoaiTonTai;

    public MessageResponse() {

    }
}
