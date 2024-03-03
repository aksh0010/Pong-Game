package comp2800_lab4;

import java.awt.*;
import java.util.Random;

import javax.swing.JPanel;

public class Ball extends JPanel {
	
	private static final int BALL_SPEED = 20;
    private static final int MAX_RANDOM_DIRECTION = 3;
    private static final int MIN_RANDOM_DIRECTION = -1;
	private int x, y;
	private int diameter;
    private int dx = 1, dy = 1;
    private int speedX ,speedY;
    public Ball(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
        setBounds(this.x, this.y, getPreferredSize().width, getPreferredSize().height);

        // Generate random initial directions
        Random random = new Random();
        dx = random.nextInt(MAX_RANDOM_DIRECTION - MIN_RANDOM_DIRECTION) + MIN_RANDOM_DIRECTION;
        dy = random.nextInt(MAX_RANDOM_DIRECTION - MIN_RANDOM_DIRECTION) + MIN_RANDOM_DIRECTION;

        while (dx == 0 || dy == 0) {
            dx = random.nextInt(MAX_RANDOM_DIRECTION - MIN_RANDOM_DIRECTION) + MIN_RANDOM_DIRECTION;
            dy = random.nextInt(MAX_RANDOM_DIRECTION - MIN_RANDOM_DIRECTION) + MIN_RANDOM_DIRECTION;
        }
    }
    public void reset(int initialX, int initialY, int initialSpeedX, int initialSpeedY) {
        this.x = initialX;
        this.y = initialY;
        x += dx * BALL_SPEED;
        y += dy * BALL_SPEED;
        this.speedX = initialSpeedX;
        this.speedY = initialSpeedY;
    }
    
    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean move(int frameWidth, int frameHeight, JPanel leftpaddle , JPanel rightpaddle) {
        // Previous bounds
        Rectangle oldBounds = getBounds();

        x += dx * BALL_SPEED;
        y += dy * BALL_SPEED;
        // Check for collision with the left paddle
        if (leftpaddle.getBounds().intersects(getBounds())) {
        	reflectHorizontally(); // Reverse horizontal direction
            x = leftpaddle.getX() + leftpaddle.getWidth();
        	System.out.println("LeftPaddle");
        	SoundPlayer.playSound("resources/ping.wav");
            
        } else if (rightpaddle.getBounds().intersects(getBounds())) {
            // Check for collision with the right paddle
            reflectHorizontally(); // Change horizontal direction
            x = rightpaddle.getX() - diameter; // Set x-coordinate to the left edge of the right paddle
            // Adjust y-coordinate based on the collision point
            y = Math.max(rightpaddle.getY() - diameter, Math.min(y, rightpaddle.getY() + rightpaddle.getHeight()));
            System.out.println("RightPaddle");
            SoundPlayer.playSound("resources/ping.wav");
        }
        // Check for collision with the left or right boundary
        else if (x <= 0 || x + diameter >= frameWidth) {
        	 System.out.println("Touched Sides");
        	 SoundPlayer.playSound("resources/round_over.wav");
        	 try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 return false;
//            reflectHorizontally(); // Change horizontal direction          
        }

        // Check for collision with the top or bottom boundary
        else if (y <= 0 || y + diameter >= frameHeight) {
        	
        	System.out.println("Touched Top or bottom");
        	SoundPlayer.playSound("resources/wall_deflect.wav");
            reflectVertically(); // Change vertical direction
           
        }

     

        // New bounds
        Rectangle newBounds = getBounds().union(oldBounds);

        // Repaint the entire panel (the parent container of the ball)
        getParent().repaint();
        return true;
    }

    public void reflectHorizontally() {
        dx = -dx;
    }

    public void reflectVertically() {
        dy = -dy;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, diameter, diameter);
        
//        System.out.println("____________________________________________________");
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }
	public int getBallDiameter() {
		// TODO Auto-generated method stub
		return this.diameter;
	}
}