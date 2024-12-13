package com.bosch.stocktoship.entity;
 
 
import org.springframework.context.annotation.ComponentScan;

import jakarta.persistence.*;
 
//Author - Gurpartap Singh (INU2COB)


@Entity
public class Product {
 
	@Id
	@Column(unique = true, nullable = false)
	private Integer productCode;
	private String name;
	private Integer length;
	private Integer breadth;
	private Integer height;
	private Integer weight;
	private Integer volume;
	
	
 
	public Product(Integer productCode, String name, Integer length, Integer breadth, Integer height, Integer weight,
			 Integer volume) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		this.weight = weight;
		this.volume = volume;
	}
	
	
 
	public Product() {
		this(0, "", 0,0,0,0,0);
	}
 
	public int getProductCode() {
		return productCode;
	}
 
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public Integer getLength() {
		return length;
	}
 
	public void setLength(Integer length) {
		this.length = length;
	}
 
	public Integer getBreadth() {
		return breadth;
	}
 
	public void setBreadth(Integer breadth) {
		this.breadth = breadth;
	}
 
	public Integer getHeight() {
		return height;
	}
 
	public void setHeight(Integer height) {
		this.height = height;
	}
 
	public Integer getWeight() {
		return weight;
	}
 
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
 
	public Integer getVolume() {
		return volume;
	}
 
	@Override
	public String toString() {
		return "Product [productCode=" + productCode + ", name=" + name + ", length=" + length + ", breadth=" + breadth
				+ ", height=" + height + ", weight=" + weight + ", volume=" + volume + "]";
	}



	public void setVolume(Integer volume) {
		this.volume = volume;
	}
 
}