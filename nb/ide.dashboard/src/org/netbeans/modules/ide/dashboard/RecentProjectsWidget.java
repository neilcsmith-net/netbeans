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

package org.netbeans.modules.ide.dashboard;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.modules.project.ui.api.RecentProjects;
import org.netbeans.modules.project.ui.api.UnloadedProjectInformation;
import org.netbeans.spi.dashboard.DashboardDisplayer;
import org.netbeans.spi.dashboard.DashboardWidget;
import org.netbeans.spi.dashboard.WidgetElement;
import org.openide.awt.Actions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

/**
 * List of recent projects.
 */
@Messages({
    "TITLE_RecentProjects=Recent Projects",
    "LBL_NoRecentProjects=<no recent projects>"
})
public class RecentProjectsWidget implements DashboardWidget {
    
    private static final int MAX_PROJECTS = 5;
    
    private static final RequestProcessor RP = new RequestProcessor("RecentProjects");

    private final List<WidgetElement> elements;
    private final List<UnloadedProjectInformation> projects;
    
    private final Action newProject;
    private final Action openProject;
    
    public RecentProjectsWidget() {
        elements = new ArrayList<>();
        projects = new ArrayList<>();
        Action newProjectOriginal = Actions.forID("Project", "org.netbeans.modules.project.ui.NewProject");
        if (newProjectOriginal != null) {
            newProject = new ProjectDelegateAction(newProjectOriginal);
        } else {
            newProject = null;
        }
        Action openProjectOriginal = Actions.forID("Project", "org.netbeans.modules.project.ui.OpenProject");
        if (openProjectOriginal != null) {
            openProject = new ProjectDelegateAction(openProjectOriginal);
        } else {
            openProject = null;
        }
        elements.add(WidgetElement.text(Bundle.LBL_NoRecentProjects()));
        if (newProject != null) {
            elements.add(WidgetElement.actionLink(newProject));
        }
        if (openProject != null) {
            elements.add(WidgetElement.actionLink(openProject));
        }
    }
    
    private void buildElements() {
        elements.clear();
        if (projects.isEmpty()) {
            elements.add(WidgetElement.text(Bundle.LBL_NoRecentProjects()));
        }
        if (newProject != null) {
            elements.add(WidgetElement.actionLink(newProject));
        }
        if (openProject != null) {
            elements.add(WidgetElement.actionLink(openProject));
        }
    }
    
    @Override
    public String title(DashboardDisplayer.Panel panel) {
        return Bundle.TITLE_RecentProjects();
    }

    @Override
    public List<WidgetElement> elements(DashboardDisplayer.Panel panel) {
        return List.copyOf(elements);
    }
    
    private static class ProjectDelegateAction extends AbstractAction {
        
        private Action delegate;
        
        private ProjectDelegateAction(Action delegate) {
            super(Actions.cutAmpersand(String.valueOf(delegate.getValue(NAME))));
            this.delegate = delegate;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            delegate.actionPerformed(e);
        }
        
    }


}
