package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.CartForm;
import com.example.service.CartService;

import jakarta.servlet.http.HttpSession;

/**
 * カートに商品を追加、削除、カートの表示をするコントローラ.
 * 
 * @author hongo
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService service;

	@Autowired
	private HttpSession session;

	/**
	 * 選択されたOrderItemを登録する.
	 * 
	 * @param form CartForm
	 * @return 商品一覧ページに移動
	 */
	@PostMapping("/insertOrderItem")
	public String insertOrderItem(CartForm form) {
		Integer userId = null;
		User user = (User) session.getAttribute("user");
		Integer dummyId = null;

		if (user == null) {
			dummyId = session.hashCode();
			session.setAttribute("dummyId", dummyId);
			service.addItem(form, dummyId);
		} else {
			userId = user.getId();
			service.addItem(form, userId);
		}
		return "redirect:/cart/showCart";
	}

	/**
	 * カートの中身を表示する.
	 * 
	 * @param model リクエストスコープ用
	 * @return カート詳細画面に移動
	 */
	@GetMapping("/showCart")
	public String showCart(Model model) {

		User user = (User) session.getAttribute("user");
		Integer userId = 0;
		Order order = null;
		Integer dummyId = null;
		dummyId = (Integer) session.getAttribute("dummyId");

		if (user == null && dummyId!=null) {
			order = service.showCart(dummyId);
			session.setAttribute("order", order);
			session.setAttribute("throughOrderConfirmation", true);
		} else if(user != null) {
			userId = user.getId();
			order = service.showCart(userId);
			session.setAttribute("order", order);
		}
		if (order == null) {
			model.addAttribute("NoOrder", "カート内は空です。");
		} else {

			if (order.getOrderItemList().size() == 0) {
				model.addAttribute("NoOrder", "カート内は空です。");
			} else {
				model.addAttribute("order", order);
			}
		}
		return "cart_list";
	}

	/**
	 * 選択されたOrderItemとそのOrderToppingを削除する.
	 * 
	 * @param orderItemId 選択されたorderitemId
	 * @return 削除後のカート一覧画面
	 */
	@PostMapping("/deleteOrderItem")
	public String deleteOrderItem(Integer orderItemId, String toOrderConfirm) {
		service.deleteOrderItem(orderItemId);
		session.removeAttribute("order");
		if (toOrderConfirm.equals("toOrderConfirm")) {
			return "redirect:/order/toOrder";
		}
		return "redirect:/cart/showCart";
	}
}
