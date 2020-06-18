import Map.*;

public enum PlayerStatus {
    DEAD(TileType.WATER),
    WINS(TileType.TREASURE),
    SAFE(TileType.GRASS);

    private TileType type;

    PlayerStatus(TileType type){
        this.type = type;
    }

    public static PlayerStatus getStatus(TileType type) {
        for (PlayerStatus status : values()) {
            if (status.type.equals(type)) {
                return status;
            }
        }
        return null;
    }
}
