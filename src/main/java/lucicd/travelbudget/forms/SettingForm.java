package lucicd.travelbudget.forms;

public class SettingForm {
    private String id;
    private String name;
    private String textValue;
    private String numberValue;

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

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(String numberValue) {
        this.numberValue = numberValue;
    }
}
