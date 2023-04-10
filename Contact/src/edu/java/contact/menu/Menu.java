package edu.java.contact.menu;

public enum Menu {
	QUIT, CREATE, READ_ALL, READ_BY_INDEX, UPDATE, DELETE, UNKNOWN;
	
	public static Menu getValue(int n) {
		Menu[] menu = values();
		// values(): 모든 enum 클래스가 가지고 있는 static 메서드
		// values()의 리턴 값: enum에서 정의한 열거형 상수들의 배열
		// values의 리턴 값: {QUIT, CREATE, READ_ALL, READ_BY_INDEX, UPDATE, DELETE, UNKNOWN}
		if (n >= 0 && n < menu.length) {
			return menu[n];
		} else {
			return menu[menu.length - 1]; // UNKNOWN
		}
	}
	
}
