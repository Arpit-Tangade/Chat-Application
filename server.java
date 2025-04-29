import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*; // To set bg color of frame
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import java.io.*;

public class server implements ActionListener {
    JTextField text;
    JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    server(){

        f.setLayout(null);// not using any swing layout and creating our own layout

        //to set the green panel on top of frame
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);// setting size of panel
        p1.setLayout(null);
        f.add(p1); // setting panel on top of frame

        //to get the arrow on left top
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        //to make arrow button functionable, if we clicked the arrow program will be terminated
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //to get the profile on left top
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/manu.jpg"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 20, 50, 50);
        p1.add(profile);

        //to get the video on right top
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        //to get the phone on right top
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        //to get the more on right top
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        //name beside profile picture using jlable
        JLabel name = new JLabel("Manu");
        name.setBounds(110, 20, 100, 25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        //status below name picture using jlable
        JLabel status = new JLabel("Active now");
        status.setBounds(110, 40, 100, 25);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 10));
        p1.add(status);

        p2 = new JPanel();
        p2.setBounds(5, 75, 440, 570);
        f.add(p2);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

         JButton send = new JButton("Send");
         send.setBounds(320, 655, 123, 40);
         send.setBackground(new Color(7, 94, 84));
         send.setForeground(Color.WHITE);
         send.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
         send.addActionListener(this);
        f.add(send);


        //to set location of frame and color
        f.setSize(450, 700);  //size of frame
        f.setLocation(200, 50); // By default th location of frame will be left top
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE );

        f.setVisible(true); //By default the frame will be invisible, to make it visible this function is used

    }

    //In-order to implement actionListner we should over ride this method
    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            JPanel p3 = formatLabel(out);


            p2.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            p2.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");// emptying text after sending

            f.repaint();
            f.invalidate();
            f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+ out + "</p></html>");
        output.setFont(new Font("SAN_SERIEF", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(output);

        Calendar cal = Calendar.getInstance();// for time under msg
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);


        return panel;
    }

    public static void main(String[] args) {
        new server();

        try{
            ServerSocket skt = new ServerSocket(6001);//this creats a server
            while(true){
                Socket s = skt.accept();//this listens msg's
                DataInputStream din = new DataInputStream(s.getInputStream());//for reading
                 dout = new DataOutputStream(s.getOutputStream());//for sending

                while(true){
                    String msg = din.readUTF();//for reading msg's
                    JPanel panel = formatLabel(msg);//for displaying msg

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);//for displaying mag at left
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
