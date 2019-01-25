/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ccooy.gankio.utils.encrypt;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Json Serializer/Deserializer.
 */
@Singleton
public class Serializer {

  private final Gson gson = new Gson();

  @Inject
  Serializer() {
  }

  /**
   * Serialize an object to Json.
   *
   * @param object to serialize.
   */
  public String serialize(Object object) {
    return gson.toJson(object);
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param string A json string to deserialize.
   */
  public <T> T deserialize(String string, Class<T> clazz) {
    T t = gson.fromJson(string, clazz);
    if(t == null){
      try {
        t = clazz.newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return t;
  }

  /**
   * @return 一个不能再添加元素的list集合
   */
  public <T> List<T> deserializeList(String string, Class<? extends T[]> clazz) {
    if(string == null){
      return null;
    }
    if(string.equals("")){
      return null;
    }
    ArrayList<T> list = new ArrayList<>();
    T[] ts = gson.fromJson(string, clazz);
    list.addAll(Arrays.asList(ts));
    return list;
  }
}
