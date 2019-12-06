import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "unused" })
public class FileReader extends JFrame {

	public static String getTxtFile(String fileName,String flag){
		StringBuffer sn = null;
		try {
			File file = new File(fileName);
			InputStreamReader input = new InputStreamReader(
					new FileInputStream(file));
			BufferedReader bf = new BufferedReader(input);
			
			sn = new StringBuffer();
			
			String str = "";
			
			while ((str = bf.readLine()) != null) {
				sn.append(str);
			}
			
			bf.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sn.toString();
	}
}