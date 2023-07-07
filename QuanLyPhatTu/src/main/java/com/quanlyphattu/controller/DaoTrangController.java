package com.quanlyphattu.controller;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.DaoTrang;
import com.quanlyphattu.service.IDaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class DaoTrangController {

    @Autowired
    private IDaoTrangService daoTrangService;

    @GetMapping(value = "daotrang")
    public Page<DaoTrang> getAllDaoTrang(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<DaoTrang> daoTrangPage = daoTrangService.findAllDaoTrang(page, pageSize);
        return daoTrangPage;
    }

    @GetMapping(value = "daotrang/{id}")
    public ResponseEntity<?> findDaoTrangById(@PathVariable(value = "id") Integer id) {
        return daoTrangService.findDaoTrangById(id);
    }

    @GetMapping(value = "daotrang/search")
    public Page<DaoTrang> searchPhatTu(@RequestParam(required = false) String noiToChuc,
                                       @RequestParam(required = false) String chuTri,
                                       @RequestParam(required = false) String thoiGianTu,
                                       @RequestParam(required = false) String thoiGianDen,
                                       @RequestParam(required = false) Boolean tinhTrang,
                                       @RequestParam Integer page,
                                       @RequestParam Integer pageSize) {
        Page<DaoTrang> daoTrangPage = daoTrangService.searchDaoTrang(noiToChuc, chuTri, thoiGianTu, thoiGianDen, tinhTrang, page, pageSize);
        return daoTrangPage;
    }

    @PutMapping(value = "auth/daotrang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suaThongTinPhatTu(@RequestBody String json) {
        DaoTrang daoTrang = CommonUtil.jsonToObject(json, DaoTrang.class);
        return daoTrangService.suaDaoTrang(daoTrang);
    }

    @PostMapping(value = "auth/daotrang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> themDaoTrang(@RequestBody String json) {
        DaoTrang daoTrang = CommonUtil.jsonToObject(json, DaoTrang.class);
        return daoTrangService.themDaoTrang(daoTrang);
    }

    @DeleteMapping(value = "auth/daotrang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> xoaDaoTrang(@RequestBody String json) {
        List<Integer> idList = CommonUtil.jsonToList(json, Integer[].class);
        return daoTrangService.xoaDaoTrangByIsd(idList);
    }
}
