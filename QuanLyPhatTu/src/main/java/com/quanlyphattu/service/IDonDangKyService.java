package com.quanlyphattu.service;

import com.quanlyphattu.model.DonDangKy;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IDonDangKyService {
    Page<DonDangKy> findAllDonDangKy(Integer page, Integer sizePage);

    ResponseEntity<?> findDonDangKyById(Integer id);

    ResponseEntity<?> themDonDangKy(DonDangKy donDangKy);

    ResponseEntity<?> suaDonDangKy(DonDangKy donDangKy);
}
