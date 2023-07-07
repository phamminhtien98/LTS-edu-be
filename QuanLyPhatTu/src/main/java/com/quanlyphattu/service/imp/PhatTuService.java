package com.quanlyphattu.service.imp;

import com.quanlyphattu.common.CommonUtil;
import com.quanlyphattu.dto.DoiMatKhauDto;
import com.quanlyphattu.jwt.JwtTokenProvider;
import com.quanlyphattu.model.Chua;
import com.quanlyphattu.model.DaoTrang;
import com.quanlyphattu.model.KieuThanhVien;
import com.quanlyphattu.model.PhatTu;
import com.quanlyphattu.repository.*;
import com.quanlyphattu.response.JwtResponse;
import com.quanlyphattu.response.MessageResponse;
import com.quanlyphattu.security.CustomUserDetails;
import com.quanlyphattu.service.IDaoTrangService;
import com.quanlyphattu.service.IPhatTuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhatTuService implements IPhatTuService {
    @Autowired
    private IPhatTuRepository phatTuRepository;
    @Autowired
    private IChuaRepository chuaRepository;
    @Autowired
    private IKieuThanhVienRepository kieuThanhVienRepository;
    @Autowired
    private IDonDangKyRepository donDangKyRepository;
    @Autowired
    private IPhatTuDaoTrangRepository phatTuDaoTrangRepository;
    @Autowired
    private IDaoTrangRepository daoTrangRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IDaoTrangService daoTrangService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<PhatTu> findAllPhatTus() {
        return phatTuRepository.findAll();
    }

    @Override
    public Page<PhatTu> findAllPhatTu(Integer page, Integer sizePage) {
        return phatTuRepository.findAll(PageRequest.of(page - 1, sizePage));
    }

    @Override
    public Page<PhatTu> searchPhatTu(String ten, String phapDanh, Integer gioiTinh, Boolean trangThai, Integer page, Integer sizePage) {
        if (ten.trim().isEmpty()) {
            ten = null;
        } else {
            ten = ten.trim();
        }
        if (phapDanh.trim().isEmpty()) {
            phapDanh = null;
        } else {
            phapDanh = phapDanh.trim();
        }
        return phatTuRepository.searchPhatTu(ten, phapDanh, gioiTinh, trangThai, PageRequest.of(page - 1, sizePage));
    }

    @Override
    public ResponseEntity<?> findPhatTuById(Integer id) {
        PhatTu phatTu = phatTuRepository.findById(id).orElse(null);
        if (phatTu != null) {
            return ResponseEntity.ok(phatTu);
        }
        return ResponseEntity.badRequest().body("Không tìm thấy phật tử này");
    }

    @Override
    public ResponseEntity<?> themPhatTu(PhatTu phatTu) {
        List<String> err = CommonUtil.validator(phatTu);
        MessageResponse messageResponse = new MessageResponse();
        if (err.isEmpty()) {
            if (phatTuRepository.findByEmail(phatTu.getEmail()) != null) {
                err.add("Email đã tồn tại");
                messageResponse.setEmailTonTai(phatTu.getEmail());
            }
            if (phatTuRepository.findBySoDienThoai(phatTu.getSoDienThoai()) != null) {
                err.add("Số điện thoại đã sử dụng");
                messageResponse.setSoDienThoaiTonTai(phatTu.getSoDienThoai());
            }
            if (phatTu.getChua() != null) {
                if (phatTu.getChua().getId() == null || chuaRepository.findById(phatTu.getChua().getId()).isEmpty())
                    err.add("Chưa có chùa này trong data");
            }
            if (err.isEmpty()) {
                phatTu.setNgayCapNhat(LocalDateTime.now());
                phatTu.setKieuThanhVien(kieuThanhVienRepository.findById(CommonUtil.EKieuThanhVien.PHAT_TU.getCode()).orElse(null));
                phatTu.setMatKhau(passwordEncoder.encode(phatTu.getMatKhau()));
                phatTuRepository.save(phatTu);
                return ResponseEntity.ok("Thêm thành công");
            }
        }
        messageResponse.setMessage(err);
        return ResponseEntity.badRequest().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> doiMatKhau(DoiMatKhauDto doiMatKhauDto) {
        List<String> err = CommonUtil.validator(doiMatKhauDto);
        if (err.isEmpty()) {
//            PhatTu phatTu = phatTuRepository.findById(doiMatKhauDto.getId()).orElse(null);
            PhatTu phatTu = phatTuRepository.findByEmail(doiMatKhauDto.getEmail());
            if (phatTu != null) {
                if (!passwordEncoder.matches(doiMatKhauDto.getMatKhauCu(), phatTu.getMatKhau())) {
                    err.add("Mật khẩu cũ không chính xác");
                }
                if (err.isEmpty()) {
                    phatTu.setMatKhau(passwordEncoder.encode(doiMatKhauDto.getMatKhauMoi()));
                    phatTu.setNgayCapNhat(LocalDateTime.now());
                    phatTuRepository.save(phatTu);
                    return ResponseEntity.ok("Đổi mật khẩu thành công");
                }
            } else {
                err.add("Phật tử không tồn tại");
            }
        }
        return ResponseEntity.badRequest().body(err);
    }

    @Override
    public ResponseEntity<?> suaThongTinPhatTu(PhatTu phatTu) {
        List<String> err = new ArrayList<String>();
        MessageResponse messageResponse = new MessageResponse();
        if (phatTu.getId() != null) {
            PhatTu phatTuCurrent = phatTuRepository.findById(phatTu.getId()).orElse(null);
            String emailCurrent = phatTuCurrent.getEmail();
            String phoneNumberCurrent = phatTuCurrent.getSoDienThoai();
            if (phatTuCurrent != null) {
                if (phatTu.getHo() != null)
                    phatTuCurrent.setHo(phatTu.getHo());
                if (phatTu.getTenDem() != null)
                    phatTuCurrent.setTenDem(phatTu.getTenDem());
                if (phatTu.getTen() != null)
                    phatTuCurrent.setTen(phatTu.getTen());
                if (phatTu.getPhapDanh() != null)
                    phatTuCurrent.setPhapDanh(phatTu.getPhapDanh());
                if (phatTu.getAnhChup() != null)
                    phatTuCurrent.setAnhChup(phatTu.getAnhChup());
                if (phatTu.getSoDienThoai() != null)
                    phatTuCurrent.setSoDienThoai(phatTu.getSoDienThoai());
                if (phatTu.getEmail() != null)
                    phatTuCurrent.setEmail(phatTu.getEmail());
                if (phatTu.getNgaySinh() != null)
                    phatTuCurrent.setNgaySinh(phatTu.getNgaySinh());
                if (phatTu.getNgayXuatGia() != null)
                    phatTuCurrent.setNgayXuatGia(phatTu.getNgayXuatGia());
                if (phatTu.getDaHoanTuc() != null)
                    phatTuCurrent.setDaHoanTuc(phatTu.getDaHoanTuc());
                if (phatTuCurrent.getNgayHoanTuc() == null && phatTu.getDaHoanTuc() != null && phatTu.getDaHoanTuc())
                    phatTuCurrent.setNgayHoanTuc(LocalDate.now());
                if (phatTu.getNgayHoanTuc() != null && phatTu.getDaHoanTuc() != null && phatTuCurrent.getDaHoanTuc())
                    phatTuCurrent.setNgayHoanTuc(phatTu.getNgayHoanTuc());
                if (phatTu.getGioiTinh() != null)
                    phatTuCurrent.setGioiTinh(phatTu.getGioiTinh());

                phatTuCurrent.setNgayCapNhat(LocalDateTime.now());
                err = CommonUtil.validator(phatTuCurrent);
                if (err.isEmpty()) {
                    if (phatTu.getChua() != null && phatTu.getChua().getId() != null) {
                        Chua chua = chuaRepository.findById(phatTu.getChua().getId()).orElse(null);
                        if (chua != null) {
                            phatTuCurrent.setChua(chua);
                        } else {
                            err.add("Chùa không có trong data");
                        }
                    }
                    if(phatTu.getKieuThanhVien() != null && phatTu.getKieuThanhVien().getId() != null){
                        KieuThanhVien kieuThanhVien = kieuThanhVienRepository.findById(phatTu.getKieuThanhVien().getId()).orElse(null);
                        if (kieuThanhVien != null) {
                            phatTuCurrent.setKieuThanhVien(kieuThanhVien);
                        } else {
                            err.add("Kiểu thành viên không có trong data");
                        }
                    }
                    if (!emailCurrent.equals(phatTuCurrent.getEmail()) && phatTuRepository.findByEmail(phatTuCurrent.getEmail()) != null) {
                        err.add("Email đã được sử dụng");
                        messageResponse.setEmailTonTai(phatTuCurrent.getEmail());
                    }
                    if (!phoneNumberCurrent.equals(phatTuCurrent.getSoDienThoai()) && phatTuRepository.findBySoDienThoai(phatTuCurrent.getSoDienThoai()) != null) {
                        err.add("SDT đã được sử dụng");
                        messageResponse.setSoDienThoaiTonTai(phatTuCurrent.getSoDienThoai());
                    }
                    if (err.isEmpty()) {
                        phatTuRepository.save(phatTuCurrent);
                        return ResponseEntity.ok("Sửa thành công");
                    }
                }
            } else {
                return ResponseEntity.ok(err.add("Không có phật tử này"));
            }
        } else {
            return ResponseEntity.ok(err.add("Thiếu trường id phật tử"));
        }
        messageResponse.setMessage(err);
        return ResponseEntity.badRequest().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> login(PhatTu phatTu) {
        PhatTu phatTu1 = phatTuRepository.findByEmail(phatTu.getEmail());
        if (phatTu1 != null) {
            if (passwordEncoder.matches(phatTu.getMatKhau(), phatTu1.getMatKhau())) {

                Authentication authentication =
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phatTu1.getEmail(), phatTu1.getMatKhau()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                String jwt = jwtTokenProvider.generateToken(customUserDetails);


                return ResponseEntity.ok(new JwtResponse(jwt, phatTu1));
            }
        }
        return ResponseEntity.badRequest().body("Sai email hoặc mật khẩu");
    }

    @Override
    public ResponseEntity<?> findPhatTuByJwt(String jwt) {
        PhatTu phatTu = null;
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            //lấy email từ jwt
            String email = jwtTokenProvider.getPhatTuEmailFromJwt(jwt);
            //lấy thông tin phật tử từ email
            return ResponseEntity.ok(phatTuRepository.findByEmail(email));
        }
        return ResponseEntity.badRequest().body(phatTu);
    }

    @Override
    public ResponseEntity<?> deletePhatTuByIdList(List<Integer> ids) {
        try {
            for (Integer id : ids){
                if(id!=1){
                    PhatTu phatTu = phatTuRepository.findById(id).orElse(null);
                    PhatTu phatTu1 = new PhatTu();
                    phatTu1.setId(1);
                    if(phatTu!=null){
                        List<Chua> chuaList = phatTu.getTruTriChuaList();
                        List<DaoTrang> daoTrangList = phatTu.getDaoTrangList();
                        for (Chua chua : chuaList){
                            chua.setTruTri(phatTu1);
                            chuaRepository.save(chua);
                        }
                        for (DaoTrang daoTrang : daoTrangList){
                            daoTrang.setPhatTuNguoiTruTri(phatTu1);
                            daoTrangRepository.save(daoTrang);
                        }
                        phatTuDaoTrangRepository.deletePhatTuDaoTrangByPhatTu_Id(id);
                        donDangKyRepository.deleteDonDangKyByPhatTu_Id(id);
                        donDangKyRepository.deleteDonDangKyByNguoiXuLy_Id(id);
                        phatTuRepository.delete(phatTu);
                    }
                }
            }
        return ResponseEntity.ok("ok");
        }
        catch (Exception e){
        return ResponseEntity.badRequest().body("err");
        }
    }
}
