/*
 * Copyright (C) 2015 roah
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
package org.multiverseking.editor.core;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import java.awt.Canvas;
import org.hexgridapi.editor.utility.gui.Base3DModuleTab;

/**
 *
 * @author roah
 */
class testRootModule extends Base3DModuleTab {

    public testRootModule(Application app) {
        super(app.getAssetManager().loadTexture("org/hexgridapi/assets/Textures/"
                + "Icons/Buttons/hexIconBW.png").getImage(), 
                "hello test Module", new Node("xoxo"), true);
    }

    @Override
    public void onContextGainFocus(final SimpleApplication app, Canvas canvas) {
        add(canvas);
    }

    @Override
    public void onContextLostFocus() {
    }
    
}