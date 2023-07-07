package com.quanlyphattu.service;

import com.quanlyphattu.model.DaoTrang;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IDaoTrangService {
    Page<DaoTrang> findAllDaoTrang(Integer page, Integer pageSize);

    Page<DaoTrang> searchDaoTrang(String noiToChuc, String chuTri, String thoiGianTu, String thoiGianDen, Boolean tinhTrang, Integer page, Integer pageSize);

    ResponseEntity<?> findDaoTrangById(Integer id);

    ResponseEntity<?> themDaoTrang(DaoTrang daoTrang);

    ResponseEntity<?> suaDaoTrang(DaoTrang daoTrang);

    ResponseEntity<?> xoaDaoTrangByIsd(List<Integer> idList);

}
