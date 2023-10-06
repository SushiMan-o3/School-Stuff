/*
 * Written by: Sushim Malla
 * Description: A music player that plays downloaded music. 
 * Date: 18/5/2023
*/

//importing all libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import sun.audio.*;
import java.util.*;

public class Ipod implements MouseListener, ActionListener {
  // Declaring all variables
  static int i = 0;
  // play is true, pause is false
  public boolean play_or_pause = true;
  static String auFile;
  static AudioStream as = null;
  static boolean  isAudioStreamNull = false;
  
  // Setting up panels and frames
  JFrame frame = new JFrame("iPod");
  JPanel panel = new JPanel();
  JFrame popupFrame = new JFrame("iPod");
  JPanel popupPanel = new JPanel();
  
  // Declaring all Jlabels and Jbuttons
  public static JTextField songInput = new JTextField(5);
  JButton cancelButton = new JButton("Cancel");
  JButton addButton = new JButton("Add");
  JButton removeButton = new JButton("Remove");
  JLabel songImage = new JLabel(); 
  JLabel iPodButtons = new JLabel(); 
  JLabel title = new JLabel("Title: "); 
  JLabel artist = new JLabel("Artist: "); 
  
  // instance of the Playlist class
  Playlist PlaylistChanger = new Playlist(); 
       
  // creating an instance of the class and running screen which opens the frame
  public static void main(String[] args){
    Ipod myself = new Ipod();
    myself.startUp();
  }
  
  // Setting up the frames and panels
  public void startUp(){
    frame.setSize(400, 700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    frame.add(panel);
    placeComponents(panel);
    
    frame.addMouseListener(this);
    frame.setVisible(true);
    
    // popup frames frame
    popupFrame.setSize(400, 200);
    popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    popupFrame.add(popupPanel);
    placeComponents_2(popupPanel);
    popupFrame.setVisible(false);
    
    // Adding the songs
    PlaylistChanger.addingSongs("Bizet - L'Arlesienne Suite No.1 'Carillon'");
    PlaylistChanger.addingSongs("Patrick Watson - Je te lasserai des mots");
    PlaylistChanger.addingSongs("Die Toten Hosen - Tage wie diese");
  }
     
  // the image for the buttons is uploaded and set up
  public void placeComponents(JPanel panel){
    panel.setLayout(null);
    
    iPodButtons.setIcon(new ImageIcon("Assets/ipod_assets_1.png"));
    iPodButtons.setBounds(50,350, 300, 300);
    panel.add(iPodButtons);
    
    songImage.setBounds(50,10, 300, 300);
    panel.add(songImage);
     
    title.setBounds(50, 320, 200, 25);
    panel.add(title);
     
    artist.setBounds(250, 320, 200, 25);
    panel.add(artist);
  }
  
  // Components for the pop up panel
  public void placeComponents_2(JPanel popupPanel){
    popupPanel.setLayout(null);
    
    JLabel addOrRemove = new JLabel("Would you like to add or remove a song?");
    addOrRemove.setBounds(10, 10, 400, 25);
    popupPanel.add(addOrRemove);
    
    songInput.setBounds(10, 40, 350, 25);
    popupPanel.add(songInput);
    
    cancelButton.setBounds(100, 100, 80, 25);
    popupPanel.add(cancelButton);
    cancelButton.addActionListener(this);
    
    addButton.setBounds(200, 70, 80, 25);
    popupPanel.add(addButton);
    addButton.addActionListener(this);
    
    removeButton.setBounds(100, 70, 80, 25);
    popupPanel.add(removeButton);
    removeButton.addActionListener(this);
  }
  
    // It's nesscary to have all the mouse... because you need to declare all the methods
   @Override
   public void mouseClicked(MouseEvent e){}
   
   // here is where we are checking where the mouse has been clicked and acting accordingly. 
   @Override
   public void mousePressed(MouseEvent e){
     // Uses if statements and when the song it changed, it adds 1 to the i and it also changes the image and title
     int x = e.getX();
     int y = e.getY();
     System.out.println(i);
     
     System.out.println(x +", "+ y);
     
     // menu - option to add and remove song
     if ((x > 50 && x < 350) && (y > 350 && y < 450)){
       popupFrame.setVisible(true);
     }
     
     //back button
     if ((x > 50 && x < 200) && (y > 450 && y < 550)){
       System.out.println("Back");
       if (i != 0){
         i--;
         if (as != null) {
           isAudioStreamNull = false;
           AudioPlayer.player.stop(as);
           System.out.println("Next song playing now.");
         } 
         else if (as == null) {
           isAudioStreamNull = true;
         }
         
         try {
           auFile = "Music/" + PlaylistChanger.songs.get(i) + ".wav";
           songImage.setIcon(new ImageIcon("Assets/" + PlaylistChanger.songs.get(i) + ".jpg"));
           String[] name_title = (PlaylistChanger.songs.get(i)).split(" - ");
           artist.setText("Artist: " + name_title[0]);
           title.setText("Title: " + name_title[1]);
      
           InputStream in = new FileInputStream(auFile);
           as = new AudioStream(in);
           AudioPlayer.player.start(as);
         } 
         
         catch (Exception f) {
           i = 0;
           System.out.println(e.toString());
         }
       }
       else {
         ;
       }
     }
     
     //forward button
     if ((x > 200 && x < 350) && (y > 450 && y < 550)){
       System.out.println("forward");
       i++;
       if (as != null) {
         isAudioStreamNull = false;
         AudioPlayer.player.stop(as);
         System.out.println("Next song playing now.");
       } 
       else if (as == null) {
         isAudioStreamNull = true;
       }
       
       try {
         auFile = "Music/" + PlaylistChanger.songs.get(i) + ".wav";
         songImage.setIcon(new ImageIcon("Assets/" + PlaylistChanger.songs.get(i) + ".jpg"));
         String[] name_title = (PlaylistChanger.songs.get(i)).split(" - ");
         artist.setText("Artist: " + name_title[0]);
         title.setText("Title: " + name_title[1]);
         
         InputStream in = new FileInputStream(auFile);
         as = new AudioStream(in);
         AudioPlayer.player.start(as);
       } 
       
       catch (Exception f) {
         i = 0;
       }

     }
     
     // pause and play button
     if((x > 50 && x < 350) && (y > 550 && y < 675)){
       if (play_or_pause == true){
         AudioPlayer.player.stop(as);
         play_or_pause = false;
         System.out.println("Pausing the song: " + PlaylistChanger.songs.get(i));
       }
       else {
         AudioPlayer.player.start(as);
         play_or_pause = true;
         System.out.println("Resumiong the song:" + PlaylistChanger.songs.get(i));
       }
     }
   }

   @Override
   public void mouseReleased(MouseEvent e) {}
   
   @Override
   public void mouseEntered(MouseEvent e) {}
   
   @Override
   public void mouseExited(MouseEvent e) {}
   
   // Action prreformed for the buttons where if it is clicked, it will go to the other class to do stuff and eventually close the frame
   public void actionPerformed(ActionEvent e) {
     if (e.getSource() == addButton){
       PlaylistChanger.addingSongs(songInput.getText());
       popupFrame.setVisible(false);
     }
     
     else if (e.getSource() == removeButton){
       PlaylistChanger.removingSongs(songInput.getText());
       popupFrame.setVisible(false);
     }
     
     else if (e.getSource() == cancelButton){
       PlaylistChanger.clearPlaylist();
       popupFrame.setVisible(false);
     }
   }
}


// Second class for manipuling the playlist
class Playlist {
  // the playlist itself is stored in a link list, and songs and such are added to it. It's in this clas because it's easier to access the methods
  LinkedList<String> songs = new LinkedList<String>();
  
  // The methods below are used to manipulate the linked list by adding songs, removing songs or clearing the entire playlist.
  public void addingSongs(String song){
    songs.add(song);
    System.out.println("The Playlist now: " + songs);
  }
  
  public void removingSongs(String song){
    songs.remove(song);
    System.out.println("The Playlist now: " + songs);
  }
  
  public void clearPlaylist(){
    songs.clear();
    Ipod.i = 0;
    System.out.println("The Playlist now: " + songs);
  }

}