package edu.java.contact05;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.java.contact.fileutil.FileUtil;
import edu.java.contact.model.Contact;

// MVC(Model-view-Controller)아키텍쳐에서 컨트롤러 구현 클래스
// singleton 디자인 패턴을 적용

public class ContactDaoImpl implements ContactDao{
	// fields
		private File dataDir; // 연락처 데이터 파일을 저장할 폴더
		private File dataFile; // 연락처 데이터 파일
		private List<Contact> contacts; // Contact 리스트
		
	// singleton step 1
	private static ContactDaoImpl instance = null;
	
	// singleton step 2
	private ContactDaoImpl() {	
		dataDir = FileUtil.initDataDir(); // 폴더 생성
		dataFile = new File(FileUtil.DATA_DIR, FileUtil.DATA_FILE); // 파일 생성
		contacts = FileUtil.initData(); // 데이터 초기화 - 파일의 내용을 메모리에 로딩
	}
	// TODO: ContactDaoImpl에는 연락처 데이터를 변경하는 메서드들이 있음.
	// -> 연락처 데이터가 변경되는 메서드에서 FileUtil.writeDataToFile() 메서드 호출.
	
	// singleton step 3
	public static ContactDaoImpl getInstance() {
		if (instance == null) {
			instance = new ContactDaoImpl();
		}
		return instance;
	}
	
	
	/**
	 * 어떤 인덱스가 검색, 수정, 삭제할 때 사용 가능한 범위 안에 있는 인덱스인 지를 리턴 
	 * @param index 유효한 인덱스인 지 검사할 인덱스.
	 * @return 사용 가능한 인덱스는 true, 그렇지 않으면 false를 리턴 
	 */
	public boolean isValidIndex(int index) {
		return (index >= 0) && (index < contacts.size());		
	}
	
	// CRUD(Create, Read, Update, Delete) 기능 메서드들:
	@Override
	public int create(Contact c) {
		contacts.add(c); // (heap) 메모리에 있는 List에 연락처를 추가
		FileUtil.writeDataToFile(contacts, dataFile);
		return 1;
	}

	@Override
	public List<Contact> read() {
		return contacts;
	}

	@Override
	public Contact read(int index) {
		if (isValidIndex(index)) {
			return contacts.get(index);
		} else {
			return null;
		}
	}

	@Override
	public int update(int index, Contact contact) {
		if (isValidIndex(index)) { // 유효한 인덱스이면
			contacts.set(index, contact);
			FileUtil.writeDataToFile(contacts, dataFile);
			return 1;
		} else { // 유효하지 않은 인덱스이면
			return 0;
		}
	}

	@Override
	public int delete(int index) {
		if (!isValidIndex(index)) {
			return 0;
		}
		contacts.remove(index);
		FileUtil.writeDataToFile(contacts, dataFile);		
		return 1;
	}
	
	public void empty() {
		if (contacts.get(0) == null) {
			System.out.println("저장된 연락처가 없습니다.");
		}
	}

}
