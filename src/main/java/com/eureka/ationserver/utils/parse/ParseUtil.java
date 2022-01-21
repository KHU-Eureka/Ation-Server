package com.eureka.ationserver.utils.parse;

import com.eureka.ationserver.model.insight.Insight;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseUtil {

  public static Parse parse(String task, String url) throws Exception{

    Parse parse = new Parse();

    String crawlingUrl = url;
    Document document = Jsoup.connect(crawlingUrl).get();


    try{
      parse.setTitle(document.select("meta[property=og:title]").first().attr("content"));
    }catch (Exception e){
      parse.setTitle("-");
    }

    try {
      parse.setDescription(document.select("meta[property=og:description]").get(0).attr("content"));
      if(parse.getDescription().length()>255){
        parse.setDescription(parse.getDescription().substring(0,255));
      }

    } catch (Exception e) {
      parse.setDescription("-");
    }


    try {
      parse.setImageUrl(document.select("meta[property=og:image]").get(0).attr("content"));
    } catch (Exception e) {
      parse.setImageUrl(ImageUtil.getDefaultImagePath(task));
    }


    try {
      parse.setSiteName(document.select("meta[property=og:site_name]").get(0).attr("content"));

    } catch (Exception e) {
      parse.setSiteName("-");
    }

    parse.setIcon(ImageUtil.getDefaultImagePath(Insight.INSIGHT_ICON_PREFIX));

    URL connUrl = new URL(url);
    URL faviconUrl = new URL(connUrl.getProtocol()+"://"+connUrl.getHost()+"/favicon.ico");

    try{
      URLConnection conn = faviconUrl.openConnection();

      if(conn.getContentType() != null && conn.getContentType().contains("image")){
        parse.setIcon(faviconUrl.toString());
      }
    } catch (Exception e){

    }


    return parse;
  }
}
