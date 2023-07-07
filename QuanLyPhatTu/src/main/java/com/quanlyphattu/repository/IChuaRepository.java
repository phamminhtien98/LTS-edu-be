package com.quanlyphattu.repository;

import com.quanlyphattu.model.Chua;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface IChuaRepository extends JpaRepository<Chua, Integer> {
    @Query("SELECT c FROM Chua c WHERE " +
            "(:tenChua IS NULL OR c.tenChua like concat('%',:tenChua,'%')) " +
            "AND (:truTri IS NULL OR c.truTri.phapDanh like concat('%',:truTri,'%')) " +
            "AND (:ngayThanhLapTu IS NULL OR c.ngayThanhLap >= :ngayThanhLapTu) " +
            "AND (:ngayThanhLapDen IS NULL OR c.ngayThanhLap <= :ngayThanhLapDen) " +
            "AND (:diaChi IS NULL OR c.diaChi like concat('%',:diaChi,'%'))")
    Page<Chua> searchChua(
            @Param("tenChua") String tenChua,
            @Param("truTri") String truTri,
            @Param("diaChi") String diaChi,
            @Param("ngayThanhLapTu") LocalDateTime ngayThanhLapTu,
            @Param("ngayThanhLapDen") LocalDateTime ngayThanhLapDen,
            Pageable pageable);

    @Transactional
    void deleteChuaById(Integer id);
}
