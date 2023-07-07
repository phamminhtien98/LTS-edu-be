package com.quanlyphattu.controller;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.PhatTuDaoTrang;
import com.quanlyphattu.service.IPhatTuDaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/auth/phattudaotrang")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class PhatTuDaoTrangController {
    @Autowired
    private IPhatTuDaoTrangService phatTuDaoTrangService;

    @GetMapping(value = "{id}")
    public ResponseEntity<?> findPhatTuDaoTrangById(@PathVariable(value = "id") Integer id) {
        return phatTuDaoTrangService.findPhatTuDaoTrangById(id);
    }

    @GetMapping(value = "/daotrang/{id}")
    public List<PhatTuDaoTrang> findPhatTuDaoTrangByDaoTrang(@PathVariable(value = "id") Integer id) {
        return phatTuDaoTrangService.findPhatTuDaoTrangByDaoTrangId(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> themPhatTuDaoTrang(@RequestBody String json) {
        PhatTuDaoTrang phatTuDaoTrang = CommonUtil.jsonToObject(json, PhatTuDaoTrang.class);
        return phatTuDaoTrangService.themPhatTuDaoTrang(phatTuDaoTrang);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suaPhatTuDaoTrang(@RequestBody String json) {
        PhatTuDaoTrang phatTuDaoTrang = CommonUtil.jsonToObject(json, PhatTuDaoTrang.class);
        return phatTuDaoTrangService.suaPhatTuDaoTrang(phatTuDaoTrang);
    }
}
