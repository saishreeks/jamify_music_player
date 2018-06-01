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


public class FancyStuff {

	public SongDetails fancy(String songPath) throws IOException {
		String name = songPath;
		SongDetails songDetails = null;
		try{
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

	public static void DisplayName(String songNameQ) throws IOException {

//		JFrame frame = new JFrame();
//		frame.setLayout(new FlowLayout());
//		frame.setSize(400, 400);
		JLabel lbl = new JLabel(songNameQ);
		lbl.setForeground(Color.white);
		lbl.setLocation(200,100);
		MusicPlayer.songdetail.add(lbl);
//		frame.add(lbl);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void DisplayImage(String songImageQ) throws IOException {

		BufferedImage img = ImageIO.read(new File(String.valueOf(songImageQ)));
		ImageIcon icon = new ImageIcon(img);
//		JFrame frame = new JFrame();
//		frame.setLayout(new FlowLayout());
//		frame.setSize(200, 300);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		lbl.setLocation(100,100);
		MusicPlayer.songdetail.add(lbl);
//		frame.add(lbl);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String getSongName(String path) {
		String csvFile = "/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/AllSongs.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
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
		String csvFile = "/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/AllSongs.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] csvitems = line.split(cvsSplitBy);
				if (csvitems[2].equals(path)) {
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
//			JFrame frame = new JFrame();
//			frame.setLayout(new FlowLayout());
//			frame.setSize(400, 400);
//			JLabel labelTitle = new JLabel(metadata.get("title") );
//			JLabel labelArtist = new JLabel(metadata.get("xmpDM:artist") );
//			JLabel labelComposer = new JLabel(metadata.get("xmpDM:composer") );
//			JLabel labelGenre = new JLabel(metadata.get("xmpDM:genre") );
//			JLabel labelAlbum = new JLabel(metadata.get("xmpDM:album") );
//			JLabel labelYear = new JLabel(metadata.get("xmpDM:year") );
//			frame.add(labelTitle);
//			frame.add(labelArtist);frame.add(labelComposer);frame.add(labelGenre);frame.add(labelAlbum);
//			frame.add(labelYear);
//			MusicPlayer.songdetail.add(labelTitle);
//			MusicPlayer.songdetail.add(labelArtist);
//			MusicPlayer.songdetail.add(labelComposer);
//			MusicPlayer.songdetail.add(labelGenre);
//			MusicPlayer.songdetail.add(labelAlbum);
//			MusicPlayer.songdetail.add(labelYear);

//			frame.setVisible(true);
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			System.out.println("Title: " + metadata.get("title"));
			System.out.println("Artists: " + metadata.get("xmpDM:artist"));
			System.out.println("Composer: " + metadata.get("xmpDM:composer"));
			System.out.println("Genre: " + metadata.get("xmpDM:genre"));
			System.out.println("Album: " + metadata.get("xmpDM:album"));
			System.out.println("Year: " + metadata.get("xmpDM:year"));
			//System.out.println("Alnum: "+metadata.get("xmpDM:image"));

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

}


