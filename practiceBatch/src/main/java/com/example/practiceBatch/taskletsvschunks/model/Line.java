package com.example.practiceBatch.taskletsvschunks.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Line implements Serializable {

	private String name;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dob;
	private Long age;

	public Line(String name, LocalDate dob) {
		this.name = name;
		this.dob = dob;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(this.name);
		sb.append(",");
		sb.append(this.dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		if (this.age != null) {
			sb.append(",");
			sb.append(this.age);
		}
		sb.append("]");
		return sb.toString();
	}
}
