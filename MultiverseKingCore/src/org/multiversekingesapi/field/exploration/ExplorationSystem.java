package org.multiversekingesapi.field.exploration;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hexgridapi.core.RTSCamera;
import org.hexgridapi.core.data.MapData;
import org.hexgridapi.core.appstate.HexGridDefaultApp;
import org.hexgridapi.core.appstate.MapDataAppState;
import org.hexgridapi.core.appstate.MouseControlSystem;
import org.hexgridapi.events.MouseInputEvent;
import org.hexgridapi.events.TileInputListener;
import org.hexgridapi.utility.HexCoordinate;
import org.hexgridapi.utility.Vector2Int;
import org.multiversekingesapi.EntityDataAppState;
import org.multiversekingesapi.SubSystem;
import org.multiversekingesapi.field.CollisionSystem;
import org.multiversekingesapi.field.position.HexPositionComponent;
import org.multiversekingesapi.field.position.MoveToComponent;
import org.multiversekingesapi.render.AbstractRender;
import org.multiversekingesapi.render.RenderComponent;
import org.multiversekingesapi.render.animation.Animation;
import org.multiversekingesapi.render.animation.AnimationComponent;
import org.multiversekingesapi.render.animation.AnimationSystem;
import org.multiversekingesapi.render.camera.CameraControlSystem;
import org.multiversekingesapi.render.camera.CameraTrackComponent;

/**
 *
 * @author roah
 */
public class ExplorationSystem extends AbstractAppState implements SubSystem {

    private SimpleApplication app;
    private EntityData entityData;
    private MapData mapData;
    private MouseControlSystem mouseSystem;
    private EntityId playerId;
//    private AreaEventRenderDebugSystem renderDebugSystem;
    private final HexCoordinate startPosition;

    public ExplorationSystem() {
        this.startPosition = loadStart();
    }

    public ExplorationSystem(HexCoordinate startPosition) {
        if (startPosition != null) {
            this.startPosition = startPosition;
        } else {
            this.startPosition = loadStart();
        }
    }

    private HexCoordinate loadStart() {
        Spatial playerData = app.getAssetManager().loadModel("Data/playerData.j3o");
        if (playerData.getUserData("savedPosition") != null) {
            return new HexCoordinate(HexCoordinate.Coordinate.OFFSET,
                    new Vector2Int((String) playerData.getUserData("savedPosition")));
        } else {
            Logger.getLogger(ExplorationSystem.class.getName())
                    .log(Level.WARNING, "There is no Starting position to load, "
                    + "setting the position at the Hearth World.");
            return new HexCoordinate();
        }
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.entityData = app.getStateManager().getState(EntityDataAppState.class).getEntityData();
        this.mapData = app.getStateManager().getState(MapDataAppState.class).getMapData();
        this.mouseSystem = app.getStateManager().getState(MouseControlSystem.class);
        mouseSystem.registerTileInputListener(tileInputListener);
//        this.renderDebugSystem = app.getStateManager().getState(AreaEventRenderDebugSystem.class);

        /**
         * Initialise all other system needed.
         */
        setUsedState(true);

        /**
         * Define the starting position from the AreaEventSystem.
         */
//        HexCoordinate startPosition = app.getStateManager().getState(AreaEventSystem.class).getStartPosition();
//        if (renderDebugSystem != null) {
//            renderDebugSystem.showDebug(false, startPosition, this);
//        }
        /**
         * Load the titan controlled by the player outside of battle to move
         * arround.
         */
        playerId = entityData.createEntity();
        String name = (String) ((Node) app.getAssetManager().loadModel("Data/playerData.j3o")).getUserData("blessedTitan");
        entityData.setComponents(playerId, new RenderComponent(
                name, AbstractRender.RenderType.Titan),
                new HexPositionComponent(startPosition),
                new AnimationComponent(Animation.IDLE),
                new CameraTrackComponent());
    }

    @Override
    public void rootSystemIsRemoved() {
    }
    private TileInputListener tileInputListener = new TileInputListener() {
        @Override
        public void onMouseAction(MouseInputEvent event) {
            if (event.getType().equals(MouseInputEvent.MouseInputEventType.LMB)) {
                entityData.setComponent(playerId, new MoveToComponent(event.getPosition()));
            }
        }
    };

    @Override
    public void cleanup() {
        super.cleanup();

        setUsedState(false);

        entityData.removeEntity(playerId);
//        HexCoordinate startPosition = app.getStateManager().getState(AreaEventSystem.class).getStartPosition();
//        if (renderDebugSystem != null) {
//            renderDebugSystem.showDebug(true, startPosition, this);
//        }
        ((HexGridDefaultApp) app).getRtsCam().setCenter(
                startPosition.toWorldPosition(
                mapData.getTile(startPosition) != null
                ? mapData.getTile(startPosition).getHeight() : 0));
    }

    private void setUsedState(boolean enable) {
        AppState state;
        Class<? extends AppState>[] states = new Class[]{
            CollisionSystem.class,
            AnimationSystem.class,
            HexMovementSystem.class,
            MonsterNest.class,
            CameraControlSystem.class
        };
        for (Class c : states) {
            state = getState(enable, c);
            if (enable && state != null) {
                app.getStateManager().attach(state);
            } else if (!enable && state != null) {
                app.getStateManager().detach(state);
            }
        }
    }

    private <T extends AppState> AppState getState(boolean enable, Class<? extends AppState> classType) {
        AppState state = app.getStateManager().getState(classType);
        if (enable && state == null) {
            try {
                return classType.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ExplorationSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (!enable && state != null) {
            return state;
        }
        return null;
    }

    public HexCoordinate getPlayerPosition() {
        return entityData.getComponent(playerId, HexPositionComponent.class).getPosition();
    }
}
