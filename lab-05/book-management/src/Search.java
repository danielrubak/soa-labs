import CurrencyExchange.Exchanger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "Search")
@SessionScoped
public class Search {
    // filter values
    String title = "";
    String author = "";
    String category = "";
    int priceMin = 0;
    int priceMax = 50;
    String currency = "";
    int numOfPagesMin = 0;
    int numOfPagesMax = 500;
    boolean inPLN = true;
    Exchanger exchanger= new Exchanger();

    BookLibrary library = new BookLibrary();
    List<Book> selectedBooks = new ArrayList<>();
    double sumOfOrder = 0.0;

    boolean isTitleVisible = true;
    boolean isAuthorVisible = true;
    boolean isCategoryVisible = true;
    boolean isPriceVisible = true;
    boolean isCurrencyVisible = true;
    boolean isPagesVisible = true;

    boolean searchByTitle = false;
    boolean searchByAuthor = false;
    boolean searchByCategory = false;
    boolean searchByPrice = false;
    boolean searchByCurrency = false;
    boolean searchByPages = false;

    public List<Book> getBooksForSearch()
    {
        List<Book> result = new ArrayList<>();
        boolean matches;
        for (Book book: library.books)
        {
            matches = true;
            if (searchByTitle) {
                String pattern = ".*" + title + ".*";
                matches = matches && book.title.matches(pattern);
            }

            if (searchByAuthor) {
                String pattern = ".*" + author + ".*";
                matches = matches && book.author.matches(pattern);
            }

            if (searchByCategory) {
                String pattern = ".*" + category + ".*";
                matches = matches && book.category.matches(pattern);
            }

            if (searchByPrice) {
                matches = matches && priceForBook(book)>=priceMin && priceForBook(book)<=priceMax;
            }

            if (searchByCurrency) {
                matches = matches && book.currency.equals(currency);
            }

            if (searchByPages) {
                matches = matches && (book.numOfPages>=numOfPagesMin && book.numOfPages<=numOfPagesMax);
            }

            if(matches) result.add(book);
        }

        return result;
    }

    public String currencyForBook(Book book)
    {
        return this.inPLN ? "PLN" : book.currency;
    }

    public double priceForBook(Book book)
    {
        return this.inPLN ? Math.ceil(exchanger.exchangeToPLN(book.currency,book.price)) : book.price;
    }


    public String submitOrder(){
        selectedBooks.clear();
        sumOfOrder = 0;

        for (Book book: getBooksForSearch())
        {
            if (book.selected){
                selectedBooks.add(book);
                sumOfOrder += Math.ceil(exchanger.exchangeToPLN(book.currency,book.price));
            }
        }
        return "summary";
    }

    public Boolean isSthVisible() {
        return isTitleVisible || isAuthorVisible || isCategoryVisible || isPriceVisible || isCurrencyVisible || isPagesVisible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getNumOfPagesMin() {
        return numOfPagesMin;
    }

    public void setNumOfPagesMin(int numOfPagesMin) {
        this.numOfPagesMin = numOfPagesMin;
    }

    public int getNumOfPagesMax() {
        return numOfPagesMax;
    }

    public void setNumOfPagesMax(int numOfPagesMax) {
        this.numOfPagesMax = numOfPagesMax;
    }

    public boolean isInPLN() {
        return inPLN;
    }

    public void setInPLN(boolean inPLN) {
        this.inPLN = inPLN;
    }

    public boolean isTitleVisible() {
        return isTitleVisible;
    }

    public void setTitleVisible(boolean titleVisible) {
        this.isTitleVisible = titleVisible;
    }

    public boolean isAuthorVisible() {
        return isAuthorVisible;
    }

    public void setAuthorVisible(boolean authorVisible) {
        this.isAuthorVisible = authorVisible;
    }

    public boolean isCategoryVisible() {
        return isCategoryVisible;
    }

    public void setCategoryVisible(boolean categoryVisible) {
        this.isCategoryVisible = categoryVisible;
    }

    public boolean isPriceVisible() {
        return isPriceVisible;
    }

    public void setPriceVisible(boolean priceVisible) {
        this.isPriceVisible = priceVisible;
    }

    public boolean isCurrencyVisible() {
        return isCurrencyVisible;
    }

    public void setCurrencyVisible(boolean currencyVisible) {
        this.isCurrencyVisible = currencyVisible;
    }

    public boolean isPagesVisible() {
        return isPagesVisible;
    }

    public void setPagesVisible(boolean pagesVisible) {
        this.isPagesVisible = pagesVisible;
    }

    public boolean isSearchByTitle() {
        return searchByTitle;
    }

    public void setSearchByTitle(boolean searchByTitle) {
        this.searchByTitle = searchByTitle;
    }

    public boolean isSearchByAuthor() {
        return searchByAuthor;
    }

    public void setSearchByAuthor(boolean searchByAuthor) {
        this.searchByAuthor = searchByAuthor;
    }

    public boolean isSearchByCategory() {
        return searchByCategory;
    }

    public void setSearchByCategory(boolean searchByCategory) {
        this.searchByCategory = searchByCategory;
    }

    public boolean isSearchByPrice() {
        return searchByPrice;
    }

    public void setSearchByPrice(boolean searchByPrice) {
        this.searchByPrice = searchByPrice;
    }

    public boolean isSearchByCurrency() {
        return searchByCurrency;
    }

    public void setSearchByCurrency(boolean searchByCurrency) {
        this.searchByCurrency = searchByCurrency;
    }

    public boolean isSearchByPages() {
        return searchByPages;
    }

    public void setSearchByPages(boolean searchByPages) {
        this.searchByPages = searchByPages;
    }

    public double getSumOfOrder() {
        return sumOfOrder;
    }

    public void setSumOfOrder(double sumOfOrder) {
        this.sumOfOrder = sumOfOrder;
    }

    public List<Book> getSelectedBooks() {
        return selectedBooks;
    }

    public void setSelectedBooks(List<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }
}
