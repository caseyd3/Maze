import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


public class Mazedisplay{
    public static void main(String [] args){
        Maze labyrinth = new Maze(40, .4);
        JFrame mazeframe = new JFrame("The Amazing Maze");
        JPanel mazepanel = new JPanel();
        GridLayout gnm = new GridLayout(labyrinth.getn(), labyrinth.getm(), 0, 0);
        int w = 30;
        int h = 30;

        mazepanel.setLayout(gnm);
        Maze.Cell[][] maisy = labyrinth.generateGraph();
        ArrayList<Maze.Cell> solution = labyrinth.solveMaze(maisy);

        BufferedImage north = null;
        BufferedImage west = null;
        BufferedImage south = null;
        BufferedImage east = null;
        BufferedImage onpath = null;
        BufferedImage tonorth = null;
        BufferedImage towest = null;
        BufferedImage tosouth = null;
        BufferedImage toeast = null;

        try{
            north = ImageIO.read(new File("North.png"));
            west = ImageIO.read(new File("West.png"));
            south = ImageIO.read(new File("South.png"));
            east = ImageIO.read(new File("East.png"));
            tonorth = ImageIO.read(new File("ToNorth.png"));
            towest = ImageIO.read(new File("ToWest.png"));
            tosouth = ImageIO.read(new File("ToSouth.png"));
            toeast = ImageIO.read(new File("ToEast.png"));
            onpath = ImageIO.read(new File("OnPath.png"));
        }
        catch (IOException e) {
            System.out.println("not loading wall images correctly");
        }

        for (int i = 0; i < labyrinth.getn(); i ++){
            for (int j = 0; j < labyrinth.getm(); j++) {
                Maze.Cell celery = maisy[i][j];

                BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics g = combined.getGraphics();
                if (celery.getNorth() == true){
                    g.drawImage(north, 0, 0, null);
                }
                if (celery.getWest() == true){
                    g.drawImage(west, 0, 0, null);
                }
                if (celery.getSouth() == true){
                    g.drawImage(south, 0, 0, null);
                }
                if (celery.getEast() == true){
                    g.drawImage(east, 0, 0, null);
                }
                if (solution != null && solution.contains(celery)){
                    g.drawImage(onpath, 0, 0, null);
                }
                if (solution != null && labyrinth.gettonorth() != null && labyrinth.gettonorth().contains(celery)){
                        g.drawImage(tonorth, 0, 0, null);
                    }
                if (solution != null && labyrinth.gettowest() != null && labyrinth.gettowest().contains(celery)){
                        g.drawImage(towest, 0, 0, null);
                    }
                if (solution != null && labyrinth.gettosouth() != null && labyrinth.gettosouth().contains(celery)){
                        g.drawImage(tosouth, 0, 0, null);
                    }
                if (solution != null && labyrinth.gettoeast() != null && labyrinth.gettoeast().contains(celery)){
                        g.drawImage(toeast, 0, 0, null);
                    }                
                JLabel thiscell = new JLabel(new  ImageIcon(combined));
                mazepanel.add(thiscell);
            }
        }
        mazeframe.add(mazepanel);
        mazeframe.setSize(w * labyrinth.getm(), h * labyrinth.getn());
        mazeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mazeframe.setVisible(true);
    }
}
