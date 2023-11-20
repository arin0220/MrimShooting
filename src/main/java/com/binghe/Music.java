package com.binghe;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




// 음악 스레드.
class Music implements Runnable{
	boolean checkPlaying = true;
	String filename;
	long playTime = 0;
	Clip clip;
	public Music(String filename) {
		this.filename = filename;
		try {
			this.clip = AudioSystem.getClip();
			this.clip.open(AudioSystem.getAudioInputStream(new File(this.filename)));
		}catch(Exception e) {e.printStackTrace();}
	}

	@Override
	public void run() {
		try {
//			this.clip.open(AudioSystem.getAudioInputStream(new File(this.filename)));
			this.clip.start();
			this.clip.loop(this.clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {e.printStackTrace();}
	}

	// check은 loop로 실행할 것이냐 start로 실행할 것이냐를 구분하기 위함.
	public void playOnetime(int check) {
		try {
			if(check == 1) {
				this.clip.start();
				Thread.sleep(100);
			}
			else if(check == 2)
				this.clip.loop(1);
//			this.clip.open(AudioSystem.getAudioInputStream(new File(this.filename)));

		} catch (Exception e) {e.printStackTrace();}
	}

	public void pause() {
		this.playTime = this.clip.getMicrosecondPosition();
		this.checkPlaying = false;
		this.clip.stop();
	}

	public void resume() {
		this.checkPlaying = true;
		this.clip.setMicrosecondPosition(this.playTime);
		this.clip.start();
		this.clip.loop(this.clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		this.clip.stop();
	}

}


//데몬스레드를 사용한 스토리 설명.

class Story extends JFrame {
	private JLabel label;
	private String story;
	private int currentIndex;
	private KeyMusic keyMusicThread;
	private Music musicThread;

	public Story() {
		story = "<html>2019년 연이은 여러 나라들의 패권싸움과 갈등으로 인해 세계 대전이 발발한다.<br><br>" +
//				"지리적 전략적인 위치에 있는 대한민국은 여러 나라들의 표적이 되고 만다.<br><br>" +
//				"많은 나라들이 한국에 전투기를 보내 한국을 점령하려고 하고 있다.<br><br>" +
//				"이에 대한민국 공군은 공격해 오는 많은 나라들의 전투기로부터 영토를 지키려한다.<br><br>" +
				"대한민국의 영토와 자주권은 여러분에게 달렸습니다. 꼭 지켜주기 바랍니다!</html>";

		currentIndex = 0;

		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER); // 텍스트를 중앙 정렬
		label.setVerticalAlignment(JLabel.CENTER); // 텍스트를 중앙 정렬
		add(label, BorderLayout.CENTER); // label을 CENTER에 배치

		JButton skipButton = new JButton("Skip");

		skipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// "Skip" 버튼 클릭 시 스토리 스레드 중지
				if (keyMusicThread != null) {
					keyMusicThread.interrupt(); // KeyMusic 스레드 중지
				}
				if (musicThread != null) {
					musicThread.stop(); // Music 스레드 중지
				}
				dispose(); // 프레임 종료
				// 실행할 GUI_MENU 클래스의 메인 메서드 호출
				GUI_MENU.main(null);
			}
		});
		add(skipButton, BorderLayout.SOUTH);

		musicThread = new Music("src/main/SOURCE/keyboard.wav");

		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentIndex < story.length()) {
					label.setText(story.substring(0, currentIndex + 1));
					currentIndex++;
				} else {
					((Timer) e.getSource()).stop();
					keyMusicThread.interrupt(); // KeyMusic 스레드 중지
					musicThread.stop(); // Music 스레드 중지
					// 3초 후에 프레임 종료
					Timer exitTimer = new Timer(3000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							((Timer) e.getSource()).stop();
							dispose(); // 프레임 종료
							//menu 실행
							// 실행할 GUI_MENU 클래스의 메인 메서드 호출
							GUI_MENU.main(null);
						}
					});
					exitTimer.setRepeats(false);
					exitTimer.start();
				}
			}
		});

		timer.start();

		init();
	}

	class KeyMusic extends Thread {
		@Override
		public void run() {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("src/main/SOURCE/keyboard.wav")));
				while (true) {
					if (isInterrupted()) {
						clip.stop();
						return;
					}
					clip.loop(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init() {
		keyMusicThread = new KeyMusic();
		keyMusicThread.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null); // 화면 중앙에 프레임 표시
		setResizable(false); // 프레임 크기 조절 비활성화
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Story();
			}
		});

	}
}