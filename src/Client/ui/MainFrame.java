package Client.ui;

import Client.ClientService.RemoteHelper;
import Client.ClientService.UserInfo;
import Client.ClientService.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;
	private Map<String, MMenu> menus;
	private JTextField queryField;
	private JTextPane textPane;
	private JButton queryButton;
	private JLabel inputLabel;
	private JLabel title;
	private JTextPane baiduText;
	private JTextPane youdaoText;
	private JTextPane bingText;
	private JLabel baiduApprovNum;
	private JLabel youdaoApprovNum;
	private JLabel bingApprovNum;
	private JToolBar toolBar;
	private JLabel status;
	private boolean[] checkedQuery;


	public MainFrame() {
		// 创建窗体
		super("Web Dict");
		setLayout(null);


		initComponent();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 650);
		setLocation(450, 50);
		setResizable(false);
		setVisible(true);
	}

	private void initComponent() {
		menus = new HashMap<>();
		checkedQuery = new boolean[] {true, true, true};

		ImageIcon favorIcon = new ImageIcon(MainFrame.class.getClassLoader().getResource("favor.png"));

		/***********Title Area*************/
		title = new JLabel("WEB  DICT");
		title.setFont(new Font("宋体", Font.PLAIN, 30));
		title.setBounds(180, 0, 200, 60);
		add(title);

		/***********Check Area***********/
		JPanel checkPanel = new JPanel(new GridLayout(1, 0));

		JCheckBox baiduCheck;
		JCheckBox youdaoCheck;
		JCheckBox bingCheck;

		baiduCheck = new JCheckBox("百度");
		baiduCheck.setMnemonic(KeyEvent.VK_C);
		baiduCheck.setSelected(true);

		youdaoCheck = new JCheckBox("有道");
		youdaoCheck.setMnemonic(KeyEvent.VK_C);
		youdaoCheck.setSelected(true);

		bingCheck = new JCheckBox("必应");
		bingCheck.setMnemonic(KeyEvent.VK_C);
		bingCheck.setSelected(true);

		ItemListener checkListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getSource();
				String name = ((JCheckBox)source).getText();

				int index = 0;
				if (name.equals("百度")) {
					index = 0;
				} else if (name.equals("有道")) {
					index = 1;
				} else if (name.equals("必应")) {
					index = 2;
				}

				if(e.getStateChange() == ItemEvent.DESELECTED)
					checkedQuery[index] = false;
				else
					checkedQuery[index] = true;
			}
		};

		baiduCheck.addItemListener(checkListener);
		youdaoCheck.addItemListener(checkListener);
		bingCheck.addItemListener(checkListener);

		checkPanel.add(baiduCheck);
		checkPanel.add(youdaoCheck);
		checkPanel.add(bingCheck);
		checkPanel.setBounds(100, 120, 300, 30);
		add(checkPanel);


		/*********Query Area*************/
		queryField = new JTextField();
		queryField.setBounds(80, 70, 300, 30);
		add(queryField);

		inputLabel = new JLabel("Input");
		inputLabel.setFont(new Font("宋体", Font.PLAIN, 24));
		inputLabel.setBounds(10, 68, 60, 30);
		add(inputLabel);
		textPane = new JTextPane();
		add(textPane);
		queryButton = new JButton();
		queryButton.setText("Search");
		queryButton.setBounds(390, 70, 80, 30);
		queryButton.setFont(new Font("宋体", Font.PLAIN, 13));
		queryButton.setBackground(Color.lightGray);
		queryButton.setFocusPainted(false);
		queryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = queryField.getText();
				try {
					String[] ret = RemoteHelper.getInstance().getQueryService().queryWord(word);
					if (checkedQuery[0]) {
						baiduText.setText(ret[0]);
					}
					else {
						baiduText.setText("");
					}
					if (checkedQuery[1]) {
						youdaoText.setText(ret[1]);
					}
					else {
						youdaoText.setText("");
					}
					if (checkedQuery[2]) {
						bingText.setText(ret[2]);
					} else {
						bingText.setText("");
					}

					baiduApprovNum.setText("");
					youdaoApprovNum.setText("");
					bingApprovNum.setText("");

					new Thread(new Runnable() {
						@Override
						public void run() {
							if (checkedQuery[0]) {
								try {
									String word = queryField.getText();
									new Thread(()->{baiduApprovNum.setText("赞：" +
											RemoteHelper.getInstance().getQueryService().getApprovNum(word, "baidu"));}).run();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (checkedQuery[1]) {
								try {
									String word = queryField.getText();
									new Thread(()->{youdaoApprovNum.setText("赞：" +
											RemoteHelper.getInstance().getQueryService().getApprovNum(word, "youdao"));}).run();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (checkedQuery[2]) {
								try {
									String word = queryField.getText();
									new Thread(()->{bingApprovNum.setText("赞：" +
											RemoteHelper.getInstance().getQueryService().getApprovNum(word, "bing"));}).run();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
					}).run();
				}
				catch (Exception r) {
					r.printStackTrace();
				}
			}
		});
		add(queryButton);



		/***********BaiDu Result*******************/
		JTextField baiduTitle = new JTextField();
		baiduTitle.setText("百  度");
		baiduTitle.setEditable(false);
		baiduTitle.setFont(new Font("微软雅黑", Font.BOLD, 15));
		baiduTitle.setBounds(10, 150, 60, 30);
		baiduTitle.setHorizontalAlignment(JTextField.CENTER);
		baiduTitle.setBackground(Color.WHITE);
		add(baiduTitle);

		baiduText = new JTextPane();
		baiduText.setFont(new Font("宋体", Font.PLAIN, 12));
		baiduText.setEditable(false);
		baiduText.setBounds(10, 190, 390, 80);
		baiduText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		add(baiduText);

		JButton baiduFavor = new JButton();
		baiduFavor.setBounds(410, 230, 40, 40);
		baiduFavor.setIcon(favorIcon);
		baiduFavor.setFocusPainted(false);
		baiduFavor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (checkedQuery[0] && UserManager.isLogin() && !baiduText.getText().equals("")) {
							try {
								String word = queryField.getText();
								RemoteHelper.getInstance().getQueryService().
										toggleApprovTranslation(UserManager.getUserInfo().getSession(), word, "baidu");
								baiduApprovNum.setText("赞：" +
										RemoteHelper.getInstance().getQueryService().getApprovNum(word, "baidu"));
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				}).run();
			}
		});
		add(baiduFavor);

		baiduApprovNum = new JLabel();
		baiduApprovNum.setFont(new Font("宋体", Font.PLAIN, 12));
		baiduApprovNum.setBounds(410, 210, 100, 20);
		add(baiduApprovNum);

		/***********Youdao Result*******************/
		JTextField youdaoTitle = new JTextField();
		youdaoTitle.setText("有  道");
		youdaoTitle.setEditable(false);
		youdaoTitle.setFont(new Font("微软雅黑", Font.BOLD, 15));
		youdaoTitle.setBounds(10, 290, 60, 30);
		youdaoTitle.setHorizontalAlignment(JTextField.CENTER);
		youdaoTitle.setBackground(Color.WHITE);
		add(youdaoTitle);

		youdaoText = new JTextPane();
		youdaoText.setFont(new Font("宋体", Font.PLAIN, 12));
		youdaoText.setEditable(false);
		youdaoText.setBounds(10, 330, 390, 80);
		youdaoText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		add(youdaoText);

		JButton youdaoFavor = new JButton();
		youdaoFavor.setBounds(410, 370, 40, 40);
		youdaoFavor.setIcon(favorIcon);
		youdaoFavor.setFocusPainted(false);
		youdaoFavor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (checkedQuery[1] && UserManager.isLogin() && !youdaoText.getText().equals("")) {
							try {
								String word = queryField.getText();
								RemoteHelper.getInstance().getQueryService().
										toggleApprovTranslation(UserManager.getUserInfo().getSession(), word, "youdao");
								youdaoApprovNum.setText("赞：" +
										RemoteHelper.getInstance().getQueryService().getApprovNum(word, "youdao"));
							} catch (Exception e2) {
								e2.printStackTrace();
							}


						}
					}
				}).run();
			}
		});
		add(youdaoFavor);

		youdaoApprovNum = new JLabel();
		youdaoApprovNum.setFont(new Font("宋体", Font.PLAIN, 12));
		youdaoApprovNum.setBounds(410, 350, 100, 20);
		add(youdaoApprovNum);


		/***********Bing Result*******************/
		JTextField bingTitle = new JTextField();
		bingTitle.setText("必  应");
		bingTitle.setEditable(false);
		bingTitle.setFont(new Font("微软雅黑", Font.BOLD, 15));
		bingTitle.setBounds(10, 430, 60, 30);
		bingTitle.setHorizontalAlignment(JTextField.CENTER);
		bingTitle.setBackground(Color.WHITE);
		add(bingTitle);

		bingText = new JTextPane();
		bingText.setFont(new Font("宋体", Font.PLAIN, 12));
		bingText.setEditable(false);
		bingText.setBounds(10, 470, 390, 80);
		bingText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		add(bingText);

		JButton bingFavor = new JButton();
		bingFavor.setBounds(410, 510, 40, 40);
		bingFavor.setIcon(favorIcon);
		bingFavor.setFocusPainted(false);
		bingFavor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (checkedQuery[2] && UserManager.isLogin() &&!bingText.getText().equals("")) {
							try {
								String word = queryField.getText();
								RemoteHelper.getInstance().getQueryService().
										toggleApprovTranslation(UserManager.getUserInfo().getSession(), word, "bing");
								bingApprovNum.setText("赞：" +
										RemoteHelper.getInstance().getQueryService().getApprovNum(word, "bing"));
							} catch (Exception e2) {
								e2.printStackTrace();
							}


						}
					}
				}).run();
			}
		});
		add(bingFavor);

		bingApprovNum = new JLabel();
		bingApprovNum.setFont(new Font("宋体", Font.PLAIN, 12));
		bingApprovNum.setBounds(410, 490, 100, 20);
		add(bingApprovNum);


		/***********Status**************/
		toolBar = new JToolBar();
		status = new JLabel();
		status.setFont(new Font("微软雅黑", Font.BOLD, 12));
		toolBar.add(status);
		toolBar.setBounds(0, 580, 500, 20);
		add(toolBar);


		initMenu();
	}

	private void initMenu() {
		/*************Register Menu***********/
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);


		/**************User Service************/
		MMenu menu = new MMenu("User");
		menuBar.add(menu.getMenu());
		menus.put("User", menu);

		JMenuItem loginItem = new JMenuItem("登录");
		loginItem.setFont(new Font("宋体", Font.PLAIN, 12));
		loginItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserInfo userInfo = new LoginDialog().start();
				if (userInfo != null) {
					UserManager.Login(userInfo);
					status.setText("已登录: " + userInfo.getUsername());
				}
			}
		});
		menu.addItem("Login", loginItem);
		JMenuItem logoutItem = new JMenuItem("注销");
		logoutItem.setFont(new Font("宋体", Font.PLAIN, 12));
		logoutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserManager.logout();
				status.setText("");
			}
		});
		menu.addItem("Logout", logoutItem);
		JMenuItem registerItem = new JMenuItem("注册");
		registerItem.setFont(new Font("宋体", Font.PLAIN, 12));
		registerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserInfo userInfo = new RegisterDialog().start();
				if (userInfo != null) {
					UserManager.Login(userInfo);
					status.setText("已登录: " + userInfo.getUsername());
				}
			}
		});
		menu.addItem("Register", registerItem);
	}
}
