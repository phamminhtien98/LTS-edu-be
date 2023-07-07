package com.quanlyphattu.repository;

import com.quanlyphattu.model.PhatTuDaoTrang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IPhatTuDaoTrangRepository extends JpaRepository<PhatTuDaoTrang, Integer> {
    @Transactional
    void deletePhatTuDaoTrangById(Integer id);

    @Transactional
    void deletePhatTuDaoTrangByPhatTu_Id(Integer id);

    @Transactional
    void deletePhatTuDaoTrangByDaoTrang_Id(Integer id);

    List<PhatTuDaoTrang> findPhatTuDaoTrangsByDaoTrang_Id(Integer id);
}
