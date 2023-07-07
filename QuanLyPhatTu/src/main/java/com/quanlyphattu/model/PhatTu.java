package com.quanlyphattu.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "phattus")
public class PhatTu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "ho", columnDefinition = "TEXT")
    private String ho;
    @NotNull
    @Column(name = "tendem", columnDefinition = "TEXT")
    private String tenDem;
    @NotNull
    @Column(name = "ten", columnDefinition = "TEXT")
    private String ten;
    @Column(name = "phapdanh", columnDefinition = "TEXT")
    private String phapDanh;
    @Column(name = "anhchup", columnDefinition = "TEXT")
    private String anhChup;
    @NotNull
    @Column(name = "sodienthoai", columnDefinition = "TEXT")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String soDienThoai;
    @NotNull
    @Column(name = "email", columnDefinition = "TEXT")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    private String email;
    @NotNull
    @JsonIgnore
    @Column(name = "matkhau", columnDefinition = "TEXT")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_])[A-Za-z\\d@$!%*?&_]{8,}$", message = "Mật khẩu phải có tối thiểu 8 ký tự có cả chữ hoa chữ thường và ký tự đặc biệt")
    private String matKhau;
    @Column(name = "ngaysinh", length = 7)
    private LocalDate ngaySinh;
    @Column(name = "ngayxuatgia", length = 7)
    private LocalDate ngayXuatGia;
    @Column(name = "dahoantuc", columnDefinition = "BIT")
    private Boolean daHoanTuc;
    @Column(name = "ngayhoantuc", length = 7)
    private LocalDate ngayHoanTuc;
    @Column(name = "gioitinh")
    private Integer gioiTinh;
    @Column(name = "ngaycapnhat", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayCapNhat;

    @ManyToOne
    @JoinColumn(name = "chuaid")
    @JsonIgnoreProperties(value = {"phatTuList", "truTri"})
    private Chua chua;

    @ManyToOne
    @JoinColumn(name = "kieuthanhvienid")
    @JsonIgnoreProperties(value = "phatTuList")
    private KieuThanhVien kieuThanhVien;

    @OneToMany(mappedBy = "phatTu")
    @JsonManagedReference
    private List<DonDangKy> donDangKyList;

    @OneToMany(mappedBy = "nguoiXuLy")
//    @JsonManagedReference
    @JsonIgnoreProperties(value = {"nguoiXuLy"})
    private List<DonDangKy> donDangKyListNguoiXuLy;

    @OneToMany(mappedBy = "phatTuNguoiTruTri")
    @JsonIgnoreProperties(value = {"phatTuNguoiTruTri", "phatTuDaoTrangListdt", "donDangKyList"})
    private List<DaoTrang> daoTrangList;

    @OneToMany(mappedBy = "phatTu")
    @JsonManagedReference
    private List<PhatTuDaoTrang> phatTuDaoTrangListpt;

    @OneToMany(mappedBy = "truTri")
    @JsonIgnoreProperties(value = {"truTri", "phatTuList"})
    private List<Chua> truTriChuaList;

    public PhatTu() {
    }

    public List<Chua> getTruTriChuaList() {
        return truTriChuaList;
    }

    public void setTruTriChuaList(List<Chua> truTriChuaList) {
        this.truTriChuaList = truTriChuaList;
    }

    public List<DaoTrang> getDaoTrangList() {
        return daoTrangList;
    }

    public void setDaoTrangList(List<DaoTrang> daoTrangList) {
        this.daoTrangList = daoTrangList;
    }

    public List<PhatTuDaoTrang> getPhatTuDaoTrangListpt() {
        return phatTuDaoTrangListpt;
    }

    public void setPhatTuDaoTrangListpt(List<PhatTuDaoTrang> phatTuDaoTrangListpt) {
        this.phatTuDaoTrangListpt = phatTuDaoTrangListpt;
    }

    public List<DonDangKy> getDonDangKyListNguoiXuLy() {
        return donDangKyListNguoiXuLy;
    }

    public void setDonDangKyListNguoiXuLy(List<DonDangKy> donDangKyListNguoiXuLy) {
        this.donDangKyListNguoiXuLy = donDangKyListNguoiXuLy;
    }

    public List<DonDangKy> getDonDangKyList() {
        return donDangKyList;
    }

    public void setDonDangKyList(List<DonDangKy> donDangKyList) {
        this.donDangKyList = donDangKyList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTenDem() {
        return tenDem;
    }

    public void setTenDem(String tenDem) {
        this.tenDem = tenDem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getPhapDanh() {
        return phapDanh;
    }

    public void setPhapDanh(String phapDanh) {
        this.phapDanh = phapDanh;
    }

    public String getAnhChup() {
        return anhChup;
    }

    public void setAnhChup(String anhChup) {
        this.anhChup = anhChup;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LocalDate getNgayXuatGia() {
        return ngayXuatGia;
    }

    public void setNgayXuatGia(LocalDate ngayXuatGia) {
        this.ngayXuatGia = ngayXuatGia;
    }

    public Boolean getDaHoanTuc() {
        return daHoanTuc;
    }

    public void setDaHoanTuc(Boolean daHoanTuc) {
        this.daHoanTuc = daHoanTuc;
    }

    public LocalDate getNgayHoanTuc() {
        return ngayHoanTuc;
    }

    public void setNgayHoanTuc(LocalDate ngayHoanTuc) {
        this.ngayHoanTuc = ngayHoanTuc;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public Chua getChua() {
        return chua;
    }

    public void setChua(Chua chua) {
        this.chua = chua;
    }

    public KieuThanhVien getKieuThanhVien() {
        return kieuThanhVien;
    }

    public void setKieuThanhVien(KieuThanhVien kieuThanhVien) {
        this.kieuThanhVien = kieuThanhVien;
    }
}
