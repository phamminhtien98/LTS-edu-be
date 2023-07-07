package com.quanlyphattu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dondangkys")
public class DonDangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "phattuid")
    @JsonIgnoreProperties(value = {"donDangKyList", "donDangKyListNguoiXuLy", "daoTrangList", "phatTuDaoTrangListpt", "truTriChuaList"})
    private PhatTu phatTu;

    @ManyToOne
    @JoinColumn(name = "nguoixulyid")
    @JsonIgnoreProperties(value = {"donDangKyList", "donDangKyListNguoiXuLy", "daoTrangList", "phatTuDaoTrangListpt", "truTriChuaList"})
    private PhatTu nguoiXuLy;

    @Column(name = "ngayguidon", length = 7)
    private LocalDate ngayGuiDon;

    @Column(name = "ngayxuly", length = 7)
    private LocalDate ngayXuLy;

    @Column(name = "trangthaidon")
    private Integer trangThaiDon;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "daotrangid")
    @JsonIgnoreProperties(value = {"donDangKyList", "phatTuDaoTrangListdt", "phatTuNguoiTruTri"})
    private DaoTrang daoTrang;

    public DonDangKy() {
    }

    public DaoTrang getDaoTrang() {
        return daoTrang;
    }

    public void setDaoTrang(DaoTrang daoTrang) {
        this.daoTrang = daoTrang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PhatTu getPhatTu() {
        return phatTu;
    }

    public void setPhatTu(PhatTu phatTu) {
        this.phatTu = phatTu;
    }

    public PhatTu getNguoiXuLy() {
        return nguoiXuLy;
    }

    public void setNguoiXuLy(PhatTu nguoiXuLy) {
        this.nguoiXuLy = nguoiXuLy;
    }

    public LocalDate getNgayGuiDon() {
        return ngayGuiDon;
    }

    public void setNgayGuiDon(LocalDate ngayGuiDon) {
        this.ngayGuiDon = ngayGuiDon;
    }

    public LocalDate getNgayXuLy() {
        return ngayXuLy;
    }

    public void setNgayXuLy(LocalDate ngayXuLy) {
        this.ngayXuLy = ngayXuLy;
    }

    public Integer getTrangThaiDon() {
        return trangThaiDon;
    }

    public void setTrangThaiDon(Integer trangThaiDon) {
        this.trangThaiDon = trangThaiDon;
    }
}
