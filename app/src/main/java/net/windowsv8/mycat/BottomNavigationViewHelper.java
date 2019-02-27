package net.windowsv8.mycat;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Windowsv8 on 22/11/2017.
 */

public class BottomNavigationViewHelper {

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try{
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for(int i=0; i<menuView.getChildCount();i++){
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (IllegalAccessException e) {
            Log.e("BNVHELPER : ", "Unable to get shift mode Field", e);
            //e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Log.e("BNVHELPER : ", "Unable To Change Value of Shift Mode", e);
            //e.printStackTrace();
        }
    }
}
