package Client.ui;

import Client.ClientService.UserInfo;
import Client.rmi.RemoteHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
public class LoginDialog extends JDialog {
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton okButton;
    private JButton cancleButton;
    private UserInfo userInfo;

    public LoginDialog() {
        setTitle("登录");
        setModal(true);
        usernameText = new JTextField();
        passwordText = new JPasswordField();
        okButton = new JButton("登录");
        cancleButton = new JButton("取消");



        Container pane = getContentPane();

        JLabel title = new JLabel("登 录");
        title.setFont(new Font("宋体", Font.PLAIN, 20));
        title.setBounds(120, 10, 100, 40);
        pane.add(title);

        JLabel usernameLable = new JLabel("用户名");
        usernameLable.setFont(new Font("宋体", Font.PLAIN, 12));
        usernameLable.setBounds(50, 50, 40, 20);
        pane.add(usernameLable);
        JLabel passwordLable = new JLabel("密码");
        passwordLable.setFont(new Font("宋体", Font.PLAIN, 12));
        passwordLable.setBounds(50, 80, 40, 20);
        pane.add(passwordLable);

        usernameText.setFont(new Font("宋体", Font.PLAIN, 12));
        usernameText.setBounds(100, 50, 150, 20);
        passwordText.setFont(new Font("宋体", Font.PLAIN, 12));
        passwordText.setBounds(100, 80, 150, 20);
        pane.add(usernameText);
        pane.add(passwordText);


        okButton.setBounds(50, 120, 60, 20);
        okButton.setBackground(Color.lightGray);
        okButton.setFont(new Font("宋体", Font.PLAIN, 12));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                String password = passwordText.getText();
                try {
                    String ret = RemoteHelper.getInstance().getLoginService().login(username, password);
                    String[] ss = ret.split(";");
                    if (ss[0].equals("ok")) {
                        userInfo = new UserInfo(username, ss[2]);
                    }
                    else {
                        int errno = Integer.parseInt(ss[1]);
                        String message = "";
                        switch (errno) {
                            case 1:
                                message = "该用户不存在";
                                break;
                            case 2:
                                message = "密码错误";
                                break;
                            case 3:
                                message = "密码位数必须在6-20个字符之间";
                                break;
                            case 4:
                                message = "用户名必须由4-20个字符组成";
                                break;
                            default:
                                message = "服务器内部错误";
                        }

                        JOptionPane.showConfirmDialog(null, message, "登录错误", JOptionPane.DEFAULT_OPTION);
                        return;
                    }
                }catch (Exception e1) {
                    userInfo = null;
                    e1.printStackTrace();
                }

                setVisible(false);
            }
        });
        pane.add(okButton);

        cancleButton.setBounds(190, 120, 60, 20);
        cancleButton.setBackground(Color.lightGray);
        cancleButton.setFont(new Font("宋体", Font.PLAIN, 12));
        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        pane.add(cancleButton);

        setLayout(null);
        setSize(300, 200);
        setLocation(500, 100);
    }

    public UserInfo start() {
        userInfo = null;
        passwordText.setText("");

        setVisible(true);

        return userInfo;
    }
}
