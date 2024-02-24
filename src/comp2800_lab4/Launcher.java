package comp2800_lab4;
import javax.swing.JFrame;
import java.awt.EventQueue;

public class Launcher {

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