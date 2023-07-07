package com.quanlyphattu.service;

import com.quanlyphattu.model.Chua;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IChuaService {
    List<Chua> getAllChua();
    Page<Chua> findAllChua(Integer page, Integer pageSize);

    Page<Chua> searchChua(String tenChua, String truTri, String diaChi, String ngayThanhLapTu, String ngayThanhLapDen, Integer page, Integer pageSize);

    ResponseEntity<?> findChuaById(Integer id);

    ResponseEntity<?> themChua(Chua chua);

    ResponseEntity<?> suaChua(Chua chua);

    ResponseEntity<?> xoaChua(List<Integer> idList);

}
