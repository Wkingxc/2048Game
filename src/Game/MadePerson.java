package Game;

import javax.swing.*;
import java.awt.*;

public class MadePerson extends JFrame {

    MadePerson()
    {
        person();
    }

    public void person()
    {
        //setSize(1920,1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("制作人员");
        setLayout(null);

        //JLabel w = new JLabel("Mr.Wang\nLanguage: Java\nVersion : unknown");
        JLabel w = new JLabel("<html><body><br />感谢使用！<br />Mr.Wang<br /><br />" +
                "Langguage:Java<br /><br />" +
                "Version:unknown</body></html>");
        w.setBounds(500,100,500,500);
        w.setFont(new Font("行楷", Font.BOLD,50));

        getContentPane().add(w);
        setVisible(true);
    }
}
