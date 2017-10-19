package tv.huan.master.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class FileParseUtil {
	public static File encodToMP3(String sh,File source) 
	{
		Process proc=null;
		try {
			String path=source.getPath().substring(0,source.getPath().lastIndexOf("."))+".mp3";
			if(source.getPath().lastIndexOf("webm")==-1)
				proc = Runtime.getRuntime().exec(sh+" "+source.getPath()+" mp3");
			else
				proc = Runtime.getRuntime().exec("ffmpeg -i "+source.getPath()+" "+path);
			String ls=null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ( (ls=bufferedReader.readLine()) != null)
				System.out.println(ls);
			bufferedReader.close();
			proc.waitFor();
			return new File(path);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static void downloadFile(String url,File file)
	{
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try(InputStream in= new URL(url).openStream();OutputStream out=new FileOutputStream(file))
		{
			byte[] bs=new byte[1024*1024];
			int len=0;
			while((len=in.read(bs))!=-1)
			{
				out.write(bs, 0, len);
			}
			out.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
