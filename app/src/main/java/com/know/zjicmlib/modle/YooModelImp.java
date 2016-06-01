package com.know.zjicmlib.modle;

import android.util.Log;

import com.know.zjicmlib.APP;
import com.know.zjicmlib.fragment.view.YooView;
import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.modle.bean.Yoo;
import com.know.zjicmlib.util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.framed.Header;

/**
 * Created by know on 2016/4/16.
 */
public class YooModelImp implements YooModel{

    YooView yooView ;

    public YooModelImp(YooView yooView) {
        this.yooView = yooView;
    }

    private String body2str(ResponseBody body){
        String html = "";
        try {
            html = body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    @Override
    public Yoo parseHtml1(ResponseBody body){

        String html = body2str(body);

        Log.e("html lenth", " " + html.length() + "");
        if(html.length()<6000&&html.length()>0){

            //ToastUtil.tShort("用户名或密码错误");
            if(html.length()<4600)
            Log.e("此处因为toast","用户名密码错误");

            //return null;
        }else{
            Document doc = Jsoup.parse(html);

            Element div = doc.getElementsByClass("mylib_msg").first();
            Elements as = div.getElementsByTag("a");
            String overSoon = as.get(0).text();
            String over = as.get(1).text();
            System.out.println(over+"================================="+overSoon);

            Elements tds = doc.getElementsByTag("TD");
            /*for(int i = 0;i<tds.size();i++){
                System.out.println(i+": "+tds.get(i).text());
            }*/

            Yoo yoo = new Yoo();
            String userName = tds.get(1).text().split("：")[1];
            yoo.setCollege(tds.get(18).text().split("：")[1]);
            yoo.setMajor(tds.get(19).text().split("：")[1]);
            yoo.setSex(tds.get(21).text().split("：")[1]);
            yoo.setName(userName);
            yoo.setNickname(yoo.getName());

            yoo.setWarning(over+" "+overSoon);
            //storeYoo(yoo);

            return yoo;
        }


        return null;
    }

    @Override
    public List<Boo> parseHtml2(ResponseBody body) {

        String html = body2str(body);

        Document doc = Jsoup.parse(html);

        Elements tds = doc.getElementsByClass("whitetext");
        Elements inputs = doc.getElementsByTag("input");

        /*for(int i = 0;i<tds.size();i++){
            System.out.println(i+tds.get(i).text());
        }
        System.out.println(tds.size());*/

        if(tds.size()==0){
            System.out.println("人丑就要多读书");
        }
        else {
            int n = tds.size() / 8;

            List<Boo> boos = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                Boo boo = new Boo();
                String[] naa = tds.get(i * 8 + 1).text().split("/");
                //System.out.println("naa00000000000000000000"+naa[1]);
                boo.setId(tds.get(i * 8 ).text());
                //System.out.println("id00000000000000000000"+boo.getId());
                boo.setName(naa[0]);
                //System.out.println("name00000000000000000000"+boo.getName());
                boo.setAuthor(naa[1]);
                boo.setFz(tds.get(i * 8 + 2).text());
                //System.out.println("name00000000000000000000"+boo.getFz());
                boo.setFm(tds.get(i * 8 + 3).text());
                System.out.println("fm00000000000000000000从"+boo.getFz()+"~" + boo.getFm() +" " +n);

                String stringNuber = boo.getFm();


                String[] checks = inputs.get(i).attr("onclick").split("'");
                String check = checks[3];


                boo.setCheck(check);
                System.out.println("name00" + boo.getCheck() + "now is " + System.currentTimeMillis());


                boos.add(boo);
                //System.out.println("boossize"+boos.size());


            }
            return boos;
        }
        return null;
    }

    @Override
    public void storeYoo(Yoo yoo) {

        APP.yoo = yoo;
        APP.mDb.save(yoo);
        System.out.println("stored");
        List<Yoo> yous = APP.mDb.query(Yoo.class);
        for (Yoo yo :yous){
            System.out.println("查看储存结果"+yo.getPassword());
        }

        yooView.onLoginSuccess();

    }


}
