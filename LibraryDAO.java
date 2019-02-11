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

	//ȸ������

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
			try {pstmt.close();} catch (Exception e2) {}		//sql���̶�executeUpdate���� ������ ���� �ܰ������� �ݾ��ش�.
			try {DBConn.close();} catch (Exception e2) {}
		}

		return result;


	}


	//�α���
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

	//ȸ����ü����
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




	//ȸ��Ż��
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
			
			//Ż���� ȸ���� ���� å�� ��ȣ�� ��������
			sql= "select bnum from borrow where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			while(rs.next()){
				lists1.add(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
			
			//Ż���� ȸ���� ���� å�� bcheck�� 1����(���Ⱑ��)
			for(int i=0;i<lists1.size();i++){

				sql = "update book set bcheck = 1 where bnum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,lists1.get(i));
				result = pstmt.executeUpdate();
				pstmt.close();
				if(result==0){
					System.out.println("** Ż�� bcheck = 1 ���� **");
				}
			}
			
			//Żȸ�� ȸ���� ���� å���� borrow���̺��� ����
			sql= "delete borrow  where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			pstmt.close();
			
			///////////////////////////////////////////////////////////
			
			//Ż���� ȸ���� ������ å�� ��ȣ�� ��������
			sql= "select bnum from booking where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			while(rs.next()){
				lists2.add(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
			
			//Ż���� ȸ���� ������ å�� bcount�� 0����(���� ����)
			for(int i=0;i<lists2.size();i++){
				
				sql = "update book set bcount=0 where bnum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,lists2.get(i));
				result = pstmt.executeUpdate();
				pstmt.close();
				if(result==0){
				}else{
					System.out.println("bcount=0����");
				}
			}

			//Żȸ�� ȸ���� ������ å���� booking���̺��� ����
			sql= "delete booking  where id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			pstmt.close();

			///////////////////////////////////////////////////////////

			//Żȸ�� ȸ�������� mem���̺��� ����
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


	//myselect //����������							
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

	//å�˻�
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

	//������������      
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

	//���� �ٲ�
	//��������
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
				dto.setBdate(rs.getString("nal1"));//bdate ����ð�
				dto.setTdate(rs.getString("nal2"));//tdate �ݳ��ð�
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

	//�ݳ�
	public void returnBook(int returnBN,String id){

		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result,cur=0;

		try {

			conn.setAutoCommit(false);

			//������ å�� borrow���̺��� ����
			sql = "delete borrow where bnum like ?";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,returnBN); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("borrow���̺� delete����");
				return;
			}

			pstmt.close();

			//������ å�� ���� �������� ����
			sql = "update book set bcheck = ? ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,1); 
			pstmt.setInt(2,returnBN); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("bcheck update����");
				return;
			}

			pstmt.close();

			//���� ���̵��� cur��������
			sql="select cur from mem where id like ?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();

			if(rs.next()){
				cur = rs.getInt(1);
			}else{
				System.out.println("cur�������� ����");
				return;
			}

			rs.close();
			pstmt.close();

			//�ݳ��Ϸ�	
			sql = "update mem set cur=? ";
			sql += "where id like ?"; 

			pstmt = conn.prepareStatement(sql); 

			cur = cur - 1;
			pstmt.setInt(1,cur);
			pstmt.setString(2,id);
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("cur--����");
				return;
			}else{
				System.out.println();
				System.out.println("** �ݳ��Ϸ� **");
				System.out.println();
			}

			conn.commit();


		} catch (Exception e) {
			e.toString();
		}

	}



	//����
	public void borrowBook(int borrowB,String id){

		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int check=0,cur=0,grade=0,mcount=0,bcount,mybooking=0;
		int result=0;

		try {

			conn.setAutoCommit(false);

			//���࿩�� Ȯ��
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


			//������ å�� ���� ���� ���� Ȯ��
			sql="select bcheck,bcount ";
			sql+="from book where bnum like ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, borrowB);
			rs=pstmt.executeQuery();

			if(rs.next()){
				check = rs.getInt(1);
				bcount = rs.getInt(2);

				if(check==0){//����Ұ���
					System.out.println("�̹� ����� å�Դϴ�.");
					if(bcount==1){
						System.out.println("�̹� ����� å�Դϴ�.");
						return;
					}
					booking(borrowB,id);
					return;
				}else{//���Ⱑ��
					if(mybooking==0 && bcount==1){
						System.out.println("�̹� ����� å�Դϴ�.");
						return;
					}

					if(mybooking==1){
						bookingBorrow(borrowB,id);
					}
				}

			}else{
				System.out.println("å�� �����ϴ�.");
				return;
			}

			rs.close();
			pstmt.close();
			
			
			//���� ���̵��� ���Ⱑ�� �Ǽ� üũ
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
					System.out.println("���̻� ������ �Ұ����մϴ�.");
					System.out.println("��� : "+grade+" / �������Ǽ� : "+cur);
					booking(borrowB,id);
					return;
				}
				
			}else{
				System.out.println("���Ⱑ��üũ ����");
				return;
			}

			rs.close();
			pstmt.close();


			//������ å�� ���� �Ұ������� ����
			sql = "update book set bcheck = ? ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,0); 
			pstmt.setInt(2,borrowB); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("bcheck update����");
				return;
			}

			pstmt.close();


			//����Ϸ�(mem���̺� insert)		
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
				System.out.println("mcount��cur����");
				return;
			}

			pstmt.close();
			
			//borrow���̺� 
			sql = "insert into borrow (id,bnum,bdate,tdate) ";
			sql += "values (?,?,sysdate,sysdate+1/(24*60*60)*6000)"; //100��

			pstmt = conn.prepareStatement(sql); 

			pstmt.setString(1,id); 
			pstmt.setInt(2,borrowB);
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println("borrow���̺� ������Ʈ ����");
				return;
			}else{
				System.out.println();
				System.out.println("** ���� �Ϸ� **");
				System.out.println();
			}
			pstmt.close();

			conn.commit();

			//��ް���
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
				System.out.println("** ��� ���׷��̵� ���� **");
				return;
			}else{
				System.out.println();
				System.out.println("** ��� ���׷��̵� **");
				System.out.println();
			}
			pstmt.close();

			conn.commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	
	//������ ����� ������
	public void bookingBorrow(int borrowB,String id){

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result=0;

		try {

			conn.setAutoCommit(false);

			//booking���̺��� ����
			sql = "delete booking where bnum like ?";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,borrowB); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println();
				System.out.println("** borrow���̺� delete���� **");
				System.out.println();
				return;
			}

			pstmt.close();


			//bcount 0����(���� ��������)
			sql = "update book set bcount=0 ";
			sql += "where bnum= ?";

			pstmt = conn.prepareStatement(sql);   

			pstmt.setInt(1,borrowB);

			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println();
				System.out.println("** booking update ���� **");
				System.out.println();
			}

			pstmt.close();
			conn.commit();


		} catch (Exception e) {
			e.toString();
		}
	}
	
	
	//���� ���
	public void bookingReturn(int bookingRB,String id){
		Connection conn=DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		int result=0;

		try {
			
			conn.setAutoCommit(false);

			//������ å�� booking���̺��� ����
			sql = "delete booking where bnum like ?";

			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,bookingRB); 
			result = pstmt.executeUpdate();

			if(result==0){
				System.out.println();
				System.out.println("** borrow���̺� delete���� **");
				System.out.println();
				return;
			}

			pstmt.close();
			
			//������ å�� ���� �������� ����
			sql = "update book set bcount = ? ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,0); 
			pstmt.setInt(2,bookingRB); 
			result = pstmt.executeUpdate();
			
			if(result==0){
				System.out.println();
				System.out.println("** bcount update���� **");
				System.out.println();
				return;
			}

			pstmt.close();
			
			//������ å�� ���� �������� ����
			sql = "update book set bcheck = 1 ";
			sql += "where bnum = ?"; 

			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1,bookingRB); 
			result = pstmt.executeUpdate();
			
			if(result==0){
				System.out.println();
				System.out.println("** bcount update���� **");
				System.out.println();
				return;
			}else{
				System.out.println();
				System.out.println("** ���� ��� �Ϸ� **");
				System.out.println();
			}

			
			pstmt.close();
			conn.commit();
			
			
			
		} catch (Exception e) {
			e.toString();
		}
		
	}

	//�����ϱ�
	public void booking(int borrowB,String id){

		Scanner sc=new Scanner(System.in);
		int result=0;

		while(true){

			System.out.print("�����Ͻðڽ��ϱ�?[Y/N] : ");
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
						System.out.println("������ ����� �̹� �ֽ��ϴ�.");
						return;
					}
					else{
						System.out.println();
						System.out.println("** ���� �Ϸ� **");
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
						//System.out.println("booking insert ����");
						updateBcount(borrowB);
					}
					else{
						//System.out.println("booking insert ����");
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
				System.out.println("Y/N �θ� �Է����ּ���.");
			}

		}


	}

	//���� : Bcount������Ʈ
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
				//System.out.println("booking update ����");
			}
			else{
				//System.out.println("booking update ����");
			}

			pstmt.close();
			conn.commit();


		} catch (Exception e) {
			try {pstmt.close();} catch (Exception e2) {}      
			try {DBConn.close();} catch (Exception e2) {}
		}

	}


	//������ü���
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

	//���� �ٲ�
	//��ü ������Ʈ
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
					Date tempDate = sdFormat.parse(lists.get(i).tdate);//�ݳ���
					Date now=new Date();

					long diff=now.getTime()-tempDate.getTime();//30���� ���� --sysdate�˻��ؼ� �ٲٱ� �ð�

					long sec = diff / 1000;
					
					//System.out.println("�ð�:"+sec);

					if(diff>0){
						x++;//��ü�϶� �ɸ���
						//System.out.println("��ü�Դϴ�.");

						sql = "update borrow set over=1 ";
						sql += "where id= ? and bnum=?";

						pstmt = conn.prepareStatement(sql);   

						pstmt.setString(1,id);
						pstmt.setInt(2,lists.get(i).bnum);

						result = pstmt.executeUpdate();

						if(result==1){
							//System.out.println("��ü update ����");
						}
						else{
							//System.out.println("��ü update ����");
						}

						pstmt.close();
						conn.commit();
					}
					else{
						//System.out.println("��ü �ƴմϴ�.");
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
