import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "Ads")
@SessionScoped
public class Ads {
    String [] adsLinks = new String[] {"First advertisement", "Second advertisement"};
    int entranceNum = 0;

    public void onClick() {
        entranceNum++;
    }

    public String getAd() {
        return adsLinks[(int) Math.floor(Math.random()*2)];
    }

    public int getEntranceNum() {
        return entranceNum;
    }

    public void setEntranceNum(int entranceNum) {
        this.entranceNum = entranceNum;
    }
}
