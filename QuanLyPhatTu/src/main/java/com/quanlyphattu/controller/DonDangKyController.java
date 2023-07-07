package com.quanlyphattu.controller;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.DonDangKy;
import com.quanlyphattu.service.IDonDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/auth/dondangky")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class DonDangKyController {
    @Autowired
    private IDonDangKyService donDangKyService;

    @GetMapping
    public Page<DonDangKy> getAllDonDangKy(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<DonDangKy> donDangKyPage = donDangKyService.findAllDonDangKy(page, pageSize);
        List<DonDangKy> donDangKyList = donDangKyPage.getContent();
        return new PageImpl<>(donDangKyList, PageRequest.of(page, pageSize), donDangKyPage.getTotalElements());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> findDonDangKyById(@PathVariable(value = "id") Integer id) {
        return donDangKyService.findDonDangKyById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> themDonDangKy(@RequestBody String json) {
        DonDangKy donDangKy = CommonUtil.jsonToObject(json, DonDangKy.class);
        return donDangKyService.themDonDangKy(donDangKy);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suaDonDangKy(@RequestBody String json) {
        DonDangKy donDangKy = CommonUtil.jsonToObject(json, DonDangKy.class);
        return donDangKyService.suaDonDangKy(donDangKy);
    }
}
