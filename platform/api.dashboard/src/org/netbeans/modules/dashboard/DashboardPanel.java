/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.dashboard;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.netbeans.spi.dashboard.DashboardDisplayer;

/**
 *
 */
final class DashboardPanel extends JPanel {

    private static final int WIDGET_WIDTH = 250;
    private static final int WIDGET_HEIGHT = 250;

    private final List<WidgetPanel> widgetPanels;
    
    private DashboardPanel(DashboardDisplayer displayer,
            List<DashboardDisplayer.WidgetReference> widgetRefs) {
        widgetPanels = new ArrayList<>(widgetRefs.size());
        setLayout(new WidgetLayout());
        for (var ref : widgetRefs) {
            WidgetPanel w = WidgetPanel.create(displayer, ref);
            w.setSize(WIDGET_WIDTH, WIDGET_HEIGHT);
            add(w);
            widgetPanels.add(w);
        }
    }
    
    void notifyShowing() {
        widgetPanels.forEach(WidgetPanel::notifyShowing);
    }

    void notifyHidden() {
        widgetPanels.forEach(WidgetPanel::notifyHidden);
    }
    
    DashboardPanel create(DashboardDisplayer displayer,
            List<DashboardDisplayer.WidgetReference> widgetRefs) {
        return new DashboardPanel(displayer, widgetRefs);
    }
    
    private static class WidgetLayout extends FlowLayout {

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return super.preferredLayoutSize(target);
        }
        
        
        
    }

}
