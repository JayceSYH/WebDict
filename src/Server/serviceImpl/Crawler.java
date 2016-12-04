package Server.serviceImpl;

import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

	private String _baiduDict = "http://dict.baidu.com/s?wd=";
	private Pattern _baiduOutterPattern = Pattern.compile("<div class=\"en-content\"><div>(.*?)</div>.*");
	private Pattern _baiduInnerPattern = Pattern.compile("<p><strong>(.*?)</strong><span>(.*?)</span>");

	private String _youdaoDict = "http://dict.youdao.com/w/";
	private Pattern _youdaoOutterPattern = Pattern.compile("<div class=\"trans-container\"><ul>(.*?)</ul>.*");
	private Pattern _youdaoInnerPattern = Pattern.compile("<li>(.*?)</li>");

	private String _bingDict = "http://cn.bing.com/dict/search?q=";
	private Pattern _bingOutterPattern = Pattern.compile("</div></div></div><ul>(.*?)</ul>");
	private Pattern _bingInnerPattern = Pattern.compile("<li><span class=\"pos\">(.*?)</span><span class=\"def\"><span>(.*?)</span></span></li>");

	public String queryBaidu(String s) {
		return query(s, _baiduDict, "+", _baiduOutterPattern, _baiduInnerPattern);
	}

	public String queryYoudao(String s) {
		return query(s, _youdaoDict, "%20", _youdaoOutterPattern, _youdaoInnerPattern);
	}

	public String queryBing(String s) {
		return query(s, _bingDict, "+", _bingOutterPattern, _bingInnerPattern);
	}

	public String[] queryAll(String s) {
		String[] ret = new String[3];

		try {
			Thread baiduThread = new Thread(() -> {
				ret[0] = queryBaidu(s);
			});

			Thread bingThread = new Thread(() -> {
				ret[1] = queryBing(s);
			});

			Thread youdaoThread = new Thread(() -> {
				ret[2] = queryYoudao(s);
			}); 
			
			baiduThread.start();
			bingThread.start();
			youdaoThread.start();
			
			baiduThread.join();
			bingThread.join();
			youdaoThread.join();
		} catch(Exception e) {
			System.out.println("Server crashed");
			e.printStackTrace();
		}

		return ret;
	}

	private String query(String s, String dict, String substitute, Pattern outterPattern, Pattern innerPattern) {
		try{
			URL url = new URL(dict + s.replaceAll("[ \t]", substitute));
			Scanner in = new Scanner(url.openStream(), "UTF-8");
			StringBuilder content = new StringBuilder();
			StringBuilder ret = new StringBuilder();
			while(in.hasNextLine()) content = content.append(in.nextLine().trim());

			Matcher outMatcher = outterPattern.matcher(content);
			if(outMatcher.find()) {
				String meaningGroup = outMatcher.group(1);
				Matcher inMatcher = innerPattern.matcher(meaningGroup);
				int groupCount = inMatcher.groupCount();

				while(inMatcher.find()) {
					for(int i = 1; i <= groupCount; i ++) {
						ret.append(inMatcher.group(i) + " ");						
					}
					ret.append("\n");
				}

				in.close();

				return ret.toString();				
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	 public static void main(String[] args) {
		 Crawler c = new Crawler();


		 System.out.print(c.queryAll("what")[0]);
	 }

}