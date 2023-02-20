package com.mashibing.internalcommon.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchuangye
 * @since 2023-02-20
 */
@Data
public class PriceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private Double startFare;

    /**
     * 起步里程
     */
    private Integer startMile;

    /**
     * 计程单价
     */
    private Double unitPricePreMile;

    /**
     * 计时单价
     */
    private Double unitPricePreMinute;

    /**
     * 运价版本
     */
    private Integer fareVersion;

    /**
     * 运价类型编码
     */
    private String fareType;
}
