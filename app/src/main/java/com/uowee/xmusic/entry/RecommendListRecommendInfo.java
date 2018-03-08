package com.uowee.xmusic.entry;

/**
 * Created by GuoWee on 2018/3/8.
 */

/**
 * {
 * "position": 1,
 * "tag": "华语,流行",
 * "songidlist": [ ],
 * "pic": "http://musicugc.qianqian.com/ugcdiy/pic/3776157b7d6569d763b4136f10593630.jpg",
 * "title": "“潮流小鬼”黄鸿升的玩乐世界",
 * "collectnum": 13,
 * "type": "gedan",
 * "listenum": 2797,
 * "listid": "518069934"
 * }
 */
public class RecommendListRecommendInfo {

    private String pic;
    private String title;
    private String tag;
    private String collectnum;
    private String listid;
    private String listenum;
    private String type;

    @Override
    public String toString() {
        return "RecommendListRecommendInfo{" +
                "pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", collectnum='" + collectnum + '\'' +
                ", listid='" + listid + '\'' +
                ", listenum='" + listenum + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(String collectnum) {
        this.collectnum = collectnum;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getListenum() {
        return listenum;
    }

    public void setListenum(String listenum) {
        this.listenum = listenum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
