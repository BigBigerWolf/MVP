package com.example.rxretrofitdaggermvp.mvp.module.entity;

import java.util.List;

/**
 * Created by MrKong on 2017/4/2.
 */

public class NewsInfo {
    private String stat;
    private List<DataBean> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uniquekey : 3a13fcdcd39bd359b1e87d990132fb4d
         * title : 美媒：美多名女囚控告狱警性侵 满足要求可获冰毒
         * date : 2017-04-02 10:24
         * category : 头条
         * author_name : 参考消息网
         * url : http://mini.eastday.com/mobile/170402102435933.html
         * thumbnail_pic_s : http://06.imgmini.eastday.com/mobile/20170402/20170402102435_bfb0d8a0ac6bc50e4eafccca26d6c961_1_mwpm_03200403.jpeg
         * thumbnail_pic_s02 : http://06.imgmini.eastday.com/mobile/20170402/20170402102435_f87176a24ddfb43630d484a194a0fdbb_2_mwpm_03200403.jpeg
         * thumbnail_pic_s03 : http://06.imgmini.eastday.com/mobile/20170402/20170402102435_3d6ac0f8a4f3387fc076ed7583392980_3_mwpm_03200403.jpeg
         */

        private String uniquekey;
        private String title;
        private String date;
        private String category;
        private String author_name;
        private String url;
        private String thumbnail_pic_s;
        private String thumbnail_pic_s02;
        private String thumbnail_pic_s03;

        public String getUniquekey() {
            return uniquekey;
        }

        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }

        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }
    }
}
