package Map;

public class HazardMapFactory extends MapFactory {
    public Map getMap() {
        return HazardMap.getInstance();
    }
}
