package com.uowee.xmusic.entry;

/**
 * Created by GuoWee on 2018/3/8.
 */

/**
 * {
 * "desc": "2月新歌速递",
 * "pic": "http://business.cdn.qianqian.com/qianqian/pic/bos_client_15181581484483ae2b915198ce2155f3c0b4628ab9.jpg",
 * "type_id": "519125943",
 * "type": 0,
 * "title": "新歌抢鲜听",
 * "tip_type": 0,
 * "author": "2月新歌速递"
 * }
 */
public class RecommendListNewAlbumInfo {

    private String desc;
    private String pic;
    private String type_id;
    private int type;
    private String title;
    private int tip_type;
    private String author;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTip_type() {
        return tip_type;
    }

    public void setTip_type(int tip_type) {
        this.tip_type = tip_type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
