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

import java.awt.BorderLayout;
import java.util.List;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.netbeans.spi.dashboard.DashboardDisplayer;
import org.netbeans.spi.dashboard.DashboardWidget;
import org.netbeans.spi.dashboard.WidgetElement;
import org.openide.util.Lookup;

/**
 *
 */
final class WidgetPanel extends JPanel {

    private final DashboardDisplayer displayer;
    private final String id;
    private final DashboardWidget widget;
    private final Accessor accessor;
    
    private String title;
    private List<WidgetElement> elements;
    
    private WidgetPanel(DashboardDisplayer displayer, String id, DashboardWidget widget) {
        this.displayer = Objects.requireNonNull(displayer);
        this.id = Objects.requireNonNull(id);
        this.widget = Objects.requireNonNull(widget);
        accessor = new Accessor();
        setLayout(new BorderLayout());
        title = "";
        elements = List.of();
        attachWidget();
    }
    
    private void attachWidget() {
        widget.attach(accessor);
        reconfigure();
    }
    
//    TODO : when customization added
//    void detachWidget() {
//        widget.detach(accessor);
//    }
    
    void notifyShowing() {
        widget.showing(accessor);
    }
    
    void notifyHidden() {
        widget.hidden(accessor);
    }
    
    private void reconfigure() {
        String newTitle = widget.title(accessor);
        List<WidgetElement> newElements = List.copyOf(widget.elements(accessor));
        if (!Objects.equals(title, newTitle) ||
                !Objects.equals(elements, newElements)) {
            this.title = newTitle;
            this.elements = newElements;
            build();
        }
    }
    
    private void build() {
        removeAll();
        if (!title.isEmpty()) {
            add(WidgetComponents.titleComponentFor(title), BorderLayout.NORTH);
        }
        JScrollPane scrollPane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        Box container = Box.createVerticalBox();
        scrollPane.setViewportView(container);
        for (WidgetElement element : elements) {
            JComponent cmp = WidgetComponents.componentFor(element);
            if (cmp != null) {
                container.add(cmp);
            }
        }
    }
    
    private class Accessor implements DashboardDisplayer.Panel {

        @Override
        public DashboardDisplayer displayer() {
            return displayer;
        }
        
        @Override
        public Lookup getLookup() {
            return Lookup.EMPTY;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void refresh() {
            reconfigure();
        }
        
    }
    
    static WidgetPanel create(DashboardDisplayer displayer, DashboardDisplayer.WidgetReference widgetRef) {
        return new WidgetPanel(displayer, widgetRef.id(), widgetRef.widget());
    }
    
}
