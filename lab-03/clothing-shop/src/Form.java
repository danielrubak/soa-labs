import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "Form")
@SessionScoped
public class Form {
    boolean firstPage = true;
    boolean secondPage = false;
    boolean thirdPage = false;

    // first page fields
    String name;
    String email;
    int age;
    String gender="male";
    String education="";
    int height;
    int bust;
    int bra;
    int waist;
    int hips;
    int chest;

    // second page fields
    String selectedPriceRange = "";
    String selectedShoppingFrequency = "";
    List<String> selectedColours = new ArrayList<>();
    List<String> selectedClothes = new ArrayList<>();

    // validation helpers
    Pattern namePattern = Pattern.compile("[A-Za-z ]*");
    Pattern emailPatter = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");

    public void validateName(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Matcher matcher = namePattern.matcher((String) o);

        if(!matcher.matches()){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Invalid characters");
            facesContext.addMessage(uiComponent.getClientId(facesContext), message);
        }
    }

    public void validateEmail(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Matcher matcher = emailPatter.matcher((String) o);
        if(!matcher.matches()){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Invalid e-mail address");
            facesContext.addMessage(uiComponent.getClientId(facesContext), message);
        }
    }

    public void validateAge(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int age = (int) o;
        if( age<10 || age>100 ){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Value must be between 10 and 100");
            facesContext.addMessage(uiComponent.getClientId(facesContext),message);
        }
    }

    public void validateHeight(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int height = (int) o;

        FacesMessage message = new FacesMessage();
        if (this.gender.equals("male") && (height < 165 || height > 200)) {
            message = new FacesMessage("Value must be between 165-200");
            ((UIInput) uiComponent).setValid(false);
            facesContext.addMessage(uiComponent.getClientId(facesContext), message);
        } else if (this.gender.equals("female") && (height < 150 || height > 185)) {
            message = new FacesMessage("Value must be between 150-185");
            ((UIInput) uiComponent).setValid(false);
            facesContext.addMessage(uiComponent.getClientId(facesContext), message);
        }
    }

    public void isPositiveNumber(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int number = (int) o;
        if(number <= 0){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Value must be positive number");
            facesContext.addMessage(uiComponent.getClientId(facesContext),message);
        }
    }

    public void confirmFistPage () {
        firstPage = false;
        secondPage = true;
    }

    public void confirmSecondPage () {
        secondPage = false;
        thirdPage = true;
    }

    public String selectedColoursToString() {
        if ( selectedColours.size() > 0 ) {
            StringBuffer buffer = new StringBuffer();
            for (String str: selectedColours){
                buffer.append(str).append(", ");
            }
            return buffer.toString();

        } else {
            return "No answer";
        }
    }

    public String selectedClothesToString () {
        if ( selectedClothes.size() > 0 ) {
            StringBuffer buffer = new StringBuffer();
            for (String str: selectedClothes){
                buffer.append(str).append(", ");
            }
            return buffer.toString();

        } else {
            return "No answer";
        }
    }

    public boolean isFirstPage () {
        return firstPage;
    }

    public void setFirstPage (boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isSecondPage () {
        return secondPage;
    }

    public void setSecondPage (boolean secondPage) {
        this.secondPage = secondPage;
    }

    public boolean isThirdPage () {
        return thirdPage;
    }

    public void setThirdPage (boolean thirdPage) {
        this.thirdPage = thirdPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getBra() {
        return bra;
    }

    public void setBra(int bra) {
        this.bra = bra;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getHips() {
        return hips;
    }

    public void setHips(int hips) {
        this.hips = hips;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public String getSelectedPriceRange() {
        if ( selectedPriceRange.length() > 0 ) {
            return selectedPriceRange;
        } else {
            return "No answer";
        }
    }

    public void setSelectedPriceRange(String selectedPriceRange) {
        this.selectedPriceRange = selectedPriceRange;
    }

    public String getSelectedShoppingFrequency() {
        if ( selectedShoppingFrequency.length() > 0 ) {
            return selectedShoppingFrequency;
        } else {
            return "No answer";
        }
    }

    public void setSelectedShoppingFrequency(String selectedShoppingFrequency) {
        this.selectedShoppingFrequency = selectedShoppingFrequency;
    }

    public List<String> getSelectedColours() {
        return selectedColours;
    }

    public void setSelectedColours(List<String> selectedColours) {
        this.selectedColours = selectedColours;
    }

    public List<String> getSelectedClothes() {
        return selectedClothes;
    }

    public void setSelectedClothes(List<String> selectedClothes) {
        this.selectedClothes = selectedClothes;
    }
}
