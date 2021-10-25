package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
public class Device {

    /**
     * 产品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品型号
     */
    private String model;

}