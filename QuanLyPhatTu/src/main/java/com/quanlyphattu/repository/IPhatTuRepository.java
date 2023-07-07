package com.quanlyphattu.repository;

import com.quanlyphattu.model.PhatTu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IPhatTuRepository extends JpaRepository<PhatTu,Integer> {

    @Query("SELECT pt FROM PhatTu pt WHERE (:ten IS NULL OR pt.ten  like concat('%',:ten,'%')) " +
            "AND (:phapdanh IS NULL OR pt.phapDanh like concat('%',:phapdanh,'%')) " +
            "AND (:gioiTinh IS NULL OR pt.gioiTinh = :gioiTinh) " +
            "AND (:trangthai IS NULL OR pt.daHoanTuc = :trangthai)")
    Page<PhatTu> searchPhatTu(@Param("ten") String ten, @Param("phapdanh") String phapDanh, @Param("gioiTinh") Integer gioiTinh, @Param("trangthai") Boolean trangThai, Pageable pageable);
    PhatTu findBySoDienThoai(String sdt);
    PhatTu findByEmail(String email);
    PhatTu findByChua_Id(Integer id);

    PhatTu findByKieuThanhVien_Id(Integer id);

    @Transactional
    void deletePhatTuById(Integer id);

}
