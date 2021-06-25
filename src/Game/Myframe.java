package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class Myframe extends JFrame implements KeyListener ,ActionListener {

    String user;
    int[][] photos = new int[4][4];

    String maxscore, maxnumber;
    int fail = 0;
    int score = 0;
    int success = 0;
    String theme = "A-";

    JMenuItem xin = new JMenuItem("新游戏");
    JMenuItem item1 = new JMenuItem("经典");
    JMenuItem item2 = new JMenuItem("炫光");
    JMenuItem item3 = new JMenuItem("糖果");
    JMenuItem item4 = new JMenuItem("手写");
    JMenuItem jifen = new JMenuItem("修改积分");
    JMenuItem editphoto = new JMenuItem("修改数据");
    JMenuItem person = new JMenuItem("制作人员");

    public Myframe(String user,String maxscore,String maxnumber)
    {
        this.maxscore = maxscore;
        this.maxnumber = maxnumber;
        this.user = user;
        //初始化窗体
        start_frame();
        //初始化菜单栏
        initMenu();
        //初始化数据
        initPhoto();
        //绘制界面
        paint_view();
        //为窗体添加键盘监听
        addKeyListener(this);
        setVisible(true);

    }
    //设置窗体初始化
    public void start_frame()
    {
        //设置窗体大小
        setSize(514,538);
        //设置窗体居中
        setLocationRelativeTo(null);
        //设置窗体置顶
        //setAlwaysOnTop(true);
        //设置关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗体标题
        setTitle("2048Game (欢迎您的使用）");
        //取消默认布局
        setLayout(null);
    }
    //绘制窗口
    public void paint_view()
    {
        URL imgURL;//根据不同地址取程序运行的根目录
        getContentPane().removeAll();
        if(fail==1)
        {
            imgURL = Myframe.class.getResource("image/"+theme+"lose.png");
            JLabel lose  = new JLabel(new ImageIcon(imgURL));
            lose.setBounds(85,100,334,228);
            getContentPane().add(lose);
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                imgURL = Myframe.class.getResource("image/"+theme+photos[i][j]+".png");
                JLabel image = new JLabel(new ImageIcon(imgURL));
                image.setBounds(50+100*j,50+100*i,100,100);
                getContentPane().add(image);
            }
        }
        imgURL = Myframe.class.getResource("image/"+theme+"Background.jpg");
        JLabel background = new JLabel(new ImageIcon(imgURL));
        background.setBounds(40,40,420,420);
        getContentPane().add(background);


        JLabel scorelabel = new JLabel("积分："+score+"      历史:  "+" 最高积分:"+maxscore+"   最大数字:"+maxnumber);
        scorelabel.setBounds(15,15,500,20);
        scorelabel.setFont(new Font("Dialog", Font.BOLD,20));
        scorelabel.setForeground(Color.blue);
        getContentPane().add(scorelabel);

        imgURL = Myframe.class.getResource("image/Back.jpg");
        JLabel back = new JLabel(new ImageIcon(imgURL));
        back.setBounds(0,0,1080,1920);
        getContentPane().add(back);

        getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //TODO:事件监听器
    @Override
    public void keyPressed(KeyEvent e)
    {
        int keycode = e.getKeyCode();
        if(success!=1)
        {
            switch (keycode)
            {
                case 37 ->
                        {
                            leftmove();
                            randomnum();
                        }
                case 38 ->
                        {
                            upmove();
                            randomnum();
                        }
                case 39 ->
                        {
                            rightmove();
                            randomnum();
                        }
                case 40 ->
                        {
                            downmove();
                            randomnum();
                        }
            }
        }
        if(keycode==KeyEvent.VK_S)
        {
            try {
                save();
                JOptionPane.showMessageDialog(null,"保存成功！");
                LoginFrame.frame.setVisible(true);
                setVisible(false);
                //System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(isFailure())
        {
            fail = 1;
        }
        if(success==0&&issuccess())
        {
            JOptionPane.showConfirmDialog(null,"成功达到2048！太厉害了！","Congratulations!",JOptionPane.OK_CANCEL_OPTION);
            success = 1;
        }
        paint_view();

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    //操纵数组的算法以及辅助函数
    public void leftmove()
    {
        for(int x=0; x<4; x++)
        {
            int[] temp = new int[4];
            int k = 0;
            //后置0元素
            for(int i:photos[x])
            {
                if(i!=0)
                {
                    temp[k++]=i;
                }
            }
            photos[x]=temp;

            for(int i = 0; i<3; i++)
            {
                if(photos[x][i]==photos[x][i+1])
                {
                    photos[x][i]*=2;
                    score+=photos[x][i];

                    System.arraycopy(photos[x], i + 1 + 1, photos[x], i + 1, 3 - (i + 1));
                    photos[x][3]=0;
                }
            }

        }
    }
    public void rightmove()
    {
        for(int i=0; i<4 ;i++)
        {
            reverse(photos[i]);
        }
        leftmove();
        for(int i=0; i<4 ;i++)
        {
            reverse(photos[i]);
        }
    }
    public void upmove()
    {
        anticlockwise();
        leftmove();
        clockwise();
    }
    public void downmove()
    {
        clockwise();
        leftmove();
        anticlockwise();
    }
    public void reverse(int []a)
    {
        for(int start=0,end=a.length-1; start<end; start++,end--)
        {
            int temp = a[start];
            a[start] = a[end];
            a[end] = temp;
        }
    }
    public void clockwise()
    {
        int[][] a = new int[4][4];
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                a[i][j] = photos[3-j][i];
            }
        }
        System.arraycopy(a, 0, photos, 0, 4);
    }
    public void anticlockwise()
    {
        int[][] a = new int[4][4];
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                a[i][j] = photos[j][3-i];
            }
        }
        System.arraycopy(a, 0, photos, 0, 4);
    }

    //判断是否成功
    public boolean issuccess()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if(photos[i][j]==2048)
                {
                    return true;
                }
            }
        }
        return false;
    }
    //判断失败
    public boolean isFailure()
    {
        int v_score = score;
        int[][] temp = new int[4][4];
        copyArray(temp, photos);
        //left judge
        leftmove();
        if(!equalArray(temp,photos))
        {
            copyArray(photos,temp);
            score = v_score;
            return false;
        }
        //right judge
        rightmove();
        if(!equalArray(temp,photos))
        {
            copyArray(photos,temp);
            score = v_score;
            return false;
        }
        //up judge
        upmove();
        if(!equalArray(temp,photos))
        {
            copyArray(photos,temp);
            score = v_score;
            return false;
        }
        //down judge
        downmove();
        if(!equalArray(temp,photos))
        {
            copyArray(photos,temp);
            score = v_score;
            return false;
        }
        score = v_score;
        return true;
    }

    public void copyArray(int[][] dest, int[][] src)
    {
        int line = src.length;
        int column = src.length;
        for(int i=0; i<line; i++)
        {
            System.arraycopy(src[i],0,dest[i],0,column);
        }
    }
    public boolean equalArray(int[][] a, int[][] b)
    {
        int line = a.length;
        int column = a[0].length;
        for(int i=0; i<line; i++)
        {
            for(int j=0; j<column; j++)
            {
                if(a[i][j] !=b [i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    //每次移动时，新方块出现的位置和数字大小（按照标准游戏设计概率）
    public void randomnum()
    {
        int[] a = new int[16];
        int[] b = new int[16];
        int k = 0;
        for(int i =0; i<16; i++)
        {
            a[i]=-1;
            b[i]=-1;
        }
        for (int i = 0; i < 4; i++)
        {
            for(int j=0; j < 4; j++)
            {
                if(photos[i][j]==0)
                {
                    a[k] = i;
                    b[k] = j;
                    k++;
                }
            }
        }
        if(k!=0)
        {
            Random r = new Random();
            int index = r.nextInt(k);
            int x = a[index];
            int y = b[index];
            int value = Math.random()<0.9? 2 : 4;
            photos[x][y] = value;
        }
    }
    public void initPhoto()
    {
        randomnum();
        randomnum();
    }

    //绘制菜单栏
    public void initMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu("开始");
        JMenu menu2 = new JMenu("皮肤");
        JMenu menu3 = new JMenu("修改器");
        JMenu menu4 = new JMenu("制作人员");
        JMenu menu5 = new JMenu("按S键保存");

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        menuBar.add(menu5);

        menu1.add(xin);
        menu2.add(item1);
        menu2.add(item2);
        menu2.add(item3);
        menu2.add(item4);
        menu3.add(jifen);
        menu3.add(editphoto);
        menu4.add(person);

        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        xin.addActionListener(this);
        jifen.addActionListener(this);
        editphoto.addActionListener(this);
        person.addActionListener(this);


        setJMenuBar(menuBar);
    }


    //TODO:保存游戏数据
    public void save() throws IOException
    {
        FileInputStream fis = new FileInputStream("2048game.dat");
        byte[] datas = new byte[fis.available()];
        fis.read(datas);
        String[] users = new String(datas).split("\n");
        int index = 0;//获取当前用户的序号
        for(int i=0; i<users.length;i++)
        {
            String[] temp = users[i].split(" ");
            if(temp[0].equals(user))
            {
                index = i;
                break;
            }
        }
        int amaxscore = Math.max(score,Integer.parseInt(maxscore));
        int amaxnumer = Integer.parseInt(maxnumber);
        for(int[] i:photos)
        {
            for(int j: i)
            {
                amaxnumer = Math.max(j,amaxnumer);
            }
        }
        FileWriter fw = new FileWriter("2048game.dat");
        for(int i=0; i<users.length;i++)
        {
            if(i!=index)
            {
                fw.write(users[i]+"\n");
            }else
            {
                fw.write(user+" "+amaxscore+" "+amaxnumer+"\n");
            }
        }
        fw.close();
        fis.close();
    }

    //用于子菜单的事件监听
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == item1)
        {
            theme = "A-";
            paint_view();
        }else if(e.getSource() == item2)
        {
            theme = "B-";
            paint_view();
        }else if (e.getSource() == item3)
        {
            theme = "C-";
            paint_view();
        }else if(e.getSource()==item4)
        {
            theme = "D-";
            paint_view();
        }
        else if(e.getSource()== xin)
        {
            score = 0;
            fail = 0;
            success = 0;
            for(int i=0; i<4; i++)
            {
                for(int j=0; j<4;j++)
                {
                    photos[i][j] = 0;
                }
            }
            initPhoto();
            paint_view();
        }else if(e.getSource()==jifen)
        {
            String s;
            s = JOptionPane.showInputDialog(null,"请输入要修改的积分:\n");
            if(s==null||s.equals(""))
            {
                JOptionPane.showMessageDialog(null,"???","what are you doing?",JOptionPane.WARNING_MESSAGE);
            }else
            {
                score = Integer.parseInt(s);
                JOptionPane.showMessageDialog(null,"修改成功！");
                paint_view();
            }
        }else if(e.getSource()==editphoto)
        {
            String s;
            int i,j;
            Object[] num = {"1","2","3","4"};
            Object[] data = {"2","4","8","16","32","64","128","256","512","1024","2048"};
            i = JOptionPane.showOptionDialog(null,"请输入要修改的行数：","修改器",JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null,num,num[0]);
            j = JOptionPane.showOptionDialog(null,"请输入要修改的列数：","修改器",JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null,num,num[0]);

            s = (String) JOptionPane.showInputDialog(null,"请修改数据为","标题",JOptionPane.QUESTION_MESSAGE,
                    null,data,data[0]);

           photos[i][j] = Integer.parseInt(s);
           paint_view();

        }
        else if(e.getSource()==person)
        {
            new MadePerson();
        }
    }
}
