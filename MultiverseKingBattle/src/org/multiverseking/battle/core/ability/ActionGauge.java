/*
 * Copyright (C) 2016 roah
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.multiverseking.battle.core.ability;

import org.multiverseking.battle.gui.ATBGauge;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import java.util.ArrayList;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author roah
 */
public class ActionGauge extends ATBGauge {

    private final ArrayList<Indicator> segmentsList = new ArrayList<>();
    private int gaugeSize;
    private final float speed = 15;     // Rate speed for the segment to fill up
    private float timer = 0;
    private int currentSegment = 0;     // Segment currently operated

    public ActionGauge(Screen screen, String filePath, int gaugeSize) {
        this.gaugeSize = gaugeSize;
        
        gauge = new Element(screen, "atbGroup",
                new Vector2f(20, -35), new Vector2f(500, 500).mult(.75f), Vector4f.ZERO, null);
        gauge.setAsContainerOnly();

        Element atbHolderA = new Element(screen, "atbHolderA", new Vector2f(12, 0),
                new Vector2f(131, 49).mult(.75f), new Vector4f(),
                filePath + "ATBHolderA_bis.png");
        Element atbHolderB = new Element(screen, "atbHolderB", new Vector2f(70, 27),
                new Vector2f(187 + 110 * (gaugeSize), 13).mult(.75f), new Vector4f(),
                filePath + "ATBHolderB.png");
        Element atbHolderC = new Element(screen, "atbHolderC", new Vector2f(155 + 110 * (gaugeSize - 1), 27),
                new Vector2f(36, 13).mult(.75f), new Vector4f(),
                filePath + "ATBHolderC.png");
        gauge.addChild(atbHolderA);
        gauge.addChild(atbHolderB);
        gauge.addChild(atbHolderC);

//        for (int i = 0; i < gaugeSize; i++) {
//            Indicator ind = ATBGauge.initIndicator(screen, "atbSegment.Blue.HORIZONTAL." + filePath,
//                    new Vector2f(58 + ((100 + 10) * i), 10), new Vector2f(110, 15), true);
//            ind.setOverlayImage(filePath + "atbSegmentOverlay.png");
//            ind.setBaseImage(filePath + "atbSegment.png");
//            segmentsList.add(ind);
//            gauge.addChild(ind);
//        }
    }

    @Override
    public Element getGauge() {
        return gauge;
    }

    /**
     * @param value
     * @todo add events for when the gauge is fill to restart it
     */
    protected void update(float value) {
        if (currentSegment != -1) { // if the gauge is not fill
            timer += value * speed;
            if (timer <= 100) {
                segmentsList.get(currentSegment).setCurrentValue((int) timer);
            } else if (timer > 100) {
                timer = 0;
                currentSegment++;
                if (currentSegment >= gaugeSize) {
                    currentSegment = -1;
                }
            }
        }
    }
}
