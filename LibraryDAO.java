package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.db.DBConn;


public class LibraryDAO {

	//회원가입

	public int insertData(LibraryDTO dto ){

		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {

			conn.setAutoCommit(false);

			sql = "insert into mem (id,pw,name,email,tel) ";
			sql += "values(?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);	

			pstmt.setString(1,dto.getId());
			pstmt.setString(2,dto.getPw());
			pstmt.setString(3,dto.getName());
			pstmt.setString(4,dto.getEmail());
			pstmt.setString(5,dto.getTel());

			result = pstmt.executeUpdate();

			pstmt.close();

			conn.commit();

		} catch (Exception e) {
			try {pstmt.close();} catch (Exception e2) {}		//sql문이랑executeUpdate에서 오류가 나면 단계적으로 닫아준다.
			try {DBConn.close();} catch (Exception e2) {}
		}

		return result;


	}


	//로그인
	public int loginPs(String id,String ps){

		int result=0;
		String str="";

		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;

		try {
			sql="select pw ";
			sql+="from mem where id like ?";

			pstmt=conn.prepareStatement(sql);

			pstmt.setString(1, id);

			rs=pstmt.executeQuery();

			if(rs.next()){
				str=rs.getString(1);
			}

			if(str.equals(ps)){
				result=1;
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;

	}

	//회원전체보기
	public List<LibraryDTO> getList(){

		List<LibraryDTO> lists=new ArrayList<LibraryDTO>();
		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;

		try {
			sql="select id,pw,name ";
			sql+="from mem ";

			pstmt=conn.prepareStatement(sql);

			rs=pstmt.executeQuery();

			while(rs.next()){
				LibraryDTO dto=new LibraryDTO();

				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));

				lists.add(dto);
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}




	//회원탈퇴
	public int deleteData(String id){

		int result = 0;

		Connection conn = DBConn.getConnection();
		List<Integer> lists1 = new ArrayList<Integer>();
		List<Integer> lists2 = new ArrayList<Integer>();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;

		try {	

			conn.setAutoCommit(false);
			///////////////////////////////////////////////////////////
			
			//탈퇴할 회원이 빌린 책의 번호들 가져오기
			sql= "select bnum from borrow where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			while(rs.next()){
				lists1.add(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
			
			//탈퇴할 회원이 빌린 책의 bcheck를 1으로(대출가능)
			for(int i=0;i<lists1.size();i++){

				sql = "update book set bcheck = 1 where bnum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,lists1.get(i));
				result = pstmt.executeUpdate();
				pstmt.close();
				if(result==0){
					System.out.println("** 탈퇴 bcheck = 1 오류 **");
				}
			}
			
			//탈회할 회원이 빌린 책들을 borrow테이블에서 삭제
			sql= "delete borrow  where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			pstmt.close();
			
			///////////////////////////////////////////////////////////
			
			//탈퇴할 회원이 예약한 책의 번호들 가져오기
			sql= "select bnum from booking where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			while(rs.next()){
				lists2.add(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
			
			//탈퇴할 회원이 예약한 책의 bcount를 0으로(예약 가능)
			for(int i=0;i<lists2.size();i++){
				
				sql = "update book set bcount=0 where bnum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,lists2.get(i));
				result = pstmt.executeUpdate();
				pstmt.close();
				if(result==0){
				}else{
					System.out.println("bcount=0성공");
				}
			}

			//탈회할 회원이 예약한 책들을 booking테이블에서 삭제
			sql= "delete booking  where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			pstmt.close();

			///////////////////////////////////////////////////////////

			//탈회할 회원정보를 mem테이블에서 삭제
			sql= "delete mem  where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			pstmt.close();

			conn.commit();

		} catch (Exception e) {
			System.out.println(e.toString());

		}

		
		return result;

	}


	//myselect //내정보보기							
	public LibraryDTO getList(String id){

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		LibraryDTO dto = new LibraryDTO();
		try {
			sql = "select id,pw,name,email,tel,grade,mcount ";
			sql+= "from mem where id =?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				dto.setGrade(rs.getInt("grade"));
				dto.setMcount(rs.getInt("mcount"));
			}
			rs.close();
			pstmt.close();			
		} catch (Exception e) {
			System.out.println(e.toString());

		}
		return dto;			

	}

	//책검색
	public List<LibraryDTO> searchBook(String searchB){

		List<LibraryDTO> lists = new ArrayList<LibraryDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = "select bnum,bname,author,publisher,bcheck,bcount ";
			sql += "from book where bname like '%" + searchB + "%'";

			pstmt = conn.prepareStatement(sql); 
			rs = pstmt.executeQuery(); 	


			while(rs.next()){

				LibraryDTO dto = new LibraryDTO();

				dto.setBnum(rs.getInt("bnum")); 
				dto.setBname(rs.getString("bname"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setBcheck(rs.getInt("bcheck"));
				dto.setBcount(rs.getInt("bcount"));
				lists.add(dto);

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return lists;

	}

	//예약정보보기      
	public List<LibraryDTO> getListReserve(String id){

		List<LibraryDTO> lists = new ArrayList<LibraryDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select A.id,A.bnum,B.bname,A.rdate,B.bcheck ";
			sql+= "from booking A,book B where A.id =? and B.bnum=A.bnum";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()){
				LibraryDTO dto = new LibraryDTO();
				dto.setId(rs.getString("id"));
				dto.setBnum(rs.getInt("bnum"));
				dto.setBname(rs.getString("bname"));
				dto.setRdate(rs.getString("rdate"));
				dto.setBcheck(rs.getInt("bcheck"));
				lists.add(dto);
			}

			rs.close();
			pstmt.close();         
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;         

	}

	//여기 바뀜
	//대출정보
	public List<LibraryDTO> borrowBookInfo(String id){

		List<LibraryDTO> lists = new ArrayList<LibraryDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = "select a.bnum,b.bname,b.author,b.publisher,to_char(a.bdate,'YY-MM-DD HH24:MI:SS') nal1,to_char(a.tdate,'YY-MM-DD HH24:MI:SS') nal2,a.over from borrow a,book b where a.id=? and a.bnum=b.bnum";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, id);
			rs = pstmt.executeQuery(); 	


			while(rs.next()){

				LibraryDTO dto = new LibraryDTO();

				dto.setBnum(rs.getInt("bnum")); 
				dto.setBname(rs.getString("bname"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setBdate(rs.getString("nal1"));//bdate 대출시간
				dto.setTdate(rs.getString("nal2"));//tdate 반납시간
				dto.setOver(rs.getInt("over"));
				lists.add(dto);

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return lists;

	}

	//반납
	public void returnBook(int returnBN,String id){

		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result,cur=0;

		try {

			conn.setAutoCommit(false);

			//선택한 책을 borrow테이블에서 삭제
			sql = "delete borrow where bnum like ?";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,returnBN); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("borrow테이블 delete실패");
				return;
			}

			pstmt.close();

			//선택한 책의 대출 가능으로 변경
			sql = "update book set bcheck = ? ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,1); 
			pstmt.setInt(2,returnBN); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("bcheck update실패");
				return;
			}

			pstmt.close();

			//현재 아이디의 cur가져오기
			sql="select cur from mem where id like ?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			if(rs.next()){
				cur = rs.getInt(1);
			}else{
				System.out.println("cur가져오기 오류");
				return;
			}

			rs.close();
			pstmt.close();

			//반납완료	
			sql = "update mem set cur=? ";
			sql += "where id like ?"; 

			pstmt = conn.prepareStatement(sql); 

			cur = cur - 1;
			pstmt.setInt(1,cur);
			pstmt.setString(2,id);
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("cur--오류");
				return;
			}else{
				System.out.println();
				System.out.println("** 반납완료 **");
				System.out.println();
			}

			conn.commit();


		} catch (Exception e) {
			e.toString();
		}

	}



	//대출
	public void borrowBook(int borrowB,String id){

		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int check=0,cur=0,grade=0,mcount=0,bcount,mybooking=0;
		int result=0;

		try {

			conn.setAutoCommit(false);

			//예약여부 확인
			sql = "select bnum from booking where id like ? ";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();


			while(rs.next()){
				if(borrowB==rs.getInt("bnum")){
					mybooking=1;
				}
			}

			rs.close();
			pstmt.close();         


			//선택한 책의 대출 가능 여부 확인
			sql="select bcheck,bcount ";
			sql+="from book where bnum like ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, borrowB);
			rs=pstmt.executeQuery();

			if(rs.next()){
				check = rs.getInt(1);
				bcount = rs.getInt(2);

				if(check==0){//대출불가능
					System.out.println("이미 대출된 책입니다.");
					if(bcount==1){
						System.out.println("이미 예약된 책입니다.");
						return;
					}
					booking(borrowB,id);
					return;
				}else{//대출가능
					if(mybooking==0 && bcount==1){
						System.out.println("이미 예약된 책입니다.");
						return;
					}

					if(mybooking==1){
						bookingBorrow(borrowB,id);
					}
				}

			}else{
				System.out.println("책이 없습니다.");
				return;
			}

			rs.close();
			pstmt.close();
			
			
			//현재 아이디의 대출가능 권수 체크
			sql="select grade,cur,mcount ";
			sql+="from mem where id like ?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			if(rs.next()){
				grade = rs.getInt(1);
				cur = rs.getInt(2);
				mcount = rs.getInt(3);

				if(grade<=cur){
					System.out.println("더이상 대출이 불가능합니다.");
					System.out.println("등급 : "+grade+" / 현재대출권수 : "+cur);
					booking(borrowB,id);
					return;
				}
				
			}else{
				System.out.println("대출가능체크 오류");
				return;
			}

			rs.close();
			pstmt.close();


			//선택한 책의 대출 불가능으로 변경
			sql = "update book set bcheck = ? ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,0); 
			pstmt.setInt(2,borrowB); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("bcheck update실패");
				return;
			}

			pstmt.close();


			//대출완료(mem테이블 insert)		
			sql = "update mem set mcount = ?,cur=?";
			sql += "where id like ?"; 

			pstmt = conn.prepareStatement(sql); 

			mcount = mcount+1;
			pstmt.setInt(1,mcount); 
			cur = cur + 1;
			pstmt.setInt(2,cur);
			pstmt.setString(3,id);
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("mcount랑cur오류");
				return;
			}

			pstmt.close();
			
			//borrow테이블 
			sql = "insert into borrow (id,bnum,bdate,tdate) ";
			sql += "values (?,?,sysdate,sysdate+1/(24*60*60)*6000)"; //100분

			pstmt = conn.prepareStatement(sql); 

			pstmt.setString(1,id); 
			pstmt.setInt(2,borrowB);
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("borrow테이블 업데이트 실패");
				return;
			}else{
				System.out.println();
				System.out.println("** 대출 완료 **");
				System.out.println();
			}
			pstmt.close();

			conn.commit();

			//등급관련
			sql = "update mem set grade = ? ";
			sql += "where id like ?";

			pstmt = conn.prepareStatement(sql); 

			if(mcount==5){
				pstmt.setInt(1,2);
			}else if(mcount==10){
				pstmt.setInt(1,3);
			}else{
				return;
			}

			pstmt.setString(2,id); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("** 등급 업그레이드 실패 **");
				return;
			}else{
				System.out.println();
				System.out.println("** 등급 업그레이드 **");
				System.out.println();
			}
			pstmt.close();

			conn.commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	
	//예약한 사람이 빌리기
	public void bookingBorrow(int borrowB,String id){

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result=0;

		try {

			conn.setAutoCommit(false);

			//booking테이블에서 삭제
			sql = "delete booking where bnum like ?";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,borrowB); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println();
				System.out.println("** borrow테이블 delete실패 **");
				System.out.println();
				return;
			}

			pstmt.close();


			//bcount 0으로(예약 가능으로)
			sql = "update book set bcount=0 ";
			sql += "where bnum= ?";

			pstmt = conn.prepareStatement(sql);   

			pstmt.setInt(1,borrowB);

			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println();
				System.out.println("** booking update 실패 **");
				System.out.println();
			}

			pstmt.close();
			conn.commit();


		} catch (Exception e) {
			e.toString();
		}
	}
	
	
	//예약 취소
	public void bookingReturn(int bookingRB,String id){
		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result=0;

		try {
			
			conn.setAutoCommit(false);

			//선택한 책을 booking테이블에서 삭제
			sql = "delete booking where bnum like ?";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,bookingRB); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println();
				System.out.println("** borrow테이블 delete실패 **");
				System.out.println();
				return;
			}

			pstmt.close();
			
			//선택한 책의 예약 가능으로 변경
			sql = "update book set bcount = ? ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,0); 
			pstmt.setInt(2,bookingRB); 
			result = pstmt.executeUpdate();
			
			if(result==0){
				System.out.println();
				System.out.println("** bcount update실패 **");
				System.out.println();
				return;
			}

			pstmt.close();
			
			//선택한 책의 대출 가능으로 변경
			sql = "update book set bcheck = 1 ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,bookingRB); 
			result = pstmt.executeUpdate();
			
			if(result==0){
				System.out.println();
				System.out.println("** bcount update실패 **");
				System.out.println();
				return;
			}else{
				System.out.println();
				System.out.println("** 예약 취소 완료 **");
				System.out.println();
			}

			
			pstmt.close();
			conn.commit();
			
			
			
		} catch (Exception e) {
			e.toString();
		}
		
	}

	//예약하기
	public void booking(int borrowB,String id){

		Scanner sc=new Scanner(System.in);
		int result=0;

		while(true){

			System.out.print("예약하시겠습니까?[Y/N] : ");
			String str=sc.next();

			if(str.equalsIgnoreCase("y")){

				Connection conn = DBConn.getConnection();
				PreparedStatement pstmt = null;
				ResultSet rs=null;
				String sql;
				int bcount=0;

				try {
					sql = "select bcount from book ";
					sql += "where bnum = ?";

					pstmt = conn.prepareStatement(sql);   
					pstmt.setInt(1,borrowB);
					rs = pstmt.executeQuery();

					while(rs.next()){
						bcount=rs.getInt("bcount");
					}

					if(bcount==1){
						System.out.println("예약한 사람이 이미 있습니다.");
						return;
					}
					else{
						System.out.println();
						System.out.println("** 예약 완료 **");
						System.out.println();
					}

					pstmt.close();
					rs.close();

					conn.setAutoCommit(false);

					sql = "insert into booking (id,bnum,rdate) ";
					sql += "values(?,?,sysdate)";

					pstmt = conn.prepareStatement(sql);   

					pstmt.setString(1,id);
					pstmt.setInt(2,borrowB);

					result = pstmt.executeUpdate();

					if(result==1){
						//System.out.println("booking insert 성공");
						updateBcount(borrowB);
					}
					else{
						//System.out.println("booking insert 실패");
					}

					pstmt.close();
					conn.commit();

					break;
				} catch (Exception e) {
					try {pstmt.close();} catch (Exception e2) {}      
					try {DBConn.close();} catch (Exception e2) {}
				}

			}else if(str.equalsIgnoreCase("n")){
				break;
			}
			else{
				System.out.println("Y/N 로만 입력해주세요.");
			}

		}


	}

	//예약 : Bcount업데이트
	public void updateBcount(int borrowB){

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result=0;

		try {

			sql = "update book set bcount=1 ";
			sql += "where bnum= ?";

			pstmt = conn.prepareStatement(sql);   

			pstmt.setInt(1,borrowB);

			result = pstmt.executeUpdate();

			if(result==1){
				//System.out.println("booking update 성공");
			}
			else{
				//System.out.println("booking update 실패");
			}

			pstmt.close();
			conn.commit();


		} catch (Exception e) {
			try {pstmt.close();} catch (Exception e2) {}      
			try {DBConn.close();} catch (Exception e2) {}
		}

	}


	//도서전체목록
	public List<LibraryDTO> getList2(){

		List<LibraryDTO> lists=new ArrayList<LibraryDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;      
		String sql;

		try {

			sql = "select bnum, bname, author, publisher, bcheck, bcount ";
			sql+= "from book";

			pstmt=conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()){

				LibraryDTO dto = new LibraryDTO();

				dto.setBnum(rs.getInt("bnum"));
				dto.setBname(rs.getString("bname"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setBcheck(rs.getInt("bcheck"));
				dto.setBcount(rs.getInt("bcount"));

				lists.add(dto);

			}

			rs.close();
			pstmt.close();         
		} catch (Exception e) {
			System.out.println(e.toString());

		}         
		return lists;
	}

	//여기 바뀜
	//연체 업데이트
	public int overReturn(String id){

		List<LibraryDTO> lists=new ArrayList<LibraryDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;      
		String sql;
		int result;
		int x=0;

		try {

			sql = "select tdate,bnum ";
			sql+= "from borrow where id=?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,id);

			rs = pstmt.executeQuery();

			while(rs.next()){
				LibraryDTO dto=new LibraryDTO();
				dto.setTdate(rs.getString("tdate"));
				dto.setBnum(rs.getInt("bnum"));
				lists.add(dto);
			}

			try {

				for(int i=0;i<lists.size();i++){
					DateFormat sdFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
					Date tempDate = sdFormat.parse(lists.get(i).tdate);//반납일
					Date now=new Date();

					long diff=now.getTime()-tempDate.getTime();//30초임 지금 --sysdate검색해서 바꾸기 시간

					long sec = diff / 1000;
					
					//System.out.println("시간:"+sec);

					if(diff>0){
						x++;//연체일때 걸리게
						//System.out.println("연체입니다.");

						sql = "update borrow set over=1 ";
						sql += "where id= ? and bnum=?";

						pstmt = conn.prepareStatement(sql);   

						pstmt.setString(1,id);
						pstmt.setInt(2,lists.get(i).bnum);

						result = pstmt.executeUpdate();

						if(result==1){
							//System.out.println("연체 update 성공");
						}
						else{
							//System.out.println("연체 update 실패");
						}

						pstmt.close();
						conn.commit();
					}
					else{
						//System.out.println("연체 아닙니다.");
					}

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return x;


	}

}
