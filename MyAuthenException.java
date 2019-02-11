package com.library;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyAuthenticator extends Exception{

	
	//¾ÆÀÌµð È®ÀÎ

	public void logId(String id) throws AuthenException{

		if(id.length()<5 || id.length()>15){

			throw new AuthenException("5~15ÀÚ ÀÌ³»ÀÇ ¾ÆÀÌµð¸¸ °¡´ÉÇÕ´Ï´Ù");

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

			throw new AuthenException("¾ÆÀÌµð´Â ¿µ¹®ÀÚ¿Í ¼ýÀÚ¸¦ È¥¿ëÇØ¼­ ¸¸µé¾îÁÖ¼¼¿ä");	

	}

	//ºñ¹Ð¹øÈ£ È®ÀÎ

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

			throw new AuthenException("ºñ¹Ð¹øÈ£´Â ¿µ¹®ÀÚ¿Í ¼ýÀÚ¸¦ È¥¿ëÇØ¼­ ¸¸µé¾îÁÖ¼¼¿ä");	

		if(!ps1.equals(ps2))

			throw new AuthenException("ºñ¹Ð¹øÈ£°¡ ´Ù¸¨´Ï´Ù");	

	}

	//ÀÌ¸§ È®ÀÎ

	public void nameCheck(String name) throws AuthenException {

		boolean check = Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", name);

		if (!check)

			throw new AuthenException("ÀÌ¸§Àº ÇÑ±Û·Î ÀÔ·ÂÇØÁÖ¼¼¿ä");

	}

	// ÀüÈ­¹øÈ£ È®ÀÎ

	public void telCheck(String tel) throws AuthenException {

		boolean check = Pattern.matches(

				"(010|011|016|017|018?019)-(\\d{3,4})-(\\d{4})", tel);

		if (!check)

			throw new AuthenException("ÀüÈ­¹øÈ£ ÀÔ·Â Çü½ÄÀº [XXX-XXXX-XXXX]ÀÔ´Ï´Ù");

	}

	//ÀÌ¸ÞÀÏ È®ÀÎ
	public void emailCheck(String email) throws AuthenException {

		boolean check = Pattern.matches(

				"^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",email);

		if (!check)

			throw new AuthenException("ÀÌ¸ÞÀÏÇüÅÂ·Î ÀÔ·ÂÇØÁÖ¼¼¿ä");
	}

}





