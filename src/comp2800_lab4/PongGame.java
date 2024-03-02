package comp2800_lab4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame {
    private JFrame mainframe;
    private JMenuBar menu_bar;
    private JMenu menu;
    /*Creating Arena Panel, Left paddle, Right Paddle , ScorePanel
     * */
    private JPanel arena,scorePanel,leftPaddle ,rightPaddle;
    /*Creating JLabel for divider line and score
     * */
    private JLabel divider,scoreLabel;

    /*
     *Creating Vars to store Coordinates
     *of left and right Paddle
     * 
     * */
    private int leftPaddle_Y ,leftPaddle_X,rightPaddle_Y,rightPaddle_X ; 
   
    /*Default Paddle speed
     * Will change it once we know the ball speed to make he game fair
     * */
    private final int PADDLE_SPEED = 60,DELAY =350; // Paddle movement speed
   
    /*
     * Creating Ball object and required parameters such as speed
     * */
    private Ball ball;
    private Timer timer;
    private int initialX ,initialY;

    /*
     * Creating vars to hold scores
     * */
    private int player2Score =0,player1Score=0;
    
    private int initialSpeedX;
    private int initialSpeedY;
    /*
     *  Constructor to initialize the PongGame
     */
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
        mainframe.setSize(850, 750);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLayout(new BorderLayout());
        mainframe.setLocationRelativeTo(null);
//        mainframe.setResizable(false);

//        menu_panel = new JPanel();
        menu_bar = new JMenuBar();
        
        scoreLabel = new JLabel("HEllo");
        scorePanel = new JPanel(new BorderLayout());
        scorePanel.add(scoreLabel);
        add_menu_to_menu_bar(menu_bar);
//        menu_panel.add(menu_bar);
        mainframe.setJMenuBar(menu_bar);
//        mainframe.add(menu_panel, BorderLayout.NORTH);
        createScoreCard();
        create_arena();
     	game_loop();
   
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
        this.arena = new JPanel();
        this.arena.setBackground(Color.BLACK);
        this.arena.setLayout(null);

        this.leftPaddle_Y = (mainframe.getHeight() - 100) / 2;
        this.rightPaddle_Y = (mainframe.getHeight() - 100) / 2;

        this.leftPaddle_X = mainframe.getWidth() / 20; // 5% from the left side
        this.rightPaddle_X = mainframe.getWidth() - (mainframe.getWidth() / 20) - 20; // 5% from the right side

        this.leftPaddle = new JPanel();
        this.leftPaddle.setBackground(Color.WHITE);
        this.leftPaddle.setBounds(leftPaddle_X, leftPaddle_Y, 20, 100);
        this.arena.add(leftPaddle);

        this.rightPaddle = new JPanel();
        this.rightPaddle.setBackground(Color.WHITE);
        this.rightPaddle.setBounds(rightPaddle_X, rightPaddle_Y, 20, 100);
        this.arena.add(rightPaddle);

        int dividerX = (mainframe.getWidth() / 2) - 1;
        this.divider = new JLabel();
        this.divider.setBackground(Color.WHITE);
        this.divider.setOpaque(true);
        this.divider.setBounds(dividerX, 0, 2, mainframe.getHeight());
        this.arena.add(this.divider);

        this.mainframe.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                adjustComponentsOnResize();
            }
        });
        // Adding key listener for keys and calling respective method to update paddle location
        this.mainframe.addKeyListener(new KeyAdapter() {
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
        
       this.initialX=mainframe.getWidth() / 2;
        this.initialY =mainframe.getHeight() / 2;
        this.initialSpeedX = 40;
        this.initialSpeedY= 40;
        
        this.ball = new Ball(this.initialX, this.initialY, 20); // Adjust the diameter as needed
        this.arena.add(ball);
        
        this.ball.setBounds(this.initialX- ball.getBallDiameter() / 2, this.initialY - ball.getBallDiameter() / 2, ball.getBallDiameter(), ball.getBallDiameter());
        this.ball.repaint();
        this.arena.repaint();
        this.mainframe.add(arena, BorderLayout.CENTER);
    
         }
    /*
     * Creating game logic inside below method
     * */
    private void game_loop() {
    	   this.timer = new Timer(DELAY, new ActionListener() {
               public void actionPerformed(ActionEvent e) {
               	boolean GAME_ON = ball.move(mainframe.getWidth(), mainframe.getHeight(),leftPaddle,rightPaddle); // Call the move() method of the ball
                  
               	
	               	 if (player1Score==10 || player2Score ==10) {
	              	   timer.stop();
	              	
	                 }
               		if(GAME_ON) {
                       	ball.repaint();
                           System.out.println("Log: Moving Ball X:"+ ball.getX()+ " Y :"+ball.getY());
                       
                       }
                       else {
                       	timer.stop();
                           // Check if the ball hits the left side wall
                           if (ball.getX() <= 0) {
                               // Player 2 scores a point
                           	System.out.println("OLD Score: Player 1 = "+  player1Score + " Player 2 = "+player2Score);

                           	player2Score++;
                               // Reset the ball
                           	System.out.println("Score: Player 1 = "+  player1Score + " Player 2 = "+player2Score);
                            updateScoreCard();
                           	resetRound();
                           }
                           		// Check if the ball hits the right side wall
                           else if (ball.getX() >= mainframe.getWidth() - ball.getBallDiameter()) {
                               // Player 1 scores a point
                           	System.out.println("OLD Score: Player 1 = "+  player1Score + " Player 2 = "+player2Score);

                           	player1Score++;                           	
                               // Reset the ball
                            System.out.println("NEW Score: Player 1 = "+  player1Score + " Player 2 = "+player2Score); 
                            updateScoreCard();
                            resetRound();
                           }                    
                       }
               	}                         
           });
    	this.timer.start();
    }
    /*
     * Creating the ScoreCard
     * */    
    private void createScoreCard() {
        JPanel scorePanel = new JPanel(new BorderLayout());
        
        scoreLabel = new JLabel("Player 1: 0  Player 2: 0", SwingConstants.CENTER);
       
        scoreLabel.setForeground(new Color(220, 20, 60));
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        
        mainframe.add(scorePanel, BorderLayout.NORTH);
    }
/*
 * Updating the ScoreCard
 * */
    private void updateScoreCard() {
        scoreLabel.setText("Player 1: " + player1Score + "  Player 2: " + player2Score);
    }
    
    /**Resting the round once 
     * any player scores a point
     * 
     * */
    private void resetRound() {
        // Reset the ball's position and speed
        ball.reset(mainframe.getWidth() / 2, mainframe.getHeight() / 2, 40, 40);

        // Reset paddle positions
        leftPaddle_Y = (mainframe.getHeight() - 100) / 2;
        rightPaddle_Y = (mainframe.getHeight() - 100) / 2;
        leftPaddle.setLocation(leftPaddle.getX(), leftPaddle_Y);
        rightPaddle.setLocation(rightPaddle.getX(), rightPaddle_Y);

        // Continue the game
        timer.start();
    }
    
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

        Font menuFont = new Font("Arial", Font.BOLD, 14);
        menu.setFont(menuFont);
        option_start.setFont(menuFont);
        option_restart.setFont(menuFont);
        option_speed.setFont(menuFont);
        option_quit.setFont(menuFont);

        // Set custom colors for menu items
        Color menuForeground = new Color(255, 255, 255); // White color
        Color menuBackground = new Color(152, 0, 152); // Custom blue color
        menu.setForeground(menuBackground);
//        menu.setBackground(menuBackground);
        option_start.setForeground(menuForeground);
        option_start.setBackground(menuBackground);
        option_restart.setForeground(menuForeground);
        option_restart.setBackground(menuBackground);
        option_speed.setForeground(menuForeground);
        option_speed.setBackground(menuBackground);
        option_quit.setForeground(menuForeground);
        option_quit.setBackground(menuBackground);
//        bar.setBackground(menuBackground);
//        bar.setForeground(menuBackground);
        bar.add(menu);
        option_quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	System.exit(0);
            }
        });
        
        option_restart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				   // Reset scores to 0-0
	            player1Score = 0;
	            player2Score = 0;
	            
	            // Update the score card to display the new scores
	            updateScoreCard();
	            
	            // Call the resetRound() method to restart the game
	            resetRound();
	            
	            // Restart the timer if it's stopped
	            if (!timer.isRunning()) {
	                timer.start();
	            }

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
