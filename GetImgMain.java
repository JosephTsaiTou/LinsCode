package CrawlPic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetImgMain {

	public static void main(String[] args) {
		
		
		File dirGroup_1 = new File("E:/Group_1/");	//Group_1資料夾
		if (!dirGroup_1.exists()) {
			dirGroup_1.mkdir();
		}
		
		File dirImgURLs = new File("E:/Group_1/ImgURLs/");	//放置classname.txt檔的資料夾
		if (!dirImgURLs.exists()) {
			dirImgURLs.mkdir();
		}
		
		File dirImgs = new File("E:/Group_1/Imgs/");	//圖檔資料夾
		if (!dirImgs.exists()) {
			dirImgs.mkdir();
		}
		
		File[] txts = dirImgURLs.listFiles();
		for(int i = 0; i < txts.length; i++){
//			System.out.println(txts[i].getName());	//取得classname.txt檔的檔名
			File file = new File("E:/Group_1/ImgURLs/" + txts[i].getName());
			String dirname = txts[i].getName().substring(0, txts[i].getName().lastIndexOf("."));	//去掉.txt
			File dir = new File("E:/Group_1/Imgs/" + dirname);	//建立存放各類別圖檔的資料夾
			if (!dir.exists()) {
				dir.mkdir();
			}
			
			BufferedReader br = null;
			int j_thousand = 10000;
			int j = 0;
			
			try{
				br = new BufferedReader(new FileReader(file));
				String url;
				while ((url = br.readLine()) != null) {	//一行一行讀取URL網址
					
					j++;
					int count = j_thousand + j;
					String countName = "a" + count;
					countName = countName.substring(2);
					if(url != ""){
						Thread t = new Thread(new GetImgThread(dirname, countName, dir, url));
						t.start();
					}
					System.out.println(url);
					System.out.flush();
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch(IOException ee) {
						ee.printStackTrace();
					}
				}
				System.out.println();
			}
			
			System.out.println("=========================================================================");
		}
		
		//http://www.sozai-page.com/cgi/dl_rank_a/dlranklog.cgi?dl=a03_261
		//261應該是最後一個
		//用多執行緒會短時間內對網站發出過多下載請求，此網站無法負荷，會出現錯誤
		
//		int i_thousand = 1000;
//		for (int i = 1; i <= 3; i++) {
//			int count = i_thousand + i;
//			String countName = "a" + count;
//			countName = countName.substring(2);
//			String url = "http://www.sozai-page.com/cgi/dl_rank_a/dlranklog.cgi?dl=a03_" + countName;
//			Thread t = new Thread(new GetImgThread(dir, url));
//			t.start();
//		}
	}

}
