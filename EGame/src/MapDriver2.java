import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MapDriver2 extends JFrame {
	private int userLifes = Constant.userLifes_default;// Your life
	private int npcLifes = Constant.npcLifes_default;// NPC life

	private int n = 0;// number of my skills
	private int m = 0;// number of enemy skills
	private int f = 0;// number of "f" tab
	private static int width = Toolkit.getDefaultToolkit().getScreenSize().width; 
	private static int height = Toolkit.getDefaultToolkit().getScreenSize().height; 

	private static int windowsWidth = 1310;
	private static int windowsHeight = 660;

	private MapDriver Map = null;

	private JLabel lblNpc = null; // NPC Picture
	private JTextArea jtaTip = null;
	private JButton btnFire = null; // Fire ball
	private JButton btnSnow = null; // Ice arrow
	private JButton btnLightning = null; // Light
	private JButton btnCrystal = null; // water cannons
	private JButton btnStart = null; // Start

	private Thread thUser = null; // Player use the skill
	private Thread thNpc = null; // NPC use the skill
	private boolean bFlag = false; 
	private final Object object = new Object();// 

	public MapDriver2() {

		getContentPane().setLayout(null);

		Map = new MapDriver();
		Map.setBounds(149, 11, 1250, 600);
		getContentPane().add(Map);
		Map.setLayout(null);

		JLabel lblBgImg = new JLabel("New label");
		lblBgImg.setIcon(new ImageIcon("images/bgimg.png"));
		lblBgImg.setBounds(260, 0, 600, 600);
		Map.add(lblBgImg);

		JLabel lblPs1 = new JLabel("New label");
		lblPs1.setIcon(new ImageIcon("images/person.jpg"));
		lblPs1.setBounds(0, 260, 260, 340);
		Map.add(lblPs1);

		lblNpc = new JLabel("New label");
		lblNpc.setIcon(new ImageIcon("images/npc.jpg"));
		lblNpc.setBounds(860, 0, 260, 340);
		lblNpc.setVisible(false);
		Map.add(lblNpc);

		JPanel Contorl = new JPanel();
		Contorl.setBounds(10, 11, 200, 600);
		getContentPane().add(Contorl);
		Contorl.setLayout(null);

		btnFire = new JButton("Fire");
		btnFire.setBounds(10, 11, 89, 23);
		Contorl.add(btnFire);

		btnSnow = new JButton("Water Cannon");
		btnSnow.setBounds(10, 56, 89, 23);
		Contorl.add(btnSnow);

		btnLightning = new JButton("Lightning");
		btnLightning.setBounds(10, 101, 89, 23);
		Contorl.add(btnLightning);

		btnCrystal = new JButton("Ice Arrow");
		btnCrystal.setBounds(10, 146, 89, 23);
		Contorl.add(btnCrystal);

		jtaTip = new JTextArea();
		jtaTip.setBounds(0, 191, 180, 200);
		jtaTip.setEditable(false);
		Contorl.add(jtaTip);

		btnStart = new JButton("Start");
		btnStart.setBounds(10, 436, 89, 23);
		Contorl.add(btnStart);

		btnFire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bIsGameOver) {
					return;
				}
				userFireSync("Fire");
			}
		});

		btnSnow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bIsGameOver) {
					return;
				}
				userFireSync("Water");
			}

		});

		btnLightning.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (bIsGameOver) {
					return;
				}
				userFireSync("Lightning");
			}

		});

		btnCrystal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (bIsGameOver) {
					return;
				}
				userFireSync("Ice");
			}
		});

		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setStart(true);
				jtaTip.setText("TAP f serval times to\n"+" find your enemy\n" + jtaTip.getText());
			}
		});

		setStart(false);
	}


	private void userFireSync(String type) {
		thNpc = null;
		thUser = new Thread(new Runnable() {
			public void run() {
				try {
					synchronized (object) {
						if (bFlag) {
							object.wait();
						}
						if (!bIsGameOver) {
							Thread.sleep(Constant.user_shooting_timeout_default);
							if (type.equals("Fire")) {
								jtaTip.setText("You use\n" + "Fireball!\n"
										+ jtaTip.getText());
								Map.fire(n, "images/Fireball.png", false);
								killNpcLifes(Constant.fireball_killLifes_default);
							} else if (type.equals("Water")) {
								jtaTip.setText("You use\n" + "Water Cannon!\n"
										+ jtaTip.getText());
								Map.fire(n, "images/Water Cannon.png", false);
								killNpcLifes(Constant.snowball_killLifes_default);
							} else if (type.equals("Lightning")) {
								jtaTip.setText("You use\n" + "Lightning!\n"
										+ jtaTip.getText());
								Map.fire(n, "images/Lightning.png", false);
								killNpcLifes(Constant.lightning_killLifes_default);
							} else if (type.equals("Ice")) {
								jtaTip.setText("You use\n" + "Ice Arrow!\n"
										+ jtaTip.getText());
								Map.fire(n, "images/Ice Arrow.png", false);
								killNpcLifes(Constant.crystal_killLifes_default);
							}
							n++;
						}
						bFlag = true;
						object.notify();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thUser.start();
	}


	private void npcFireSync() {
		thNpc = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						synchronized (object) {
							if (!bFlag) {
								object.wait();
							}
							if (!bIsGameOver) {
								Thread.sleep(Constant.npc_shooting_timeout_default);
								Random random = new Random();
								int number = random.nextInt(2);
								if (number == 1) {
									Map.fire(m, "images/att.jpg", true);
									killUserLifes(Constant.snowball_killLifes_default);
								} else {
									Map.fire(m, "images/dtt.jpg", true);
									killUserLifes(Constant.lightning_killLifes_default);
								}
								m++;
							}
							bFlag = false;
							object.notify();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thNpc.start();
	}

	private boolean bIsGameOver = false; // boolean the game is over

	private synchronized void killNpcLifes(int lifes) {
		npcLifes = npcLifes - lifes;
		jtaTip.setText("Current npc lifes is " + npcLifes + "\n"
				+ jtaTip.getText());
		if (npcLifes <= 0) {
			bIsGameOver = true;
			try {
				Thread.sleep(Constant.user_shooting_timeout_default);
				setStart(false);
				if (thNpc != null && thNpc.isAlive()) {
					thNpc.interrupt();
				}
				jtaTip.setText("You Win!!!\n" + jtaTip.getText());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void killUserLifes(int lifes) {
		userLifes = userLifes - lifes;
		jtaTip.setText("Current user lifes is " + userLifes + "\n"
				+ jtaTip.getText());
		if (userLifes <= 0) {
			bIsGameOver = true;
			setStart(false);
			if (thNpc != null && thNpc.isAlive()) {
				thNpc.interrupt();
			}
			jtaTip.setText("You Losed!!!\n" + jtaTip.getText());
		}
	}

	private void setStart(Boolean bFlag) {
		if (bFlag) {
			bIsGameOver = false;
			jtaTip.setText("");
			btnFire.setEnabled(true);
			btnSnow.setEnabled(true);
			btnLightning.setEnabled(true);
			btnCrystal.setEnabled(true);
			btnStart.setEnabled(false);
			lblNpc.setVisible((f == Constant.npc_show_keypresstimes_default ? true
					: false));
		} else { // game over
			bIsGameOver = true;
			f = 0;
			m = 0;
			n = 0;
			userLifes = Constant.userLifes_default;
			npcLifes = Constant.npcLifes_default;
			jtaTip.setText("");
			btnFire.setEnabled(false);
			btnSnow.setEnabled(false);
			btnLightning.setEnabled(false);
			btnCrystal.setEnabled(false);
			btnStart.setEnabled(true);
			lblNpc.setVisible(false);
		}
	}

	class MyListener implements KeyListener {
		@Override
		// press
		public void keyPressed(KeyEvent e) {

		}

		@Override
		// release
		public void keyReleased(KeyEvent e) {

		}

		@Override
		// the content
		public void keyTyped(KeyEvent e) {

		}
	}

	public KeyEventPostProcessor getMyKeyEventHandler() {
		return new KeyEventPostProcessor() {
			public boolean postProcessKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F) {
					f = f + 1;
					if (f == Constant.npc_show_keypresstimes_default) {
						lblNpc.setVisible(true);
						setStart(true);
						jtaTip.setText("Your find a Vamper\n"+"Now Fight!\n" + jtaTip.getText());
						return true;
					}
				}
				return false;
			}
		};
	}

	public static void main(String[] args) {
		MapDriver2 m = new MapDriver2();
		m.setTitle("Egame");
		m.setResizable(false);
		m.setSize(940, 660);
		m.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2,
				windowsWidth, windowsHeight);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setVisible(true);

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventPostProcessor((KeyEventPostProcessor) m
				.getMyKeyEventHandler());

		getConfig("config.txt");

		m.npcFireSync();
	}

	private static void getConfig(String fileName) {
		String[] values = FileReader.getTxtFile(fileName, "").split("\\|");
		Constant.fireball_killLifes_default = Integer.parseInt(values[0]);
		Constant.snowball_killLifes_default = Integer.parseInt(values[1]);
		Constant.lightning_killLifes_default = Integer.parseInt(values[2]);
		Constant.crystal_killLifes_default = Integer.parseInt(values[3]);
	}
}
