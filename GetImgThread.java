package CrawlPic;

import java.io.*;
import java.net.*;

public class GetImgThread implements Runnable {
	private String dirname;
	private String countName;
	private File dir;
	private String url;
	
	public GetImgThread(String dirname, String countName, File dir, String url) {
		this.dirname = dirname;
		this.countName = countName;
		this.dir = dir;
		this.url = url;
	}
	
	@Override
	public void run() {
		String filename = "";
		
		if(url.contains(".jpg") || url.contains(".JPG")) {
			filename = dirname + countName + ".jpg";
		} else if(url.contains(".png") || url.contains(".PNG")) {
			filename = dirname + countName + ".png";
		} else if(url.contains(".gif") || url.contains(".PNG")) {
			filename = dirname + countName + ".gif";
		} else{
			filename = dirname + countName + ".jpg";
		}
		
//		String filename = url.substring(url.lastIndexOf("=") + 1) + ".zip";
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File file = new File(dir, filename);
		
		try {
			URL myURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
			bis = new BufferedInputStream(conn.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(file));

			System.out.println("figure: " + filename + " kick-off!");

			int length = 0;
			byte[] b = new byte[8192];
			while ((length = bis.read(b)) != -1)
				bos.write(b, 0, length);
			bos.flush();
			bos.close();
			System.out.println(filename + " Done!");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
