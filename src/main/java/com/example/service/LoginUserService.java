package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログインの処理を行うサービスクラス.
 * 
 * @author hongo
 *
 */
@Service
public class LoginUserService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * ログインの機能を持つメソッド.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return user メールアドレスとパスワードの検索結果
	 */
	public User login(String email, String password) {
		User user = userRepository.findByEmail(email);
		
		return user;
	}

}
