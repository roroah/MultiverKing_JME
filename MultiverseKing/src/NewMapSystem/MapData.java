package NewMapSystem;

import com.jme3.app.state.AbstractAppState;
import java.util.ArrayList;
import utility.attribut.ElementalAttribut;

/**
 * This class holds the data of the map. It's implementation can be easily
 * replaced using other datastructures, so that you can handle dynamic map sizes
 *
 * @author Eike Foede
 */
public class MapData extends AbstractAppState {

    private HexTile[][] hexTiles;       //Will be used later for faster calculation, mainly neighbourg, pathfinding etc...
    private ArrayList<TileChangeListener> listeners = new ArrayList<TileChangeListener>();

    /**
     * Initializes a new MapData,
     *
     * @param size
     */
    public MapData(int size) {
        hexTiles = new HexTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hexTiles[i][j] = new HexTile( ElementalAttribut.NATURE);
            }
        }
    }

    public HexTile[][] getAllTiles() {
        return hexTiles;
    }

    //TODO: Check if position is out of bounds
    public void setTile(int x, int y, HexTile t) {
        TileChangeEvent tce = new TileChangeEvent(x, y, hexTiles[x][y], t);
        hexTiles[x][y] = t;
        for (TileChangeListener l : listeners) {
            l.tileChange(tce);
        }
    }

    public HexTile getTile(int x, int y) {
        return hexTiles[x][y];
    }

    public void registerTileChangeListener(TileChangeListener l) {
        listeners.add(l);
    }
}