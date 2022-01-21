package com.eureka.ationserver.utils.image;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

  private static String HOST;

  private static String PORT;

  private static String IMAGEPATH;

  @Value("${eureka.app.publicIp}")
  public void setHost(String host){
    HOST = host;
  }

  @Value("${server.port}")
  public void setPort(String port){
    PORT = port;
  }

  @Value("${eureka.app.imagePath}")
  public void setImagePath(String imagePath){
    IMAGEPATH = imagePath;
  }

  public static String getDefaultImagePath(String task){

    String apiUrl = "http://"+HOST+":"+PORT+"/api/image?path=";

    // set file name
    List<String> pathList = new ArrayList<>();

    String fileName = task+".png";
    String imageUrl = apiUrl + IMAGEPATH + fileName;
    return imageUrl;
  }

  public static List<String> getImagePath(String task, Long id){

    String apiUrl = "http://"+HOST+":"+PORT+"/api/image?path=";

    // set file name
    List<String> pathList = new ArrayList<>();

    String fileName = task+"-"+ id +".png";
    String imageUrl = apiUrl+IMAGEPATH+ fileName;

    String path = IMAGEPATH + fileName;
    pathList.add(imageUrl);
    pathList.add(path);
    return pathList;
  }

}
