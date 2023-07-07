package com.quanlyphattu.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "phattudaotrangs")
public class PhatTuDaoTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "daotrangid")
    @JsonBackReference
    @NotNull
    private DaoTrang daoTrang;

    @ManyToOne
    @JoinColumn(name = "phattuid")
//    @JsonBackReference
    @NotNull
    @JsonIgnoreProperties(value = {"kieuThanhVien",
            "donDangKyList",
            "donDangKyListNguoiXuLy",
            "phatTuDaoTrangListpt",
            "phatTuDaoTrangListpt",
            "chua.phatTuList",
            "chua.truTri",
            "truTriChuaList",
            "daoTrangList"})
    private PhatTu phatTu;

    @Column(name = "dathamgia", columnDefinition = "BIT")
    private Boolean daThamGia;
    @Column(name = "lydokhongthamgia", columnDefinition = "TEXT")
    private String lyDoKhongThamGia;

    public PhatTuDaoTrang() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DaoTrang getDaoTrang() {
        return daoTrang;
    }

    public void setDaoTrang(DaoTrang daoTrang) {
        this.daoTrang = daoTrang;
    }

    public PhatTu getPhatTu() {
        return phatTu;
    }

    public void setPhatTu(PhatTu phatTu) {
        this.phatTu = phatTu;
    }

    public Boolean getDaThamGia() {
        return daThamGia;
    }

    public void setDaThamGia(Boolean daThamGia) {
        this.daThamGia = daThamGia;
    }

    public String getLyDoKhongThamGia() {
        return lyDoKhongThamGia;
    }

    public void setLyDoKhongThamGia(String lyDoKhongThamGia) {
        this.lyDoKhongThamGia = lyDoKhongThamGia;
    }
}
