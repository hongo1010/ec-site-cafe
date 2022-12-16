package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.InsertUserForm;
import com.example.service.InsertUserService;

/**
 * ユーザー登録画面の操作を行うコントローラー.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/insertUser")
public class InsertUserController {
	@Autowired
	InsertUserService service;

	/**
	 * 会員登録画面に飛ばすメソッド.
	 * 
	 * @param form ユーザー登録フォーム
	 * @return ユーザー登録画面
	 */
	@GetMapping("/toInsert")
	public String insert(InsertUserForm form) {

		return "register_user";
	}

	/**
	 * ユーザー情報を登録するメソッド.
	 * 
	 * @param form   ユーザーフォーム
	 * @param result エラーメッセージが格納されるオブジェクト
	 * @return ログイン画面へ飛ばすメソッド
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertUserForm form, BindingResult result) {
		
		//入力したメールアドレスが、既にデータベースに登録されていた場合エラーメッセージを出す　if文
		if (service.searchByEmail(form.getEmail()) != null) {
			result.rejectValue("email", null, "そのメールアドレスは使用されています");
		}
		//入力した再確認パスワードが、入力したパスワードと一致しなかった場合エラーメッセージを出す　if文
		if (!form.getPassword().equals(form.getConfimationPassword())) {
			result.rejectValue("confimationPassword", null, "パスワードが一致しません");
		}
		
		//エラーが出た場合、入力画面に戻す文
		if (result.hasErrors()) {
			return insert(form);
		}

		
		//Userオブジェクトを作成し、フォームの内容をユーザーにコピー、その内容をＤＢに登録し、ログイン画面に
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setName(form.getLastName() + form.getFirstName());
		service.insert(user);

		return "redirect:/loginUser/toLogin";

	}

}
