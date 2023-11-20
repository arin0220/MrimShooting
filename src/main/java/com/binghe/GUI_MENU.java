package com.binghe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_MENU extends JFrame {
    private User user;

    public GUI_MENU(User user) {
        this.user = user;

        setTitle("Game Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton descriptionButton = createMenuButton("게임 설명");
        JButton statusButton = createMenuButton("상태창");
        JButton inventoryButton = createMenuButton("격납고");
        JButton shopButton = createMenuButton("상점");
        JButton battleButton = createMenuButton("전투");
        JButton saveButton = createMenuButton("게임 저장");
        JButton musicButton = createMenuButton("음악 On/Off");
        JButton exitButton = createMenuButton("게임 종료");

        add(Box.createRigidArea(new Dimension(0, 20))); // 간격 조절
        add(descriptionButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(statusButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(inventoryButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(shopButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(battleButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(saveButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(musicButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // 간격 조절
        add(exitButton);
        add(Box.createVerticalGlue()); // Align buttons to the center
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40)); // 버튼 크기 조절
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });
        return button;
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "게임 설명":
                showStory();
                dispose();
                break;
            case "상태창":
//                Status.show();
                break;
            case "격납고":
//                Inventory.show();
                break;
            case "상점":
//                Shop.show();
                break;
            case "전투":
                // 배경음악.pause();
                dispose(); // 현재 메뉴 창을 닫습니다.
                new GUI_BATTLE(user).setVisible(true);

                break;
            case "게임 저장":
                ETC.save(user);
                break;
            case "음악 On/Off":
                // 음악 On/Off 동작 추가
                break;
            case "게임 종료":
                // 종료 코드
                System.out.println(" [ 게임을 종료합니다. ]");
                System.exit(0);
                break;
        }
    }
    private void showStory() {
        // Story 클래스를 인스턴스화하여 보여주는 코드
        Story storyFrame = new Story();
        storyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user = new User(""); // 빈 사용자 생성
            GUI_MENU gui_menu = new GUI_MENU(user);
            gui_menu.setVisible(true);
        });
    }
}
