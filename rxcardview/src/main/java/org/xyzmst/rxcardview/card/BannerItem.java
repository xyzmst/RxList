package org.xyzmst.rxcardview.card;

import com.google.gson.JsonObject;

/**
 * @author Chad
 * @title com.memory.me.dto.home
 * @description
 * @modifier
 * @date
 * @since 15/1/30 下午4:02
 */
public class BannerItem {
//    {
//        id: 54,
//        thumbnail: {
//                1242x700: "http://www.morefunenglish.com/storage/pool0/img/146/48/20150130000544363627000438.jpg",
//                720x405: "http://www.morefunenglish.com/storage/pool0/img/112/189/20150130000547226099000223.jpg"
//        },
//        target: {
//                  action: "course",
//                  data: {
//                      course_id: "444"
//                  }
//               }
//     }
    int id;
    JsonObject thumbnail;
    JsonObject target;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JsonObject getTarget() {
        return target;
    }

    public void setTarget(JsonObject target) {
        this.target = target;
    }

    public String getThumbnail_1242x700(){
        return thumbnail.get("1242x700").getAsString();
    }
    public String getThumbnail_720x405(){
        return thumbnail.get("720x405").getAsString();
    }
}
