package com.quanlyphattu.controller;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.dto.DoiMatKhauDto;
import com.quanlyphattu.model.PhatTu;
import com.quanlyphattu.service.IPhatTuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(value = "*", allowedHeaders = "*")
public class PhatTuController {
    @Autowired
    private IPhatTuService phatTuService;
    @GetMapping(value = "api/phattujwt")
    public ResponseEntity<?> getPhatTuByJwt(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        //kiểm tra header Authorization có thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        return phatTuService.findPhatTuByJwt(bearerToken);
    }
    @GetMapping(value = "api/phattu")
    public Page<PhatTu> getAllPhatTu(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<PhatTu> phatTuPage = phatTuService.findAllPhatTu(page, pageSize);
        return phatTuPage;
    }

    @GetMapping(value = "api/phattus")
    public List<PhatTu> getAllPhatTu() {
        List<PhatTu> phatTuPage = phatTuService.findAllPhatTus();
        return phatTuPage;
    }
    @GetMapping(value = "api/phattu/search")
    public Page<PhatTu> searchPhatTu(@RequestParam(required = false) String ten, @RequestParam(required = false) String phapDanh, @RequestParam(required = false) Integer gioiTinh, @RequestParam(required = false) Boolean trangThai, @RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<PhatTu> phatTuPage = phatTuService.searchPhatTu(ten, phapDanh, gioiTinh, trangThai, page, pageSize);
        return phatTuPage;
    }

    @GetMapping(value = "api/phattu/{id}")
    public ResponseEntity<?> findPhatTuById(@PathVariable(value = "id") Integer id) {
        return phatTuService.findPhatTuById(id);
    }

    @PutMapping(value = "api/auth/phattu/doimatkhau", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doiMatKhau(@RequestBody String json) {
        DoiMatKhauDto doiMatKhauDto = CommonUtil.jsonToObject(json, DoiMatKhauDto.class);
        return phatTuService.doiMatKhau(doiMatKhauDto);
    }

    @PutMapping(value = "api/auth/phattu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suaThongTinPhatTu(@RequestBody String json) {
        PhatTu phatTu = CommonUtil.jsonToObject(json, PhatTu.class);
        return phatTuService.suaThongTinPhatTu(phatTu);
    }

    @DeleteMapping(value = "api/auth/phattu",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePhatTu(@RequestBody String json){
        List<Integer> ids = CommonUtil.jsonToList(json, Integer[].class);
        return phatTuService.deletePhatTuByIdList(ids);
    }

}
