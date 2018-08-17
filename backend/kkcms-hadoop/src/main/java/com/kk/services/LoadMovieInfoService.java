package com.kk.services;

import com.kk.models.MovieInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created by hutwanghui on 2018/7/14 13:37.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@Service
public class LoadMovieInfoService {
	private OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.connectTimeout(60000, TimeUnit.SECONDS)
			.readTimeout(60000, TimeUnit.SECONDS)
			.writeTimeout(60000,TimeUnit.SECONDS)
			.build();

	//获得图片的基本路径
	private static String getPathBase = "https://v2.sg.media-imdb.com/suggests/";

	@Autowired
	private MovieInfoService movieInfoService;

	public List<String> loadPics(){
		List<String> messages = new ArrayList<>();
		try {
			List<MovieInfo> movieInfos = movieInfoService.findAll();
			for (MovieInfo m: movieInfos
					) {
				String picPath =
						ResourceUtils.getURL("classpath:").getPath()
								+"\\static\\MoviePics\\"+m.getName()+".jpg";
				messages.add(downloadPicture(m.getImageUrl(), picPath));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return messages;
	}

	private String downloadPicture(String urlList, String path) {
		String message = "";
		URL url = null;
		try {
			url = new URL(urlList);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());

			FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			fileOutputStream.write(output.toByteArray());
			message = "正在下载图片"+urlList;
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (message != ""){
			return message;
		}else {
			return "下载图片"+urlList+"失败";
		}
	}

	public List<MovieInfo> loadInfo(){
		File path = null;
		List movieInfos = new ArrayList();
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
			File fin = new File(
					path.getAbsolutePath()+"\\static\\data\\u.item");
			FileInputStream fis = null;
			fis = new FileInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "ISO-8859-1"));
			String line = "";
			StringBuffer appendStr = new StringBuffer();
			while ((line = br.readLine()) != null ) {
				MovieInfo movieInfo = new MovieInfo();
				String[] infos = line.split("\\|");
				movieInfo.setMovieId(infos[0]);
				movieInfo.setName(infos[1]);
				movieInfo.setDate(infos[2]);
				String title = infos[1].toLowerCase();
				String t = title.substring(0, 1);
				String year = "";
				Pattern pattern = compile("(?<=\\()[^\\)]+");
				Matcher matcher = pattern.matcher(title);
				while(matcher.find()){
					year = matcher.group();
				}
				String lowerPath = title.replaceAll("[^a-z]+" ,"_");
				String pathTitle = lowerPath.substring(0, lowerPath.length());
				String getPathReal = getPathBase
						+ appendStr.append(t).append("/").append(pathTitle).append("_"+year).append(".json").toString();
				movieInfo.setInfoUrl(getPathReal);
				System.out.println("电影id为："+infos[0]+"请求的地址为："+getPathReal);
				String imagePath = null;
				try {
					imagePath = getImageByJson(getPathReal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				movieInfo.setImageUrl(imagePath);
				movieInfos.add(movieInfo);
				appendStr.delete(0, appendStr.length());
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		movieInfoService.saveAll(movieInfos);
		return movieInfos;
	}

	public String getImageByJson(String path) throws Exception{
		JsonObject object = null;
		String imagePath = "";
//		try {
//			object = getJsonObject(path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		int count = 0;
		while(object == null && count <= 3){
			try {
				count += 1;
				Thread.sleep(1000);
				object = getJsonObject(path);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("object: "+object);
		if (object != null){
			JsonArray data = object.getAsJsonArray("d");
			if (data != null){
				JsonElement firstSearch = data.get(0);
				if (firstSearch != null){
					JsonArray in = firstSearch.getAsJsonObject().getAsJsonArray("i");
					if (in != null){
						imagePath = in.get(0).getAsString();
					}else {
						imagePath = "null";
					}
				}else{
					imagePath = "null";
				}
			}else {
				imagePath = "null";
			}
		}else {
			imagePath = "null";
		}
		//imagePath = object.getAsJsonArray("d").get(0).getAsJsonObject().getAsJsonArray("i").get(0).getAsString();
		System.out.println(imagePath);
		return imagePath;
	}

	private JsonObject getJsonObject(String path) throws IOException {
		Response response;
		response = getResponse(path, okHttpClient);
		String resStr = response.body().string();
		resStr = resStr.substring(resStr.indexOf("(")+1);
		if(resStr.lastIndexOf(")") != -1){
			resStr = resStr.substring(0, resStr.lastIndexOf(")"));
		}
		JsonParser parse =new JsonParser();
		return (JsonObject) parse.parse(resStr);
	}

	private Response getResponse(String path, OkHttpClient okHttpClient) throws IOException {
		Request request = new Request.Builder()
				.url(path)
				.build();
		return okHttpClient.newCall(request).execute();
	}

}
