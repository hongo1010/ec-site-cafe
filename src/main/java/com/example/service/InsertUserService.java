package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー情報を登録する操作を行うサービスクラス.
 * 
 * @author hongo
 *
 */
@Service
@Transactional
public class InsertUserService {
	@Autowired
	private UserRepository userRepository;



	/**
	 * ユーザー情報を登録するメソッド.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {

		userRepository.insert(user);
	}

	/**
	 * メールアドレスからユーザー情報を検索するメソッド. *
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User searchByEmail(String email) {
		return userRepository.findByEmail(email);

	}
}
