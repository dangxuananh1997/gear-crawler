package dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dangxuananh1997
 */
public enum ProductType {
    LAPTOP(1),
    MOUSE(2),
    KEYBOARD(3);

    private int value;
    private static Map map = new HashMap<>();

    private ProductType(int value) {
        this.value = value;
    }

    static {
        for (ProductType pageType : ProductType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static ProductType valueOf(int productType) {
        return (ProductType) map.get(productType);
    }

    public int getValue() {
        return value;
    }
}
