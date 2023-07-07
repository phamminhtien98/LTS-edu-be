package com.quanlyphattu.repository;

import com.quanlyphattu.model.DaoTrang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IDaoTrangRepository extends JpaRepository<DaoTrang, Integer> {
    @Query("SELECT dt FROM DaoTrang dt WHERE " +
            "(:noiToChuc IS NULL OR dt.noiToChuc like concat('%',:noiToChuc,'%')) " +
            "AND (:chuTri IS NULL OR dt.phatTuNguoiTruTri.phapDanh like concat('%',:chuTri,'%')) " +
            "AND (:thoiGianTu IS NULL OR dt.thoiGianToChuc >= :thoiGianTu) " +
            "AND (:thoiGianDen IS NULL OR dt.thoiGianToChuc <= :thoiGianDen) " +
            "AND (:tinhTrang IS NULL OR dt.daKetThuc = :tinhTrang)")
    Page<DaoTrang> searchDaoTrang(
            @Param("noiToChuc") String noiToChuc,
            @Param("chuTri") String chuTri,
            @Param("thoiGianTu") LocalDateTime thoiGianTu,
            @Param("thoiGianDen") LocalDateTime thoiGianDen,
            @Param("tinhTrang") Boolean tinhTrang,
            Pageable pageable);
    @Query("select d from DaoTrang d where d.thoiGianToChuc < CURRENT_TIMESTAMP and d.daKetThuc = false ")
    List<DaoTrang> findByThoiGianToChucLessThanNow();


    @Transactional
    void deleteDaoTrangById(Integer id);
}
