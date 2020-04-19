import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name = "Provider")
@ViewScoped
public class AnswersProvider {
    List<String> spendingRanges = new ArrayList<>(Arrays.asList(
            "Less than 100 PLN", "Between 100 and 500 PLN", "Between 500 and 1000 PLN", "More than 1000 PLN"
    ));

    List<String> shoppingFrequency = new ArrayList<>(Arrays.asList(
            "Everyday", "Once a week", "Once a month", "couple times a year"
    ));

    List<String> colourPreferences = new ArrayList<>(Arrays.asList(
            "Bright-coloured", "Grey", "Black and white", "Only Black"
    ));

    List<String> femaleClothes = new ArrayList<>(Arrays.asList(
            "Blouses", "Skirts", "Trousers", "Suits"
    ));

    List<String> maleClothes = new ArrayList<>(Arrays.asList(
            "Trousers", "Shorts", "Shirts", "Ties"
    ));

    public List<String> getSpendingRanges() {
        return spendingRanges;
    }

    public void setSpendingRanges(List<String> spendingRanges) {
        this.spendingRanges = spendingRanges;
    }

    public List<String> getShoppingFrequency() {
        return shoppingFrequency;
    }

    public void setShoppingFrequency(List<String> shoppingFrequency) {
        this.shoppingFrequency = shoppingFrequency;
    }

    public List<String> getColourPreferences() {
        return colourPreferences;
    }

    public void setColourPreferences(List<String> colourPreferences) {
        this.colourPreferences = colourPreferences;
    }

    public List<String> getFemaleClothes() {
        return femaleClothes;
    }

    public void setFemaleClothes(List<String> femaleClothes) {
        this.femaleClothes = femaleClothes;
    }

    public List<String> getMaleClothes() {
        return maleClothes;
    }

    public void setMaleClothes(List<String> maleClothes) {
        this.maleClothes = maleClothes;
    }
}
