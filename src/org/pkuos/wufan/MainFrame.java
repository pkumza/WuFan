package org.pkuos.wufan;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by marchon on 15/6/2.
 * 作用：
 *     作为主要的Frame，创建窗口用的。
 */
public class MainFrame extends JFrame implements ActionListener{

    JTextField jt = new JTextField("APK 1", 20);
    JTextField jt2 = new JTextField("APK 2", 20);
    JLabel jl = new JLabel(" Notes:");

    public void CreateJFrame(String title){
        JFrame jf = new JFrame(title);
        Container container = jf.getContentPane();
        container.setLayout(new GridLayout(3,2));

        jf.add(jt);
        JButton jb = new JButton("Choose APK File1");
        jf.add(jb);
        jf.add(jt2);
        JButton jb2 = new JButton("Choose APK File2");
        jf.add(jb2);
        jf.add(jl);
        JButton jb3 = new JButton("Compare");
        container.add(jb3);
        container.setBackground(Color.WHITE);
        jf.setVisible(true);
        jf.setBounds(400, 300, 500, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jb.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // System.out.println(e.getActionCommand());
        if(e.getActionCommand().equals("Choose APK File1")) {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".apk");
                }

                @Override
                public String getDescription() {
                    return ".apk";
                }
            });
            jfc.showDialog(new JLabel(), "Choose File");
            File file = jfc.getSelectedFile();
            jt.setText(file.getAbsolutePath());
        }
        else if(e.getActionCommand().equals("Choose APK File2")){
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".apk");
                }

                @Override
                public String getDescription() {
                    return ".apk";
                }
            });
            jfc.showDialog(new JLabel(), "Choose File");
            File file = jfc.getSelectedFile();
            jt2.setText(file.getAbsolutePath());
        }
        else if(e.getActionCommand().equals("Compare")){
            jl.setText(" Notes: Comparing... Please wait...");
            Compare c = new Compare();
            double sim = c.get_similarity(jt.getText(),jt2.getText());
            if(sim < -0.5 && sim > -1.5)
            {
                jl.setText(" Notes: Same APK Author.");
            }
            else if(sim > -0.5 && sim < 1.5) {
                jl.setText(" Notes: Similarity is "+String.valueOf(sim).substring(0,7));
            }
            else{
                jl.setText(" Notes: Something Wrong.");
            }
        }
        else{
            jl.setText(" Notes: Something Wrong.");
        }
    }

    public static void main(String args[]){
        new MainFrame().CreateJFrame("WuFan - App Clone Detection");
    }
}
