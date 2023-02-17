package com.mashibing.servicedriveruser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchuangye
 * @since 2023-02-17
 */
@TableName("driver_car_binding_relationship")
@Data
public class DriverCarBindingRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long driverId;

    private Long carId;

    /**
     * 绑定状态（1：绑定，2：解绑）
     */
    private Integer bindState;

    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;

    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;
}
