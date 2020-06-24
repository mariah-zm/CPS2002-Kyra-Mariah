package Map;

import org.apache.commons.lang.NullArgumentException;

public abstract class MapFactory {

    public static Map getMap(MapMode type) {
        MapFactory creator;

        switch (type){
            case SAFE:
                creator = new SafeMapFactory();
                break;

            case HAZARDOUS:
                creator = new HazardMapFactory();
                break;

            default:
                throw new NullArgumentException("");
        }
        //create and return map
        return creator.getMap();
    }

    public abstract Map getMap();
}
