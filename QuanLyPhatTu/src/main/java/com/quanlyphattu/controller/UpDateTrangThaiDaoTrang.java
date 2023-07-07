package com.quanlyphattu.controller;

import com.quanlyphattu.model.DaoTrang;
import com.quanlyphattu.repository.IDaoTrangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class UpDateTrangThaiDaoTrang {

    @Autowired
    private IDaoTrangRepository daoTrangRepository;

    @Scheduled(cron = "0 0 1,13 * * *")
    public void upDateTrangThaiDaoTrang() {
        List<DaoTrang> daoTrangList = daoTrangRepository.findByThoiGianToChucLessThanNow();
        for (DaoTrang daoTrang : daoTrangList) {
            daoTrang.setDaKetThuc(true);
            daoTrangRepository.save(daoTrang);
        }
    }
}
