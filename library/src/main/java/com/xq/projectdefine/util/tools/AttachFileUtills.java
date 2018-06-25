package com.xq.projectdefine.util.tools;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import com.xq.projectdefine.FasterInterface;
import com.xq.projectdefine.R;

import java.io.File;

//指明文件路径可一键调起相应应用
public final class AttachFileUtills {

    private Context context;
    private String path ;

    public AttachFileUtills(Context context, String path) {
        this.context = context;
        this.path = path;
    }

    public Intent getHtmlFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        uri = uri.buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    public Intent getImageFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    public Intent getPdfFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public Intent getTextFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    public Intent getAudioFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    public Intent getVideoFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "video/*");

        return intent;
    }

    public Intent getChmFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    public Intent getWordFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    public Intent getExcelFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    public Intent getPPTFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    public Intent getApkFileIntent(File file)
    {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, getFileProvider(), file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri,  "application/vnd.android.package-archive");
        return intent;
    }

    public void startActivity() {

        Intent intent = null;

        File file = new File(path);

        if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingPdf)))
        {
            intent = getPdfFileIntent(file);
        }
        else  if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingWord)))
        {
            intent = getWordFileIntent(file);
        }
        else  if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingExcel)))
        {
            intent = getExcelFileIntent(file);
        }
        else  if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingPPT)))
        {
            intent = getPPTFileIntent(file);
        }
        else  if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingText)))
        {
            intent = getTextFileIntent(file);
        }
        else  if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingImage)))
        {
            intent = getImageFileIntent(file);
        }
        else    if (checkExtensions(path, context.getResources().getStringArray(R.array.fileEndingVideo)))
        {
            intent = getVideoFileIntent(file);
        }
        else    if (checkExtensions(path,context.getResources().getStringArray(R.array.fileEndingAudio)))
        {
            intent = getAudioFileIntent( file);
        }
        else    if (checkExtensions(path,new String[]{".chm"}))
        {
            intent = getChmFileIntent(file);
        }
        else    if (checkExtensions(path,new String[]{".apk"}))
        {
            intent = getApkFileIntent(file);
        }
        else    if (checkExtensions(path,new String[]{".html"}))
        {
            intent = getHtmlFileIntent(file);
        }

        if (intent != null)
            context.startActivity(intent);
        else
            Toast.makeText(context,"不支持的文件类型",Toast.LENGTH_SHORT).show();

    }

    public static boolean checkExtensions(String checkItsEnd,String[] fileEndings) {
        for (String aEnd : fileEndings)
        {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private String getFileProvider(){
        return FasterInterface.getFileProvider();
    }
}
