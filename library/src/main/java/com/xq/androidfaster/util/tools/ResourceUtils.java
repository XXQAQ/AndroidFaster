package com.xq.androidfaster.util.tools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FractionRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.xq.androidfaster.AndroidFaster.getApp;

public final class ResourceUtils {

    private static final int BUFFER_SIZE = 8192;

    private ResourceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return boolean.
     *
     * @param id The resource id.
     * @return boolean
     */
    public static boolean getBoolean(@BoolRes int id) {
        return getApp().getResources().getBoolean(id);
    }

    /**
     * Return Fractio.
     *
     * @param id The resource id.
     * @return Fractio
     */
    public static float getFraction(@FractionRes int id) {
        return getApp().getResources().getFraction(id,1,1);
    }

    /**
     * Return Integer.
     *
     * @param id The resource id.
     * @return Integer
     */
    public static int getInteger(@IntegerRes int id) {
        return getApp().getResources().getInteger(id);
    }

    /**
     * Return IntArray.
     *
     * @param id The resource id.
     * @return IntArray
     */
    public static int[] getIntArray(@ArrayRes int id) {
        return getApp().getResources().getIntArray(id);
    }

    /**
     * Return Dimension.
     *
     * @param id The resource id.
     * @return Dimension
     */
    public static float getDimension(@DimenRes int id) {
        return getApp().getResources().getDimension(id);
    }

    /**
     * Return Color.
     *
     * @param id The resource id.
     * @return Color
     */
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getApp(), id);
    }

    /**
     * Return String.
     *
     * @param id The resource id.
     * @return String
     */
    public static String getString(@StringRes int id) {
        try {
            return getApp().getResources().getString(id);
        } catch (Resources.NotFoundException ignore) {
            return "";
        }
    }

    /**
     * Return String.
     *
     * @param id The resource id.
     * @param formatArgs The format.
     * @return String
     */
    public static String getString(@StringRes int id, Object... formatArgs) {
        try {
            return getApp().getString(id, formatArgs);
        } catch (Resources.NotFoundException ignore) {
            return "";
        }
    }

    /**
     * Return StringArray.
     *
     * @param id The resource id.
     * @return StringArray
     */
    public static String[] getStringArray(@ArrayRes int id) {
        try {
            return getApp().getResources().getStringArray(id);
        } catch (Resources.NotFoundException ignore) {
            return new String[0];
        }
    }

    /**
     * Return Drawable.
     *
     * @param resId The resource id.
     * @return Drawable
     */
    public static Drawable getDrawable(@DrawableRes final int resId) {
        return ContextCompat.getDrawable(getApp(), resId);
    }

    /**
     * Return Bitmap.
     *
     * @param resId The resource id.
     * @return Bitmap
     */
    public static Bitmap getBitmap(@DrawableRes final int resId) {
        Drawable drawable = ContextCompat.getDrawable(getApp(), resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 以下均为通过资源name获取资源id的方法
     *
     */
    public static int getIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "id", getApp().getPackageName());
    }

    public static int getStringIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "string", getApp().getPackageName());
    }

    public static int getColorIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "color", getApp().getPackageName());
    }

    public static int getDimenIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "dimen", getApp().getPackageName());
    }

    public static int getDrawableIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "drawable", getApp().getPackageName());
    }

    public static int getMipmapIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "mipmap", getApp().getPackageName());
    }

    public static int getLayoutIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "layout", getApp().getPackageName());
    }

    public static int getStyleIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "style", getApp().getPackageName());
    }

    public static int getAnimIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "anim", getApp().getPackageName());
    }

    public static int getAnimatorIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "animator", getApp().getPackageName());
    }

    public static int getMenuIdByName(String name) {
        return getApp().getResources().getIdentifier(name, "menu", getApp().getPackageName());
    }


    /**
     * Copy the file from assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param destFilePath   The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromAssets(final String assetsFilePath, final String destFilePath) {
        boolean res = true;
        try {
            String[] assets = getApp().getAssets().list(assetsFilePath);
            if (assets.length > 0) {
                for (String asset : assets) {
                    res &= copyFileFromAssets(assetsFilePath + "/" + asset, destFilePath + "/" + asset);
                }
            } else {
                res = writeFileFromIS(
                        destFilePath,
                        getApp().getAssets().open(assetsFilePath),
                        false
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @return the content of assets
     */
    public static String readAssets2String(final String assetsFilePath) {
        return readAssets2String(assetsFilePath, null);
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param charsetName    The name of charset.
     * @return the content of assets
     */
    public static String readAssets2String(final String assetsFilePath, final String charsetName) {
        InputStream is;
        try {
            is = getApp().getAssets().open(assetsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes = is2Bytes(is);
        if (bytes == null) return null;
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath The path of file in assets.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(final String assetsPath) {
        return readAssets2List(assetsPath, null);
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath  The path of file in assets.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(final String assetsPath,
                                               final String charsetName) {
        try {
            return is2List(getApp().getResources().getAssets().open(assetsPath), charsetName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Copy the file from raw.
     *
     * @param resId        The resource id.
     * @param destFilePath The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromRaw(@RawRes final int resId, final String destFilePath) {
        return writeFileFromIS(
                destFilePath,
                getApp().getResources().openRawResource(resId),
                false
        );
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of resource in raw
     */
    public static String readRaw2String(@RawRes final int resId) {
        return readRaw2String(resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of resource in raw
     */
    public static String readRaw2String(@RawRes final int resId, final String charsetName) {
        InputStream is = getApp().getResources().openRawResource(resId);
        byte[] bytes = is2Bytes(is);
        if (bytes == null) return null;
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(@RawRes final int resId) {
        return readRaw2List(resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(@RawRes final int resId,
                                            final String charsetName) {
        return is2List(getApp().getResources().openRawResource(resId), charsetName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static boolean writeFileFromIS(final String filePath,
                                           final InputStream is,
                                           final boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    private static boolean writeFileFromIS(final File file,
                                           final InputStream is,
                                           final boolean append) {
        if (!createOrExistsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(data, 0, BUFFER_SIZE)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static byte[] is2Bytes(final InputStream is) {
        if (is == null) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> is2List(final InputStream is,
                                        final String charsetName) {
        BufferedReader reader = null;
        try {
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(is));
            } else {
                reader = new BufferedReader(new InputStreamReader(is, charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
