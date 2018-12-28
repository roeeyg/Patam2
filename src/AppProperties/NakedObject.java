package AppProperties;

import java.util.List;

public interface NakedObject {

    List<String> fieldNames();

    void fieldChanged(String fieldName, String newValue);

    String getFieldValue(String fieldName);

}