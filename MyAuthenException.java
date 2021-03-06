package com.library;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyAuthenticator extends Exception{

	
	//아이디 확인

	public void logId(String id) throws AuthenException{

		if(id.length()<5 || id.length()>15){

			throw new AuthenException("5~15자 이내의 아이디만 가능합니다");

		}

		int cnt1=0;

		int cnt2=0;

		for(int i=0;i<id.length();i++){

			char ch = id.charAt(i);

			if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z'))

				cnt1++;

			else if(ch>='0' && ch<='9')

				cnt2++;

		}

		if(cnt1==0 || cnt2==0)

			throw new AuthenException("아이디는 영문자와 숫자를 혼용해서 만들어주세요");	

	}

	//비밀번호 확인

	public void password(String ps1, String ps2) throws AuthenException{

		int cnt1=0;

		int cnt2=0;

		for(int i=0;i<ps1.length();i++){

			char ch = ps1.charAt(i);

			if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z'))

				cnt1++;

			else if(ch>='0' && ch<='9')

				cnt2++;
		}


		if(cnt1==0 || cnt2==0)

			throw new AuthenException("비밀번호는 영문자와 숫자를 혼용해서 만들어주세요");	

		if(!ps1.equals(ps2))

			throw new AuthenException("비밀번호가 다릅니다");	

	}

	//이름 확인

	public void nameCheck(String name) throws AuthenException {

		boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name);

		if (!check)

			throw new AuthenException("이름은 한글로 입력해주세요");

	}

	// 전화번호 확인

	public void telCheck(String tel) throws AuthenException {

		boolean check = Pattern.matches(

				"(010|011|016|017|018?019)-(\\d{3,4})-(\\d{4})", tel);

		if (!check)

			throw new AuthenException("전화번호 입력 형식은 [XXX-XXXX-XXXX]입니다");

	}

	//이메일 확인
	public void emailCheck(String email) throws AuthenException {

		boolean check = Pattern.matches(

				"^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",email);

		if (!check)

			throw new AuthenException("이메일형태로 입력해주세요");
	}

}





