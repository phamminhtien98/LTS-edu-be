package com.quanlyphattu.repository;

import com.quanlyphattu.model.KieuThanhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IKieuThanhVienRepository extends JpaRepository<KieuThanhVien, Integer> {
    @Transactional
    void deleteKieuThanhVienById(Integer id);
}
