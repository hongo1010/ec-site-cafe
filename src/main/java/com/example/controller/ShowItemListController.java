package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 *商品一覧の画面を操作するコントローラークラス.
 * @author hongo
 *
 */
@Controller
@RequestMapping("/showItemList")
public class ShowItemListController {

	@Autowired
	private ShowItemListService service;

	/**
	 * 商品情報を検索してリクエストスコープに情報を格納するメソッド.
	 * @param name　商品名
	 * @param model　リクエストスコープ
	 * @return 商品一覧ページ
	 */
	@RequestMapping("/ItemList")
	public String showItemList(String name , Model model) {
		List<Item> itemList = service.showItemList(name);
		model.addAttribute("itemList", itemList);
		return "item_list";
	}

}
