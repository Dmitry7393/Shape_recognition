package user_interface;

import javax.swing.JFrame;

public class GameWindow {
        JFrame frame;
        public GameWindow(){
            frame = new JFrame("Game window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700,400);  //1024,600
            frame.setLocation(100, 50);
            frame.setResizable(false);
            frame.add(new GameScreen());
            frame.setVisible(true);
           }
        public static void main(String [] args){
             new GameWindow();
           }
}