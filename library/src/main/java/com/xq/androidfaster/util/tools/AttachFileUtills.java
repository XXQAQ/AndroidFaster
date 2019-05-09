package com.xq.androidfaster.util.tools;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import com.xq.androidfaster.R;
import java.io.File;
import static com.xq.androidfaster.AndroidFaster.getApp;
import static com.xq.androidfaster.AndroidFaster.getFileProvider;

public final class AttachFileUtills {

    private AttachFileUtills() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     *指明路径可一键调用相关应用打开该文件
     * @param path 路径
     */
    public static void startActivity(String path) {

        Intent intent = null;

        File file = new File(path);

        if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingPdf)))
            intent = getPdfFileIntent(file);
        else  if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingWord)))
            intent = getWordFileIntent(file);
        else  if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingExcel)))
            intent = getExcelFileIntent(file);
        else  if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingPPT)))
            intent = getPPTFileIntent(file);
        else  if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingText)))
            intent = getTextFileIntent(file);
        else  if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingImage)))
            intent = getImageFileIntent(file);
        else    if (checkExtensions(path, getApp().getResources().getStringArray(R.array.fileEndingVideo)))
            intent = getVideoFileIntent(file);
        else    if (checkExtensions(path,getApp().getResources().getStringArray(R.array.fileEndingAudio)))
            intent = getAudioFileIntent( file);
        else    if (checkExtensions(path,new String[]{".chm"}))
            intent = getChmFileIntent(file);
        else    if (checkExtensions(path,new String[]{".apk"}))
            intent = getApkFileIntent(file);
        else    if (checkExtensions(path,new String[]{".html"}))
            intent = getHtmlFileIntent(file);

        if (intent != null && isIntentAvaileble(intent)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getApp().startActivity(intent);
        }
        else
            Toast.makeText(getApp(),"Can't open the file",Toast.LENGTH_SHORT).show();
    }

    /**
     * @param file
     * @return
     */
    private static Intent getHtmlFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        uri = uri.buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");

        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * @param file
     * @return
     */
    private static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(getApp(), getFileProvider(), file);
        else
            uri = Uri.fromFile(file);
        intent.setDataAndType(uri,  "application/vnd.android.package-archive");
        return intent;
    }

    public static boolean checkExtensions(String checkItsEnd,String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private static boolean isIntentAvaileble(Intent intent){
        return IntentUtils.isIntentAvailable(intent);
    }

}
