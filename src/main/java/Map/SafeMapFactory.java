package Map;

public class SafeMapFactory extends MapFactory{

    public Map getMap() {
        return SafeMap.getInstance();
    }

}
