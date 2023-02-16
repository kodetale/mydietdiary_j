package com.diary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="diet")
@Getter @Setter
@ToString
public class Diet {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "memberid")
	private String memberId;
	
	private java.sql.Date date;
	
	private String time;
	
	private String food;
	
	private int kcal;
	

}
