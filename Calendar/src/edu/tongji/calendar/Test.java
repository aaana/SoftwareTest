package edu.tongji.calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class Test 
{
	public static void main(String[] args) throws IOException 
	{  
		doTest( "testDataBasicBoundary.xlsx" );
		doTest( "testDataWorstBoundary.xlsx" );
		doTest("strongRobust.xlsx");
    }  
	
	static void doTest( String file_dir ) throws IOException
	{
		//璇诲彇骞舵祴璇�
        Workbook book = null;
        book = getExcelWorkbook( file_dir );
        Sheet sheet = getSheetByNum( book, 0 );
        
        int numOfData = sheet.getLastRowNum();       
        System.out.println( "共有" + numOfData + "个测试用例" );

        List< MyDate > testList = new ArrayList< MyDate >();
        List< MyDate > expectedList = new ArrayList< MyDate >();
        List< MyDate > resultList = new ArrayList< MyDate >();
        List< Boolean > list = new ArrayList< Boolean >();
        
        List< String > expectedOutput = new ArrayList< String >();
        List< Boolean > dataInvalidLog = new ArrayList< Boolean >();
        
        List< Double > cannotSavingTestInstance = new ArrayList< Double >();
        
        for( int i = 1 ; i <= numOfData ; i++ )
        {  
        	Row row = null;
            row = sheet.getRow( i );
            
            if( row != null )
            {
                double testYear = row.getCell( 0 ).getNumericCellValue();
                double testMonth = row.getCell( 1 ).getNumericCellValue();
                double testDay = row.getCell( 2 ).getNumericCellValue();
                
                MyDate testDate = Check.check( testYear, testMonth, testDay );
                if( testDate != null )
                {
                	testList.add( testDate );
            		MyDate resultDate = testDate.nextDay();
            		
            		if ( resultDate != null ) 
            		{
            			double expectedYear = row.getCell( 3 ).getNumericCellValue();
                        double expectedMonth = row.getCell( 4 ).getNumericCellValue();
                        double expectedDay = row.getCell( 5 ).getNumericCellValue();
                    	MyDate expectedDate = Check.check( expectedYear, expectedMonth, expectedDay );
                    	
                		expectedList.add( expectedDate );
                		resultList.add( resultDate );
            			boolean result = resultDate.equals( expectedDate );
            			list.add( result );
            			expectedOutput.add( null );
            			dataInvalidLog.add( null );
            			System.out.println( "NO " + i + ":  " + testDate + " => " + resultDate + " ==?== " + expectedDate + " 判断:" + result );     		
            		
            		}else
            		{
            			expectedList.add( null );
                		resultList.add( null );
            			list.add( null );
            			
            			expectedOutput.add( row.getCell( 5 ).getStringCellValue() );
            			dataInvalidLog.add( true );
            			
            			System.out.println( "NO " + i + ":  数据超出范围" );
            		}
                }else
                {
                	cannotSavingTestInstance.add( testYear );
        			cannotSavingTestInstance.add( testMonth );
        			cannotSavingTestInstance.add( testDay );
        			
                	testList.add( null );
                	expectedList.add( null );
            		resultList.add( null );
        			list.add( null );
        			
        			expectedOutput.add( row.getCell( 5 ).getStringCellValue() );
                	dataInvalidLog.add( false );
                	
                	System.out.println( "NO " + i + ":  输入数据非正数" );
                }
            }
        }
        
        //鍐欏洖excel
        List< List< MyDate > > dateList = new ArrayList< List< MyDate > >();
        dateList.add( testList );
        dateList.add( expectedList );
        dateList.add( resultList );
        
        writeIntoExcel( file_dir, book, dateList, list, expectedOutput, dataInvalidLog, cannotSavingTestInstance );
        
	}
    
	//閫夊畾宸ヤ綔琛�
	static Sheet getSheetByNum( Workbook book, int number )
	{  
		
        Sheet sheet = null;  
        
        try 
        {  
            sheet = book.getSheetAt( number );  
//          if( sheet == null )
//          {  
//              sheet = book.createSheet( "Sheet" + number );  
//          }  
        } catch ( Exception e ) 
        {  
            throw new RuntimeException( e.getMessage() );  
        }  
        return sheet;  
        
    }  
	
	//閫夊畾excel鏂囦欢
    public static Workbook getExcelWorkbook( String filePath ) throws IOException
    {  
    	
        Workbook book = null;  
        File file  = null;  
        FileInputStream fis = null;   
          
        try {  
            file = new File( filePath );  
            if( !file.exists() )
            {  
                throw new RuntimeException( "文件不存在" );  
            }else
            {  
                fis = new FileInputStream( file );  
                book = WorkbookFactory.create( fis ); 
            }  
        } catch ( Exception e ) 
        {  
            throw new RuntimeException( e.getMessage() );  
        } finally 
        {  
            if( fis != null )
            {  
                fis.close();  
            }  
        }  
        return book;  
        
    } 
  
    //鍐欏叆Xlsx
    public static void writeIntoExcel( String fileName, Workbook wb, List< List< MyDate > > dateList, List< Boolean > result, List< String > expectedOutput, List< Boolean > dataInvalidLog, List< Double > cannotSavingTestInstance ) 
    {
        try {
        	//Sheet sheet = wb.createSheet( "1" );
        	Sheet sheet = getSheetByNum( wb, 1 );
        	int instanceCount = dataInvalidLog.size();
        	Row row = sheet.createRow( 0 );
        	row.createCell( 0 ).setCellValue( "测试" );
        	row.createCell( 4 ).setCellValue( "预期" );
        	row.createCell( 8 ).setCellValue( "结果" );
        	row.createCell( 12 ).setCellValue( "判断" );
        	
        	int indexForCannotSavingInstance = 0;
        	if ( cannotSavingTestInstance.size() != 0 ) 
        	{
        		sheet.setColumnWidth( 11, 7000 );
        	}else
        	{
        		sheet.setColumnWidth( 11, 2857 );
        	}
        	
            for( int count = 0; count < instanceCount; count ++ )
            {

            	row = sheet.createRow( count + 1 );
            	
            	if( dataInvalidLog.get( count ) != null ) 
            	{
            		if( dataInvalidLog.get( count ) == true )
            		{
            			row.createCell( 0 ).setCellValue( dateList.get( 0 ).get( count ).getYear() );
                        row.createCell( 1 ).setCellValue( dateList.get( 0 ).get( count ).getMonth() );
                        row.createCell( 2 ).setCellValue( dateList.get( 0 ).get( count ).getDay() );
                        //CellRangeAddress region1 = new CellRangeAddress( count + 1, count + 1, (short) 6, (short) 8); 
                        //sheet.addMergedRegion(region1);
                        row.createCell( 11 ).setCellValue( "错误：数据超出范围" );
                        
            		}else
            		{
            			row.createCell( 0 ).setCellValue( cannotSavingTestInstance.get( indexForCannotSavingInstance ) );
                        row.createCell( 1 ).setCellValue( cannotSavingTestInstance.get( indexForCannotSavingInstance + 1 ) );
                        row.createCell( 2 ).setCellValue( cannotSavingTestInstance.get( indexForCannotSavingInstance + 2 ) );
                        indexForCannotSavingInstance = indexForCannotSavingInstance + 3; 
                        //CellRangeAddress region1 = new CellRangeAddress( count + 1, count + 1, (short) 6, (short) 8); 
                        //sheet.addMergedRegion(region1);
                        row.createCell( 11 ).setCellValue( "错误：输入数据为非正整数" );
            		}
            		
                    row.createCell( 7 ).setCellValue( expectedOutput.get( count ) );    
            	}else
            	{
            		int columnIndex = 0;
                	for( int i = 0; i < dateList.size(); i ++ )
                	{   
                        row.createCell( columnIndex ).setCellValue( dateList.get( i ).get( count ).getYear() );
                        row.createCell( columnIndex + 1 ).setCellValue( dateList.get( i ).get( count ).getMonth() );
                        row.createCell( columnIndex + 2 ).setCellValue( dateList.get( i ).get( count ).getDay() );
                        columnIndex = columnIndex + 4;
                    }
                	row.createCell( columnIndex ).setCellValue( result.get( count ) );
            	}
       
            }
            System.out.println("saving complete");
            FileOutputStream outputStream = new FileOutputStream(fileName);
            wb.write(outputStream);
            outputStream.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}