package com.quanlyphattu.repository;

import com.quanlyphattu.model.DonDangKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IDonDangKyRepository extends JpaRepository<DonDangKy, Integer> {
    @Transactional
    void deleteDonDangKyById(Integer id);
    @Transactional
    void deleteDonDangKyByPhatTu_Id(Integer id);
    @Transactional
    void deleteDonDangKyByNguoiXuLy_Id(Integer id);
    @Transactional
    void deleteDonDangKyByDaoTrang_Id(Integer id);
}
