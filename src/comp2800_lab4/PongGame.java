package comp2800_lab4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame {
    private JFrame mainframe;
    private JPanel menu_panel;
    private JMenuBar menu_bar;
    private JMenu menu;
    private JMenuItem option_start;
    private JMenuItem option_restart;
    private JMenuItem option_speed;
    private JMenuItem option_quit;

    private JPanel arena;
    private JPanel leftPaddle;
    private JPanel rightPaddle;
    private JLabel divider;

    private int leftPaddle_Y = 0; // Y-coordinate of the left paddle
    private int rightPaddle_Y = 0; // Y-coordinate of the right paddle
    private final int PADDLE_SPEED = 10; // Paddle movement speed

    // Constructor to initialize the PongGame
    public PongGame() {
        initialize();
    }

    // Method to initialize the GUI components
    public void initialize() {
        mainframe = new JFrame();
        mainframe.setTitle("Welcome to Pong game");
        mainframe.setSize(900, 800);
        mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainframe.setLayout(new BorderLayout());
        mainframe.setLocationRelativeTo(null);
//        mainframe.setResizable(false);

        menu_panel = new JPanel();
        menu_bar = new JMenuBar();
        
        add_menu_to_menu_bar(menu_bar);
        menu_panel.add(menu_bar);

        create_arena();

        mainframe.add(menu_panel, BorderLayout.NORTH);
    }

    // Method to display the main frame
    public void show() {
        mainframe.setVisible(true);
    }

    private void create_arena() {
        this.arena = new JPanel();
        this.arena.setBackground(Color.BLACK);
        this.arena.setLayout(null);

        leftPaddle_Y = (mainframe.getHeight() - 100) / 2;
        rightPaddle_Y = (mainframe.getHeight() - 100) / 2;

        leftPaddle = new JPanel();
        leftPaddle.setBackground(Color.WHITE);
        leftPaddle.setBounds(20, leftPaddle_Y, 20, 100);
        this.arena.add(leftPaddle);

        rightPaddle = new JPanel();
        rightPaddle.setBackground(Color.WHITE);
        rightPaddle.setBounds(860, rightPaddle_Y, 20, 100);
        this.arena.add(rightPaddle);

        int dividerX = (mainframe.getWidth() / 2) - 1;
        divider = new JLabel();
        divider.setBackground(Color.WHITE);
        divider.setOpaque(true);
        divider.setBounds(dividerX, 0, 2, mainframe.getHeight());
        this.arena.add(divider);

        mainframe.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                adjustComponentsOnResize();
            }
        });

        mainframe.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    moveRightPaddleUp();
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    moveRightPaddleDown();
                } else if (keyCode == KeyEvent.VK_W) {
                    moveLeftPaddleUp();
                } else if (keyCode == KeyEvent.VK_S) {
                    moveLeftPaddleDown();
                }
            }
        });

        mainframe.add(arena, BorderLayout.CENTER);
    }

    private void adjustComponentsOnResize() {
        leftPaddle_Y = (mainframe.getHeight() - 100) / 2;
        rightPaddle_Y = (mainframe.getHeight() - 100) / 2;
        leftPaddle.setBounds(20, leftPaddle_Y, 20, 100);
        rightPaddle.setBounds(mainframe.getWidth() - 40, rightPaddle_Y, 20, 100);
        int dividerX = (mainframe.getWidth() / 2) - 1;
        divider.setBounds(dividerX, 0, 2, mainframe.getHeight());
    }

    protected void moveLeftPaddleDown() {
        leftPaddle_Y += PADDLE_SPEED;
        if (leftPaddle_Y + leftPaddle.getHeight() > mainframe.getHeight()) {
            leftPaddle_Y = mainframe.getHeight() - leftPaddle.getHeight();
        }
        leftPaddle.setLocation(leftPaddle.getX(), leftPaddle_Y);
    }

    protected void moveLeftPaddleUp() {
        leftPaddle_Y -= PADDLE_SPEED;
        if (leftPaddle_Y < 0) {
            leftPaddle_Y = 0;
        }
        leftPaddle.setLocation(leftPaddle.getX(), leftPaddle_Y);
    }

    protected void moveRightPaddleDown() {
        rightPaddle_Y += PADDLE_SPEED;
        if (rightPaddle_Y + rightPaddle.getHeight() > mainframe.getHeight()) {
            rightPaddle_Y = mainframe.getHeight() - rightPaddle.getHeight();
        }
        rightPaddle.setLocation(rightPaddle.getX(), rightPaddle_Y);
    }

    protected void moveRightPaddleUp() {
        rightPaddle_Y -= PADDLE_SPEED;
        if (rightPaddle_Y < 0) {
            rightPaddle_Y = 0;
        }
        rightPaddle.setLocation(rightPaddle.getX(), rightPaddle_Y);
    }

    private void add_menu_to_menu_bar(JMenuBar bar) {
        this.menu = new JMenu();

        this.option_start = new JMenuItem("Start");
        this.option_restart = new JMenuItem("Restart");
        this.option_speed = new JMenuItem("Speed");
        this.option_quit = new JMenuItem("Quit");
        this.menu.add(option_start);
        this.menu.add(option_restart);
        this.menu.add(option_speed);
        this.menu.add(option_quit);

        bar.setBackground(Color.white);
        bar.add(menu);

        this.option_quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainframe.dispose();
            }
        });
    }

   
}
