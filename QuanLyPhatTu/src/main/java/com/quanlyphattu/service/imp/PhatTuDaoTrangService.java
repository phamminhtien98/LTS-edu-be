package com.quanlyphattu.service.imp;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.model.DaoTrang;
import com.quanlyphattu.model.PhatTuDaoTrang;
import com.quanlyphattu.repository.IDaoTrangRepository;
import com.quanlyphattu.repository.IPhatTuDaoTrangRepository;
import com.quanlyphattu.repository.IPhatTuRepository;
import com.quanlyphattu.service.IPhatTuDaoTrangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhatTuDaoTrangService implements IPhatTuDaoTrangService {
    @Autowired
    private IPhatTuDaoTrangRepository phatTuDaoTrangRepository;
    @Autowired
    private IPhatTuRepository phatTuRepository;
    @Autowired
    private IDaoTrangRepository daoTrangRepository;

    @Override
    public List<PhatTuDaoTrang> findPhatTuDaoTrangByDaoTrangId(Integer id) {
        return  phatTuDaoTrangRepository.findPhatTuDaoTrangsByDaoTrang_Id(id);
    }

    @Override
    public ResponseEntity<?> findPhatTuDaoTrangById(Integer id) {
        PhatTuDaoTrang phatTuDaoTrang = phatTuDaoTrangRepository.findById(id).orElse(null);
        if (phatTuDaoTrang != null) {
            return ResponseEntity.ok(phatTuDaoTrang);
        }
        return ResponseEntity.ok("Không tìm thấy đạo tràng này");
    }

    @Override
    public ResponseEntity<?> themPhatTuDaoTrang(PhatTuDaoTrang phatTuDaoTrang) {
        List<String> err = CommonUtil.validator(phatTuDaoTrang);
        if (phatTuDaoTrang.getPhatTu().getId() == null) {
            err.add("Lỗi trường id phật tử");
        } else {
            if (phatTuRepository.findById(phatTuDaoTrang.getPhatTu().getId()).isEmpty()) {
                err.add("Không tìm thấy phật tử này");
            }
        }
        if (phatTuDaoTrang.getDaoTrang().getId() == null) {
            err.add("Lỗi trường id đạo tràng");
        } else {
            if (daoTrangRepository.findById(phatTuDaoTrang.getDaoTrang().getId()).isEmpty()) {
                err.add("Không tìm thấy đạo tràng này");
            }
        }
        if (err.isEmpty()) {
            DaoTrang daoTrang = daoTrangRepository.findById(phatTuDaoTrang.getDaoTrang().getId()).orElse(null);
            int soThanhVien = 0;
            if (daoTrang.getPhatTuDaoTrangListdt() == null) {
                soThanhVien = 1;
            } else {
                //= số phật tử tham gia + 1 pt mới + chủ trì
                soThanhVien = daoTrang.getPhatTuDaoTrangListdt().size() + 1 + 1;
            }
            daoTrang.setSoThanhVienThamGia(soThanhVien);
            daoTrangRepository.save(daoTrang);
            phatTuDaoTrangRepository.save(phatTuDaoTrang);
            return ResponseEntity.ok("Thêm thành công");
        }
        return ResponseEntity.ok(err);
    }

    @Override
    public ResponseEntity<?> suaPhatTuDaoTrang(PhatTuDaoTrang phatTuDaoTrang) {
        List<String> err = CommonUtil.validator(phatTuDaoTrang);
        Integer idDaoTrangCurrent = null;
        Integer idDaoTrangNew = null;
        if (phatTuDaoTrang.getId() == null) {
            err.add("Thiếu trường id phật tử đạo tràng cần cập nhật");
        } else {
            PhatTuDaoTrang phatTuDaoTrangCurrent = phatTuDaoTrangRepository.findById(phatTuDaoTrang.getId()).orElse(null);
            if (phatTuDaoTrangCurrent == null) {
                err.add("Không tìm thấy phật tử đạo tràng này");
            } else {
                if (phatTuDaoTrang.getDaoTrang().getId() == null || daoTrangRepository.findById(phatTuDaoTrang.getDaoTrang().getId()).orElse(null) == null) {
                    err.add("Không tìm thấy đạo tràng này");
                } else {
                    idDaoTrangCurrent = phatTuDaoTrangCurrent.getDaoTrang().getId();
                    idDaoTrangNew = phatTuDaoTrang.getDaoTrang().getId();
                }
                if (phatTuDaoTrang.getPhatTu().getId() == null || phatTuRepository.findById(phatTuDaoTrang.getPhatTu().getId()).orElse(null) == null) {
                    err.add("Không tìm thấy phật tử này");
                } else {
                    phatTuDaoTrangCurrent.setPhatTu(phatTuDaoTrang.getPhatTu());
                }
                if (phatTuDaoTrang.getDaThamGia() != null) {
                    phatTuDaoTrangCurrent.setDaThamGia(phatTuDaoTrang.getDaThamGia());
                }
                if (phatTuDaoTrang.getLyDoKhongThamGia() != null) {
                    phatTuDaoTrangCurrent.setLyDoKhongThamGia(phatTuDaoTrang.getLyDoKhongThamGia());
                }
                if (err.isEmpty()) {
                    if (idDaoTrangCurrent != null && idDaoTrangNew != null) {
                        DaoTrang daoTrangCurrent = daoTrangRepository.findById(phatTuDaoTrangCurrent.getDaoTrang().getId()).orElse(null);
                        DaoTrang daoTrangNew = daoTrangRepository.findById(phatTuDaoTrang.getDaoTrang().getId()).orElse(null);
                        int soThanhVienDaoTrangCurrent = 0;
                        if (daoTrangCurrent.getPhatTuDaoTrangListdt() == null) {
                            soThanhVienDaoTrangCurrent = 1;
                        } else {
                            //= số phật tử tham gia + 1 pt mới + chủ trì
                            soThanhVienDaoTrangCurrent = daoTrangCurrent.getPhatTuDaoTrangListdt().size() - 1 + 1;
                        }
                        int soThanhVienDaoTrangNew = 0;
                        if (daoTrangNew.getPhatTuDaoTrangListdt() == null) {
                            soThanhVienDaoTrangNew = 1;
                        } else {
                            //= số phật tử tham gia + 1 pt mới + chủ trì
                            soThanhVienDaoTrangNew = daoTrangNew.getPhatTuDaoTrangListdt().size() + 1 + 1;
                        }
                        daoTrangCurrent.setSoThanhVienThamGia(soThanhVienDaoTrangCurrent);
                        daoTrangNew.setSoThanhVienThamGia(soThanhVienDaoTrangNew);
                        daoTrangRepository.save(daoTrangCurrent);
                        daoTrangRepository.save(daoTrangNew);
                    }
                    phatTuDaoTrangRepository.save(phatTuDaoTrangCurrent);
                    return ResponseEntity.ok("cập nhật thành công");
                }
            }
        }
        return ResponseEntity.ok(err);
    }
}
