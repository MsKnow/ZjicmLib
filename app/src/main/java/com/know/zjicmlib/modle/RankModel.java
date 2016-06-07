package com.know.zjicmlib.modle;

import com.know.zjicmlib.modle.bean.Ranker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by yang on 2016/6/7.
 */
public class RankModel {

    public List<Ranker> getRankList(ResponseBody body){


        List<Ranker> rankerList = new ArrayList<>();
        String html = "";
        try {
            html = body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(html);

        Elements blues = doc.getElementsByClass("blue");

        for(int i = 0;i<blues.size();i++){

            Element blue = blues.get(i);

            String rankRaw = blue.text();

            String word = rankRaw.substring(0, rankRaw.indexOf("("));
            String rank = rankRaw.substring(rankRaw.indexOf("(") + 1, rankRaw.indexOf(")"));
            int rankk = Integer.parseInt(rank);
            System.out.println(word + "-------------->" + rank);

            Ranker ranker = new Ranker();
            ranker.setWord(word);
            ranker.setRank(rankk);

            rankerList.add(ranker);

        }


        return rankerList;

    }

}
