/*
Author: Sushim Malla
Created: 4/11/2023
Description: You pick a map, and you can move around in the map!
*/

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.*;  
import java.util.Scanner;
import java.lang.*;

public class maze
{
  public static int map[][] = new int[20][20];
    
  public static void main(String[] args) throws FileNotFoundException
  {
    Scanner map_name = new Scanner(System.in);  
    System.out.println("Enter Map: ");

    String map_name_input = map_name.nextLine();
    
    Scanner map_file = new Scanner(new File(map_name_input));
    int row = 0;
    
    while (map_file.hasNextLine()) {
      String line = map_file.nextLine();
      String rowVal[] = line.split(",");
      
      for (int col=0; col<20; col++){
        maze.map[col][row] = Integer.parseInt(rowVal[col]);
      }
      row++;
    }
    
    JFrame j = new JFrame("Maze - Path findng");
    MazePanel pnl = new MazePanel();
    
    pnl.setFocusable(true);
    j.setSize(800, 840);
    j.add(pnl);
    j.setIconImage(new ImageIcon("icon.png").getImage());
    
    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.setVisible(true);
    
    
    
    while(true)
    {
      pnl.animate();
      try{
        Thread.sleep(100);
      }
      catch(Exception e){
        System.out.println("Exception in thread sleep: "+e);
      }
    }
        
  }      
}

class MazePanel extends JPanel  implements KeyListener
{
    BufferedImage draw_wall, draw_character;
    BufferedImage[][] subImages;
    final static String img_wall = "wall.jpg"; 
    final static String img_character = "character.png";
    int[][] edit_map = maze.map;
    int direction;
    int xp, yp, d;
    
    MazePanel()
    {
      direction = KeyEvent.VK_RIGHT;
      this.addKeyListener(this);
      xp = 1;
      yp = 0;
      d = 1;
      
        try
        {
            draw_wall = ImageIO.read(new File(img_wall));
            draw_character = ImageIO.read(new File(img_character));
        }
        catch(Exception e)
        {
            System.out.println("Exception in Loading Image: "+e);
        }
    }

    @Override
    public void paint(Graphics g)
    {
      super.paint(g);
      for(int x=0; x<20; x++){
        for(int y=0; y<20;y++){
          int num = maze.map[x][y];
          
          if (num == 1){
            g.drawImage(draw_wall, 40*x, 40*y, null);
          }
          else {
            ;
          }
        } 
      }
      
      g.drawImage(draw_character, xp*40, yp*40, null);
      
    }


    public void animate(){
      repaint();
      switch(direction)
        {
            case KeyEvent.VK_DOWN:
              if(yp < 20){
                 if (maze.map[xp][yp+1] == 1){
                     break;
                 }
              else{
                yp = yp + d;
                break;
              }
            }

            case KeyEvent.VK_UP:
              if(yp > 0){
                if (maze.map[xp][yp-1] == 1){
                  break;
                }
                else{
                  yp = yp - d;
                  break;
                }
              }

            case KeyEvent.VK_RIGHT:
              if(xp < 19){
               if (maze.map[xp+1][yp] == 1){
                  break;
               }
               else {
                 xp = xp + d;
                 break;
               }
            }

            case KeyEvent.VK_LEFT:
              if(xp > 0){
                if (maze.map[xp-1][yp] == 1){
                  break;
                 }
                else {
                  xp = xp - d;
                  break;
              }
            }
        }
              
    }
    
    
    public void keyPressed(KeyEvent e){
        direction = e.getKeyCode();
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
 
}