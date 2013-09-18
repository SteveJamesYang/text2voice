package com.troyer.tts;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;

/**
 * ����google_tts�ӿ�,ʵ�ֽ��ı�ת��Ϊ�����ĳ���
 * 
 * @author Troy
 * @Email troy_1988@126.com
 */
public class TTSMain extends JFrame {

	private static final long serialVersionUID = 1914286717004357562L;

	// ��ȡ��Ļ��Ⱥ͸߶�
	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;

	static {
		SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth();
		SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight();
	}

	// �������ת�����������࣬��ʵgoogle_tts�ṩ�˼��������ϵ��������ԣ���������ֻѡ�����еļ��������Ҫ���ӣ�ֻ��Ҫ��������ӾͿ�����
	private String[][] destl = { { "zh-CN", "����" }, { "en", "Ӣ��" },
			{ "de", "����" }, { "ja", "����" }, { "fr", "����" } };

	// �������Ԫ��
	private JPanel panel = new JPanel();
	private JMenuBar menuBar;
	private JMenu menu1;
	private JMenu menu2;
	private JMenuItem menuitem1;
	private JMenuItem menuitem2;
	private JMenuItem menuitem3;
	private JButton[] desbtns = new JButton[destl.length];
	private JTextArea sourceText;
	private JButton start;
	private JLabel label;

	// ���嵱ǰת������
	public String tl = "";

	public TTSMain() {

		setTitle("google_tts Client v1.0");
		setIconImage(TTSUtils.getImage("/resources/images/tts_48.png"));
		setBounds((SCREEN_WIDTH - 500) / 2, (SCREEN_HEIGHT - 320) / 2, 500, 320);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setContentPane(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setResizable(false);
		initFrame();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initFrame() {

		// ����˵���
		menuBar = new JMenuBar();
		menu1 = new JMenu("�ļ�");
		menuitem1 = new JMenuItem("���ļ�");
		menuitem2 = new JMenuItem("�˳�");
		menuitem3 = new JMenuItem("������");
		menu2 = new JMenu("����");

		menuBar.add(menu1);
		menuBar.add(menu2);
		menu1.add(menuitem1);
		menu1.addSeparator();
		menu1.add(menuitem2);
		menu2.add(menuitem3);

		setJMenuBar(menuBar);

		// �����ļ�����¼�
		menuitem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return null;
					}

					// ֻ�ܴ���չ��Ϊ.txt���ļ�
					@Override
					public boolean accept(File f) {
						return f.getName().endsWith("txt") || f.isDirectory();
					}
				});

				int i = fc.showOpenDialog(panel);
				if (i == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						List<String> words = FileUtils.readLines(file);
						String str = "";
						for (int j = 0; j < words.size(); j++) {
							if (j == words.size() - 1) {
								str += words.get(j);
							} else {
								str += words.get(j) + "\n";
							}
						}
						sourceText.setText(str);

					} catch (IOException e1) {
						System.out.println("��ȡ�ļ�ʧ�ܣ�");
					}
				}
			}
		});

		// ���˳�����¼�
		menuitem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		menuitem3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(TTSMain.this,
						"Author�� Troy \nEmail�� Troy_1988@126.com");
			}
		});
		
		// �������ת�������ְ�ť��������¼���Ӧ�������ť�趨��Ӧ������Ϊ��ǰҪת��������
		for (int i = 0; i < destl.length; i++) {
			desbtns[i] = new JButton();
			desbtns[i].setText(destl[i][0]);
			desbtns[i].setToolTipText(destl[i][1]);
			desbtns[i].setBackground(new Color(238, 238, 238));
			desbtns[i].setForeground(Color.BLACK);
			panel.add(desbtns[i]);

			desbtns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < destl.length; i++) {
						if (desbtns[i] == e.getSource()) {
							desbtns[i].setBackground(Color.BLUE);
							desbtns[i].setForeground(Color.white);
						} else {
							desbtns[i].setBackground(new Color(238, 238, 238));
							desbtns[i].setForeground(Color.BLACK);
						}
					}
					tl = e.getActionCommand();
				}
			});
		}

		// ���һ���ı���
		sourceText = new JTextArea();
		sourceText.setBackground(Color.white);
		sourceText.setRows(10);
		sourceText.setColumns(40);
		// Ϊ�ı�����ӹ�����
		JScrollPane scroll = new JScrollPane(sourceText);
		// �趨�����ı������ʾ������
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(scroll);

		// ��ӿ�ʼת����ť
		start = new JButton();
		start.setSize(10, 20);
		start.setText("��ʼת��");
		start.setToolTipText("���ı�ת��Ϊ����");
		start.setBackground(new Color(238, 238, 238));
		start.setForeground(Color.BLACK);
		panel.add(start);

		// ���һЩ������Ϣ
		label = new JLabel();
		label.setText("ת�����ļ�������D:/tts_result��");
		label.setForeground(Color.BLUE);
		panel.add(label);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!tl.equals("") && sourceText.getText().trim().length() != 0) {
					String str[] = sourceText.getText().split("\n|\t");
					for (String words : str) {
						if (words.trim().length() != 0) {
							TTSUtils.saveMp3File(words, "d:/tts_result", tl);
						}
					}
					JOptionPane.showMessageDialog(TTSMain.this,
							"ת���ɹ����뵽D:/tts_result�в鿴ת�������");
				} else {
					JOptionPane.showMessageDialog(TTSMain.this,
							"��ѡ��Ҫת�������������Լ�Ҫת�����ı���");
				}
			}
		});

	}

	public static void main(String[] args) {

		new TTSMain();
	}

}
