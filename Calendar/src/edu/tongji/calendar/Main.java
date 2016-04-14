package edu.tongji.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("请输入要查询的日期（空格隔开）：");  
		Scanner scanner = new Scanner(System.in);
		String read = scanner.nextLine();
		List<String> list = new ArrayList<String>(); 
		
        StringTokenizer token=new StringTokenizer(read," ");  
        while(token.hasMoreElements()){  
//         System.out.print(token.nextToken()+"  ");  
        	list.add(token.nextToken());
        } 
        try{
    		int year = Integer.parseInt(list.get(0));
    		int month = Integer.parseInt(list.get(1));
    		int day = Integer.parseInt(list.get(2));
    		if(year>0&&month>0&&day>0){
        		MyDate myDate = new MyDate(year, month, day);
        		MyDate nextDay = myDate.nextDay();
        		if (nextDay != null) {
        			System.out.println("下一天为："+nextDay);
        		}
    		}else{
    			System.out.println("年月日都必须为正数！");
    		}

        }catch(NumberFormatException e){
        	System.out.println("年月日都必须是整数!");
        }		
	}

}
