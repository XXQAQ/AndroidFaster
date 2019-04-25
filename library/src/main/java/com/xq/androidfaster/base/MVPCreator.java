package com.xq.androidfaster.base;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

//一键生成MVP标准模板的工具。如需调整请自行修改，以下为示例代码
//	new MVP(name).create();
public class MVPCreator {
	
	public static final String BASE ="Base";
	
	private String parents;
	private String name;

	public static void main(String[] args){
		try {
			String name = "Test";
			new MVPCreator(name).create();
			System.out.print(name + "创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}

	public MVPCreator(String name) {
		this(name, BASE);
	}

	public MVPCreator(String name, String parents) {
		this.parents = parents;
		this.name = name;
	}
	
	public void create() throws Exception {
		
		String parent = String.format("%s", name).toLowerCase();
		
		FileUtil.writeData(createInterfacePresenter().getBytes(), parent+ File.separator+ String.format("I%sPresenter.java", name));
		FileUtil.writeData(createActivity().getBytes(), parent+ File.separator+ String.format("%sActivity.java", name));
		FileUtil.writeData(createFragment().getBytes(), parent+ File.separator+ String.format("%sFragment.java", name));
		FileUtil.writeData(createInterfaceView().getBytes(), parent+ File.separator+ String.format("I%sView.java", name));
		FileUtil.writeData(createView().getBytes(), parent+ File.separator+ String.format("%sView.java", name));
	}
	
	private String createInterfacePresenter(){

		return String.format(IPRESENTER,name+"Presenter",parents+"Presenter",name+"View");
	}
	
	private String createActivity(){
		
		return String.format(PRESENTER,name+"Activity",parents+"Activity",name+"View",name+"Presenter");
	}
	
	private String createFragment(){

		return String.format(PRESENTER,name+"Fragment",parents+"Fragment",name+"View",name+"Presenter");
	}
	
	private String createInterfaceView(){

		return String.format(IVIEW,name+"View",parents+"View",name+"Presenter");
	}
	
	private String createView(){

		return String.format(VIEW,name+"View",parents+"View",name+"Presenter",name+"View");
	}
	
	static class FileUtil{
		public static void writeData(byte []data, String path) throws Exception {
			
			File file = new File(path);

			if (file.exists())
			{
				 throw new Exception("File is exists!");
			}

			if(!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			
			ByteArrayInputStream in=null;
			BufferedOutputStream out=null;
			try 
			{
				in=new ByteArrayInputStream(data);
				out=new BufferedOutputStream(new FileOutputStream(file));
				
				byte []buffer = new byte[8*1024];
				int length=0;
				
				while( (length = in.read(buffer)) != -1 )
				{
					out.write(buffer, 0, length);
					out.flush();
				}
			} 
			finally
			{
				if(in != null)
					in.close();
				if(out != null)
					out.close();
			}
				
		}
	}

	private static String IPRESENTER = "public interface I%s extends I%s<I%s> {\n" +
			"}";

	private static String PRESENTER = "public class %s extends %s<I%s> implements I%s {\n" +
			"}";

	private static final String IVIEW = "public interface I%s extends I%s<I%s> {\n" +
			"}";

	private static final String VIEW = "public class %s extends %s<I%s> implements I%s {\n" +
			"}";

}
