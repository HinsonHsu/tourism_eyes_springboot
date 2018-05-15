package cb.tourism.domain;

import org.springframework.data.annotation.Id;

public class TourRoute {
    @Id
    public String id;

    public String scenicId;

    public String dayRoute;

    public int dayIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
    }

    public String getDayRoute() {
        return dayRoute;
    }

    public void setDayRoute(String dayRoute) {
        this.dayRoute = dayRoute;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }
}
