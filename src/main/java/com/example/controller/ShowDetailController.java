package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowDetailService;

/**
 * 商品詳細画面の表示を処理するコントローラークラス.
 * @author hongo
 *
 */
@Controller
@RequestMapping("/showDateil")
public class ShowDetailController {
	
	@Autowired
	private ShowDetailService service;

	/**
	 * idから商品詳細ページを表示
	 * @param id ID
	 * @param model リクエストスコープ
	 * @return 商品詳細画面
	 */
	@RequestMapping("/")
	public String showDetail(int id, Model model) {
		
		Item item = service.showDetail(id);
		
		model.addAttribute("item", item);

		return "item_detail";
	}
}
