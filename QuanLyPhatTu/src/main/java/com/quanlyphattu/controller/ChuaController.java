package com.quanlyphattu.controller;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.Chua;
import com.quanlyphattu.service.IChuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class ChuaController {
    @Autowired
    private IChuaService chuaService;

    @GetMapping(value = "chuas")
    public List<Chua> getAllChuas() {
        return chuaService.getAllChua();
    }

    @GetMapping(value = "chua")
    public Page<Chua> getAllChua(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Chua> chuaPage = chuaService.findAllChua(page, pageSize);
        return chuaPage;
    }

    @GetMapping(value = "chua/search")
    public Page<Chua> searchChua(@RequestParam(required = false) String tenChua,
                                 @RequestParam(required = false) String truTri,
                                 @RequestParam(required = false) String diaChi,
                                 @RequestParam(required = false) String ngayThanhLapTu,
                                 @RequestParam(required = false) String ngayThanhLapDen,
                                 @RequestParam Integer page,
                                 @RequestParam Integer pageSize) {
        Page<Chua> chuaPage = chuaService.searchChua(tenChua, truTri, diaChi, ngayThanhLapTu, ngayThanhLapDen, page, pageSize);
        return chuaPage;
    }

    @GetMapping(value = "chua/{id}")
    public ResponseEntity<?> findChuaById(@PathVariable(value = "id") Integer id) {
        return chuaService.findChuaById(id);
    }

    @PutMapping(value = "auth/chua", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suaThongTinChua(@RequestBody String json) {
        Chua chua = CommonUtil.jsonToObject(json, Chua.class);
        return chuaService.suaChua(chua);
    }

    @PostMapping(value = "auth/chua", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> themChua(@RequestBody String json) {
        Chua chua = CommonUtil.jsonToObject(json, Chua.class);
        return chuaService.themChua(chua);
    }
    @DeleteMapping(value = "auth/chua", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> xoaChua(@RequestBody String json) {
        List<Integer> idList = CommonUtil.jsonToList(json,Integer[].class);
        return chuaService.xoaChua(idList);
    }
}
