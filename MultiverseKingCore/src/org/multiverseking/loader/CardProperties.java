package org.multiverseking.loader;

import org.json.simple.JSONObject;
import org.multiverseking.card.attribut.Rarity;
import org.multiverseking.render.AbstractRender.RenderType;
import org.multiverseking.utility.ElementalAttribut;

/**
 * Contain the properties of the card to show up when used by the
 * CardRendersystem,
 *
 * @author roah
 */
public class CardProperties {
    // <editor-fold defaultstate="collapsed" desc="Used Variable">
    /**
     * Used to know where the card can be played etc, mainly used stats.
     */
    private final RenderType renderType;
    /**
     * Used to know the amount of time the player can have/play this card.
     * Balance stats mainly.
     */
    private final Rarity rarity;
    /**
     * Used for the elemental interaction.
     */
    private final ElementalAttribut element;
    /**
     * Used to show the card description text.
     */
    private final String description;
    /**
     * Used to show the card name.
     */
    private final String name;
    /**
     * Used to know the img to load for the card.
     */
    private final String visual;

    // </editor-fold>
    /**
     * Create a new card type component.
     *
     * @param obj
     * @param name
     * @param renderType
     */
    public CardProperties(JSONObject obj, String name, RenderType renderType) {
        this.name = name;
        this.renderType = renderType;
        visual = (String) obj.get("visual");
        rarity = Rarity.valueOf(obj.get("rarity").toString());
        element = ElementalAttribut.valueOf(obj.get("eAttribut").toString());
        description = (String) obj.get("description");
    }

    /**
     * Constructor used for the editor mode.
     */
    public CardProperties(String name, String visual, RenderType renderType,
            Rarity rarity, ElementalAttribut element, String description) {
        this.name = name;
        this.renderType = renderType;
        this.rarity = rarity;
        this.element = element;
        this.description = description;
        this.visual = visual;
    }

    /**
     * Internal use.
     */
    public CardProperties() {
        this.renderType = null;
        this.rarity = null;
        this.element = null;
        this.description = null;
        this.name = null;
        this.visual = null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getter">

    /**
     * Card subType properties.
     *
     * @see CardSubType
     * @return
     */
    public RenderType getRenderType() {
        return renderType;
    }

    /**
     * card Rarity.
     *
     * @see Rarity
     * @return
     */
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * The element this card entity belong to.
     *
     * @return
     */
    public ElementalAttribut getElement() {
        return element;
    }

    /**
     * The description Text belong to this card.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * The name to use for this card.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * The card img texture to use for this card.
     *
     * @return
     */
    public String getVisual() {
        return visual;
    }
    // </editor-fold>
}
