package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品詳細の操作を行うサービスクラス.
 * 
 * @author hongo
 *
 */
@Service
public class ShowDetailService {

	@Autowired
	private ItemRepository itemReposiroty;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 商品のIDから商品情報を検索し、トッピングリストとベースリストも全件検索を行うメソッド.
	 * @param id ID
	 * @return アイテム
	 */
	public Item showDetail(int id) {
		
		Item item = new Item();

		item = itemReposiroty.load(id);
		
		List<Topping>toppingList = toppingRepository.findAll();

		item.setToppingList(toppingList);
		
		return item;
	}

}
