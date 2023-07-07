package com.quanlyphattu.security;

import com.quanlyphattu.model.PhatTu;
import com.quanlyphattu.repository.IPhatTuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private IPhatTuRepository phatTuRepository;
    //username dùng email phương thức ghi đè ko thể đổi tên
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PhatTu phatTu = phatTuRepository.findByEmail(email);
        if(phatTu==null){
            throw new UsernameNotFoundException("Không tìm thấy phật tử");
        }
        return CustomUserDetails.mapUserToUserDetail(phatTu);
    }
}
