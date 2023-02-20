package com.mashibing.internalcommon.constant;

/**
 * @author xcy
 * @date 2023/2/20 - 10:31
 */
public class OrderConstant {

	/*
	订单状态
	1：订单开始
	2：司机接单
	3：去接乘客
	4：司机到达乘客起点
	5：乘客上车，司机开始行程
	6：到达目的地，行程结束，未支付
	7：发起收款
	8：支付完成
	9：订单取消
	 */
	/**
	 * 1.订单开始
	 */
	public static final int ORDER_START = 1;

	/**
	 * 2.司机接单
	 */
	public static final int DRIVER_RECEIVE_ORDER = 2;

	/**
	 * 3.司机去接乘客
	 */
	public static final int DRIVER_TO_PICK_UP_PASSENGER = 3;

	/**
	 * 4.司机到达乘客起点
	 */
	public static final int DRIVER_ARRIVED_DEPARTURE = 4;

	/**
	 * 5.乘客上车，司机开始行程
	 */
	public static final int DRIVER_PICK_UP_PASSENGER = 5;

	/**
	 * 6.到达目的地，行程结束，未支付
	 */
	public static final int PASSENGER_GET_OFF = 6;

	/**
	 * 7.发起收款
	 */
	public static final int INITIATE_COLLECTIONS = 7;

	/**
	 * 8.支付完成
	 */
	public static final int SUCCESS_PAY = 8;

	/**
	 * 9.订单取消
	 */
	public static final int ORDER_CANCEL = 9;
}
