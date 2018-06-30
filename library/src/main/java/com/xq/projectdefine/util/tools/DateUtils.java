/*
 *   Copyright (C)  2016 android@19code.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.xq.projectdefine.util.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Deprecated
public final class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");

    public static String formatDataTime(long date) {
        return DATE_FORMAT_DATETIME.format(new Date(date));
    }

    public static String formatDate(long date) {
        return DATE_FORMAT_DATE.format(new Date(date));
    }

    public static String formatTime(long date) {
        return DATE_FORMAT_TIME.format(new Date(date));
    }

    public static String formatTimeForHMSWatch(long date) {
        String[] array = formatTimeForForWatchArray(date);
        return array[0] + ":" + array[1] + ":" + array[2];
    }

    public static String formatTimeForHMWatch(long date) {
        String[] array = formatTimeForForWatchArray(date);
        return array[0] + ":" + array[1];
    }

    public static String formatTimeForHMWatchChinese(long date) {
        String[] array = formatTimeForForWatchArray(date);
        if (array[0].equals("00"))
            return array[1]+"分";
        else
            return array[0] + "时" + array[1]+"分";
    }

    public static String formatTimeForMSWatch(long date) {
        String[] array = formatTimeForForWatchArray(date);
        return array[1] + ":" + array[2];
    }

    private static String[] formatTimeForForWatchArray(long date) {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        formatTime.setTimeZone(java.util.TimeZone.getTimeZone("GMT+0"));
        String str_time = formatTime.format(new Date(date));
        String[] array = str_time.split(":");
        return array;
    }

    public static String formatDateCustom(String beginDate, String format) {
        return new SimpleDateFormat(format).format(new Date(Long.parseLong(beginDate)));
    }

    public static String formatDateCustom(Date beginDate, String format) {
        return new SimpleDateFormat(format).format(beginDate);
    }

    public static Date string2Date(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 6) {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    public static String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
    }

    public static String getDateTime() {
        return DATE_FORMAT_DATETIME.format(System.currentTimeMillis());
    }

    public static String getDateTime(String format) {
        return new SimpleDateFormat(format).format(System.currentTimeMillis());
    }

    public static long subtractDate(Date dateStart, Date dateEnd) {
        return dateEnd.getTime() - dateStart.getTime();
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }
}
