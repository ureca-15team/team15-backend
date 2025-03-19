package com.shop.living.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.shop.living.dto.Order;
import com.shop.living.dto.OrderItem;

@Mapper
public interface OrderDao {

    // ✅ 새로운 주문 생성
	@Insert("INSERT INTO orders (email) VALUES (#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void createOrder(Order order);

    // ✅ 주문 항목 추가 (주문한 상품 추가)
	@Insert("INSERT INTO order_items (order_id, prodcode, quantity) VALUES (#{orderId}, #{prodcode}, #{quantity})")
    void addOrderItem(@Param("orderId") int orderId, @Param("prodcode") int prodcode, @Param("quantity") int quantity);

    // ✅ 주문 목록 조회 (내가 구매한 상품 조회)
	@Select("SELECT * FROM orders WHERE email = #{email}")
    @Results(id = "orderMap", value = {
        @Result(property = "orderId", column = "order_id", id = true),
        @Result(property = "email", column = "email"),
        @Result(property = "orderDate", column = "order_date"),
        @Result(property = "orderItems", column = "order_id", 
                many = @Many(select = "com.shop.living.dao.OrderDao.getOrderItemsByOrderId"))
    })
    List<Order> getOrderHistory(String email);
    
    // ✅ 특정 주문에 대한 주문 상세 항목 조회
	// ✅ 특정 주문에 대한 주문 상세 항목 조회
	@Select("SELECT item_id, order_id, prodcode, quantity FROM order_items WHERE order_id = #{orderId}")
	@Results({
	    @Result(property = "itemId", column = "item_id"),
	    @Result(property = "orderId", column = "order_id"),
	    @Result(property = "prodcode", column = "prodcode"),
	    @Result(property = "quantity", column = "quantity")
	})
	List<OrderItem> getOrderItemsByOrderId(int orderId);


    // ✅ 주문 취소
    @Delete("DELETE FROM orders WHERE order_id = #{orderId}")
    void cancelOrder(int orderId);

    // ✅ 장바구니에서 선택한 상품 구매 후 장바구니에서 삭제
    @Delete({
        "<script>",
        "DELETE FROM cart WHERE email = #{email} AND prodcode IN",
        "<foreach item='item' collection='prodcodeList' open='(' separator=',' close=')'>",
        "#{item}",
        "</foreach>",
        "</script>"
    })
    void removeMultipleItems(String email, List<Integer> prodcodeList);
}
