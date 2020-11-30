package com.stone.notificationfilter.notificationhandler.databases;

import android.content.Context;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.stone.notificationfilter.util.SpUtil;
import com.stone.notificationfilter.util.filestore.Crypter;
//import com.tencent.mmkv.MMKV;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class JsonData {
  /**
   *
   */
  private  final String saveKey ;
  private  final String prencesName;
  private final Context context;
  private HashMap<String, NotificationHandlerItem> storageMap = new HashMap<>();
//    private Set<NotificationHandlerItem> storageMap;
//    private Set<Str>

  private final boolean autosave;

  public JsonData(Context context,String prencesName, String saveKey, boolean autosave) {
    this.context = context;
    this.prencesName = prencesName;
    this.saveKey = prencesName;
    this.autosave = autosave;
    load();
  }


  /**
   * Stores the FileStorage in the file on disk
   */
  public void save() throws IOException {

    if (storageMap == null || storageMap.size()==0){
      return ;
    }
    String json = "";
    try {
      Gson gson = new GsonBuilder().serializeNulls().create();
      json = gson.toJson(storageMap);
    }catch (JsonIOException e)
    {
      e.printStackTrace();
      return;
    }
//        kv.encode(saveKey,json);
    SpUtil.putString(context,prencesName,saveKey,json);
  }

  /**
   * Loads the FileStorage from the file
   */
  @SuppressWarnings("unchecked")
  private void load()  {
    String content = SpUtil.getString(context,prencesName,saveKey,"");
//        String content = kv.decodeString(saveKey,"");
    if (content.length() !=0){
      storageMap = new Gson().fromJson(content, new TypeToken<HashMap<String,NotificationHandlerItem>>(){}.getType());
    }
  }

  /**
   * Stores an Object <i>o</i> using a String <i>key</i> for later identification.<br>
   * Use <code>store(String key, Object o, String password)</code> for storing your data using AES
   * encryption.
   *
   * @param key The key as String.
   * @param o The Object.
   */
  public void store(String key, NotificationHandlerItem o) throws IOException {
    storageMap.put(key, o);
    if (autosave) {
      save();
    }
  }

  /**
   * Stores an Object <i>o</i> using a String <i>key</i> for later identification.<br>
   * Use <code>store(String key, Object o)</code> for storing your data without encryption.
   *
   * @param key The key as String.
   * @param o The Object.
   * @param password The password.
   */
//  public void store(String key, T o, String password) throws IOException {
//    store(key, Crypter.encrypt(o, password));
//  }

  /**
   * Reads your object from the storage.<br>
   * <br>
   * Use <u>get(String key, String password)</u> for AES encrypted objects.
   *
   * @param key The key the object is available under
   * @return your Object or null if nothing was found for <i>key</i>
   */
  public NotificationHandlerItem get(String key) {
    return storageMap.get(key);
  }

  /**
   * Reads your AES encrypted object from the storage.<br>
   * <br>
   * Use <u>get(String key)</u> instead for unencrypted objects.
   *
   * @param key The key the object is available under
   * @param password The password to use for decryption
   * @return your object or null if nothing was found for <i>key</i> or if decryption failed (wrong
   *         password)
   * @throws Crypter.DecryptionFailedException This usually happens if the password is wrong
   */
//  public T get(String key, String password) throws DecryptionFailedException {
//    if (storageMap.get(key) instanceof CryptedObject) {
//      return Crypter.decrypt((CryptedObject) get(key), password);
//    } else {
//      return get(key);
//    }
//  }

  /**
   * All stored objects in an ArrayList of Objects
   *
   * @return all stored objects in an ArrayList of Objects
   */
  public ArrayList<NotificationHandlerItem> getAllAsArrayList() {
    ArrayList<NotificationHandlerItem> result = new ArrayList<>(storageMap.values());
    result.sort((n1, n2) ->  n2.orderID - n1.orderID);
    return result;
  }

  /**
   * All stored objects in a HashMap of Strings and Objects
   *
   * @return all stored objects in a HashMap of Strings and Objects
   */
  public HashMap<String, NotificationHandlerItem> getAll() {
    return storageMap;
  }

  /**
   * Prints all stored keys with corresponding objects
   */
  public void printAll() {
    System.out.println(this);
  }

  /**
   * Removes an Key-Object pair from the storage
   *
   * @param key The key of the object
   */
  public void remove(String key) throws IOException {
    storageMap.remove(key);
    if (autosave) {
      save();
    }
  }

  /**
   * Checks whether a key is registerd
   *
   * @param key The Key.
   * @return true if an object is available for that key
   */
  public boolean hasKey(String key) {
    return storageMap.containsKey(key);
  }

  /**
   * Checks whether an object is stored at all
   *
   * @param o The Object.
   * @return true if the object is stored
   */
  public boolean hasObject(NotificationHandlerItem o) {
    return storageMap.containsValue(o);
  }

  /**
   * Returns the number of objects (elements) stored
   *
   * @return The number of objects (elements) stored
   */
  public int getSize() {
    return storageMap.size();
  }

  /**
   * @return a String representation of the HashMap containing all the key-object pairs.
   */
  @Override
  public String toString() {
    String result = "FileStorage @ "  + "\n";
    for (String cKey : storageMap.keySet()) {
      result += cKey + " :: " + storageMap.get(cKey) + "\n";
    }
    return result.trim();
  }
}