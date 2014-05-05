/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysystem.card;

import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.simsilica.es.EntityId;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Element;
import tonegod.gui.core.ElementManager;
import tonegod.gui.effects.Effect;

/**
 *
 * @author roah
 */
public class Card extends ButtonAdapter {

    private final float rescaleValue;
    private final Vector2f cardSize;
    private int isRescale = 1;
    private int handPosition;
    private String cardName;
    private EntityId UID;

    String getCardName() {
        return cardName;
    }

    EntityId getCardEntityUID() {
        return this.UID;
    }

    public Card(ElementManager screen, boolean rescale, String cardName, int handPosition, EntityId UID) {
        super(screen, UID.toString(), Vector2f.ZERO, new Vector2f(200f / (2.5f * (rescale ? 1 : 0)), 300f / (2.5f * (rescale ? 1 : 0))), Vector4f.ZERO, "Textures/Cards/" + cardName + "_256px.png");
        this.isRescale = (rescale ? 1 : 0);
        this.rescaleValue = 2.5f; //if you change this change it in the super constructor.
        this.cardSize = new Vector2f(200f / (rescaleValue * isRescale), 300f / (rescaleValue * isRescale));
        this.handPosition = handPosition;
        this.cardName = cardName;
        this.UID = UID;
        this.init();
    }

    private void init() {
        this.removeEffect(Effect.EffectEvent.Hover);
        this.removeEffect(Effect.EffectEvent.Press);
        this.setIsResizable(false);
        this.setIsMovable(true);
    }

    final void resetHandPosition() {
        this.setPosition(new Vector2f(220f + ((cardSize.x - 20) * handPosition), screen.getHeight() - this.getHeight() - 20));
    }

    @Override
    public void setHasFocus(boolean hasFocus) {
        super.setHasFocus(hasFocus);
        if (hasFocus) {
            app.getStateManager().getState(CardEntityRenderSystem.class).hasFocus(this);
        } else {
            app.getStateManager().getState(CardEntityRenderSystem.class).lostFocus(this);
        }
    }
    @Override
    public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
        super.onButtonMouseLeftDown(evt, toggled);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        super.onButtonMouseLeftUp(evt, toggled);
        CardEntityRenderSystem renderSystem = app.getStateManager().getState(CardEntityRenderSystem.class);
        renderSystem.isInCastArea(this);
        resetHandPosition();
    }

    int getHandPosition() {
        return this.handPosition;
    }

    @Override
    public void addChild(Element child) {
        super.addChild(child);
        child.setDimensions(cardSize);
        child.centerToParent();
    }
}