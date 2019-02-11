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

	//회원가입
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
					System.out.print("아이디를 입력하세요: ");
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
					System.out.print("첫번째 PW를 입력하세요 : ");
					ps1 = sc.next();
					System.out.print("두번째 PW를 입력하세요 : ");
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
					System.out.print("이름을 입력해주세요 : ");
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
					System.out.print("e-mail을 입력하세요 : ");
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
					System.out.print("전화번호를 입력하세요 : ");
					Tel = sc.next();
					ma.telCheck(Tel);			
					dto.setTel(Tel);
										
					flagTel = false;
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}while(flagTel);

			int result = dao.insertData(dto);

			if(result != 0){							//DB랑 연결되어 잘 실행되면 1값이 반환되고 아니면 0이 반환
				System.out.println();
				System.out.println("** 회원가입 완료 **");
				System.out.println();
			}
			else{
				System.out.println();
				System.out.println("이미 사용중인 아이디입니다.");
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println(e.toString());	
		}

	}

	//로그인
	public void login(){
		String tempid;
		String tempps;

		while(true){
			System.out.print("아이디를 입력하세요: ");
			tempid=sc.next();

			System.out.print("비밀번호을 입력하세요: ");
			tempps=sc.next();
			
			System.out.println();

			if(dao.loginPs(tempid,tempps)==1){
				connId=tempid;
				System.out.println("** 로그인 성공하였습니다. **");
				break;
			}
			else{
				System.out.println("** 로그인 실패하였습니다. **");
			}
		}
	}

	//회원전체출력
	public void selectAll(){

		List<LibraryDTO> lists=dao.getList();

		Iterator<LibraryDTO> it=lists.iterator();

		System.out.println();
		System.out.println("< 회원 정보 전체 출력 >");
		System.out.println();
		while(it.hasNext()){
			LibraryDTO dto=it.next();
			System.out.println(dto.toString());
		}

	}

	//회원탈퇴
	public void delete(){

		try {
			int result = dao.deleteData(connId);

			if(result!=0)
				System.out.println("** 회원탈퇴 성공 **");
			else
				System.out.println("** 회원탈퇴 실패 **");

		} catch (Exception e) {
			System.out.println(e.toString());

		}	

	}


	//내정보보기		
	public void myselect(){

		LibraryDTO dto = dao.getList(connId);
		System.out.println();
		System.out.println("< 내 정보 보기 >");
		System.out.println();
		System.out.println(dto.MytoString());

	}

	//검색
	public int search(){

		int result=0;
		
		System.out.println(">>검색할책제목 :");
		String searchB = sc.next();

		List<LibraryDTO> lists = dao.searchBook(searchB);

		Iterator<LibraryDTO> it = lists.iterator();

		System.out.println();
		System.out.println("< 검색한 책 정보  >");
		System.out.println();
		while(it.hasNext()){
			result++;
			LibraryDTO dto = it.next();
			System.out.println(dto.toString2());
		}

		return result;
	}

	//대출
	public void borrow(){

		System.out.println(">>대출할 번호 :");
		int borrowB = sc.nextInt();

		dao.borrowBook(borrowB,connId);
		
	}		
	

	//예약 취소
	public void bookingR(){

		System.out.println(">>취소할 번호 :");
		int bookingRB = sc.nextInt();

		dao.bookingReturn(bookingRB,connId);
		
	}		
	
	//대출정보
	public int borrowInfo(){

		int count = 0;
		
		List<LibraryDTO> lists = dao.borrowBookInfo(connId);		

		Iterator<LibraryDTO> it = lists.iterator();

		System.out.println();
		System.out.println("< 대출 정보 >");
		System.out.println();
		while(it.hasNext()){
			count++;
			LibraryDTO dto = it.next();

			System.out.println(dto.toString3());
		}
		
		return count;
		
	}		
	

	//예약정보보기      
	public int reserveSelect(){

		int chk=0;

		List<LibraryDTO> lists=dao.getListReserve(connId);

		Iterator<LibraryDTO> it=lists.iterator();

		System.out.println();
		System.out.println("< 예약정보 >");
		System.out.println();
		while(it.hasNext()){
			chk++;
			LibraryDTO dto=it.next();
			System.out.println(dto.RestoString());
		}
		System.out.println();

		return chk;

	}
	
	//반납
	public void returnB(){

		int ch;
		
		do{
			System.out.print("*1.반납하기 2.뒤로가기: ");
			ch = sc.nextInt();
		}while(ch<1||ch>2);

		switch(ch){
		case 1:
			System.out.println(">>반납할 번호: ");
			int returnBN = sc.nextInt();
			dao.returnBook(returnBN,connId);
		case 2:
			return;
		}
		 
	}	
	
	

	//예약
	public void booking(){

		System.out.println(">>빌릴 책번호?");
		String bnum=sc.next();


	}	

	//book전체목록
	public void bookSelectAll(){

		List<LibraryDTO> lists=dao.getList2();

		Iterator<LibraryDTO> it=lists.iterator();

		System.out.println();
		System.out.println("< 책정보 >");
		System.out.println();
		while(it.hasNext()){
			LibraryDTO dto=it.next();
			System.out.println(dto.toString2());
		}


	}
	
	//연체업데이트 
	public int overUpdate(){

		int chk = 0;
		List<LibraryDTO> lists = dao.borrowBookInfo(connId);		
		Iterator<LibraryDTO> it = lists.iterator();

		chk=dao.overReturn(connId);
		
		return chk;
		
	}
	


}
