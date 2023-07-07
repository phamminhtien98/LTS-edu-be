package com.quanlyphattu.controller;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.PhatTu;
import com.quanlyphattu.service.IPhatTuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*",allowedHeaders = "*")
public class LoginController {
    @Autowired
    private IPhatTuService phatTuService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping(value = "api/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String json){
        PhatTu phatTu = CommonUtil.jsonToObject(json, PhatTu.class);
        return phatTuService.login(phatTu);
    }
    @PostMapping(value = "api/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> themPhatTu(@RequestBody String json){
        PhatTu phatTu = CommonUtil.jsonToObject(json, PhatTu.class);
        return phatTuService.themPhatTu(phatTu);
    }
}
