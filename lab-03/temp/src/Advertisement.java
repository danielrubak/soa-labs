import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "Advertisement")
@SessionScoped
public class Advertisement {

    int clicks = 0;

    String [] adsLinks = new String[] {"Add no 1","Add no 2","Add no 3"};

    public void add()
    {
        System.out.println("Ajax");
        clicks = clicks+1;
    }

    public String getRandomAdd(){return adsLinks[(int) Math.floor(Math.random()*3)];}

    public int getClicks() {
        System.out.println("Getter");
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
