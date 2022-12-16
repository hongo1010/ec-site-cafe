package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * OrderToppingの操作を行うリポジトリ.
 * 
 * @author hongo
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * OrderToppingを１件登録する.
	 * 
	 * @param orderTopping OrderToppingオブジェクト
	 */
	public void insert(OrderTopping orderTopping) {

		String sql = "INSERT INTO order_toppings (topping_id,order_item_id) VALUES (:toppingId, :orderItemId);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);

		template.update(sql, param);
	}

	/**
	 * OrderItemIdを元に該当するOrderToppingを削除する.
	 * 
	 * @param orderItemId orderItemId
	 */
	public void delete(Integer orderItemId) {

		String sql = "DELETE FROM order_toppings WHERE order_item_id = :orderItemId ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);

		template.update(sql, param);
	}

}
