package com.quanlyphattu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chuas")
public class Chua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "tenchua", columnDefinition = "TEXT")
    private String tenChua;
    @Column(name = "ngaythanhlap", length = 7)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayThanhLap;
    @NotNull
    @Column(name = "diachi", columnDefinition = "TEXT")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "trutriid")
    @JsonIgnoreProperties(value = {"truTriChuaList","chua","kieuThanhVien","donDangKyList","donDangKyListNguoiXuLy","daoTrangList","phatTuDaoTrangListpt"})
    private PhatTu truTri;

    @Column(name = "capnhat", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime capNhat;

    @OneToMany(mappedBy = "chua")
    @JsonIgnoreProperties(value = {"chua", "kieuThanhVien", "donDangKyList", "donDangKyListNguoiXuLy", "daoTrangList", "phatTuDaoTrangListpt", "truTriChuaList"})
    private List<PhatTu> phatTuList;

    public Chua() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenChua() {
        return tenChua;
    }

    public void setTenChua(String tenChua) {
        this.tenChua = tenChua;
    }

    public LocalDateTime getNgayThanhLap() {
        return ngayThanhLap;
    }

    public void setNgayThanhLap(LocalDateTime ngayThanhLap) {
        this.ngayThanhLap = ngayThanhLap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public PhatTu getTruTri() {
        return truTri;
    }

    public void setTruTri(PhatTu truTri) {
        this.truTri = truTri;
    }

    public LocalDateTime getCapNhat() {
        return capNhat;
    }

    public void setCapNhat(LocalDateTime capNhat) {
        this.capNhat = capNhat;
    }

    public LocalDateTime capNhat() {
        return capNhat;
    }

    public void setcapNhat(LocalDateTime capNhat) {
        this.capNhat = capNhat;
    }

    public List<PhatTu> getPhatTuList() {
        return phatTuList;
    }

    public void setPhatTuList(List<PhatTu> phatTuList) {
        this.phatTuList = phatTuList;
    }
}
