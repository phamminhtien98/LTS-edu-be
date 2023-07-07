package com.quanlyphattu.service.imp;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.DaoTrang;
import com.quanlyphattu.model.PhatTu;
import com.quanlyphattu.repository.IDaoTrangRepository;
import com.quanlyphattu.repository.IDonDangKyRepository;
import com.quanlyphattu.repository.IPhatTuDaoTrangRepository;
import com.quanlyphattu.repository.IPhatTuRepository;
import com.quanlyphattu.service.IDaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaoTrangService implements IDaoTrangService {
    @Autowired
    private IDaoTrangRepository daoTrangRepository;
    @Autowired
    private IPhatTuRepository phatTuRepository;
    @Autowired
    private IPhatTuDaoTrangRepository phatTuDaoTrangRepository;
    @Autowired
    private IDonDangKyRepository donDangKyRepository;

    @Override
    public Page<DaoTrang> findAllDaoTrang(Integer page, Integer pageSize) {
        return daoTrangRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public Page<DaoTrang> searchDaoTrang(String noiToChuc, String chuTri, String thoiGianTu, String thoiGianDen, Boolean tinhTrang, Integer page, Integer pageSize) {
        if (noiToChuc.trim().isEmpty()) {
            noiToChuc = null;
        } else {
            noiToChuc = noiToChuc.trim();
        }
        if (chuTri.trim().isEmpty()) {
            chuTri = null;
        } else {
            chuTri = chuTri.trim();
        }
        if (thoiGianTu.trim().isEmpty()) {
            thoiGianTu = null;
        } else {
            thoiGianTu = thoiGianTu.trim();
        }
        if (thoiGianDen.trim().isEmpty()) {
            thoiGianDen = null;
        } else {
            thoiGianDen = thoiGianDen.trim();
        }
        LocalDateTime thoiGianTu1 = null;
        LocalDateTime thoiGianDen1 = null;
        try {
            thoiGianTu1 = LocalDateTime.parse(thoiGianTu, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            thoiGianTu1 = null;
        }
        try {
            thoiGianDen1 = LocalDateTime.parse(thoiGianDen, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            thoiGianDen1 = null;
        }
        return daoTrangRepository.searchDaoTrang(noiToChuc, chuTri, thoiGianTu1, thoiGianDen1, tinhTrang, PageRequest.of(page - 1, pageSize));
    }

    @Override
    public ResponseEntity<?> findDaoTrangById(Integer id) {
        DaoTrang daoTrang = daoTrangRepository.findById(id).orElse(null);
        if (daoTrang != null) {
            return ResponseEntity.ok(daoTrang);
        }
        return ResponseEntity.badRequest().body("Không tìm thấy đạo tràng này");
    }

    @Override
    public ResponseEntity<?> themDaoTrang(DaoTrang daoTrang) {
        List<String> err = CommonUtil.validator(daoTrang);
        if (daoTrang.getPhatTuNguoiTruTri().getId() == null || phatTuRepository.findById(daoTrang.getPhatTuNguoiTruTri().getId()).isEmpty()) {
            err.add("Không tìm thấy người Chủ trì");
        }
        if (daoTrang.getThoiGianToChuc().compareTo(LocalDateTime.now()) < 0) {
            daoTrang.setDaKetThuc(true);
        } else {
            daoTrang.setDaKetThuc(false);
        }
        if (err.isEmpty()) {
            daoTrang.setSoThanhVienThamGia(1);
            daoTrangRepository.save(daoTrang);
            return ResponseEntity.ok("Thêm thành công");
        }
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    public ResponseEntity<?> suaDaoTrang(DaoTrang daoTrang) {
        List<String> err = new ArrayList<>();
        if (daoTrang.getId() == null) {
            err.add("Thiếu trường ID đạo tràng");
        } else {
            DaoTrang daoTrangCurrent = daoTrangRepository.findById(daoTrang.getId()).orElse(null);
            if (daoTrangCurrent == null) {
                err.add("Không tìm thấy đạo tràng");
            } else {
                if(daoTrangCurrent.getPhatTuDaoTrangListdt().isEmpty()||daoTrangCurrent.getPhatTuDaoTrangListdt()==null){
                    daoTrangCurrent.setSoThanhVienThamGia(1);
                }else {
                    daoTrangCurrent.setSoThanhVienThamGia(daoTrangCurrent.getPhatTuDaoTrangListdt().size()+1);
                }
                if (daoTrang.getNoiToChuc() != null) {
                    daoTrangCurrent.setNoiToChuc(daoTrang.getNoiToChuc());
                }
                if (daoTrang.getThoiGianToChuc() != null) {
                    daoTrangCurrent.setThoiGianToChuc(daoTrang.getThoiGianToChuc());
                    if (daoTrang.getThoiGianToChuc().compareTo(LocalDateTime.now()) < 0) {
                        daoTrangCurrent.setDaKetThuc(true);
                    } else {
                        daoTrangCurrent.setDaKetThuc(false);
                    }
                }
                if (daoTrang.getPhatTuNguoiTruTri().getId() != null) {
                    PhatTu phatTu = phatTuRepository.findById(daoTrang.getPhatTuNguoiTruTri().getId()).orElse(null);
                    if (phatTu != null) {
                        daoTrangCurrent.setPhatTuNguoiTruTri(phatTu);
                    } else {
                        err.add("Không tìm thấy người chủ trì");
                    }
                }
                if (daoTrang.getNoiDung() != null) {
                    daoTrangCurrent.setNoiDung(daoTrang.getNoiDung());
                }
                if (err.isEmpty()) {
                    daoTrangRepository.save(daoTrangCurrent);
                    return ResponseEntity.ok("Update thành công");
                }
            }
        }
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    public ResponseEntity<?> xoaDaoTrangByIsd(List<Integer> idList) {
        try {
            for (Integer id : idList) {
                DaoTrang daoTrang = daoTrangRepository.findById(id).orElse(null);
                if (daoTrang != null) {
                    phatTuDaoTrangRepository.deletePhatTuDaoTrangByDaoTrang_Id(id);
                    donDangKyRepository.deleteDonDangKyByDaoTrang_Id(id);
                    daoTrangRepository.delete(daoTrang);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("err");
        }
        return ResponseEntity.ok("ok");
    }


}
