package com.binghe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class GUI_ETC extends JFrame implements ActionListener {
    private User user;

    public GUI_ETC(User user) {
        this.user = user;

        setTitle("Game Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정

        JButton newGameButton = new JButton("새 게임");
        JButton loadGameButton = new JButton("불러오기");
        JButton exitButton = new JButton("게임 종료");

        newGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);
        exitButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(newGameButton, gbc);

        gbc.gridy = 1;
        add(loadGameButton, gbc);

        gbc.gridy = 2;
        add(exitButton, gbc);

        centerFrameOnScreen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "새 게임":
                String userName = JOptionPane.showInputDialog(this, "만드실 계정 이름을 입력해주세요.");
                if (userName != null && !userName.isEmpty()) {
                    User newUser = new User(userName);
                    JOptionPane.showMessageDialog(this, "새 게임이 시작되었습니다.");

                    // Story 객체 생성
                    Story story = new Story();

                    // 사용자 정보 갱신
                    this.user = newUser;

                    // GUI_MENU 클래스로 전환 -> 안된다 story 끝나고 전환하는 법 찾아야함ㄴ
//                    GUI_MENU gui_menu = new GUI_MENU(newUser);
//                    gui_menu.setVisible(true);
                    this.dispose(); // 현재 프레임 닫기
                } else {
                    JOptionPane.showMessageDialog(this, "계정 이름을 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "불러오기":
                JOptionPane.showMessageDialog(this, "불러오기");
                // 여기서 저장된 게임을 불러오는 코드 추가
                User newUser;
                File file = new File("save.dat");
                try {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    newUser = (User) oin.readObject();
//                ETC.printLoading();
                    // menu 보이게

                    // GUI_MENU 클래스로 전환
                    GUI_MENU gui_menu = new GUI_MENU(newUser);
                    gui_menu.setVisible(true);
                    this.dispose(); // 현재 프레임 닫기

                } catch (Exception ee) {
//                    System.out.println("저장된 계정이 없습니다.");
                    JOptionPane.showMessageDialog(this, "저장된 계정이 없습니다.");
                    //다시 지금 창으로 돌아오기
                }
                break;
            case "게임 종료":
                System.out.println(" [ 게임을 종료합니다. ]");
                System.exit(0);
                break;
        }
    }

    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    public static void main(String[] args) {
        User user = new User(""); // 빈 사용자 생성
        GUI_ETC gui_etc = new GUI_ETC(user);
        gui_etc.setVisible(true);
    }

}
