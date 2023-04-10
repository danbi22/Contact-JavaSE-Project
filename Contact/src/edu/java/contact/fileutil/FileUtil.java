package edu.java.contact.fileutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.java.contact.model.Contact;

/*
 * 도우미 클래스 - 파일 관련(read, write, 폴더 생성) 기능을 제공하기 위한 클래스.
 * 모든 필드와 메서드는 static으로 선언, 객체 생성은 하지 못하도록.
 */
public class FileUtil {
	// 상수 정의
	public static final String DATA_DIR = "data"; // 데이터 폴더 이름
	public static final String DATA_FILE = "contacts.dat"; // 데이터 파일 이름
	//-> .\data\contacts.dat 파일에 연락처 리스트를 저장하기 위해서.
	
	// private 생성자 - 다른 클래스에서는 생성자가 보이지 않기 떄문에 객체를 생성할 수 없음.
	private FileUtil() {}
	
	/**
	 * 연락처 데이터 파일을 저장하는 폴더가 존재하지 않으면 생성하고, File 객체를 리턴.
	 * 이미 폴더가 생성되어 있는 경우에는, 그 폴더의 File 객체를 리턴.
	 * 
	 * @return 데이터 파일을 저장할 폴더의 File 객체.
	 */
	public static File initDataDir() {
		File folder = new File(DATA_DIR);
		if (folder.exists()) { //폴더가 존재하면
			System.out.println("폴더가 이미 있습니다.");
		} else {  // 폴더가 존재하지 않으면
			folder.mkdir(); 
			System.out.println("폴더를 생성합니다.");
		}
		return folder;
	}
	
	/**
	 * readDataFromFile.
	 * argument로 전달된 File 객체를 사용해서, 파일에 저장된 연락처 정보를 읽고,
	 * 그 결과를 List<Contact> 타입의 객체로 리턴.
	 * 
	 * @param file 연락처 정보가 저장된 파일 경로를 가지고 있는 File 타입 객체
	 * @return List<Contact>
	 */
	public static List<Contact> readDataFromFile(File file) {
		
		try(FileInputStream in = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream oin = new ObjectInputStream(bin);
		){
		List<Contact> list = (List<Contact>)oin.readObject();
		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Contact> empty = new ArrayList<>();
		return empty;
	}
	
	
	/**
	 * writeDataToFile
	 * argument로 저장된 data를 (직렬화해서) file에 씀.
	 * 
	 * @param data 파일에 쓸 데이터. Contact 타입을 저장하는 리스트(List<Contact>)
	 * @param file 데이터 파일(File) 객체
	 */
	public static void writeDataToFile(List<Contact> list, File file) {
		try (FileOutputStream out = new FileOutputStream(file);
				BufferedOutputStream bout = new BufferedOutputStream(out);
				ObjectOutputStream oout = new ObjectOutputStream(bout);
		){
			oout.writeObject(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * initData.
	 * 연락처 데이터 파일이 있으면, 파일의 내용을 읽어서 리스트를 생성하고 리턴.
	 * 연락처 데이터 파일이 없으면, 빈 리스트를 리턴
	 * 
	 *  @return Contact 타입을 원소로 갖는 리스트(List<Contact>)
	 */
	public static List<Contact> initData(){
		File file = new File(DATA_DIR, DATA_FILE);
		if (file.exists()) {
			return readDataFromFile(file);
		}
		List<Contact> empty = new ArrayList<>();
		return empty;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
