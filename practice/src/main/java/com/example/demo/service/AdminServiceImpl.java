package com.example.demo.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminEntity;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepositry;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepositry adminRepositry;

	@Override
	public void saveAdmin(AdminForm adminForm) {

		if (adminRepositry.existsByEmail(adminForm.getEmail())) {
			throw new RuntimeException("このメールアドレスは既に登録されています");
		}
		AdminEntity admin = new AdminEntity();
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		try {
			String hashedPassword = hashPassword(adminForm.getPassword());
			admin.setPassword(hashedPassword);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("パスワードのハッシュ化に失敗しました", e);
		}
		adminRepositry.save(admin);
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] sha256_result = sha256.digest(password.getBytes());

		StringBuilder stringBuilder = new StringBuilder();
		{
			stringBuilder.append(String.format("%02x", new BigInteger(1, sha256_result)));
		}
		return stringBuilder.toString();
	}
}
