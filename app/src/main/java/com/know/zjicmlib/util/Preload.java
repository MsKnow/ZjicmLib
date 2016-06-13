package com.know.zjicmlib.util;

import com.google.gson.Gson;
import com.know.zjicmlib.modle.bean.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2016/6/13.
 */
public class Preload {

    static String noticeJson[] ={
            "{title:'欢迎试用雄狮美术知识库', date:'2016-5-31', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D691'}",
            "{title:'欢迎试用中科UMajor大学专业课学习数据库', date:'2016-5-31', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D692'}",
            "{title:'欢迎试用中科VIPExam考试学习数据库', date:'2016-5-31', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D693'}"
            ,"{title:'欢迎试用森途学院-职业能力与创业学习资源总库', date:'2016-5-23', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D685'}"
            ,"{title:'51CTO学院“IT技能基础知识”有奖问答活动', date:'2016-5-9', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D682'}"
            ,"{title:'关于中国典藏古籍库试用的通知', date:'2016-4-28', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D677'}"
            ,"{title:'关于起点考研网试用的通知', date:'2016-4-28', src:'http://218.75.124.139:8091/sms/opac/news/showNewsDetail.action?type=1&xc=4&linkHref=http%3A%2F%2Flib.zjicm.edu.cn%2Fnewsview.aspx%3Fid%3D678'}"
    };

    public static List<Notice> getPreNotice(){

        List<Notice> preNotices = new ArrayList<>();

        for (int i = 0;i<noticeJson.length;i++){
            Gson gson = new Gson();
            Notice notice = gson.fromJson(noticeJson[i], Notice.class);

            preNotices.add(notice);

        }

        return preNotices;
    }

}
