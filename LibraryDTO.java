package com.library;

public class LibraryDTO {

	//회원정보
	String id;
	String pw;
	String name;
	String email;
	String tel;
	String grade;
	int mcount;

	//책 정보
	int bnum;
	String bname;
	String author;
	String publisher;
	int bcheck;
	int bcount;

	//대출정보
	String bdate;
	String tdate;
	int over;
	
	//예약정보
	String rdate;


	public String getRdate() {
		return rdate;
	}


	public void setRdate(String rdate) {
		this.rdate = rdate;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(int grade) {

		if(grade==1){
			this.grade="실버";
		}
		else if(grade==2){
			this.grade="골드";
		}
		else if(grade==3){
			this.grade="VIP";
		}
		else {//0인 데이터 있어서 임시로 넣어놈
			this.grade="기타";
		}
	}


	public int getMcount() {
		return mcount;
	}


	public void setMcount(int mcount) {
		this.mcount = mcount;
	}


	public int getBnum() {
		return bnum;
	}


	public void setBnum(int bnum) {
		this.bnum = bnum;
	}


	public String getBname() {
		return bname;
	}


	public void setBname(String bname) {
		this.bname = bname;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public int getBcheck() {
		return bcheck;
	}


	public void setBcheck(int bcheck) {
		this.bcheck = bcheck;
	}


	public int getBcount() {
		return bcount;
	}


	public void setBcount(int bcount) {
		this.bcount = bcount;
	}


	public String getBdate() {
		return bdate;
	}


	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	
	public String getTdate() {
		return tdate;
	}


	public void setTdate(String tdate) {
		this.tdate = tdate;
	}


	public int getOver() {
		return over;
	}


	public void setOver(int over) {
		this.over = over;
	}


	//회원정보 출력
	public String toString() {
		String str;

		str=String.format("아이디:%s\n비밀번호:%s\n이름:%s\n----",id,pw,name);

		return str;
	}

	//책정보 출력
	public String toString2() {
		String str;
		String bstr,rstr;

		if(bcheck==0){
			bstr="대출불가";
		}
		else{
			bstr="대출가능";
		}

		if(bcount==0){
			rstr="예약가능";
		}
		else{
			rstr="예약불가";
		}

		str=String.format("책제목:%s\n저자:%s\n출판사:%s\n%d %s %s\n----",bname,author,publisher,bnum,bstr,rstr);

		return str;
	}
	
	//대출정보 출력
	public String toString3() {
		String str;
		
		String ostr;
		
		if(over==0){
			ostr="연체 X";
		}
		else{
			ostr="연체 O";
		}

		str=String.format("책제목: %s\n저자: %s\n출판사: %s\n책번호: %d\n대출한 날짜: %s\n반납예정 날짜: %s\n연체유무: %s\n---",bname,author,publisher,bnum,bdate,tdate,ostr);

		return str;
	}

	//내정보 출력
	public String MytoString() {
		String str;

		str=String.format("아이디  :%s\n비밀번호  :%s\n이름  :%s\n이메일  :%s\n전화번호  :%s\n등급  :%s\n총대출횟수  :%d",id,pw,name,email,tel,grade,mcount);

		return str;
	}
	

	//예약정보 출력
	public String RestoString() {
		String str;
		String bstr;

		if(bcheck==0){
			bstr="대출중";
		}
		else{
			bstr="대출가능";
		}

		str=String.format("책제목:%s\n%d %s %s\n----",bname,bnum,rdate,bstr);

		return str;
	}


}
