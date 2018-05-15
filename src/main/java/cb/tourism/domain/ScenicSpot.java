package cb.tourism.domain;

import org.springframework.data.annotation.Id;

public class ScenicSpot {
    @Id
    public String id;

    public String name;
    public String title;
    public String des;
    public float score;
    public String time;
    public int recommendTakeDay;
    public String visaDegree;
    public String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRecommendTakeDay() {
        return recommendTakeDay;
    }

    public void setRecommendTakeDay(int recommendTakeDay) {
        this.recommendTakeDay = recommendTakeDay;
    }

    public String getVisaDegree() {
        return visaDegree;
    }

    public void setVisaDegree(String visaDegree) {
        this.visaDegree = visaDegree;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
