package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.CartService;
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
	private CartService cartService;

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

		// throughOrderConfirmationは未ログイン時にカートに商品を入れた場合、セッションスコープにセットされる。
		Boolean isThroughOrderConfirmation = (Boolean) session.getAttribute("throughOrderConfirmation");

		// もし、未ログイン時にカートに商品をいれてisThroughOrderConfirmationがあった場合、未ログインのカートの中身をログイン後のカートの中身に移動し、オーダー画面へ遷移する
		if (isThroughOrderConfirmation != null) {
			session.removeAttribute("throughOrderConfirmation");
			Integer dummyId = (Integer) session.getAttribute("dummyId");
			if (dummyId != null) {

				// dummyIDから未ログイン時のオーダー情報を取ってくる
				Order dummyOrder = cartService.searchOrder(dummyId);
				Integer dummyOrderId = dummyOrder.getId();
				List<OrderItem> dummyOrderItemList = cartService.getOrderItemListByOrderId(dummyOrderId);
			
				session.removeAttribute("order");
				model.addAttribute("order", dummyOrder);
				session.setAttribute("order", dummyOrder);

				// ログイン後のオーダー情報を取ってくる
				Order trueOrder = cartService.searchOrder(user.getId());

				// ログイン後のオーダーが空じゃなかった場合
				if (trueOrder != null) {
					// 未ログイン時のオーダー情報のオーダーIDをログイン後のオーダーIDに更新する（つまりログイン後のオーダーに追加される）
					Order transferdOrder = cartService.transferItemList(trueOrder, dummyOrderItemList);
					cartService.update(transferdOrder);

					// 新しいオーダー情報をセット
					session.removeAttribute("order");
					model.addAttribute("order", transferdOrder);
					session.setAttribute("order", transferdOrder);

				}else {
					session.removeAttribute("order");
					model.addAttribute("order", null);
					session.setAttribute("order", null);
				}

				// 未ログイン時に登録されていたオーダー情報を一度削除
				session.removeAttribute("dummyOrder");
				session.removeAttribute("dummyId");
				return "redirect:/order/toOrder";
			}
		}

		// 未ログインで何もせずログインを行った場合は商品一覧画面に遷移
		return "redirect:/showItemList/ItemList";

	}
}
