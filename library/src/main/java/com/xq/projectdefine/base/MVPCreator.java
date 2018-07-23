package com.xq.projectdefine.base;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//一键生成MVP标准模板的工具。请将本文件拖拽至您的eclipse工程中运行。如需调整请自行修改，以下为示例代码
//	new MVP(name).create();
public class MVPCreator {
	
	public static final String BASE ="CustomBase";
	
	private String parents;
	private String name;

	public MVPCreator(String name) {
		this(name, BASE);
	}

	public MVPCreator(String name, String parents) {
		this.parents = parents;
		this.name = name;
	}
	
	public void create() throws IOException{
		
		String parent = "MVP" + File.separator + String.format("%s", name).toLowerCase();
		
		FileUtil.writeData(createInterfacePresenter().getBytes(), parent+File.separator+String.format("I%sPresenter.java", name));
		FileUtil.writeData(createActivity().getBytes(), parent+File.separator+String.format("%sActivity.java", name));
		FileUtil.writeData(createFragment().getBytes(), parent+File.separator+String.format("%sFragment.java", name));
		FileUtil.writeData(createInterfaceView().getBytes(), parent+File.separator+String.format("I%sView.java", name));
		FileUtil.writeData(createView().getBytes(), parent+File.separator+String.format("%sView.java", name));
	}
	
	private String createInterfacePresenter(){

		return String.format(IPRESENTER,name+"Presenter",parents+"Presenter",name+"View");
	}
	
	private String createActivity(){
		
		return String.format(PRESENTER,name+"Activity",parents+"Activity",name+"View",name+"Presenter",name+"Presenter",name+"Presenter",name+"Presenter",name+"Presenter",name+"Presenter");
	}
	
	private String createFragment(){

		return String.format(PRESENTER,name+"Fragment",parents+"Fragment",name+"View",name+"Presenter",name+"Presenter",name+"Presenter",name+"Presenter",name+"Presenter",name+"Presenter");
	}
	
	private String createInterfaceView(){

		return String.format(IVIEW,name+"View",parents+"View",name+"Presenter");
	}
	
	private String createView(){

		return String.format(VIEW,name+"View",parents+"View",name+"Presenter",name+"View",name+"View",name+"View",name+"View",name+"View",name+"View");
	}
	
	static class FileUtil{
		public static void writeData(byte []data,String path) throws IOException{
			
			File file = new File(path);

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
			"\n" +
			"    @Override\n" +
			"    default void afterOnCreate(Bundle savedInstanceState) {\n" +
			" \n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onResume() {\n" +
			"        \n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onPause() {\n" +
			"        \n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onDestroy() {\n" +
			"        \n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onActivityResult(int requestCode, int resultCode, Intent intent) {\n" +
			"        \n" +
			"    }\n" +
			"    \n" +
			"}";

	private static String PRESENTER = "public class %s extends %s<I%s> implements I%s {\n" +
			"\n" +
			"    @Override\n" +
			"    public void afterOnCreate(Bundle savedInstanceState) {\n" +
			"        super.afterOnCreate(savedInstanceState);\n" +
			"        I%s.super.afterOnCreate(savedInstanceState);\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onResume() {\n" +
			"        super.onResume();\n" +
			"        I%s.super.onResume();\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onPause() {\n" +
			"        super.onPause();\n" +
			"        I%s.super.onPause();\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onDestroy() {\n" +
			"        super.onDestroy();\n" +
			"        I%s.super.onDestroy();\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onActivityResult(int requestCode, int resultCode, Intent intent) {\n" +
			"        super.onActivityResult(requestCode, resultCode, intent);\n" +
			"        I%s.super.onActivityResult(requestCode, resultCode, intent);\n" +
			"    }\n" +
			"\n" +
			"}";

	private static final String IVIEW = "public interface I%s extends I%s<I%s> {\n" +
			"\n" +
			"    @Override\n" +
			"    default void afterOnCreate(Bundle savedInstanceState) {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onResume() {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onPause() {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onDestroy() {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    default void onSaveInstanceState(Bundle bundle) {\n" +
			"\n" +
			"    }\n" +
			"    \n" +
			"}";

	private static final String VIEW = "public class %s extends %s<I%s> implements I%s {\n" +
			"\n" +
			"    @Override\n" +
			"    public void afterOnCreate(Bundle savedInstanceState) {\n" +
			"        super.afterOnCreate(savedInstanceState);\n" +
			"        I%s.super.afterOnCreate(savedInstanceState);\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onResume() {\n" +
			"        super.onResume();\n" +
			"        I%s.super.onResume();\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onPause() {\n" +
			"        super.onPause();\n" +
			"        I%s.super.onPause();\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onDestroy() {\n" +
			"        super.onDestroy();\n" +
			"        I%s.super.onDestroy();\n" +
			"    }\n" +
			"\n" +
			"    @Override\n" +
			"    public void onSaveInstanceState(Bundle outState) {\n" +
			"        super.onSaveInstanceState(outState);\n" +
			"        I%s.super.onSaveInstanceState(outState);\n" +
			"    }\n" +
			"\n" +
			"}";

}
