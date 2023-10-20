package com.olp.user_mgmt.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToObjectConverter {
	/**
	 * Convert fron json string to Object
	 * @param <T>
	 * @param json
	 * @param type
	 * @return
	 */
	 public static <T> T getJsonFormString(String json,  Class<T> type) {
		 GsonBuilder gsonBuilder = new GsonBuilder();
		 gsonBuilder.serializeNulls();
		 Gson gson = gsonBuilder.create();
		 return (T) gson.fromJson(json, type);
	 }
}
