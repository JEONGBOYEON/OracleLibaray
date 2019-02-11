package com.library;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	
	private static Connection dbConn;											//DBConn이라는 변수를 담을 그릇을 만들어줬다.//연결자가 있어야하는데 그게 null이면
	
	public static Connection getConnection(){ 									//불러온다는
		
		if(dbConn==null){
			
			try {							  									//감싸준다는
				
				String url = "jdbc:oracle:thin:@192.168.16.11:1521:TestDB";		//jdbc의 thin방법으로 type4형식을 사용 @가져온다는 의미 1521포트를 사용 TestDB버젼에서//노트북은@localhost로 접속
				String user = "suzi";												//사용자계정
				String password = "a123";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");				//오라클에있는 드라이버를 검색하고있는지 //있어야지 오라클을 실행할 수 있다.//명령문대로 패키지의 클래스가 클래스를 차례대로 읽어온다는
				
				dbConn = DriverManager.getConnection(url,user,password);		//드라이브매니저를 통해서 접속하겠다. //dbConn한테 db를 연결해서 담기 위해, 입력한 선언문을 괄호안에 축소 시킬수 있음
							
				
			} catch (Exception e) {
				System.out.println(e.toString());
				
			}
			
		}
			
		return dbConn;															//리턴값 선언 해주고
		
	}

	public static void close(){													//문을 끊어줘야한다.
		
		if(dbConn!=null){														//무언가 있다는 의미인데 만약 null이면
			
			try {
				
				if(!dbConn.isClosed()){											//닫아준다.
					dbConn.close();												//받드시 선언해줘야 나중에 다시 접속했을 때 에러가 안생김
				}
								
				
			} catch (Exception e) {
				System.out.println(e.toString());
			
			}
			
			dbConn=null;  														//마지막은 항상 DB 초기화해줘야 쓰레기값이 안남음
		}
	}
			
}
