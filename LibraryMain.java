package com.library;

import java.util.Scanner;

public class LibraryMain {

	public static void main(String[] args) {


		Scanner sc = new Scanner(System.in);
		int ch;
		int logCk=0;
		Library ob=new Library();

		try {

			while(true){

				//1.로그인 안했을때
				if(logCk == 0) {

					do{
						
						System.out.println("  			     /●\\");
						System.out.println(" 			    /   \\");
						System.out.println("			   /     \\");
						System.out.println("※□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□※");
						System.out.println("□ ██╗                    ██╗    ██████╗       ██████╗           █████╗         ██████╗        ██╗        ██╗□");
						System.out.println("□ ██║                    ██║    ██╔══██╗    ██╔══██╗     ██╔══██╗    ██╔══██╗     ╚██╗ ██╔╝ □");
						System.out.println("□ ██║                    ██║    ██████╔╝    ██████╔╝     ███████║    ██████╔╝        ╚████╔╝  □");
						System.out.println("□ ██║                    ██║    ██╔══██╗    ██╔══██╗     ██╔══██║    ██╔══██╗            ╚██╔╝     □");
						System.out.println("□ ███████╗    ██║    ██████╔╝    ██║        ██║    ██║      ██║    ██║        ██║              ██║        □");
						System.out.println("※□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□※");
						System.out.println("                           |                               ");
						System.out.print("                             ㄴ1.회원가입  2.로그인 3.회원전체보기 4.종료 ▷    ");
						
						ch = sc.nextInt();
						
					}while(ch<1||ch>4);

					switch(ch){
					case 1:
						System.out.println();
						ob.insert();
						System.out.println();
						break;
					case 2:
						System.out.println();
						ob.login();
						System.out.println();
						logCk=1;
						break;
					case 3:
						System.out.println();
						ob.selectAll();
						System.out.println();
						break;
					case 4:
						DBConn.close();
						System.exit(0);
					}

				}else if(logCk == 1) {
					//1.로그인 했을때

					do{					
						
						
						System.out.println("            ______________________________________________________________________________            ");
						System.out.println("           |┌--------------------------------------------------------------------------┐|           ");
						System.out.println("           ||.--.    .-._                        .----.             .---.        .-.     ||           ");
						System.out.println("           |||==|____| |H|___            .---.___|''''|_____.--.___ | J |  .---. |~|__   ||           ");
						System.out.println("           |||  |====| | |xxx|_          |+++|=-=|_ _ |-=+=-|==|---|| A |--|:::| |~|--|  ||           ");
						System.out.println("           |||==|    | | |   | |         |   |   |_/_ |su|zi| ^|   || V |--|   |_|~|IU|  ||           ");
						System.out.println("           |||  |    | | |   | |    .--. |   |=-=|_/_ |-=+=-| ^|   || A |  |:::|=| |  |  ||           ");
						System.out.println("           |||  |    | | |   |_|  _( oo )|   |   |    |  |  | ^|   ||___|  |   | | |  |  ||           ");
						System.out.println("           |||==|====| |H|xxx| | | ||''| |+++|=-=|''''|-=+=-|==|---||~~~|--|:::|=|~|--|  ||           ");
						System.out.println("           ||`--^----'-^-^---'   `-' ''  '---^---^----^-----^--^---^^---'--^---^-^-------||           ");
						System.out.println("           ||----------------------------------------------------------------------------||           ");
						System.out.println("           ||----------------------------------------------------------------------------||           ");
						System.out.println("           ||               ___                   .-.__.-----. .---..---..---..---..--.--||           ");
						System.out.println("           ||         .---.|===| .---.   __   .---| |XX|=|(*)|=|^^^||   || J || D ||  |  ||           ");
						System.out.println("           ||        _| A ||III|_|' '|__|::|==|:x:| |  |=| ★   | |   || J || D || A || D||           ");
						System.out.println("           ||       / | S ||===|+|   |++|  |==|   | |  |=| ★   | | I || S || B || T || B||           ");
						System.out.println("           ||      / /| C ||===|-|   |  |''|  |:x:|=|  | | ★   | | U || P || C || A || A||           ");
						System.out.println("           ||_____/ / | I ||---| |   |  |  |  |   | |  | | ★   | |   ||~~~||~~~||~~~||~~||           ");
						System.out.println("           ||____/ /  | I ||===|+|[I]|DK|''|==|:x:|=|XX|<(*)>|=|^^^||~~~||~~~||~~~||~~|  ||           ");
						System.out.println("           ||---^-'---^---'`---^-^---^--^--'--^---^-^--^-----^-^---^^---'^---'^---'^--'--||           ");
						System.out.println("           ||----------------------------------------------------------------------------||           ");
						System.out.println("           |└--------------------------------------------------------------------------┘|           ");
						System.out.println("           └──────────────────────────────────────┘           ");
							
					
						
						
						System.out.println("================================================================================================================");
						System.out.println("1.전체목록(대출/예약) 2.검색(대출/예약) 3.대출정보(반납하기) 4.예약정보 5.내정보(회원등급) 6.회원탈퇴 7.로그아웃");
						System.out.println("================================================================================================================");
						ch = sc.nextInt();
					}while(ch<1||ch>7);

					switch(ch){
					case 1:
						ob.bookSelectAll();
						System.out.println();
						logCk=3;
						break;
					case 2:
						int count = ob.search();
						if(count==0){
							System.out.println("** 검색 정보가 없습니다 **");
							System.out.println();
						}else{
							logCk=3;
						}
						System.out.println();
						break;
					case 3:
						ob.overUpdate();
						int count2 = ob.borrowInfo();
						if(count2!=0){
							ob.returnB();
							System.out.println();
						}else{
							System.out.println("** 대출정보가 없습니다 **");
							System.out.println();
						}
						break;
					case 4:
						int chk=ob.reserveSelect();

						if(chk==0){
							System.out.println("** 예약정보가 없습니다 **\n");
							break;
						}
						
						logCk=4;
						break;
					case 5:
						ob.myselect();
						System.out.println();
						break;
					case 6:
						System.out.println();
						ob.delete();
						System.out.println();
						logCk=0;
						break;
					case 7:
						System.out.println();
						System.out.println("** 로그아웃 완료 **\n");
						logCk=0;
						break;
					}

				}else if(logCk == 3){
					do{
						System.out.print("* 1.대출하기 2.뒤로가기: ");
						ch = sc.nextInt();
					}while(ch<1||ch>2);

					switch(ch){
					case 1:
						//여기
						int chk=ob.overUpdate();
						if(chk!=0){
							System.out.println("연체때문에 책을 빌릴수 없습니다!");
							logCk=1;
							break;
						}
						ob.borrow();
						System.out.println();
						logCk=1;
						break;
					case 2:
						System.out.println();
						logCk=1;break;
					}

				}else if(logCk==4){
					do{
						System.out.print("* 1.대출하기 2.예약취소 3.뒤로가기: ");
						ch = sc.nextInt();
					}while(ch<1||ch>3);

					switch(ch){
					case 1:
						//여기
						int chk=ob.overUpdate();
						if(chk!=0){
							System.out.println("연체때문에 책을 빌릴수 없습니다!");
							logCk=1;
							break;
						}
						ob.borrow();
						System.out.println();
						logCk=1;
						break;
					case 2:
						ob.bookingR();
						System.out.println();
						logCk=1;
						break;
					case 3:
						System.out.println();
						logCk=1;
						break;
					}
				}


			}

		}catch (Exception e) {
			System.out.println(e.toString());
		}



	}

}