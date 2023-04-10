package edu.java.contact02;

// 연락처 정보를 저장하는 클래스
public class Contact {
	// field 연락처라면 가져야하는 속성
	private int cid; // 연락처 고유 번호
	private String name; // 이름
	private String phone; // 전화번호
	private String email; // 이메일
	
	// constructor
	// 기본생성자
	public Contact() {}
	
	// 각 값을 초기화하는 생성자
	public Contact(int cid, String name, String phone, String email) {
		this.cid = cid;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Contact(cid=" + this.cid +
				", name=" + this.name +
				", phone=" + this.phone +
				", email=" +this.email +")";
	}
	
	// method
	public void printInfo() {
		System.out.printf("Contact(cid=%d, name=%s, phone=%s, email=%s)\n", this.cid, this.name, this.phone, this.email);
	}
}
