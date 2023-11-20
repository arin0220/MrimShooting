package com.binghe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_BATTLE extends JFrame {
    private User user;

    public GUI_BATTLE(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Battle GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        JTextArea textArea = new JTextArea();
//        textArea.setEditable(false);

//        JScrollPane scrollPane = new JScrollPane(textArea);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton stage1Button = createStyledButton("STAGE 1");
        JButton stage2Button = createStyledButton("STAGE 2");
        JButton stage3Button = createStyledButton("STAGE 3");
        JButton exitButton = createStyledButton("나가기");

        stage1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStageSelection(1);
            }
        });

        stage2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStageSelection(2);
            }
        });

        stage3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStageSelection(3);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMenu();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        buttonPanel.add(stage1Button);
        buttonPanel.add(stage2Button);
        buttonPanel.add(stage3Button);
        buttonPanel.add(exitButton);

        setLayout(new BorderLayout());
//        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void backToMenu() {
        SwingUtilities.invokeLater(() -> new GUI_MENU(user).setVisible(true));
        dispose();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(400, 100));
        return button;
    }

    private void handleStageSelection(int stageNumber) {
        Player player = new Player(user);
        GameObject gameObject = new GameObject(player, stageNumber, user);
        Thread gameThread = new Thread(gameObject);

        // 게임 스레드가 종료될 때 GUI_MENU 창을 다시 표시하는 스레드 생성
//        Thread menuThread = new Thread(() -> {
//            try {
//                gameThread.join(); // 게임 스레드가 종료될 때까지 대기
//                SwingUtilities.invokeLater(() -> new GUI_MENU(user).setVisible(true));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        // 백그라운드 스레드 실행
//        menuThread.start();

        // 게임 스레드 시작
        gameThread.start();

        // 현재 창을 닫음
        dispose();
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_BATTLE(new User("user1")).setVisible(true);
            }
        });
    }
}


