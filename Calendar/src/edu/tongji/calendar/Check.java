package edu.tongji.calendar;

public class Check 
{

	public static MyDate check( double year, double month, double day )
	{
    	int _year = (int)year;
    	int _month = (int)month;
   		int _day = (int)day;
		if( year <= 0 || month <= 0 || day <= 0 )
		{
			System.out.println( "年月日必须为正数！" );								
		}else if( year - (double)_year != 0 || month - (double)_month != 0 || day - (double)_day != 0 )
		{
			System.out.println( "年月日必须为整数！" );
		}else
		{
			MyDate myDate = new MyDate( (int)year, (int)month, (int)day);
	    	return myDate;	
		}
		return null;
	}
}
