package edu.java.contact06;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.java.contact.model.Contact;
import edu.java.contact05.ContactDaoImpl;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContactMain06 {
	// 테이블 컬럼 이름(상수)
	private static final String[] COLUMN_NAMES = {"이름", "전화번호"};

	private JFrame frame;
	private JPanel buttonPanel;
	private JButton btnInsert;
	private JTable table;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private DefaultTableModel model; // 테이블의 데이터, 컬럼 이름 등을 관리하는 객체.
	private final ContactDaoImpl dao = ContactDaoImpl.getInstance(); // 연락처 정보 관리하는 객체(Controller)

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactMain06 window = new ContactMain06();
					window.frame.setVisible(true); // 윈도우의 프레임을 화면에 보여준다
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// ContactCreateFrame에서 새 연락처 저장을 성공했을 때 호출할 메서드.
	public void notifyContactCreated() {
		// JTable을 새로 그림.
		resetTableModel();
		
		JOptionPane.showMessageDialog(frame, "새 연락처 저장 성공");
	}
	
	private void resetTableModel() {
		model = new DefaultTableModel(null, COLUMN_NAMES);
		loadContactData();
	}
	
	// Contact
	public void notifyContactUpdate() {
		resetTableModel();
		
	}
	/**
	 * Create the application.
	 */
	public ContactMain06() {
		initialize(); // 화면에 보여질 GUI 컴포넌트들을 생성하고 초기화.
		loadContactData(); // 파일에 저장된 연락처 데이터를 로딩(테이블 초기화).
	}
	
	private void loadContactData() {
		List<Contact> list = dao.read();
		for (Contact c : list) {
			Object[] row = {c.getName(), c.getPhone()};
			model.addRow(row); // 모델에 값을 저장
		}
		table.setModel(model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("연락처 v0.6");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		
		btnInsert = new JButton("새 연락처");
		btnInsert.addActionListener(new ActionListener() { // 이벤트가 발생하는지 기다리는 메서드 JRE가 이벤트가 발생하면 이 메서드를 실행시킨다.
			public void actionPerformed(ActionEvent e) { // 이벤트가 발생했을 때 실행되는 메서드
				ContactCreateFrame.showContactCreateFrame(frame, ContactMain06.this); // 아직 실행 되지 않아서 static메서드를 부름. 
				// 파라미터 frame은  showContactCreateFrame의 Frame 위치 때문에
				// ContactMain06.this
//				String
//				dao.create(null);
			}
		});
		btnInsert.setFont(new Font("D2Coding", Font.PLAIN, 17));
		buttonPanel.add(btnInsert); // 버튼 판넬에 들어간다
		
		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContact();
			}
		});
				
		btnUpdate.setFont(new Font("D2Coding", Font.PLAIN, 17));
		buttonPanel.add(btnUpdate);
		
		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 테이블에서 선택된 행의 인덱스를 찾음
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(
							frame, 
							"삭제할 인덱스를 선택하세요", 
							"경고", 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int confirm = JOptionPane.showConfirmDialog(frame, "정말 삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					dao.delete(row); // 리스트에서 연락처 삭제, 파일 업데이트
					model.removeRow(row); // JTable에서 행 삭제
				JOptionPane.showMessageDialog(frame, "삭제 성공");
				}
			}
		});
		btnDelete.setFont(new Font("D2Coding", Font.PLAIN, 17));
		buttonPanel.add(btnDelete);
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setFont(new Font("D2Coding", Font.PLAIN, 17));
		buttonPanel.add(btnSearch);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER); // 콘텐트 판넬 안에 스크롤 판넬이 들어간다.
		
		table = new JTable(); // JTable 타입 객체 생성
		model = new DefaultTableModel(null, COLUMN_NAMES); // TableModel 객체 생성
		
		table.setModel(model); // TableModle 객체를 테이블에 세팅
		// TODO: 테이블 컬럼 이름의 폰트 변경.
		
		// TODO: 테이블 데이터 행의 폰트 변경.
		table.setFont(new Font("D2Coding", Font.PLAIN, 13));
		scrollPane.setViewportView(table); // 테이블에 스크롤페인에 넣음.
	}
			
	private void updateContact() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(
					frame, 
					"삭제할 인덱스를 선택하세요", 
					"경고", 
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		// ContactUpdateFrame을 생성해서 업데이트 창을 띄움
		// 새 창의 부모 컴포넌트(frame) - 업데이트 창을 띄울 좌표를 계산하기 위해서
		// 선택된 테이블 행 인덱스 - 업데이트 창에서 수정 전의 연락처를 출력하기 위해서
		// ContactMain의 주소 - 업데이트 성공했을 때 업데이트 창이 메인 창에게 알려주기 위해서
		
		ContactUpdateFrame.showContactUpdateFrame(frame, ContactMain06.this, row);
	}


}
