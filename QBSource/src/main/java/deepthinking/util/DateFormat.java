package deepthinking.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	
public static String simple(Date date,String type) {
	//yyyy-MM-dd hh:mm:ss
	SimpleDateFormat format=new SimpleDateFormat(type);
	return format.format(date);
	}
}
