package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品一覧を表示する操作を行うサービスクラス.
 * 
 * @author hongo
 *
 */
@Service
public class ShowItemListService {
	@Autowired
	private ItemRepository repository;

	/**
	 * 商品の情報を検索するメソッド.
	 * @param name
	 * @return
	 */
	public List<Item> showItemList(String name) {
		if (name == null) {
			List<Item> itemList = repository.findAll();
			return itemList;
		} else {

			List<Item> itemList = repository.findByItemName(name);
			return itemList;
		}
	}

}
