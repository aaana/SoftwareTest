package edu.tongji.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("������Ҫ��ѯ�����ڣ��ո��������");  
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
        			System.out.println("��һ��Ϊ��"+nextDay);
        		}
    		}else{
    			System.out.println("�����ն�����Ϊ������");
    		}

        }catch(NumberFormatException e){
        	System.out.println("�����ն�����������!");
        }		
	}

}
