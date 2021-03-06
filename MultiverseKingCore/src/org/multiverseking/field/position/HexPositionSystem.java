package org.multiverseking.field.position;

import org.multiverseking.field.position.component.HexPositionComponent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.ArrayList;
import org.hexgridapi.core.AbstractHexGridAppState;
import org.hexgridapi.core.coordinate.HexCoordinate;
import org.hexgridapi.core.data.MapData;
import org.hexgridapi.core.geometry.HexSetting;
import org.hexgridapi.events.MapDataListener;
import org.hexgridapi.events.TileChangeEvent;
import org.multiverseking.core.utility.EntitySystemAppState;
import org.multiverseking.core.utility.SubSystem;
import org.multiverseking.render.RenderComponent;
import org.multiverseking.render.RenderSystem;

/**
 * Handle the HEX position rendering of all entity.
 * @TODO Spatial parenting may cause issue with positionning
 * @author roah
 */
public class HexPositionSystem extends EntitySystemAppState implements SubSystem {

    private MapData mapData;
    private RenderSystem renderSystem;
    private ArrayList<EntityId> positionCulling = new ArrayList<>(); // Used for spatials position handled by subsustem (aka position culling?)
    private MapDataListener tileChangeListener = new MapDataListener() {
        @Override
        public void onTileChange(TileChangeEvent[] events) {
            for (Entity e : entities) {
                HexCoordinate entityPos = e.get(HexPositionComponent.class).getPosition();
                for (int i = 0; i < events.length; i++) {
                    if (entityPos.equals(events[i].getTilePos())) {
                        Spatial s = renderSystem.getSpatial(e.getId());
                        float posY = events[i].getNewTile().getHeight()
                                * HexSetting.FLOOR_OFFSET + 0.1f;
                        s.setLocalTranslation(s.getLocalTranslation().x, posY, s.getLocalTranslation().z);
                        break;
                    }
                }
            }
        }
    };

    @Override
    protected EntitySet initialiseSystem() {
        renderSystem = app.getStateManager().getState(RenderSystem.class);
        renderSystem.registerSubSystem(this);
        mapData = app.getStateManager().getState(AbstractHexGridAppState.class).getMapData();
        mapData.register(tileChangeListener);
        app.getStateManager().getState(RenderSystem.class).registerSubSystem(this);
        return entityData.getEntities(RenderComponent.class, HexPositionComponent.class);
    }

    @Override
    protected void updateSystem(float tpf) {
    }

    @Override
    protected void addEntity(Entity e) {
        RenderComponent render = entityData.getComponent(e.getId(), RenderComponent.class);
        if (render != null && render.getParent() != null) {
            entityData.removeComponent(e.getId(), HexPositionComponent.class);
//            updateSpatialTransform(e); // todo update the position to the parent
        } else {
            updateSpatialTransform(e);
        }
    }

    @Override
    protected void updateEntity(Entity e) {
        RenderComponent render = entityData.getComponent(e.getId(), RenderComponent.class);
        if (render != null && render.getParent() != null) {
            entityData.removeComponent(e.getId(), HexPositionComponent.class);
//            updateSpatialTransform(e); // todo update the position to the parent
        } else {
            updateSpatialTransform(e);
        }
        updateSpatialTransform(e); // todo parenting handling
    }

    @Override
    protected void removeEntity(Entity e) {
        if(e.get(HexPositionComponent.class) != null){
            entityData.removeComponent(e.getId(), HexPositionComponent.class);
        }
        positionCulling.remove(e.getId());
    }

    @Override
    protected void cleanupSystem() {
        if(renderSystem != null) {
            renderSystem.removeSubSystem(this, false);
        }
        mapData.unregister(tileChangeListener);
    }
    
    /**
     * @todo better implementation in case of multiple system wanting 
     * to get the control of the position of an entity at the same moment.
     */
    public void registerEntityForCulling(EntityId id) {
        positionCulling.add(id);
    }
    
    /**
     * @todo better implementation in case of multiple system wanting 
     * to get the control of the position of an entity at the same moment.
     */
    public void removeEntityFromCulling(EntityId id) {
        positionCulling.remove(id);
        Entity e = entities.getEntity(id);
        if(e != null){
            updateSpatialTransform(e);
        }
    }

    @Override
    public void rootSystemIsRemoved() {
        app.getStateManager().detach(this);
    }

    private void updateSpatialTransform(Entity e) {
        if(positionCulling.contains(e.getId())){
            return;
        }
        Spatial s = renderSystem.getSpatial(e.getId());
        if(s != null){
            Vector3f pos;
            if (mapData.getTile(e.get(HexPositionComponent.class).getPosition()) == null) {
                pos = e.get(HexPositionComponent.class).getPosition().toWorldPosition();
            } else {
                pos = e.get(HexPositionComponent.class).getPosition()
                        .toWorldPosition(mapData.getTile(
                        e.get(HexPositionComponent.class).getPosition()).getHeight());
            }
            pos.y += 0.01f;
            s.setLocalTranslation(pos);
            s.setLocalRotation(e.get(HexPositionComponent.class).getRotation().toQuaternion());
        }
    }
}
