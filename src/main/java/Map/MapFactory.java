package Map;

public abstract class MapFactory {

    public static Map getMap(MapMode type)
    {

        MapFactory creator;
        switch (type)
        {
            case SAFE:
                creator = new SafeMapFactory();
                break;
            default:
                creator = new HazardMapFactory();
                break;
        }
        //create and return map
        return creator.getMap();
    }

    public abstract Map getMap();
}
