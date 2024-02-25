package comp2800_lab4;

import java.awt.*;
import java.util.Random;

import javax.swing.JPanel;

public class Ball extends JPanel {
	
    private static final int BALL_SPEED = 40;
	private int x, y;
	int diameter;
    private int dx = 1, dy = 1;

    public Ball(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
        setBounds(this.x, this.y, getPreferredSize().width, getPreferredSize().height);
        // Generate random initial directions
        Random random = new Random();
        dx = random.nextBoolean() ? 1 : -1; // Randomly choose between 1 and -1
        dy = random.nextBoolean() ? 1 : -1; // Randomly choose between 1 and -1
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int frameWidth, int frameHeight, JPanel leftpaddle , JPanel rightpaddle) {
        // Previous bounds
        Rectangle oldBounds = getBounds();

        x += dx * BALL_SPEED;
        y += dy * BALL_SPEED;
        // Check for collision with the left paddle
        if (leftpaddle.getBounds().intersects(getBounds())) {
        	reflectHorizontally(); // Reverse horizontal direction
            x = leftpaddle.getX() + leftpaddle.getWidth();
        	System.out.println("888888888888888888888888888888888888888888");
            
        } else

     // Check for collision with the right paddle
        if (rightpaddle.getBounds().intersects(getBounds())) {
            reflectHorizontally(); // Change horizontal direction
            x = rightpaddle.getX() - diameter; // Set x-coordinate to the left edge of the right paddle
            // Adjust y-coordinate based on the collision point
            y = Math.max(rightpaddle.getY() - diameter, Math.min(y, rightpaddle.getY() + rightpaddle.getHeight()));
        }
        // Check for collision with the left or right boundary
        else if (x <= 0 || x + diameter >= frameWidth) {
            reflectHorizontally(); // Change horizontal direction
        }

        // Check for collision with the top or bottom boundary
        else if (y <= 0 || y + diameter >= frameHeight) {
            reflectVertically(); // Change vertical direction
        }

     

        // New bounds
        Rectangle newBounds = getBounds().union(oldBounds);

        // Repaint the entire panel (the parent container of the ball)
        getParent().repaint();

        System.out.println("####################################");
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
        
        System.out.println("____________________________________________________");
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }
}