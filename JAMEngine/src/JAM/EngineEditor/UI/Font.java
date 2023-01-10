package JAM.EngineEditor.UI;

import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;

public class Font {
	private int fontID;
	private BufferedImage bufferedImage;
	public Vector2f imageSize;
	private java.awt.Font font;
	private FontMetrics fontMetrics;
	private int i;
	
	private Map<Character, Glyph> charsMap=new HashMap<Character, Glyph>();
	
	public Font(String fp,float size) {
		try {font=java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,new File(fp) );
		} catch (FontFormatException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		
		generateFont();
	}

	public Font(java.awt.Font _font) {
		font=font;
		generateFont();
	}
	
	
	private void generateFont() {
		GraphicsConfiguration gc=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Graphics2D graphics2d=gc.createCompatibleImage(1,1,Transparency.TRANSLUCENT).createGraphics();
		fontMetrics=graphics2d.getFontMetrics();
		imageSize=new Vector2f(1024,1024);
		bufferedImage=graphics2d.getDeviceConfiguration().createCompatibleImage((int)imageSize.x, (int)imageSize.y,Transparency.TRANSLUCENT);
	}
}
