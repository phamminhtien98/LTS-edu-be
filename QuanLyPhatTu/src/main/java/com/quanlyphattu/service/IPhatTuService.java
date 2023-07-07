package com.quanlyphattu.service;

import com.quanlyphattu.dto.DoiMatKhauDto;
import com.quanlyphattu.model.PhatTu;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface IPhatTuService {
    List<PhatTu> findAllPhatTus();
    Page<PhatTu> findAllPhatTu(Integer page, Integer sizePage);

    Page<PhatTu> searchPhatTu(String ten, String phapDanh, Integer gioiTinh, Boolean trangThai, Integer page, Integer sizePage);

    ResponseEntity<?> findPhatTuById(Integer id);

    ResponseEntity<?> themPhatTu(PhatTu phatTu);

    ResponseEntity<?> doiMatKhau(DoiMatKhauDto doiMatKhauDto);

    ResponseEntity<?> suaThongTinPhatTu(PhatTu phatTu);

    ResponseEntity<?> login(PhatTu phatTu);

    ResponseEntity<?> findPhatTuByJwt(String jwt);

    ResponseEntity<?> deletePhatTuByIdList(List<Integer> ids);
}
