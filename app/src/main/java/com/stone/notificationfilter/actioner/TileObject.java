package com.stone.notificationfilter.actioner;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by LingC on 2019/8/6 16:06
 */
public class TileObject {
 private final  static  String TAG = "TileObject";
    public static int showTileNum = 0;
    public static  int mMostShowTitleNum =0;

    public static FloatingTileActioner lastFloatingTile;

    public static List<FloatingTileActioner> showingFloatingTileList = new ArrayList<>();
    public static int currentPosition=0;
    // key: Tile的y, value: Tile是否在显示
    // 数量一般为指定数量
    public static List<Boolean> positionArray = new ArrayList<>();

    public static List<FloatingTileActioner> waitingForShowingTileList = new ArrayList<>();



    public static int getNextPosition() {
        for (int i = 0; i <positionArray.size() ; i++) {
            if(currentPosition==mMostShowTitleNum || showTileNum ==0){
                currentPosition=0;
            }
            if(!positionArray.get(currentPosition)){
                positionArray.set(currentPosition,true);
                showTileNum++;
//                currentPosition++;
                return currentPosition;
            }
            currentPosition++;


        }
        return -1;
    }

    public static  void setMostShowTitleNum(int mostShowTitleNum){
        for (;mMostShowTitleNum < mostShowTitleNum; mMostShowTitleNum++){
            positionArray.add(mMostShowTitleNum,false);
        }

    }
    public static void removeSingleShowingTile(FloatingTileActioner floatingTileActioner){
        int showID = floatingTileActioner.showID;
        Log.e(TAG,String.valueOf(showID));
        positionArray.set(showID,false);
        showingFloatingTileList.remove(floatingTileActioner);
        showTileNum--;
        showWaitingTile();
    }

    public static void clearShowingTile() {
        showTileNum = 0;

        for (int i = 0; i < showingFloatingTileList.size();) {
            showingFloatingTileList.get(i).removeTile();
        }
//        for (FloatingTileActioner floatingTile : showingFloatingTileList) {
//            floatingTile.removeTile();
//        }
        showingFloatingTileList.clear();
//        for (int i = 0; i < positionArray.size(); i++) {
//            int y = positionArray.keyAt(i);
//            positionArray.put(y, false);
//        }
    }

    public static  void showWaitingTile() {
        if (!TileObject.waitingForShowingTileList.isEmpty()) {
            FloatingTileActioner floatingTile = TileObject.waitingForShowingTileList.get(0);
            floatingTile.addViewToWindow();
            TileObject.waitingForShowingTileList.remove(floatingTile);
        }
    }

    public static void clearAllTile() {
        waitingForShowingTileList.clear();
        // 待显示列表必须在这之前清除，否则会触发显示机会事件
        clearShowingTile();
    }
    public static void getDisplayInform (){
    }


}
