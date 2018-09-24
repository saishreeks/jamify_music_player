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

	JPanel display = new JPanel();
	static JPanel jp = new JPanel();
	static JPanel songList = new JPanel();
	JButton allSongsButton = new JButton("All Songs");
	static JLabel label = new JLabel("Jamify");
	private static int y;


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



	}
}
