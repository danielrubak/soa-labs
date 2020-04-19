import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "Validation")
@SessionScoped
public class ValidationClass {

    boolean firstPart = true;
    boolean secondPart = false;
    boolean thirdPart = false;

    String name;
    String email;
    int age;
    String gender="female";
    String education="";
    int height;

    int bust;
    int bra;
    int waist;
    int hips;

    int chest;

    String selectedPriceRange = "";
    String selectedShoppingFrequency = "";
    List<String> selectedColours;
    List<String> selectedClothes;


    public void validateHeight(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int height = (int) o;
        if (this.gender.equals("male")) {
            if (height < 165 || height > 200) {
                ((UIInput) uiComponent).setValid(false);
                FacesMessage message = new FacesMessage("Height for male must be between 165-200");
                facesContext.addMessage(uiComponent.getClientId(facesContext), message);
            }
        } else {
            if (height < 150 || height > 185) {
                ((UIInput) uiComponent).setValid(false);
                FacesMessage message = new FacesMessage("Height for female must be between 150-185");
                facesContext.addMessage(uiComponent.getClientId(facesContext), message);
            }
        }
    }

    public void validateName(FacesContext facesContext, UIComponent uiComponent, Object o){
        Pattern pattern = Pattern.compile("[A-Za-z ]*");
        Matcher matcher = pattern.matcher((String) o);
        if(!matcher.matches()){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Enter only lattin characters");
            facesContext.addMessage(uiComponent.getClientId(facesContext), message);
        }
    }

    public void validateEmail(FacesContext facesContext, UIComponent uiComponent, Object o)
    {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        Matcher matcher = pattern.matcher((String) o);
        if(!matcher.matches()){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Enter valid e-mail address");
            facesContext.addMessage(uiComponent.getClientId(facesContext), message);
        }
    }

    public void validateAge(FacesContext facesContext, UIComponent uiComponent, Object o)
    {
        int age = (int) o;
        if(age<10 || age>100){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Invalid age: must be between 10 and 100");
            facesContext.addMessage(uiComponent.getClientId(facesContext),message);
        }
    }

    public void validatePositiveNumber(FacesContext facesContext, UIComponent uiComponent, Object o)
    {
        int number = (int) o;
        if(number<=0){
            ((UIInput) uiComponent).setValid(false);
            FacesMessage message = new FacesMessage("Insert positive number");
            facesContext.addMessage(uiComponent.getClientId(facesContext),message);
        }
    }

    public void confirmFistPart() {
        firstPart = false;
        secondPart = true;
    }

    public void confirmSecondPart() {
        secondPart = false;
        thirdPart = true;
    }

    public String getSelectedColoursString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str: selectedColours){
            stringBuffer.append(str).append(", ");
        }
        return stringBuffer.toString();
    }

    public String getSelectedClothesString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str: selectedClothes){
            stringBuffer.append(str).append(", ");
        }
        return stringBuffer.toString();
    }

    // Getters and Setters


    public boolean isFirstPart() {
        return firstPart;
    }

    public void setFirstPart(boolean firstPart) {
        this.firstPart = firstPart;
    }

    public boolean isSecondPart() {
        return secondPart;
    }

    public void setSecondPart(boolean secondPart) {
        this.secondPart = secondPart;
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

    public boolean isThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(boolean thirdPart) {
        this.thirdPart = thirdPart;
    }

    public String getSelectedPriceRange() {
        return selectedPriceRange;
    }

    public void setSelectedPriceRange(String selectedPriceRange) {
        this.selectedPriceRange = selectedPriceRange;
    }

    public String getSelectedShoppingFrequency() {
        return selectedShoppingFrequency;
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
