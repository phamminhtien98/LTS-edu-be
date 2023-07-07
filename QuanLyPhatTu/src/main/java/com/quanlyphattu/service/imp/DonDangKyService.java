package com.quanlyphattu.service.imp;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.DaoTrang;
import com.quanlyphattu.model.DonDangKy;
import com.quanlyphattu.model.PhatTuDaoTrang;
import com.quanlyphattu.repository.IDaoTrangRepository;
import com.quanlyphattu.repository.IDonDangKyRepository;
import com.quanlyphattu.repository.IPhatTuDaoTrangRepository;
import com.quanlyphattu.repository.IPhatTuRepository;
import com.quanlyphattu.service.IDaoTrangService;
import com.quanlyphattu.service.IDonDangKyService;
import com.quanlyphattu.service.IPhatTuDaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DonDangKyService implements IDonDangKyService {
    @Autowired
    private IDonDangKyRepository donDangKyRepository;
    @Autowired
    private IPhatTuRepository phatTuRepository;
    @Autowired
    private IDaoTrangRepository daoTrangRepository;
    @Autowired
    private IPhatTuDaoTrangRepository phatTuDaoTrangRepository;

    @Override
    public Page<DonDangKy> findAllDonDangKy(Integer page, Integer pageSize) {
        return donDangKyRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public ResponseEntity<?> findDonDangKyById(Integer id) {
        DonDangKy donDangKy = donDangKyRepository.findById(id).orElse(null);
        if (donDangKy != null) {
            return ResponseEntity.ok(donDangKy);
        }
        return ResponseEntity.ok("Không tìm thấy đạo tràng này");
    }

    @Override
    public ResponseEntity<?> themDonDangKy(DonDangKy donDangKy) {
        List<String> err = CommonUtil.validator(donDangKy);
        if (err.isEmpty()) {
            if (phatTuRepository.findById(donDangKy.getPhatTu().getId()).isEmpty()) {
                err.add("Không tìm thấy phật tử này");
            }
            if (daoTrangRepository.findById(donDangKy.getDaoTrang().getId()).isEmpty()) {
                err.add("Không tìm thấy đạo tràng này");
            }
            if (err.isEmpty()) {
                donDangKy.setNgayGuiDon(LocalDate.now());
                donDangKy.setTrangThaiDon(CommonUtil.ETrangThaiDon.DANG_CHO.getCode());
                donDangKyRepository.save(donDangKy);
                return ResponseEntity.ok(donDangKy);
            }
        }
        return ResponseEntity.ok(err);
    }

    @Override
    public ResponseEntity<?> suaDonDangKy(DonDangKy donDangKy) {
        List<String> err = new ArrayList<>();
        if (donDangKy.getId() != null) {
            DonDangKy donDangKyCurrent = donDangKyRepository.findById(donDangKy.getId()).orElse(null);
            if (donDangKyCurrent != null) {
                if (donDangKy.getDaoTrang() != null) {
                    donDangKyCurrent.setDaoTrang(donDangKy.getDaoTrang());
                }
                if (donDangKy.getNguoiXuLy() != null) {
                    donDangKyCurrent.setNguoiXuLy(donDangKy.getNguoiXuLy());
                    if (donDangKy.getTrangThaiDon() != null) {
                        for (CommonUtil.ETrangThaiDon eTrangThaiDon : CommonUtil.ETrangThaiDon.values()) {
                            if (donDangKy.getTrangThaiDon() == eTrangThaiDon.getCode()) {
                                donDangKyCurrent.setTrangThaiDon(eTrangThaiDon.getCode());
                                break;
                            }
                        }
                        if (donDangKyCurrent.getTrangThaiDon() != null) {
                            donDangKyCurrent.setNgayXuLy(LocalDate.now());
                        }
                    }
                }
                err = CommonUtil.validator(donDangKyCurrent);
                if (err.isEmpty()) {
                    if (donDangKyCurrent.getNguoiXuLy() != null) {
                        if (donDangKyCurrent.getNguoiXuLy().getId() == null || phatTuRepository.findById(donDangKyCurrent.getNguoiXuLy().getId()).isEmpty()) {
                            err.add("Không tìm thấy người duyệt này");
                        } else {
                            if (donDangKyCurrent.getTrangThaiDon().equals(CommonUtil.ETrangThaiDon.DANG_CHO.getCode())) {
                                err.add("Người duyệt chưa chọn kiểu phê duyệt");
                            }
                        }
                    }
                    if (donDangKyCurrent.getDaoTrang() == null || donDangKyCurrent.getDaoTrang().getId() == null || daoTrangRepository.findById(donDangKyCurrent.getDaoTrang().getId()).isEmpty()) {
                        err.add("Không tìm thấy đạo tràng");
                    }
                    if (err.isEmpty()) {
                        donDangKyRepository.save(donDangKyCurrent);
                        if (donDangKyCurrent.getTrangThaiDon() == CommonUtil.ETrangThaiDon.DONG_Y.getCode()) {
                            PhatTuDaoTrang phatTuDaoTrang = new PhatTuDaoTrang();
                            phatTuDaoTrang.setPhatTu(donDangKyCurrent.getPhatTu());
                            phatTuDaoTrang.setDaoTrang(donDangKyCurrent.getDaoTrang());
                            phatTuDaoTrangRepository.save(phatTuDaoTrang);
                            DaoTrang daoTrang = daoTrangRepository.findById(donDangKyCurrent.getDaoTrang().getId()).orElse(null);
                            daoTrang.setSoThanhVienThamGia(daoTrang.getSoThanhVienThamGia() + 1);
                            daoTrangRepository.save(daoTrang);
                        }
                        return ResponseEntity.ok("Cập nhật thành công");
                    }
                }
            } else {
                err.add("Không tìm thấy đơn đăng ký này");
            }
        } else {
            err.add("Thiếu trường id đơn đăng ký");
        }
        return ResponseEntity.ok(err);
    }


}
