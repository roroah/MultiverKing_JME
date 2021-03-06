package org.multiverseking.game.battle;

import com.simsilica.es.EntityId;
import com.simsilica.es.PersistentComponent;
import java.util.ArrayList;
import org.hexgridapi.utility.Vector2Int;
import org.multiverseking.utility.component.HealthComponent;
import org.multiverseking.utility.component.InfluenceComponent;
import org.multiverseking.game.gui.control.EditorWindow;
import org.multiverseking.game.gui.control.LayoutWindow.HAlign;
import org.multiverseking.game.gui.control.LayoutWindow.VAlign;
import org.multiverseking.render.AbstractRender.RenderType;
import tonegod.gui.controls.lists.Spinner;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author roah
 */
public class EntityStatsWindow extends EditorWindow {
    
    private EntityId currentId = null;
    
    public EntityStatsWindow(Screen screen, Element parent) {
        super(screen, parent, "Stats ");
    }
    
    public void show(EntityId id, RenderType renderType, ArrayList<PersistentComponent> comps) {
        if (id != currentId) {
            populate(renderType, comps);
            super.showConstrainToParent(VAlign.bottom, HAlign.left);
            currentId = id;
        }
    }
    
    private void populate(RenderType renderType, ArrayList<PersistentComponent> comps) {
        switch (renderType) {
            case Ability:
                break;
            case Core:
                HealthComponent hp = (HealthComponent) comps.get(0);
                addLabelPropertieField("Health Point", new Vector2Int(hp.getCurrent(), hp.getMax()), HAlign.left);
                InfluenceComponent inf = (InfluenceComponent) comps.get(1);
                addLabelPropertieField("Influence range", inf.getRange(), HAlign.left);
                break;
            case Debug:
                break;
            case Environment:
                break;
            case Titan:
                break;
            case Unit:
                break;
            case Utility:
                break;
        }
    }

    @Override
    protected void onButtonTrigger(String label) {
    }

    @Override
    protected void onTextFieldInput(String UID, String input, boolean triggerOn) {
    }

    @Override
    protected void onNumericFieldInput(Integer input) {
    }

    @Override
    protected void onSelectBoxFieldChange(Enum value) {
    }

    @Override
    protected void onSpinnerChange(String sTrigger, int currentIndex, Spinner.ChangeType type) {
    }

    @Override
    public void onPressCloseAndHide() {
    }
}