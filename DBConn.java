package com.library;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	
	private static Connection dbConn;											//DBConn�̶�� ������ ���� �׸��� ��������.//�����ڰ� �־���ϴµ� �װ� null�̸�
	
	public static Connection getConnection(){ 									//�ҷ��´ٴ�
		
		if(dbConn==null){
			
			try {							  									//�����شٴ�
				
				String url = "jdbc:oracle:thin:@192.168.16.11:1521:TestDB";		//jdbc�� thin������� type4������ ��� @�����´ٴ� �ǹ� 1521��Ʈ�� ��� TestDB��������//��Ʈ����@localhost�� ����
				String user = "suzi";												//����ڰ���
				String password = "a123";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");				//����Ŭ���ִ� ����̹��� �˻��ϰ��ִ��� //�־���� ����Ŭ�� ������ �� �ִ�.//��ɹ���� ��Ű���� Ŭ������ Ŭ������ ���ʴ�� �о�´ٴ�
				
				dbConn = DriverManager.getConnection(url,user,password);		//����̺�Ŵ����� ���ؼ� �����ϰڴ�. //dbConn���� db�� �����ؼ� ��� ����, �Է��� ������ ��ȣ�ȿ� ��� ��ų�� ����
							
				
			} catch (Exception e) {
				System.out.println(e.toString());
				
			}
			
		}
			
		return dbConn;															//���ϰ� ���� ���ְ�
		
	}

	public static void close(){													//���� ��������Ѵ�.
		
		if(dbConn!=null){														//���� �ִٴ� �ǹ��ε� ���� null�̸�
			
			try {
				
				if(!dbConn.isClosed()){											//�ݾ��ش�.
					dbConn.close();												//�޵�� ��������� ���߿� �ٽ� �������� �� ������ �Ȼ���
				}
								
				
			} catch (Exception e) {
				System.out.println(e.toString());
			
			}
			
			dbConn=null;  														//�������� �׻� DB �ʱ�ȭ����� �����Ⱚ�� �ȳ���
		}
	}
			
}
