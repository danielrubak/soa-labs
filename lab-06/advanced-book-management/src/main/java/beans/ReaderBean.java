package beans;

import api.ReaderAPI;
import model.Reader;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "ReaderBean")
@SessionScoped
public class ReaderBean {
    @EJB
    ReaderAPI readerAPI;

    String name;
    String surname;

    Integer selectedReaderId;

    public List<Reader> getAllReaders()
    {
        return readerAPI.getAllReaders();
    }

    public String addReader() {
        Reader reader = new Reader(this.name, this.surname);
        readerAPI.addReader(reader);
        this.setEmptyValues();

        return "READER_ADDED";
    }

    public String deleteReader() {
        System.out.println("READER ID TO REMOVE: " + this.selectedReaderId);
        readerAPI.deleteReader(this.selectedReaderId);
        this.setEmptyValues();

        return "READER_DELETED";
    }

    public String updateReader()
    {
        Reader reader = new Reader(this.name, this.surname);
        readerAPI.updateReader(this.selectedReaderId, reader);
        this.setEmptyValues();

        return "READER_UPDATED";
    }

    public Map<String, Integer> getReadersMap() {
        Map<String, Integer> readersMap = new LinkedHashMap<>();

        String label = "";
        List <Reader> readers = readerAPI.getAllReaders();
        for (Reader reader : readers) {
            label = reader.getName() + " " + reader.getSurname();
            readersMap.put(label, reader.getId());
        }

        return readersMap;
    }

    public void onReaderSelection (AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<Reader> readers = readerAPI.getAllReaders();

        if ( this.getSelectedReaderId() == null ) {
            this.setEmptyValues();
        } else {
            for (Reader reader : readers) {
                if ( this.selectedReaderId == reader.getId() ) {
                    this.name = reader.getName();
                    this.surname = reader.getSurname();
                }
            }
        }
    }

    public String onBackButton () {
        this.setEmptyValues();
        System.out.println("ON_BACK_CALLBACK");
        return "ON_BACK_CALLBACK";
    }

    public void setEmptyValues () {
        this.name = null;
        this.surname = null;
        this.selectedReaderId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getSelectedReaderId() {
        return selectedReaderId;
    }

    public void setSelectedReaderId(Integer selectedReaderId) {
        this.selectedReaderId = selectedReaderId;
    }
}
