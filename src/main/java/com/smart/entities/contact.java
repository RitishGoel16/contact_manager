package com.smart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="contact")
public class contact {

	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Id
	private int id;
	private String name;
	private String secondname;
	private String work;
	private String email;
	private String phone;
	private String image;
	@Column(length=5000)
	private String Description;
	@ManyToOne
	@JsonIgnore
	private user user;
	
	public user getUser() {
		return user;
	}
	public void setUser(user user) {
		this.user = user;
	}
	
	
	
	@Override
	public String toString() {
		return "contact [id=" + id + ", name=" + name + ", secondname=" + secondname + ", work=" + work + ", email="
				+ email + ", phone=" + phone + ", image=" + image + ", Description=" + Description + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	
	public contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
