package org.multiversekingesapi.field.exploration;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;
import java.util.ArrayList;
import org.hexgridapi.core.HexSetting;
import org.hexgridapi.core.appstate.AbstractHexGridAppState;
import org.hexgridapi.core.appstate.MapDataAppState;
import org.hexgridapi.core.control.GhostControl;
import org.hexgridapi.core.data.procedural.ProceduralChunkData;
import org.hexgridapi.core.data.procedural.ProceduralHexGrid;
import org.hexgridapi.events.GhostListener;
import org.hexgridapi.utility.Vector2Int;
import org.multiversekingesapi.EntitySystemAppState;
import org.multiversekingesapi.procedural.ProceduralContent;

/**
 * Populate the world with Nest Lord && Handle them.
 * @author roah
 */
public class MonsterNest extends EntitySystemAppState {

    private ArrayList<Vector2Int> instancedNest = new ArrayList<>();
    private ProceduralContent generator;
    private Vector2Int currentPos;
    private GhostListener listener = new GhostListener() {
        @Override
        public void positionUpdate(Vector2Int ghostPosition) {
            if (!currentPos.equals(ghostPosition)) {
                currentPos = ghostPosition;
                updateNest();
            }
        }
    };

    @Override
    protected EntitySet initialiseSystem() {
        
        ProceduralHexGrid mdGen = app.getStateManager().getState(MapDataAppState.class).getMapData().getGenerator();
        generator = new ProceduralContent(mdGen, 1, 1.0, 0.00);
        GhostControl control = app.getStateManager().getState(AbstractHexGridAppState.class).getGhostControl();
        control.registerListener(listener);
        currentPos = control.getChunkPosition();
//        populatesNest();
        return entityData.getEntities(NestLordComponent.class);
    }

    @Override
    protected void updateSystem(float tpf) {
    }

    private void populatesNest() {
        ProceduralChunkData[] chunkValue = new ProceduralChunkData[HexSetting.GHOST_CONTROL_RADIUS*9];
//        generator.getChunkValue(currentPos, new int[]{0});
        for(int x = 0; x < HexSetting.CHUNK_SIZE; x++){
            for(int y = 0; y < HexSetting.CHUNK_SIZE; y++){
//                int nestValue = (int) (chunkValue.getData(0, new Vector2Int(x, y))*10);
            }
        }
    }

    private void updateNest() {
        
    }

    @Override
    protected void addEntity(Entity e) {
    }

    @Override
    protected void updateEntity(Entity e) {
    }

    @Override
    protected void removeEntity(Entity e) {
    }

    @Override
    protected void cleanupSystem() {
    }
}
