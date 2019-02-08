package com.example.nguyensang.anime_online_official.Customclass;

import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by NguyenSang on 04/23/2018.
 */

public class DataBaseWeb {
    public String getWebsite(String website){
        String resString = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpClient =new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(website);
        try
        {
            HttpResponse response;
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            resString = builder.toString();
            is.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return resString;
    }

    public Phim LayDanhSachPhim(Element doc) {
        Phim a = new Phim();
        a.setLink(doc.getElementsByTag("a").get(0).attr("href"));
        a.setHinhAnh(doc.getElementsByTag("a").get(1).getElementsByTag("img").get(0).attr("src").replace("&amp;", "&"));
        a.setSoTapHienCo(doc.getElementsByTag("a").get(1).getElementsByTag("span").get(0).text());
        a.setTenPhim(doc.getElementsByTag("a").get(1).getElementsByTag("span").get(1).text());
        a.setNoiDungPhim(doc.getElementsByTag("a").get(1).getElementsByClass("ah-des-hover").text());

        return a;
    }

    public  ArrayList<Phim> LayBangXepHangNgay(String document , int soLuongPhim) {
        ArrayList<Phim> ds = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(document);
            Elements div = doc.getElementsByClass("ah-widget-rank ah-box-widget ah-clear-both");
            Elements lies = div.get(0).getElementsByTag("li");
            for (int i = 0; i < lies.size(); i++) {
                Phim a = new Phim();
                a.setHinhAnh(lies.get(i).getElementsByTag("img").attr("src").replace("&amp;", "&"));
                a.setTenPhim(lies.get(i).getElementsByTag("a").get(0).text());
                a.setSoLuotXem(lies.get(i).getElementsByClass("ah-float-left w-70").get(0).getElementsByTag("div").get(2).text().toString());
                a.setLink(lies.get(i).getElementsByTag("a").get(0).attr("href"));
                ds.add(a);
                if(ds.size() == soLuongPhim){
                    break;
                }
            }
        } catch (Exception e) {
            return ds;
        }
        return ds;
    }

    public ArrayList<Phim> LayTapMoiCapNhat(String document, int soLuongPhim) {
        ArrayList<Phim> ds = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(document);
            Elements div = doc.getElementsByClass("ah-widget-nepisodes ah-box-widget ah-clear-both");
            Elements lies = div.get(0).getElementsByTag("li");
            for (int i = 0; i < lies.size() - 1; i++) {
                Phim a = new Phim();
                a.setLink(lies.get(i).getElementsByTag("a").attr("href"));
                a.setTenPhim(lies.get(i).getElementsByTag("span").get(0).text());
                a.setSoTapHienCo(lies.get(i).getElementsByTag("span").get(1).text());
                ds.add(a);
                if(ds.size() == soLuongPhim){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public ArrayList<String> LayLinkTrangPhim(String document, int soLuongPhim) {
        ArrayList<String> links = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(document);
            Elements div = doc.getElementsByClass("ah-wf-le ah-bg-bd");
            Elements lies = div.get(0).getElementsByTag("a");
            for (int i = 0; i < lies.size(); i++) {
                String a = new String();
                a = lies.get(i).attr("href");
                links.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }

    public int LaySoTrangCuaPhimMoiCapNhat(String document) {
        int soTrang = 0;
        try {
            Document doc = Jsoup.parse(document);
            Elements ul = doc.getElementsByClass("last");
            soTrang = Integer.parseInt(ul.get(0).text());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soTrang;
    }

    public Phim LayThongTinChiTietPhim(String document) {
        Phim phim=new Phim();
        try {
            Document doc = Jsoup.parse(document);
            phim.setHinhAnh(doc.getElementsByClass("ah-pif-fthumbnail").get(0).getElementsByTag("img").attr("src").replace("&amp;", "&"));
            phim.setHinhNen(doc.getElementsByClass("ah-pif-fcover ah-bg-bd").get(0).getElementsByTag("img").attr("src").replace("&amp;", "&"));
            phim.setLink(doc.getElementsByClass("ah-pif-ftool ah-bg-bd ah-clear-both").get(0).getElementsByTag("a").get(0).attr("href"));
            phim.setNoiDungPhim(doc.getElementsByClass("ah-pif-fcontent ah-float-left ah-bg-bd w-70").get(0).getElementsByTag("p").text());
            ArrayList<String> dsVersionPhim = new ArrayList<>();
            ArrayList<String> dsLinkVersionPhim = new ArrayList<>();
            Elements div = doc.getElementsByClass("ah-pif-relation ah-bg-bd").get(0).getElementsByTag("a");
            for(Element a : div)
            {
                dsVersionPhim.add(a.text());
                dsLinkVersionPhim.add(a.attr("href"));
                phim.setVersionHienTai(a.getElementsByClass("active").text());
            }
            phim.setVersionPhim(dsVersionPhim);
            phim.setLinkVersionPhim(dsLinkVersionPhim);
            ArrayList<String> dsTapMoi = new ArrayList<>();
            ArrayList<String> dsLinkTapMoi = new ArrayList<>();
            Elements lies = doc.getElementsByClass("ah-pif-ne").get(0).getElementsByTag("a");
            for(Element a: lies)
            {
                dsTapMoi.add(a.text());
                dsLinkTapMoi.add(a.attr("href"));
            }
            phim.setTapMoi(dsTapMoi);
            phim.setLinkTapMoi(dsLinkTapMoi);
            phim.setNamPhatHanh(doc.getElementsByClass("ah-pif-fdetails ah-float-left ah-bg-bd w-30").get(0).getElementsByTag("li").get(1).text());
            ArrayList<String> dsTheLoai = new ArrayList<>();
            ArrayList<String> dsLinkTheLoai = new ArrayList<>();
            Elements spanes = doc.getElementsByClass("ah-pif-fdetails ah-float-left ah-bg-bd w-30").get(0).getElementsByTag("li").get(2).getElementsByTag("span");
            for(Element a : spanes)
            {
                dsTheLoai.add(a.text());
                dsLinkTheLoai.add(a.getElementsByTag("a").attr("href"));
            }
            phim.setTheLoai(dsTheLoai);
            phim.setLinkTheLoai(dsLinkTheLoai);
            phim.setThoiLuong(doc.getElementsByClass("ah-pif-fdetails ah-float-left ah-bg-bd w-30").get(0).getElementsByTag("li").get(3).text());
            phim.setTenPhim(doc.getElementsByClass("ah-pif-fname").get(0).text());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phim;
    }
}
