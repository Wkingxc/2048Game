package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Random;

public class Welcome extends JFrame  {

    public Welcome()
    {
        //hello();
    }

    public void hello()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setSize(500,500);
        setLocationRelativeTo(null);
        //setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("欢迎使用");
        setLayout(null);

        URL img = Welcome.class.getResource("image/hello.png");
        JLabel helloimag = new JLabel(new ImageIcon(img));
        helloimag.setBounds(0,0,500,500);

        JLabel hellotext = new JLabel("欢迎使用");
        hellotext.setBounds(150,50,200,200);
        hellotext.setFont(new Font("行书",Font.BOLD,40));
        hellotext.setForeground(Color.green);

        JButton button1 = new JButton();
        button1.setText("正常进入");
        button1.setFont(new Font("Dialog", Font.BOLD,30));
        button1.setForeground(Color.red);
        button1.setBounds(300,300,200,100);

        JButton button2 = new JButton();
        button2.setText("简单进入");
        button2.setFont(new Font("Dialog", Font.BOLD,10));
        button2.setForeground(Color.black);
        button2.setBounds(0,300,200,100);

        getContentPane().add(button1);
        getContentPane().add(button2);
        getContentPane().add(hellotext);
        getContentPane().add(helloimag);

        button1.addMouseListener(new Mouselisten1());
        button2.addMouseListener(new Mouselisten2());

        setVisible(true);
    }

    class Mouselisten1 implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
           double num1 = Math.random()*(100 +1);
            double num2 = Math.random()*(100 +1);
            double result = num1 + num2;
            String input;
            input = JOptionPane.showInputDialog(null,"请输入正确答案：\n"+num1+"+"+num2+"=?\n","人机验证",JOptionPane.PLAIN_MESSAGE);
            if(input==null||input.equals(""))
            {
                JOptionPane.showMessageDialog(null,"are you kidding me?","验证失败！",JOptionPane.ERROR_MESSAGE);
            }else
            {
                double in_result = Double.parseDouble(input);
                if(Math.abs(result-in_result)<1e-6)
                {
                    JOptionPane.showMessageDialog(null,"Bingo!","验证成功",JOptionPane.PLAIN_MESSAGE);
                    //new Myframe();
                    setVisible(false);
                }else
                {
                    JOptionPane.showMessageDialog(null,"are you kidding me?","验证失败！",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class Mouselisten2 implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            Random r = new Random();
            int num1 = r.nextInt(101);
            int num2 = r.nextInt(101);
            int result = num1 + num2;
            String input;
            input = JOptionPane.showInputDialog(null,"请输入正确答案：\n"+num1+"+"+num2+"=?\n","人机验证",JOptionPane.PLAIN_MESSAGE);
            if(input==null||input.equals(""))
            {
                JOptionPane.showMessageDialog(null,"are you kidding me?","验证失败！",JOptionPane.ERROR_MESSAGE);
            }else
            {
                int in_result = Integer.parseInt(input);
                if(result==in_result)
                {
                    JOptionPane.showMessageDialog(null,"Bingo!","验证成功",JOptionPane.PLAIN_MESSAGE);
                    //new Myframe();
                    setVisible(false);
                }else
                {
                    JOptionPane.showMessageDialog(null,"are you kidding me?","验证失败！",JOptionPane.ERROR_MESSAGE);
                }
            }


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

