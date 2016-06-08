package com.know.zjicmlib.modle;

import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.retrofit.ServiceFactory;
import com.know.zjicmlib.util.OkHttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/4/12.
 */
public class SearchModel  {


    private static String getHtml(String url){
        String str = null;
        try {
            str = OkHttpUtil.getString(url);
        } catch (IOException e) {
            e.printStackTrace();
    }
        return str;
    }

    public static void searchBoos(String booN,int page, Subscriber<List<Boo>> subscriber){
        ServiceFactory.getService().searchBooss(booN, page)
                .map(s -> {
                    String html = "eee";
                    try {
                        html = s.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return parseHtml(html);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static List<Boo> parseHtml(String html){
        List<Boo> boos = new ArrayList<>();


        Document doc = Jsoup.parse(html);
        Element ol  = doc.getElementById("search_book_list");

        if(ol==null){
            System.out.println("没有找到你要的书");
            throw new RuntimeException();
            //Toast.makeText(getApplicationContext(), "没有找到...", Toast.LENGTH_LONG).show();

        }else{
            Elements lis   = ol.getElementsByClass("book_list_info");
            boos  = new ArrayList<>();
            for(int i=0;i<lis.size();i++){
                Element  li = lis.get(i);

                Elements h3s = 	li.getElementsByTag("h3");
                Element h3 = h3s.get(0);

                //Elements ps  =  li.getElementsByTag("p");
                Element  p	 =  li.getElementsByTag("p").get(0);



                String 	book123  	= h3.text();
                String  author123	= p.text();

                ////////////author   fzm
                //String 	fzm		    = p.getElementsByTag("span").get(0).text();

                String[] author6 	= author123.split(" ");

                int 	fmmaoid 		= author6[0].indexOf("：");
                String 	bookfm 		= author6[0].substring(fmmaoid+1);
                int 	fzmaoid 		= author6[1].indexOf("：");
                String 	bookfz 		= author6[1].substring(fzmaoid+1);

                String author = author6[2];

                for(int k = 0;k<author6.length-6;k++){
                    author  += " "+author6[k+3];
                }


                ///////////获取name123中。。。。
                String 	bookname 	= h3.getElementsByTag("a").get(0).text();

                int  	backspaceid = book123.lastIndexOf(" ");
                String  bookId   	= book123.substring(backspaceid+1, book123.length());

                System.out.println(bookname);
                //System.out.println(book123);
                System.out.println(bookId);
                System.out.println(author123);

                System.out.println(author6.length+"个");
                for(int j=0;j<author6.length;j++){
                    System.out.println(author6[j]);
                }

                System.out.println("可借/馆藏:"+bookfz+"/"+bookfm+author);

                System.out.println();


                //获取bookidd
                Element a = lis.get(i).select("a").first();
                String check = a.attr("href");
                System.out.println(check+"is check");

                Boo boo = new Boo();
                boo.setName(bookname);
                boo.setId(bookId);
                boo.setFm(bookfm);
                boo.setFz(bookfz);
                boo.setAuthor(author);
                boo.setCheck(check);

                boos.add(boo);


                //fzm

            }


        }


        return boos;
    }

}
