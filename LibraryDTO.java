package com.library;

public class LibraryDTO {

	//ȸ������
	String id;
	String pw;
	String name;
	String email;
	String tel;
	String grade;
	int mcount;

	//å ����
	int bnum;
	String bname;
	String author;
	String publisher;
	int bcheck;
	int bcount;

	//��������
	String bdate;
	String tdate;
	int over;
	
	//��������
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
			this.grade="�ǹ�";
		}
		else if(grade==2){
			this.grade="���";
		}
		else if(grade==3){
			this.grade="VIP";
		}
		else {//0�� ������ �־ �ӽ÷� �־��
			this.grade="��Ÿ";
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


	//ȸ������ ���
	public String toString() {
		String str;

		str=String.format("���̵�:%s\n��й�ȣ:%s\n�̸�:%s\n----",id,pw,name);

		return str;
	}

	//å���� ���
	public String toString2() {
		String str;
		String bstr,rstr;

		if(bcheck==0){
			bstr="����Ұ�";
		}
		else{
			bstr="���Ⱑ��";
		}

		if(bcount==0){
			rstr="���డ��";
		}
		else{
			rstr="����Ұ�";
		}

		str=String.format("å����:%s\n����:%s\n���ǻ�:%s\n%d %s %s\n----",bname,author,publisher,bnum,bstr,rstr);

		return str;
	}
	
	//�������� ���
	public String toString3() {
		String str;
		
		String ostr;
		
		if(over==0){
			ostr="��ü X";
		}
		else{
			ostr="��ü O";
		}

		str=String.format("å����: %s\n����: %s\n���ǻ�: %s\nå��ȣ: %d\n������ ��¥: %s\n�ݳ����� ��¥: %s\n��ü����: %s\n---",bname,author,publisher,bnum,bdate,tdate,ostr);

		return str;
	}

	//������ ���
	public String MytoString() {
		String str;

		str=String.format("���̵�  :%s\n��й�ȣ  :%s\n�̸�  :%s\n�̸���  :%s\n��ȭ��ȣ  :%s\n���  :%s\n�Ѵ���Ƚ��  :%d",id,pw,name,email,tel,grade,mcount);

		return str;
	}
	

	//�������� ���
	public String RestoString() {
		String str;
		String bstr;

		if(bcheck==0){
			bstr="������";
		}
		else{
			bstr="���Ⱑ��";
		}

		str=String.format("å����:%s\n%d %s %s\n----",bname,bnum,rdate,bstr);

		return str;
	}


}
