package com.know.zjicmlib.modle;

import com.know.zjicmlib.modle.bean.Notice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by know on 2016/5/26.
 */
public class NoticesModel {

    public List<Notice> parseHtml(ResponseBody body){
        List<Notice> notices = new ArrayList<>();
        String html = "";
        try {
            html = body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(html);

        Elements as = doc.getElementsByTag("a");

        for (int i = 0;i<as.size();i++){
            Notice notice = new Notice();
            Element a = as.get(i);

            String src = a.attr("href");
            notice.setSrc(src);
            String title = a.child(0).html();
            notice.setTitle(title);
            String date = a.child(1).text();
            notice.setDate(date);

            notices.add(notice);

            //int id = src.substring(src.length()-3);

        }

        return notices;

    }



}
