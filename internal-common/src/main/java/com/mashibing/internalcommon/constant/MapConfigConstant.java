package com.mashibing.internalcommon.constant;

/**
 * @author xcy
 * @date 2023/2/14 - 11:23
 */
public class MapConfigConstant {

	/**
	 * 路径规划地址
	 */
	public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";

	/**
	 * 路径规划地址的JSON对象中的属性
	 */
	public static final String STATUS = "status";

	public static final String ROUTE = "route";

	public static final String PATHS = "paths";
	/**
	 * 距离
	 */
	public static final String DISTANCE = "distance";
	/**
	 * 时长
	 */
	public static final String DURATION = "duration";

	/**
	 * 行政区域查询
	 */
	public static final String DISTRICT_URL = "https://restapi.amap.com/v3/config/district";

	/**
	 * 地图地区字典信息
	 */
	public static final String DISTRICTS = "districts";

	/**
	 * 地区编码
	 */
	public static final String ADCODE = "adcode";
	/**
	 * 地区名称
	 */
	public static final String NAME = "name";
	/**
	 * 地区级别：1：省/直辖市，2：市，3：区/县
	 */
	public static final String LEVEL = "level";

	/**
	 * 街道
	 */
	public static final String STREET = "street";
}