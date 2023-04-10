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
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class ContactCreateFrame extends JFrame {

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
	private ContactMain06 app; // nofifyContactCreated 메서드를 가지고 있는 객체
	
	ContactDaoImpl dao = ContactDaoImpl.getInstance();

	/**
	 * Launch the application.
	 */
	public static void showContactCreateFrame(Component parent, ContactMain06 app) { // main을 남겨놓으면 안됨. (main 메서드 이름 바꾸기) // JFrame은 Component를 상속
		// this - ActionListener 타입의 익명 객체
		// ContactMain06.this - ContactMain06 타입의 객체
		// 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactCreateFrame frame = new ContactCreateFrame(parent, app);
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
	public ContactCreateFrame(Component parent, ContactMain06 app) {
		this.parent = parent;
		this.app = app;
		initialize(); // GUI 컴포넌트들을 생성하고 초기화.
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
		setTitle("새 연락처 저장");
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
		
		btnCreate = new JButton("저장");
		btnCreate.setFont(new Font("D2Coding", Font.PLAIN, 20));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewContact();
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

	private void createNewContact() {
		// JTextField에서 이름/전화번호/이메일을 읽음
		String name = textName.getText();
		String phone = textPhone.getText();
		String email = textEmail.getText();
		// Contact 타입 객체 생성
		Contact newContact = new Contact(0, name, phone, email);
		dao.create(newContact);
		
		
		// ContactMain에게 새 연락처가 저장됐다고 알려줌.
		app.notifyContactCreated(); 
		
		// 창 닫기
		dispose();
	}
}
