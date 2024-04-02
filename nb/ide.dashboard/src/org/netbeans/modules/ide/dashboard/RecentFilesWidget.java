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

import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.netbeans.modules.openfile.RecentFiles;
import org.netbeans.spi.dashboard.DashboardDisplayer;
import org.netbeans.spi.dashboard.DashboardWidget;
import org.netbeans.spi.dashboard.WidgetElement;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;


/**
 * List of recent files.
 */
@Messages({
    "TITLE_RecentFiles=Recent Files",
    "LBL_NoRecentFiles=<no recent files>"
})
public class RecentFilesWidget implements DashboardWidget {
    
    private static final int MAX_FILES = 5;
    
    private final List<WidgetElement> elements;
    
    private final Action newFile;
    private final Action openFile;
    
    public RecentFilesWidget() {
        elements = new ArrayList<>();
        newFile = null;
        openFile = null;
    }
    
    @Override
    public String title(DashboardDisplayer.Panel panel) {
        return Bundle.TITLE_RecentFiles();
    }

    @Override
    public List<WidgetElement> elements(DashboardDisplayer.Panel panel) {
        return List.of(WidgetElement.text(Bundle.LBL_NoRecentFiles()));
    }

}
