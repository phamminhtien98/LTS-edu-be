package com.quanlyphattu.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quanlyphattu.model.KieuThanhVien;
import com.quanlyphattu.model.PhatTu;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//kiểm tra mapping với phât tử dưới CSDL
@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String email;
    @JsonIgnore
    private String matKhau;
    private String soDienThoai;
    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    //Từ thông tin user chuyển sang thông tin CustomUserDetails
    public static CustomUserDetails mapUserToUserDetail(PhatTu phatTu){
        List<GrantedAuthority> authorities = new ArrayList<>();
        //thêm các kiểu thành viên vào đối tượng List<GrantedAuthority> authorities
        authorities.add(new SimpleGrantedAuthority((phatTu.getKieuThanhVien().getTenKieu())));
        //trả về đối tượng userDetail
        return new CustomUserDetails(phatTu.getId(),
                phatTu.getEmail(),
                phatTu.getMatKhau(),
                phatTu.getSoDienThoai(),
                authorities);
    }

    @Override
    public String getPassword() {
        return this.matKhau;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
