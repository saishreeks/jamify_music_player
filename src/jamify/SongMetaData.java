package jamify;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.awt.color.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


public class SongMetaData {

	public SongDetails fancy(String songPath) throws IOException {
		String name = songPath;
		SongDetails songDetails = null;
		try{
			if (this.getSongImage(songPath)!= null)
				DisplayImage(this.getSongImage(songPath));
			//DisplayName(this.getSongName(songPath));
			songDetails = getMetaData(name);

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//make into function
		return songDetails;
	}


	public static void DisplayImage(String songImageQ) throws IOException {

		MusicPlayer.songdetail.removeAll();

		BufferedImage img = ImageIO.read(new File(String.valueOf(songImageQ)));

		JLabel lbl = new JLabel();
		lbl.setSize(100,100);
		Image dimg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(dimg);
		lbl.setIcon(icon);
		MusicPlayer.songdetail.add(lbl);

	}

	public String getSongName(String path) {
		String csvFile = MusicPlayer.commonPath+"AllSongs.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				line.trim();
				if (line.isEmpty()) continue;
				String[] csvitems = line.split(cvsSplitBy);
				if (csvitems[2].equals(path)) {
					return csvitems[1];
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public String getSongImage(String path) {
		String csvFile = MusicPlayer.commonPath+"AllSongs.csv";
		BufferedReader br = null;
		String line="";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				line.trim();
				if (line.isEmpty()) continue;

				// use comma as separator
				String[] csvitems = line.split(cvsSplitBy);
				if (csvitems[2].equals(path)) {
					if (csvitems.length==4)
						return csvitems[3];
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}


	public SongDetails getMetaData(String path) {
		SongDetails songDetails = null;
		try {
			InputStream input = new FileInputStream(new File(path));
			ContentHandler handler = new DefaultHandler();
			Metadata metadata = new Metadata();
			Parser parser = new Mp3Parser();
			ParseContext parserCtx = new ParseContext();
			parser.parse(input, (ContentHandler) handler, metadata, parserCtx);
			input.close();

			songDetails = new SongDetails();
			songDetails.setAlbum(metadata.get("xmpDM:album"));
			songDetails.setArtist(metadata.get("xmpDM:artist"));
			songDetails.setComposer(metadata.get("xmpDM:composer") );
			songDetails.setGenre(metadata.get("xmpDM:genre") );
			songDetails.setTitle(metadata.get("title"));
			songDetails.setYear(metadata.get("xmpDM:year"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		return songDetails;
	}

	public void displaySongDetails(SongDetails songDetails) {

//        JPanel songdetailParentPanel = new JPanel();

		MusicPlayer.songdetailParentPanel.removeAll();

		JPanel songdetailPanel = new JPanel();

		songdetailPanel.setPreferredSize(new Dimension(600, 100));
		songdetailPanel.setLayout(new BoxLayout(songdetailPanel,BoxLayout.Y_AXIS));
		songdetailPanel.setBackground(new Color(0,0,0,65));
		songdetailPanel.setForeground(Color.WHITE);
		songdetailPanel.setOpaque(true);
		songdetailPanel.setVisible(true);
		JLabel labelTitle = new JLabel("Title: "+ songDetails.getTitle());
		JLabel labelArtist = new JLabel("Artist: "+ songDetails.getArtist());
		JLabel labelComposer = new JLabel("Composer: " +songDetails.getComposer());
		JLabel labelGenre = new JLabel("Genre: "+ songDetails.getGenre());
		JLabel labelAlbum = new JLabel("Album: " +songDetails.getAlbum());
		JLabel labelYear = new JLabel("Year: " +songDetails.getYear());
		songdetailPanel.add(labelTitle);
		songdetailPanel.add(labelArtist);
		songdetailPanel.add(labelComposer);
		songdetailPanel.add(labelGenre);
		songdetailPanel.add(labelAlbum);
		songdetailPanel.add(labelYear);



		JPanel innerParent = new JPanel();
		innerParent.setBackground(new Color(0,0,0,65));
		innerParent.setOpaque(false);
		innerParent.add(songdetailPanel);
		innerParent.add(MusicPlayer.songdetail);
		MusicPlayer.songdetailParentPanel.add(innerParent,BorderLayout.SOUTH);





	}
}


