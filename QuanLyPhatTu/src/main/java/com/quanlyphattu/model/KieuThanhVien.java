package com.quanlyphattu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kieuthanhviens")
public class KieuThanhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code",columnDefinition = "TEXT")
    private String code;
    @Column(name = "tenkieu",columnDefinition = "TEXT")
    private String tenKieu;
    @OneToMany(mappedBy = "kieuThanhVien")
    @JsonIgnoreProperties(value = "kieuThanhVien")
    private List<PhatTu> phatTuList;

    public KieuThanhVien() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenKieu() {
        return tenKieu;
    }

    public void setTenKieu(String tenKieu) {
        this.tenKieu = tenKieu;
    }

    public List<PhatTu> getPhatTuList() {
        return phatTuList;
    }

    public void setPhatTuList(List<PhatTu> phatTuList) {
        this.phatTuList = phatTuList;
    }
}
