package com.quanlyphattu.service.imp;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.Chua;
import com.quanlyphattu.model.PhatTu;
import com.quanlyphattu.repository.IChuaRepository;
import com.quanlyphattu.repository.IPhatTuRepository;
import com.quanlyphattu.service.IChuaService;
import com.quanlyphattu.service.IDaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ChuaService implements IChuaService {
    @Autowired
    private IChuaRepository chuaRepository;
    @Autowired
    private IPhatTuRepository phatTuRepository;

    @Override
    public List<Chua> getAllChua() {
        return chuaRepository.findAll();
    }

    @Override
    public Page<Chua> findAllChua(Integer page, Integer pageSize) {
        return chuaRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public Page<Chua> searchChua(String tenChua, String truTri, String diaChi, String ngayThanhLapTu, String ngayThanhLapDen, Integer page, Integer pageSize) {
        if (tenChua.trim().isEmpty()) {
            tenChua = null;
        } else {
            tenChua = tenChua.trim();
        }
        if (truTri.trim().isEmpty()) {
            truTri = null;
        } else {
            truTri = truTri.trim();
        }
        if (diaChi.trim().isEmpty()) {
            diaChi = null;
        } else {
            diaChi = diaChi.trim();
        }
        if (ngayThanhLapTu.trim().isEmpty()) {
            ngayThanhLapTu = null;
        } else {
            ngayThanhLapTu = ngayThanhLapTu.trim();
        }
        if (ngayThanhLapDen.trim().isEmpty()) {
            ngayThanhLapDen = null;
        } else {
            ngayThanhLapDen = ngayThanhLapDen.trim();
        }
        LocalDateTime ngayThanhLapTu1 = null;
        LocalDateTime ngayThanhLapDen1 = null;
        try {
            ngayThanhLapTu1 = LocalDateTime.parse(ngayThanhLapTu, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            ngayThanhLapTu1 = null;
        }
        try {
            ngayThanhLapDen1 = LocalDateTime.parse(ngayThanhLapDen, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            ngayThanhLapDen1 = null;
        }
        return chuaRepository.searchChua(tenChua, truTri, diaChi, ngayThanhLapTu1, ngayThanhLapDen1, PageRequest.of(page - 1, pageSize));
    }

    @Override
    public ResponseEntity<?> findChuaById(Integer id) {
        Chua chua = chuaRepository.findById(id).orElse(null);
        if (chua != null) {
            return ResponseEntity.ok(chua);
        }
        return ResponseEntity.badRequest().body("Không tìm thấy chùa này");
    }

    @Override
    public ResponseEntity<?> themChua(Chua chua) {
        List<String> err = CommonUtil.validator(chua);
        if (err.isEmpty()) {
            if (err.isEmpty()) {
                chua.setcapNhat(LocalDateTime.now());
                PhatTu phatTu = new PhatTu();
                phatTu.setId(1);
                chua.setTruTri(phatTu);
                chuaRepository.save(chua);
                return ResponseEntity.ok("Thêm thành công");
            }
        }
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    public ResponseEntity<?> suaChua(Chua chua) {
        List<String> err = CommonUtil.validator(chua);
        if (chua.getId() == null) {
            err.add("Thiếu trường thông tin id chùa");
        } else {
            Chua chuaCurrent = chuaRepository.findById(chua.getId()).orElse(null);
            if (chuaCurrent == null) {
                err.add("Không tìm thấy chùa này");
            } else {
                if (chua.getTenChua() != null) {
                    chuaCurrent.setTenChua(chua.getTenChua());
                }
                if (chua.getNgayThanhLap() != null) {
                    chuaCurrent.setNgayThanhLap(chua.getNgayThanhLap());
                }
                if (chua.getDiaChi() != null) {
                    chuaCurrent.setDiaChi(chua.getDiaChi());
                }
                if (chua.getTruTri() != null) {
                    if (chua.getTruTri().getId() == null || phatTuRepository.findById(chua.getTruTri().getId()).orElse(null) == null) {
                        err.add("Không tìm thấy trụ trì này");
                    } else {
                        chuaCurrent.setTruTri(chua.getTruTri());
                    }
                }
                chuaCurrent.setCapNhat(LocalDateTime.now());
                if (err.isEmpty()) {
                    chuaRepository.save(chuaCurrent);
                    return ResponseEntity.ok("update thành công");
                }
            }
        }
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    public ResponseEntity<?> xoaChua(List<Integer> idList) {
//        try {
            for (Integer id : idList) {
                Chua chua = chuaRepository.findById(id).orElse(null);
                if (chua != null) {
                    List<PhatTu> phatTuList = chua.getPhatTuList();
                    for (PhatTu phatTu : phatTuList) {
                        phatTu.setChua(null);
                        phatTuRepository.save(phatTu);
                    }
                    chuaRepository.delete(chua);
                }
            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("err");
//        }
        return ResponseEntity.ok("ok");
    }
}
