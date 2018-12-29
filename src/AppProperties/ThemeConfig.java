package AppProperties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThemeConfig implements NakedObjectDropDown {
    private ObservableList<String> dropDownNames;
    private Themes selected = Themes.Gray;

    public ThemeConfig() {
        dropDownNames = FXCollections.observableArrayList(Themes.Gray.getName(), Themes.Blue.getName());
    }

    @Override
    public boolean setSelected(String selected) {
    	Themes themeType = Themes.getTypeByName(selected);
        if (themeType.equals(this.selected)) {
            return false;
        }
        this.selected = themeType;
        return true;
    }

    @Override
    public String getSelected() {
        return this.selected.getName();
    }

    public Themes getSelectedTheme() {
        return selected;
    }

    @Override
    public ObservableList<String> getDropDownNames() {
        return dropDownNames;
    }


}