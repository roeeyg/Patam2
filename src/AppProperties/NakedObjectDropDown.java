package AppProperties;

import javafx.collections.ObservableList;

public interface NakedObjectDropDown {
    boolean setSelected(String selected);
    String getSelected();
    ObservableList<String> getDropDownNames();
}