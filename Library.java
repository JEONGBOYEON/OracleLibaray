package com.library;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.library.LibraryDTO;


public class Library {
	
	MyAuthenticator ma = new MyAuthenticator();
	LibraryDAO dao=new LibraryDAO();
	Scanner sc=new Scanner(System.in);
	String connId;

	//ȸ������
	public void insert()throws MyAuthenticator{

		try {

			LibraryDTO dto = new LibraryDTO(); 
			String id = "";
			String ps1 = "";
			String ps2 = "";
			String name = "";
			String Email = "";
			String Tel = "";

			boolean flagId = true;
			boolean flagPw = true;
			boolean flagName = true;
			boolean flagEmail = true;
			boolean flagTel = true;

			do{
				try{
					System.out.print("���̵� �Է��ϼ���: ");
					id = sc.next();					
					ma.logId(id);			
					dto.setId(id);
					
					flagId = false;
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}while(flagId);

			
			do {
				try {
					System.out.print("ù��° PW�� �Է��ϼ��� : ");
					ps1 = sc.next();
					System.out.print("�ι�° PW�� �Է��ϼ��� : ");
					ps2 = sc.next();
					ma.password(ps1, ps2);
					dto.setPw(ps1);

					flagPw = false;
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}while(flagPw);

			
			do {
				try {
					System.out.print("�̸��� �Է����ּ��� : ");
					name = sc.next();
					ma.nameCheck(name);
					dto.setName(name);
					
					flagName = false;
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}while(flagName);

			
			do {
				try {
					System.out.print("e-mail�� �Է��ϼ��� : ");
					Email = sc.next();
					ma.emailCheck(Email);
					dto.setEmail(Email);

					flagEmail = false;
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}while(flagEmail);

			
			do {
				try {
					System.out.print("��ȭ��ȣ�� �Է��ϼ��� : ");
					Tel = sc.next();
					ma.telCheck(Tel);			
					dto.setTel(Tel);
										
					flagTel = false;
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}while(flagTel);

			int result = dao.insertData(dto);

			if(result != 0){							//DB�� ����Ǿ� �� ����Ǹ� 1���� ��ȯ�ǰ� �ƴϸ� 0�� ��ȯ
				System.out.println();
				System.out.println("** ȸ������ �Ϸ� **");
				System.out.println();
			}
			else{
				System.out.println();
				System.out.println("�̹� ������� ���̵��Դϴ�.");
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println(e.toString());	
		}

	}

	//�α���
	public void login(){
		String tempid;
		String tempps;

		while(true){
			System.out.print("���̵� �Է��ϼ���: ");
			tempid=sc.next();

			System.out.print("��й�ȣ�� �Է��ϼ���: ");
			tempps=sc.next();
			
			System.out.println();

			if(dao.loginPs(tempid,tempps)==1){
				connId=tempid;
				System.out.println("** �α��� �����Ͽ����ϴ�. **");
				break;
			}
			else{
				System.out.println("** �α��� �����Ͽ����ϴ�. **");
			}
		}
	}

	//ȸ����ü���
	public void selectAll(){

		List<LibraryDTO> lists=dao.getList();

		Iterator<LibraryDTO> it=lists.iterator();

		System.out.println();
		System.out.println("< ȸ�� ���� ��ü ��� >");
		System.out.println();
		while(it.hasNext()){
			LibraryDTO dto=it.next();
			System.out.println(dto.toString());
		}

	}

	//ȸ��Ż��
	public void delete(){

		try {
			int result = dao.deleteData(connId);

			if(result!=0)
				System.out.println("** ȸ��Ż�� ���� **");
			else
				System.out.println("** ȸ��Ż�� ���� **");

		} catch (Exception e) {
			System.out.println(e.toString());

		}	

	}


	//����������		
	public void myselect(){

		LibraryDTO dto = dao.getList(connId);
		System.out.println();
		System.out.println("< �� ���� ���� >");
		System.out.println();
		System.out.println(dto.MytoString());

	}

	//�˻�
	public int search(){

		int result=0;
		
		System.out.println(">>�˻���å���� :");
		String searchB = sc.next();

		List<LibraryDTO> lists = dao.searchBook(searchB);

		Iterator<LibraryDTO> it = lists.iterator();

		System.out.println();
		System.out.println("< �˻��� å ����  >");
		System.out.println();
		while(it.hasNext()){
			result++;
			LibraryDTO dto = it.next();
			System.out.println(dto.toString2());
		}

		return result;
	}

	//����
	public void borrow(){

		System.out.println(">>������ ��ȣ :");
		int borrowB = sc.nextInt();

		dao.borrowBook(borrowB,connId);
		
	}		
	

	//���� ���
	public void bookingR(){

		System.out.println(">>����� ��ȣ :");
		int bookingRB = sc.nextInt();

		dao.bookingReturn(bookingRB,connId);
		
	}		
	
	//��������
	public int borrowInfo(){

		int count = 0;
		
		List<LibraryDTO> lists = dao.borrowBookInfo(connId);		

		Iterator<LibraryDTO> it = lists.iterator();

		System.out.println();
		System.out.println("< ���� ���� >");
		System.out.println();
		while(it.hasNext()){
			count++;
			LibraryDTO dto = it.next();

			System.out.println(dto.toString3());
		}
		
		return count;
		
	}		
	

	//������������      
	public int reserveSelect(){

		int chk=0;

		List<LibraryDTO> lists=dao.getListReserve(connId);

		Iterator<LibraryDTO> it=lists.iterator();

		System.out.println();
		System.out.println("< �������� >");
		System.out.println();
		while(it.hasNext()){
			chk++;
			LibraryDTO dto=it.next();
			System.out.println(dto.RestoString());
		}
		System.out.println();

		return chk;

	}
	
	//�ݳ�
	public void returnB(){

		int ch;
		
		do{
			System.out.print("*1.�ݳ��ϱ� 2.�ڷΰ���: ");
			ch = sc.nextInt();
		}while(ch<1||ch>2);

		switch(ch){
		case 1:
			System.out.println(">>�ݳ��� ��ȣ: ");
			int returnBN = sc.nextInt();
			dao.returnBook(returnBN,connId);
		case 2:
			return;
		}
		 
	}	
	
	

	//����
	public void booking(){

		System.out.println(">>���� å��ȣ?");
		String bnum=sc.next();


	}	

	//book��ü���
	public void bookSelectAll(){

		List<LibraryDTO> lists=dao.getList2();

		Iterator<LibraryDTO> it=lists.iterator();

		System.out.println();
		System.out.println("< å���� >");
		System.out.println();
		while(it.hasNext()){
			LibraryDTO dto=it.next();
			System.out.println(dto.toString2());
		}


	}
	
	//��ü������Ʈ 
	public int overUpdate(){

		int chk = 0;
		List<LibraryDTO> lists = dao.borrowBookInfo(connId);		
		Iterator<LibraryDTO> it = lists.iterator();

		chk=dao.overReturn(connId);
		
		return chk;
		
	}
	


}
