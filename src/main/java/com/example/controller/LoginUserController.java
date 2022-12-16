package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

import jakarta.servlet.http.HttpSession;

/**
 * ログイン画面の処理を行うコントローラークラス.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/loginUser")
public class LoginUserController {

	@Autowired
	private LoginUserService service;

	@Autowired
	private HttpSession session;

	@GetMapping("/toLogin")
	public String toLogin(LoginUserForm form) {
		return "login";
	}

	/**
	 * ログインの処理を行うメソッド.
	 * 
	 * @param form  ユーザー情報を格納しているフォーム
	 * @param model リクエストスコープ
	 * @return 商品一覧画面
	 */
	@PostMapping("/login")
	public String login(LoginUserForm form, Model model) {

		User user = service.login(form.getEmail(), form.getPassword());
		
		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています");
			return toLogin(form);

		}
		session.setAttribute("user", user);


		//未ログインで何もせずログインを行った場合は商品一覧画面に遷移
		return "redirect:/showItemList/ItemList";

	}
}
