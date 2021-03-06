package com.song.news.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.song.news.base.BaseApplication;
import com.song.news.ui.theme.ColorUiInterface;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class ViewAttributeUtil {

    public static int getAttributeValue(AttributeSet attr, int paramInt) {
        int value = -1;
        int count = attr.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attr.getAttributeNameResource(i) == paramInt) {
                String str = attr.getAttributeValue(i);
                if (null != str && (str.startsWith("?") || paramInt == android.R.attr.src
                        || paramInt == android.R.attr.background)) {
                    //因为要动态改图片，图片没有用自定义属性
                    value = Integer.valueOf(str.substring(1, str.length())).intValue();
                    return value;
                }
            }
        }
        return value;
    }

    public static int getBackgroundAttibute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.background);
    }

    public static int getCheckMarkAttribute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.checkMark);
    }

    public static int getSrcAttribute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.src);
    }

    public static int getTextApperanceAttribute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.textAppearance);
    }

    public static int getDrawableTopAttribute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.drawableTop);
    }

    public static int getDividerAttribute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.divider);
    }

    public static int getTextColorAttribute(AttributeSet attr) {
        return getAttributeValue(attr, android.R.attr.textColor);
    }

    public static void applyBackgroundDrawable(ColorUiInterface ci, Resources.Theme theme, int paramInt) {
        TypedArray ta = theme.obtainStyledAttributes(new int[]{paramInt});
        Drawable drawable = ta.getDrawable(0);
        if (null != ci) {
            (ci.getView()).setBackgroundDrawable(drawable);
        }
        ta.recycle();
    }

    public static void applyTextAppearance(ColorUiInterface ci, Resources.Theme theme, int paramInt) {
        TypedArray ta = theme.obtainStyledAttributes(new int[]{paramInt});
        int resourceId = ta.getResourceId(0, 0);
        if (null != ci && ci instanceof TextView) {
            ((TextView) ci.getView()).setTextAppearance(ci.getView().getContext(), resourceId);
        }
        ta.recycle();
    }

    public static void applyTextColor(ColorUiInterface ci, Resources.Theme theme, int paramInt) {
        TypedArray ta = theme.obtainStyledAttributes(new int[]{paramInt});
        int resourceId = ta.getColor(0, 0);
        if (null != ci && ci instanceof TextView) {
            ((TextView) ci.getView()).setTextColor(resourceId);
        }
        ta.recycle();
    }

    public static String createDrawableName(String drawableName) {
        String newDrawableName = "";
        if (isLightTheme()) {
            //日间模式
            if (drawableName.endsWith("_night")) {
                //之前是夜间 ，去掉后缀
                newDrawableName = drawableName.split("_")[0];
            } else {
                newDrawableName = drawableName;
            }
        } else {
            //夜间模式
            if (drawableName.endsWith("_night")) {
                newDrawableName = drawableName;
            } else {
                newDrawableName = drawableName + "_night";
            }
        }
        return newDrawableName;

    }

    public static boolean isLightTheme() {
        return SharedPreferencesUtil.getInt(Consts.SP_THEME,
                Consts.THEME_LIGHT) == Consts.THEME_LIGHT;
    }
    public static int createResource(Resources res, int resId) {
        String resourceName = res.getResourceName(resId);
        if (resourceName.contains("drawable")) {
            //是drawable
            String drawableName = resourceName.substring(resourceName.lastIndexOf("/") + 1);
            String newDrawableName = createDrawableName(drawableName);
            int newResId = res.getIdentifier(newDrawableName, "drawable",
                    BaseApplication.getInstance().getPackageName());
            return newResId;
        }
        return 0;
    }
}
