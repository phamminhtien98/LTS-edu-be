package com.quanlyphattu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "daotrangs")
public class DaoTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "noitochuc", columnDefinition = "TEXT")
    private String noiToChuc;
    @Column(name = "sothanhvienthamgia")
    private Integer soThanhVienThamGia;
    @ManyToOne
    @JoinColumn(name = "nguoitrutriid")
    @JsonIgnoreProperties(value = {"daoTrangList", "chua.phatTuList", "chua.truTri", "kieuThanhVien", "donDangKyList", "donDangKyListNguoiXuLy", "phatTuDaoTrangListpt", "truTriChuaList"})
    @NotNull
    private PhatTu phatTuNguoiTruTri;
    @Column(name = "thoigiantochuc", length = 7)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime thoiGianToChuc;
    @Column(name = "noidung", columnDefinition = "TEXT")
    @NotNull
    private String noiDung;
    @Column(name = "daketthuc", columnDefinition = "BIT")
    private Boolean daKetThuc;

    @OneToMany(mappedBy = "daoTrang")
    @JsonManagedReference
    private List<PhatTuDaoTrang> phatTuDaoTrangListdt;

    @OneToMany(mappedBy = "daoTrang")
    @JsonIgnoreProperties(value = {"daoTrang"})
    private List<DonDangKy> donDangKyList;

    public DaoTrang() {
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

    public String getNoiToChuc() {
        return noiToChuc;
    }

    public void setNoiToChuc(String noiToChuc) {
        this.noiToChuc = noiToChuc;
    }

    public Integer getSoThanhVienThamGia() {
        return soThanhVienThamGia;
    }

    public void setSoThanhVienThamGia(Integer soThanhVienThamGia) {
        this.soThanhVienThamGia = soThanhVienThamGia;
    }

    public PhatTu getPhatTuNguoiTruTri() {
        return phatTuNguoiTruTri;
    }

    public void setPhatTuNguoiTruTri(PhatTu phatTuNguoiTruTri) {
        this.phatTuNguoiTruTri = phatTuNguoiTruTri;
    }

    public LocalDateTime getThoiGianToChuc() {
        return thoiGianToChuc;
    }

    public void setThoiGianToChuc(LocalDateTime thoiGianToChuc) {
        this.thoiGianToChuc = thoiGianToChuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Boolean getDaKetThuc() {
        return daKetThuc;
    }

    public void setDaKetThuc(Boolean daKetThuc) {
        this.daKetThuc = daKetThuc;
    }

    public List<PhatTuDaoTrang> getPhatTuDaoTrangListdt() {
        return phatTuDaoTrangListdt;
    }

    public void setPhatTuDaoTrangListdt(List<PhatTuDaoTrang> phatTuDaoTrangListdt) {
        this.phatTuDaoTrangListdt = phatTuDaoTrangListdt;
    }
}
