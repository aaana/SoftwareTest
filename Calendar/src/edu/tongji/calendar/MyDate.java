package edu.tongji.calendar;

public class MyDate {
	private int year;//ï¿½ï¿½ï¿½Ô·ï¿½Î§ï¿½ï¿½ï¿½Ë¹ï¿½ï¿½ï¿½
	private int month;//1 12  mid:6
	private int day;//1 30 mid:15
/*
	private class MonthDay {
		public int month;
		public int dayNum;

		public MonthDay(int month, int dayNum) {
			this.month = month;
			this.dayNum = dayNum;
		}
	}
*/
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public MyDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public MyDate() {
	}

	private Boolean isLeapYear() {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		}
		return false;
	}

/*
	private MonthDay[] initMonthDay(int year) {
		MonthDay[] monthDay = new MonthDay[12];
		monthDay[0] = new MonthDay(1, 31);
		monthDay[2] = new MonthDay(3, 31);
		monthDay[3] = new MonthDay(4, 30);
		monthDay[4] = new MonthDay(5, 31);
		monthDay[5] = new MonthDay(6, 30);
		monthDay[6] = new MonthDay(7, 31);
		monthDay[7] = new MonthDay(8, 31);
		monthDay[8] = new MonthDay(9, 30);
		monthDay[9] = new MonthDay(10, 31);
		monthDay[10] = new MonthDay(11, 30);
		monthDay[11] = new MonthDay(12, 31);
		if (isLeapYear()) {
			monthDay[1] = new MonthDay(2, 29);
		} else {
			monthDay[1] = new MonthDay(2, 28);
		}
		return monthDay;
	}
*/

	public String toString() {
		return year + "Äê" + month + "ÔÂ" + day + "ÈÕ";
	}

	public MyDate nextDay() {
		MyDate myDate = null;
//		MonthDay[] monthDay = initMonthDay(year);
		int[] monthDay = {31,28,31,30,31,30,31,31,30,31,30,31};
		if(isLeapYear()){
			monthDay[1] = 29;
		}
		for (int i = 0; i < monthDay.length; i++) {
			if (month == i+1) {
				if (day > 0 && day < monthDay[i]) {
					myDate = new MyDate(year, month, day + 1);
				} else if (day == monthDay[i]) {
					if (month == 12) {
						myDate = new MyDate(year + 1, 1, 1);
					} else {
						myDate = new MyDate(year, month + 1, 1);
					}
				} else {
					System.out.println("ÈÕÆÚ³¬³ö·¶Î§");
				}
				return myDate;
			}
		}
		System.out.println("ÔÂ·Ý³¬³ö·¶Î§");
		return myDate;

	}

	public boolean equals( MyDate anotherDate )
	{
		if( year == anotherDate.getYear() && month == anotherDate.getMonth() && day == anotherDate.getDay() )
			return true;
		else
			return false;
	}
}
