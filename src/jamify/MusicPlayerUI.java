package jamify;

import java.awt.*;
import java .awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MusicPlayerUI extends JFrame implements ActionListener {

//	public static String commonPath = "/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/";

	//	public static String songPath = "/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/Allsongs.csv";
//	public static String tempFilePath = "/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/tempFile.txt";
//	static JFrame window = new JFrame ("Something Working");
	JPanel display = new JPanel();
	static JPanel jp = new JPanel();
//	static JPanel playlist = new JPanel();
	static JPanel songList = new JPanel();
//	static JPanel showqueue = new JPanel();
	JButton allSongsButton = new JButton("All Songs");
//	static JButton play = new JButton("Play");
//	static JButton pause = new JButton("Pause");
//	static  JButton stop = new JButton("Stop");
//	static   JButton rewind = new JButton("Rewind");
//	static JButton forward= new JButton("Forward");
//	static JButton repeat = new JButton("Repeat");
//	static JButton next = new JButton("Next");
//	static JButton previous = new JButton("Previous");
//	static JButton createPlaylistButton = new JButton("Create Playlist");
	static JLabel label = new JLabel("Jamify");
//	static JButton shuffle = new JButton("Shuffle Songs");

	private static int y;
//	MusicPlayerUI()
//	{
//		AllSongs allSongs = new AllSongs();
//		display.setBackground(Color.WHITE);
//		playlist.setBackground(Color.black);
//		playlist.setLayout(new BoxLayout(playlist,BoxLayout.Y_AXIS));
//		showqueue.setBackground(Color.white);
//		window.setSize(1000,500);
//		try {
//			window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/background.jpg")))));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		//window.setResizable(false);
//		display.setPreferredSize(new Dimension(400,200));
//		playlist.setPreferredSize(new Dimension(200,100));
//
//		showqueue.setPreferredSize(new Dimension(200,200));
//		playlist.add(allSongsButton);
//		allSongsButton.setBounds(10,10,30,20);
//		 ActionListener Action1 = (ActionEvent e) -> {
//           try {
////        	 allSongs.removeAll();
//        	 allSongs.displayAllSongsPanel();
//        	 JPanel allSongsPanel = allSongs.displaySongsOnThePanel(new File("/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/Allsongs.csv"), true);
//        	 MusicPlayer.window.getContentPane().add(allSongsPanel);
//           	allSongsPanel.revalidate();
//           	allSongsPanel.repaint();
//           } catch (Exception p){
//               System.out.println(p);
//           }
//      };
//
//		allSongsButton.addActionListener(Action1);
//		playlist.add(createPlaylistButton);
//		createPlaylistButton.addActionListener(new CreatePlayListActionListener());
////		allSongsButton.addActionListener(new showAllSongs);
//		window.add(display);
//		display.add(label);
////		window.add(jp);
//		window.setLayout(new BorderLayout(3,3));
//		window.add(playlist);
//
//
////		  Insets insets = window.getInsets();
////		  Dimension sizePlaylist = playlist.getPreferredSize();
////		  Dimension sizeDisplay = display.getPreferredSize();
////		  playlist.setBounds(20+insets.left,5+insets.top,
////				  sizePlaylist.width,sizePlaylist.height);
//		play.setPreferredSize(new Dimension(40,40));
//		play.setIcon(new ImageIcon("/Users/satishrambhatla/Downloads/Buttons/playButton.png"));
//		stop.setPreferredSize(new Dimension(40,40));
//		pause.setPreferredSize(new Dimension(40,40));
//		rewind.setPreferredSize(new Dimension(40,40));
//		forward.setPreferredSize(new Dimension(40,40));
//		repeat.setPreferredSize(new Dimension(40,40));
//		shuffle.setPreferredSize(new Dimension(40,40));
//		next.setPreferredSize(new Dimension(40,40));
//		previous.setPreferredSize(new Dimension(40,40));
//		display.setLayout(new FlowLayout());
//
//		display.add(play);
//		display.add(stop);
//		display.add(pause);
//		display.add(rewind);
//		display.add(forward);
//		display.add(repeat);
//		display.add(shuffle);
//		display.add(next);
//		display.add(previous);
//		window.setLocation(100,100);
//		window.add(playlist,BorderLayout.WEST);
//		window.add(showqueue,BorderLayout.EAST);
//		window.add(display,BorderLayout.SOUTH);
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		window.pack();
//		window.setVisible(true);
////		display.setVisible(true);
//	}

	@Override
	public void actionPerformed(ActionEvent arg0){

	}



	public static void main(String [] args)
	{


		SavePlaylists savePlaylists = new SavePlaylists();
		savePlaylists.retrieve();

		MusicPlayer musicPlayer = new MusicPlayer();
//		MusicPlayerUI  mp = new MusicPlayerUI();

		/** Previously if there were any playlist created it'll be shown on the UI. On Right click of the play list button,
		 * DELETE Option is provided */

		if (AllSongs.playlistNamePathsList.size() > 0) {
			for (int i = 0; i < AllSongs.playlistNamePathsList.size(); i++) {
				JButton playlistButton = new JButton(AllSongs.playlistNamePathsList.get(i).getPlayListName());
				int finalI1 = i;
				playlistButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
						{
							System.out.println("You right clicked on the button");

							JPopupMenu rightClickMenu = new JPopupMenu();
							JMenuItem delete = new JMenuItem("Delete");
							rightClickMenu.add(delete);
							rightClickMenu.show(e.getComponent(), e.getX(), e.getY());

							delete.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									System.out.println("clicked delete ");
									MusicPlayer.playlist.remove(playlistButton);
									AllSongs.playlistNamePathsList.remove(finalI1);
									MusicPlayer.playlist.revalidate();
									MusicPlayer.playlist.repaint();
								}
							});

						}
					}
				});

				playlistButton.setBounds(20, 50, 100, 50);
				y = y + 30;
//				playlistButton.setBorder(null);
				MusicPlayer.playlist.add(playlistButton);
				int finalI = i;

				/** On click of Play list button, the songs added in that play list is shown */
				playlistButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						MusicPlayer.allSongs.removeAll();
						MusicPlayer.allSongs.displayAllSongsPanel();
						JPanel jp = MusicPlayer.allSongs.displaySongsOnThePanel(new File(AllSongs.playlistNamePathsList.get(finalI).getPlayListPath()), false);
						MusicPlayer.window.getContentPane().add(jp);
						jp.validate();
						jp.repaint();
					}
				});
			}
		}

		musicPlayer.window.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				savePlaylists.save();
			}
		});

//		mp.window.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent e){
//				savePlaylists.save();
//			}
//		});

	}
}
