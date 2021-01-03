import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GUI extends JFrame implements ActionListener {

    /**
     * author:      p1mple
     * version:     1.0.0
     */
    private static final long serialVersionUID = 1L;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    ImagePanel uslessPanel = new ImagePanel(new ImageIcon("daddy is gay.jpg").getImage());

    static JButton buttonPlusOne;
    static JButton buttonReset;
    static JLabel label;

    GridBagConstraints c = new GridBagConstraints();

    // Variables
    static int jFrameWidth = 260;
    static int jFrameHeight = 270;
    int counter = 0;

    public GUI()
    {
      // Define JFrame
      this.setVisible(true);
      this.setBounds(screenSize.width / 2 - jFrameWidth, screenSize.height / 2 - jFrameHeight, jFrameWidth, jFrameHeight);
      this.setResizable(false);
      this.setTitle("By: p1mple");
      this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      // uslessPanel
      uslessPanel.setLayout(new GridBagLayout());
      uslessPanel.setBounds(0, 0, jFrameWidth, jFrameHeight);
      this.add(uslessPanel);

      // JButtons
      c.fill = GridBagConstraints.BOTH;
      c.gridx = 0;
      c.gridy = 2;
      buttonPlusOne = new JButton("+1");
      buttonPlusOne.setVisible(true);
      buttonPlusOne.addActionListener(this);
      uslessPanel.add(buttonPlusOne, c);
      //
      c.fill = GridBagConstraints.BOTH;
      c.gridx = 0;
      c.gridy = 3;
      buttonReset = new JButton("Reset");
      buttonReset.setVisible(true);
      buttonReset.addActionListener(this);
      uslessPanel.add(buttonReset, c);

      // JLabel
      c.fill = GridBagConstraints.BOTH;
      c.gridheight = 1;
      c.gridx = 0;
      c.gridy = 1;
      label = new JLabel("", JLabel.CENTER);
      label.setVisible(true);
      label.setText("0");
      label.setForeground(Color.BLACK);
      uslessPanel.add(label, c);
      //
      c.anchor = GridBagConstraints.PAGE_END;
      c.gridheight = 1;
      c.gridx = 0;
      c.gridy = 0;
      JLabel god = new JLabel();
      god.setVisible(true);
      god.setText("Made by: p1mple :)");
      god.setForeground(Color.BLACK);
      uslessPanel.add(god, c);

    }

    //Button Press Check
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonPlusOne)
      {
        counter += 1; 
        label.setText("" + counter);
        new AudioPlayer().play("sounds/plusOne.WAV");;
      }
      if (e.getSource() == buttonReset)
      {
        counter = 0; 
        label.setText("" + counter);
        new AudioPlayer().play("sounds/reset.WAV");
      }
    }

    public static void main(String[] args) {
      new GUI();
    }
}
//Create a Panel with an Image as a Background
class ImagePanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Image img;
  
    public ImagePanel(String img) {
      this(new ImageIcon(img).getImage());
    }
  
    public ImagePanel(Image img) {
      this.img = img;
      Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
      setPreferredSize(size);
      setMinimumSize(size);
      setMaximumSize(size);
      setSize(size);
      setLayout(null);
    }
  
    public void paintComponent(Graphics g) {
      g.drawImage(img, 0, 0, null);
    }
  
  }

  //Credit: https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
  class AudioPlayer {
 
    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;
     
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
 
            audioLine.open(format);
 
            audioLine.start();
             
            System.out.println("Playback started.");
             
            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
 
            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }
             
            audioLine.drain();
            audioLine.close();
            audioStream.close();
             
            System.out.println("Playback completed.");
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }      
    }
     
    public static void main(String[] args) {
        String audioFilePath = "E:/Test/Audio.wav";
        AudioPlayer player = new AudioPlayer();
        player.play(audioFilePath);
    }
 
}