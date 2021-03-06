package org.multiverseking.utility.component;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author roah
 */
public class InfluenceComponent implements EntityComponent {

    private final byte range;

    /**
     * Size of the influence area arround the unit where you are able to summon
     * or control unit.
     *
     * @param range
     */
    public InfluenceComponent(byte range) {
        this.range = range;
    }

    /**
     * Size of the influence area arround the unit where you are able to summon
     * or control unit.
     *
     * @return
     */
    public byte getRange() {
        return range;
    }
}
