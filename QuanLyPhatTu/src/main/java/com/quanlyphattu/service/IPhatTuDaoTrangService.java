package com.quanlyphattu.service;

import com.quanlyphattu.model.PhatTuDaoTrang;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPhatTuDaoTrangService {

    List<PhatTuDaoTrang> findPhatTuDaoTrangByDaoTrangId(Integer id);

    ResponseEntity<?> findPhatTuDaoTrangById(Integer id);

    ResponseEntity<?> themPhatTuDaoTrang(PhatTuDaoTrang phatTuDaoTrang);

    ResponseEntity<?> suaPhatTuDaoTrang(PhatTuDaoTrang phatTuDaoTrang);
}
