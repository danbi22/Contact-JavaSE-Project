package edu.java.contact02;

import java.util.Scanner;

import javax.annotation.processing.SupportedSourceVersion;

public class ContactMain02 {
	// 상수(constant)
	private static final int MAX_LENGTH = 5; // 연락처 배열의 최대길이(원소개수)
	
	// field
	private Contact[] contacts = new Contact[MAX_LENGTH]; // 연락처를 저장할 배열.
	private int count = 0; // 연락처 배열에 현재까지 저장된 연락처의 개수. 배열에 저장될 때마다 값 증가
	private Scanner scanner = new Scanner(System.in); // 입력도구
	
	public static void main(String[] args) {
		
		System.out.println("***** 연락처 프로그램 v0.2 *****");
		
		ContactMain02 app = new ContactMain02();
		Menu[] menus = Menu.values();
		// static이 아닌 메서드들을 사용하기 위해서 참조 변수를 생성
		
		boolean run = true;
		while (run) {			 
			switch (menus[app.showMainMenu()]) {
			case QUIT: // 프로그램 종료
				run = false;
				break;
			case CREATE: 
				app.insertNewContact();// 새 연락처 저장
				break;
			case READ_ALL: // 연락처 전체 목록 보여주기
				app.selectAllContacts();
				break;
			case READ_BY_INDEX: // 배열의 인덱스로 검색하기
				app.selectContactByIndex();
				break;
			case UPDATE: // 연락처 이름/전화번호/이메일 수정하기
				app.updateContactByIndex();
				break;
			case DELETE: // 배열의 연락처 삭제하기
				app.deleteContactByIndex();
				break;
			default:
				System.out.println("메인 메뉴 번호를 확인하세요");
				break;
			}
		}
		System.out.println("***** 프로그램 종료 *****");
	}
	
	public int showMainMenu() {
		// 콘솔 창에서 입력된 문자열 1개 라인을 정수(int)로 변환.
			System.out.println();
			System.out.println("---------------------------------------------------------");
			System.out.println("[0]종료 [1]새 연락처 [2]전체 목록 [3]검색 [4]수정 [5]삭제");
			System.out.println("---------------------------------------------------------");
			System.out.print("선택> ");
				
			int result = inputInteger();
			if (result >=2 && result <= 5) {
				if (contacts[0] == null) {
					System.out.println("연락처가 비어있습니다.");
					return showMainMenu();
				}
			}
			return result;
	}
	
	public void insertNewContact() {
		if(count == MAX_LENGTH) {
			System.out.println("연락처 저장공간이 가득 찼습니다.");
			return; // void타입의 메서드에서의 return은 메서드를 종료시킨다. // return이 선언된 뒤쪽에는 코드가 올 수 없다.
		}
		System.out.println();
		System.out.println("--- 새 연락처 저장 ---");
		System.out.print("이름> ");
		String name = scanner.nextLine();
		System.out.print("전화번호> ");
		String phone = scanner.nextLine(); // 공백을 포함해서 엔터가 입력될 때까지 모든 문자열을 읽음.
		System.out.print("email> ");
		String email = scanner.nextLine();
		// 입력받은 정보들로 Contact 타입의 객체를 생성.
		Contact c = new Contact(0, name, phone, email);
		
		// 생성된 Contact타입의 객체를 배열에 저장.
		contacts[count] = c;
		
		// 배열에 저장된 원소(연락처) 개수를 1 증가
		count++;
		System.out.println("새 연락처 저장 성공");
	}
	
	public void selectAllContacts() {
		System.out.println();
		System.out.println("---연락처 목록 ---");
		for (int i =0; i < count; i++) {
			System.out.println(contacts[i]);
		}	
	}
	
	public void selectContactByIndex() {
		System.out.println();
		System.out.print("찾는 연락처의 인덱스 번호를 알려주세요");
		int index = inputInteger();
		if (index >= 0 && index < count) {
			System.out.println(contacts[index]);
		} else {
			System.out.println("이 인덱스 번호에는 연락처가 없습니다");
			if(contacts[0] != null) {
				selectContactByIndex();
			}
		}
	}
	
	public void updateContactByIndex() {
		System.out.println();
		System.out.println("---연락처 수정---");
		System.out.println("수정할 연락처 인덱스를 입력하세요");
		int index = inputInteger();
		if (index >= 0 && index < count) {			
		System.out.print("수정 전: ");
		System.out.println(contacts[index]); // 수정 전의 연락처 정보를 출력
		
		System.out.print("수정할 이름을 입력하세요>");
		String name = scanner.nextLine();
		System.out.print("수정할 전화번호를 입력하세요>");
		String phone = scanner.nextLine();
		System.out.print("수정할 이메일을 입력하세요>");
		String email = scanner.nextLine();
		
		// 수정할 인덱스의 정보를 업데이트
		contacts[index].setName(name);
		contacts[index].setPhone(phone);
		contacts[index].setEmail(email);
		
//		contacts[index] =new Contact(0, name, phone, email);
		
		System.out.print("수정 후: ");
		System.out.println(contacts[index]);
		} else {
			System.out.println("이 인덱스에는 수정할 연락처가 없습니다.");
			if (contacts[0] != null) {
				updateContactByIndex();
			}
		}
	}
	
	// 연락처 삭제  
	public void deleteContactByIndex() {
		System.out.println();
		System.out.println("--- 연락처 삭제 ---");
		System.out.print("삭제하고 싶은 연락처의 인덱스를 입력하세요");
		int index = inputInteger();
		if (index >= 0 && index < count) {
			// count 빼기 index만큼 반복
			for(int i = 1; i <= count-index; i++) {
				contacts[index] = contacts[index+1];
			}
		contacts[count-1] = null;
		count--;
		System.out.println("연락처 삭제 완료");
		} else {
			System.out.println("이 인덱스에는 삭제할 연락처가 없습니다.");
		}
	}
	
	public int inputInteger() {
		int x = 0;
		try {
			x = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("정수를 입력하세요");
			selectContactByIndex();
		}
		return x;
	}
	
	
	
}
