package com.example.demo.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminEntity;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepositry;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {
	@Autowired
	private AdminRepositry adminRepositry;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void saveAdmin(AdminForm adminForm) {
		AdminEntity admin = new AdminEntity();
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(passwordEncoder.encode(adminForm.getPassword()));
		adminRepositry.save(admin);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AdminEntity admin = adminRepositry.findByEmail(email);
		if (admin == null) {
			throw new UsernameNotFoundException("管理者が見つかりません: " + email);
		}
		return User.withUsername(admin.getEmail())
				.password(admin.getPassword())
				.roles("ADMIN")
				.build();
	}
}
