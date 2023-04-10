package edu.java.contact06;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.contact.model.Contact;
import edu.java.contact05.ContactDaoImpl;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class ContactUpdateFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblName;
	private JTextField textName;
	private JTextField textPhone;
	private JLabel lblName_1;
	private JTextField textEmail;
	private JLabel lblEmail;
	private JButton btnCreate;
	private JButton btnCancel;
	
	private Component parent; // 부모 컴포넌트(JFrame)를 저장하기 위한 필드.
	private ContactMain06 app; // ContactMain06의 멤버를 사용하기 위한 주소를 저장 app.머시기로 사용가능
	private int index;
	
	ContactDaoImpl dao = ContactDaoImpl.getInstance();

	/**
	 * Launch the application.
	 */
	public static void showContactUpdateFrame(Component parent, ContactMain06 app, int index) { // main을 남겨놓으면 안됨. (main 메서드 이름 바꾸기)
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactUpdateFrame frame = new ContactUpdateFrame(parent, app, index);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ContactUpdateFrame(Component parent, ContactMain06 app, int row) {
		this.parent = parent; // 부모 컴포넌트(JFrame)를 저장하기 위한 필드
		this.app = app; // 업데이트 성공 한 후 알림 메서드를 호출하기 위한 객체
		this.index = row; // 업데이트할 연락처의 인덱스를 저장하기 위한 필드
		initialize(); // GUI 컴포넌트들을 생성하고 초기화.
		readContact();
	}
	
	private void readContact() {
		Contact contact = dao.read(index);
		textName.setText(contact.getName());
		textPhone.setText(contact.getPhone());
		textEmail.setText(contact.getEmail());		
	}

	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE: 닫기 버튼의 기본 동작을 "현재 창만 닫기"로 설정 
		int x = 100;
		int y = 100;
		if (parent != null) {
			x = parent.getX()+parent.getWidth();
			y = parent.getY();
		}
		
		setBounds(x, y, 459, 402);
		setTitle("연락처 업데이트");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblName = new JLabel("이름");
		lblName.setFont(new Font("D2Coding", Font.PLAIN, 26));
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(12, 25, 110, 52);
		panel.add(lblName);
		
		textName = new JTextField();
		textName.setFont(new Font("D2Coding", Font.PLAIN, 18));
		textName.setBounds(134, 25, 257, 52);
		panel.add(textName);
		textName.setColumns(10);
		
		lblName_1 = new JLabel("전화번호");
		lblName_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblName_1.setFont(new Font("D2Coding", Font.PLAIN, 26));
		lblName_1.setBounds(12, 99, 110, 52);
		panel.add(lblName_1);
		
		textPhone = new JTextField();
		textPhone.setFont(new Font("D2Coding", Font.PLAIN, 18));
		textPhone.setColumns(10);
		textPhone.setBounds(134, 100, 257, 52);
		panel.add(textPhone);
		
		lblEmail = new JLabel("이메일");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("D2Coding", Font.PLAIN, 26));
		lblEmail.setBounds(12, 170, 110, 52);
		panel.add(lblEmail);
		
		textEmail = new JTextField();
		textEmail.setFont(new Font("D2Coding", Font.PLAIN, 18));
		textEmail.setColumns(10);
		textEmail.setBounds(134, 175, 257, 52);
		panel.add(textEmail);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		btnCreate = new JButton("수정");
		btnCreate.setFont(new Font("D2Coding", Font.PLAIN, 20));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateNewContact();
			}
		});
		buttonPanel.add(btnCreate);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // 현재 창 닫기
			}
		});
		btnCancel.setFont(new Font("D2Coding", Font.PLAIN, 20));
		buttonPanel.add(btnCancel);
	}

	private void updateNewContact() {
		String name = textName.getText();
		String phone = textPhone.getText();
		String email = textEmail.getText();
		
		Contact contact = new Contact(0, name, phone, email);
		
		int confirm = JOptionPane.showConfirmDialog(
				ContactUpdateFrame.this, 
				"정말로 업데이트할까요?", 
				"업데이트 확인", 
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			dao.update(index, contact); // 리스트를 수정하고 파일을 수정
			app.notifyContactUpdate(); // 메인에 수정했다는 것을 알려주고 메인은 수정된 파일을 불러와 table에 띄워줌
			dispose(); // 현재 창 닫기
		}
		
	}
}
