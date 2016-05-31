package com.know.zjicmlib.modle;

import android.util.Log;

import com.know.zjicmlib.activity.view.OneBoosView;
import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by know on 2016/4/15.
 */
public class OneBoosModelImp implements OneBoosModel{

    OneBoosView oneBoosView;

    public OneBoosModelImp(OneBoosView oneBoosView) {
        this.oneBoosView = oneBoosView;
    }

    @Override
    public String parseHtml(String html) {



        Document doc = Jsoup.parse(html);
        Elements trs = doc.select("tr.whitetext");



        int n = trs.size();
        System.out.println(n);

        if(n == 0){
            Log.e("oneboos","meiyou?");
            ToastUtil.tShort("没找到 - -!");
        }else{

            List<Boo> boos = new ArrayList<>();

            for(int i = 0 ;i<n;i++){
                Boo boo = new Boo();

                Elements tds = trs.get(i).getElementsByTag("td");

                boo.setId(tds.get(1).text());
                System.out.println("第" + i + "本shu " + boo.getId());

                boo.setFz(tds.get(3).text());
                boo.setFm(tds.get(4).text());
                System.out.println("88888888888" + boo.getFz()+" "+boo.getFm());

                boos.add(boo);
            }

            oneBoosView.setOneBoos(boos);

            int start = html.indexOf("isbn=");
            int end = html.indexOf('"', start);

            System.out.println(start+"zuo you"+end);

            String isbn = html.substring(start+5,end);

            Log.e("isbn",isbn);

            return isbn;

        }


        return "";

    }
}
