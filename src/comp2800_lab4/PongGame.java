package comp2800_lab4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame {
	
	
    private JFrame mainframe;
    private JPanel menu_panel;
    private JMenuBar menu_bar;
    private JMenu menu;
  
    
    /*Creating Arena Panel, Left paddle, Right Paddle
     * and the divider line
  
     * */
    private JPanel arena;
    private JPanel leftPaddle;
    private JPanel rightPaddle;
    private JLabel divider;

    /*
     *Creating Vars to store Coordinates
     *of left and right Paddle
     * 
     * */
    private int leftPaddle_Y ; // Y-coordinate of the left paddle
    private int rightPaddle_Y; // Y-coordinate of the right paddle
    private int leftPaddle_X ; // Y-coordinate of the left paddle
    private int rightPaddle_X ; // Y-coordinate of the right paddle
    
    /*Default Paddle speed
     * Will change it once we know the ball speed to make he game fair
     * */
    private final int PADDLE_SPEED = 60; // Paddle movement speed

    /*
     *  Constructor to initialize the PongGame
     */
    private Ball ball;
    private Timer timer;

    public PongGame() {
        initialize();
    }
    /*
     * Method to initialize the GUI components
     * 
     * */
    public void initialize() {
        mainframe = new JFrame();
        mainframe.setTitle("Welcome to Pong game");
        mainframe.setSize(900, 700);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    /*
     * Method to display the main frame
     * */
    public void show() {
        mainframe.setVisible(true);
    }

    /*	
     * Method to Create Arena
     * flow :	 left paddle >> Arena 
     * 			 right paddle >> Arena
     * 			 divider >> Arena
     * 			 Arena >> Mainframe
     * */
    private void create_arena() {
        arena = new JPanel();
        arena.setBackground(Color.BLACK);
        arena.setLayout(null);

        leftPaddle_Y = (mainframe.getHeight() - 100) / 2;
        rightPaddle_Y = (mainframe.getHeight() - 100) / 2;

        leftPaddle_X = mainframe.getWidth() / 20; // 5% from the left side
        rightPaddle_X = mainframe.getWidth() - (mainframe.getWidth() / 20) - 20; // 5% from the right side

        leftPaddle = new JPanel();
        leftPaddle.setBackground(Color.WHITE);
        leftPaddle.setBounds(leftPaddle_X, leftPaddle_Y, 20, 100);
        arena.add(leftPaddle);

        rightPaddle = new JPanel();
        rightPaddle.setBackground(Color.WHITE);
        rightPaddle.setBounds(rightPaddle_X, rightPaddle_Y, 20, 100);
        arena.add(rightPaddle);

        int dividerX = (mainframe.getWidth() / 2) - 1;
        divider = new JLabel();
        divider.setBackground(Color.WHITE);
        divider.setOpaque(true);
        divider.setBounds(dividerX, 0, 2, mainframe.getHeight());
        arena.add(divider);

        mainframe.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                adjustComponentsOnResize();
            }
        });
        // Adding key listener for keys and calling respective method to update paddle location
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
        ball = new Ball(mainframe.getWidth() / 2, mainframe.getHeight() / 2, 20); // Adjust the diameter as needed
        arena.add(ball);
        ball.setBounds(mainframe.getWidth() / 2 - ball.diameter / 2, mainframe.getHeight() / 2 - ball.diameter / 2, ball.diameter, ball.diameter);

        ball.repaint();
        arena.repaint();
        mainframe.add(arena, BorderLayout.CENTER);
        
        timer = new Timer(400, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ball.move(mainframe.getWidth(), mainframe.getHeight(),leftPaddle,rightPaddle); // Call the move() method of the ball
                ball.repaint();
                System.out.println("Log: Moving Ball X:"+ ball.getX()+ " Y :"+ball.getY());
            }
        });
        
       timer.start() ;   }

    /*
     * Changes the paddles location automatically when screen is resized
     * 
     * */
    private void adjustComponentsOnResize() {
        int oldHeight = mainframe.getHeight();
        int oldWidth = mainframe.getWidth();

        leftPaddle_Y = (leftPaddle_Y * mainframe.getHeight()) / oldHeight;
        rightPaddle_Y = (rightPaddle_Y * mainframe.getHeight()) / oldHeight;

        leftPaddle.setBounds(20, leftPaddle_Y, 20, 100);
        rightPaddle.setBounds(mainframe.getWidth() - 40, rightPaddle_Y, 20, 100);

        int dividerX = (mainframe.getWidth() / 2) - 1;
        divider.setBounds(dividerX, 0, 2, mainframe.getHeight());
    }

    /* 
     * Method to move left paddle Down
     * 
     * */
    private void moveLeftPaddleDown() {
        leftPaddle_Y += PADDLE_SPEED;
        if (leftPaddle_Y + leftPaddle.getHeight() > mainframe.getHeight()) {
            leftPaddle_Y = mainframe.getHeight() - leftPaddle.getHeight();
        }
        leftPaddle.setLocation(leftPaddle.getX(), leftPaddle_Y);
    }
    /* 
     * Method to move left paddle up
     * 
     * */
    
    private void moveLeftPaddleUp() {
        leftPaddle_Y -= PADDLE_SPEED;
        if (leftPaddle_Y < 0) {
            leftPaddle_Y = 0;
        }
        leftPaddle.setLocation(leftPaddle.getX(), leftPaddle_Y);
    }
    /* 
     * Method to move right paddle Down
     * 
     * */
    private void moveRightPaddleDown() {
        rightPaddle_Y += PADDLE_SPEED;
        if (rightPaddle_Y + rightPaddle.getHeight() > mainframe.getHeight()) {
            rightPaddle_Y = mainframe.getHeight() - rightPaddle.getHeight();
        }
        rightPaddle.setLocation(rightPaddle.getX(), rightPaddle_Y);
    }
    /* 
     * Method to move right paddle up
     * 
     * */
    private void moveRightPaddleUp() {
        rightPaddle_Y -= PADDLE_SPEED;
        if (rightPaddle_Y < 0) {
            rightPaddle_Y = 0;
        }
        rightPaddle.setLocation(rightPaddle.getX(), rightPaddle_Y);
    }
    /* 
     * Method to add Menu to MenuBar with options
     * 
     * flow : JOption >> menu >> MenuBar 
     * 
     * */
    private void add_menu_to_menu_bar(JMenuBar bar) {
        menu = new JMenu("Help");
      
        JMenuItem option_start = new JMenuItem("Start");
        JMenuItem option_restart = new JMenuItem("Restart");
        JMenuItem option_speed = new JMenuItem("Speed");
        JMenuItem option_quit = new JMenuItem("Quit");
        menu.add(option_start);
        menu.add(option_restart);
        menu.add(option_speed);
        menu.add(option_quit);

        bar.setBackground(Color.GREEN);
        bar.add(menu);

        option_quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                mainframe.dispose();
            }
        });
        
        option_restart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timer.stop();

			}
		});
        
        option_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timer.start();
			}
		});
        
        option_speed.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    public static void main(String[] args) {
		// Run this program on the Event Dispatch Thread (EDT)
		EventQueue.invokeLater(new Runnable() {
			//creating instance of mainframe and using show method to set frame to visible
			public void run() {	
				 	//Creating PieChart instance and then calling the show method to setvisibility true
					PongGame game =new PongGame();
					game.show();
			}
		});
	}
   
}
