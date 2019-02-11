package com.library;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyAuthenticator extends Exception{

	
	//���̵� Ȯ��

	public void logId(String id) throws AuthenException{

		if(id.length()<5 || id.length()>15){

			throw new AuthenException("5~15�� �̳��� ���̵� �����մϴ�");

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

			throw new AuthenException("���̵�� �����ڿ� ���ڸ� ȥ���ؼ� ������ּ���");	

	}

	//��й�ȣ Ȯ��

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

			throw new AuthenException("��й�ȣ�� �����ڿ� ���ڸ� ȥ���ؼ� ������ּ���");	

		if(!ps1.equals(ps2))

			throw new AuthenException("��й�ȣ�� �ٸ��ϴ�");	

	}

	//�̸� Ȯ��

	public void nameCheck(String name) throws AuthenException {

		boolean check = Pattern.matches("^[��-����-�R]*$", name);

		if (!check)

			throw new AuthenException("�̸��� �ѱ۷� �Է����ּ���");

	}

	// ��ȭ��ȣ Ȯ��

	public void telCheck(String tel) throws AuthenException {

		boolean check = Pattern.matches(

				"(010|011|016|017|018?019)-(\\d{3,4})-(\\d{4})", tel);

		if (!check)

			throw new AuthenException("��ȭ��ȣ �Է� ������ [XXX-XXXX-XXXX]�Դϴ�");

	}

	//�̸��� Ȯ��
	public void emailCheck(String email) throws AuthenException {

		boolean check = Pattern.matches(

				"^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",email);

		if (!check)

			throw new AuthenException("�̸������·� �Է����ּ���");
	}

}





