package com.gom.mago.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

	public static LocalDate dateToLocalDate(Date input) {
		return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
}
