import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "Lottery")
@RequestScoped
public class Lottery {

    public String send(){
        if (Math.random() < 0.2)
            return "OK";
        else
            return "NOT_OK";
    }
}