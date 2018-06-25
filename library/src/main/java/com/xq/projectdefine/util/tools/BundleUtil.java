package com.xq.projectdefine.util.tools;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public final class BundleUtil {

    public static final String KEY_DATA = "data";
    public static final String KEY_TYPE = "type";
    public static final String KEY_RESULT = "result";
    public static final String KEY_REFRESH = "refresh";
    public static final String KEY_LOADMORE = "loadmore";

    public static class Builder {

        private Bundle bundle = new Bundle();

        public Builder putAll(Bundle bundle) {
            bundle.putAll(bundle);
            return this;
        }

        public Builder putByte(String key, byte value) {
            bundle.putByte(key, value);
            return this;
        }

        public Builder putChar(String key, char value) {
            bundle.putChar(key, value);
            return this;
        }

        public Builder putShort(String key, short value) {
            bundle.putShort(key, value);
            return this;
        }

        public Builder putFloat(String key, float value) {
            bundle.putFloat(key, value);
            return this;
        }

        public Builder putCharSequence(String key, CharSequence value) {
            bundle.putCharSequence(key, value);
            return this;
        }

        public Builder putParcelable(String key, Parcelable value) {
            bundle.putParcelable(key, value);
            return this;
        }

        public Builder putSize(String key, Size value) {
            bundle.putSize(key, value);
            return this;
        }

        public Builder putSizeF(String key, SizeF value) {
            bundle.putSizeF(key, value);
            return this;
        }

        public Builder putParcelableArray(String key, Parcelable[] value) {
            bundle.putParcelableArray(key, value);
            return this;
        }

        public Builder putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
            bundle.putParcelableArrayList(key, value);
            return this;
        }

        public Builder putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
            bundle.putSparseParcelableArray(key, value);
            return this;
        }

        public Builder putIntegerArrayList(String key, ArrayList<Integer> value) {
            bundle.putIntegerArrayList(key, value);
            return this;
        }

        public Builder putStringArrayList(String key, ArrayList<String> value) {
            bundle.putStringArrayList(key, value);
            return this;
        }

        public Builder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
            bundle.putCharSequenceArrayList(key, value);
            return this;
        }

        public Builder putSerializable(String key, Serializable value) {
            bundle.putSerializable(key, value);
            return this;
        }

        public Builder putByteArray(String key, byte[] value) {
            bundle.putByteArray(key, value);
            return this;
        }

        public Builder putShortArray(String key, short[] value) {
            bundle.putShortArray(key, value);
            return this;
        }

        public Builder putCharArray(String key, char[] value) {
            bundle.putCharArray(key, value);
            return this;
        }

        public Builder putFloatArray(String key, float[] value) {
            bundle.putFloatArray(key, value);
            return this;
        }

        public Builder putCharSequenceArray(String key, CharSequence[] value) {
            bundle.putCharSequenceArray(key, value);
            return this;
        }

        public Builder putBundle(String key, Bundle value) {
            bundle.putBundle(key, value);
            return this;
        }

        public Builder putBinder(String key, IBinder value) {
            bundle.putBinder(key, value);
            return this;
        }

        public Builder putAll(PersistableBundle bundle) {
            bundle.putAll(bundle);
            return this;
        }

        public Builder putBoolean(String key, boolean value) {
            bundle.putBoolean(key, value);
            return this;
        }

        public Builder putInt(String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public Builder putLong(String key, long value) {
            bundle.putLong(key, value);
            return this;
        }

        public Builder putDouble(String key, double value) {
            bundle.putDouble(key, value);
            return this;
        }

        public Builder putString(String key, String value) {
            bundle.putString(key, value);
            return this;
        }

        public Builder putBooleanArray(String key, boolean[] value) {
            bundle.putBooleanArray(key, value);
            return this;
        }

        public Builder putIntArray(String key, int[] value) {
            bundle.putIntArray(key, value);
            return this;
        }

        public Builder putLongArray(String key, long[] value) {
            bundle.putLongArray(key, value);
            return this;
        }

        public Builder putDoubleArray(String key, double[] value) {
            bundle.putDoubleArray(key, value);
            return this;
        }

        public Builder putStringArray(String key, String[] value) {
            bundle.putStringArray(key, value);
            return this;
        }

        public Bundle build() {
            return bundle;
        }
    }
}
