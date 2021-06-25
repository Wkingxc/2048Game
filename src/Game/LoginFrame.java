package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginFrame extends JFrame implements ActionListener {


    FileInputStream fis = null;
    FileWriter fw = null;
    static JFrame frame = new JFrame();
    JButton button1 = new JButton("登录");
    JLabel label1 = new JLabel("账户名:");
    //JLabel label2 = new JLabel("密码:");
    JLabel label3 = new JLabel("欢迎使用！");
    JTextField account = new JTextField();

    //操作本地用户数据
    LoginFrame()
    {
        try {
            File file = new File("2048game.dat");
            if(!file.exists())
            {
                fw = new FileWriter(file);
                fw.close();
            }
            fis = new FileInputStream("2048game.dat");
            load();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"载入数据错误！","wrong!",JOptionPane.WARNING_MESSAGE);
        }
        go();
    }
    //绘制窗口
    public void go()
    {
        frame.setSize(500,450);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        label3.setBounds(180,10,200,100);
        label3.setFont(new Font("行书",Font.BOLD,20));
        label1.setBounds(60,70,80,80);
        label1.setFont(new Font("行书",Font.BOLD,20));
        account.setBounds(140,100,200,50);
        button1.setBounds(100,280,90,90);
        button1.setFont(new Font("行书",Font.BOLD,20));

        button1.addActionListener(this);

        frame.getContentPane().add(label1);
        frame.getContentPane().add(label3);
        frame.getContentPane().add(account);
        frame.getContentPane().add(button1);
        frame.setVisible(true);
    }

    //载入本地用户数据到内存
    public String load() throws IOException
    {
        fis = new FileInputStream("2048game.dat");
        byte[] datas = new byte[fis.available()];
        fis.read(datas);
        fis.close();
        return new String(datas);
    }

    //按钮功能的实现
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("登录"))
        {
            //TODO:
            try {
                String data = load();
                String username = account.getText();
                if(username.equals(""))
                {
                    JOptionPane.showMessageDialog(null,"请输入账户!");
                    return;
                }
                if(!data.contains(username))
                {
                    fw = new FileWriter("2048game.dat",true);
                    fw.write(username+" 0 0\n");
                    fw.close();
                    new Myframe(username,"0","0");
                }
                String[] datas = data.split("[ \n]");
                for(int i=0; i<datas.length;i++)
                {
                    if(datas[i].equals(username))
                    {
                        new PRcheck(username,datas[i+1],datas[i+2]);
                        break;
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.setVisible(false);
        }
    }
}
