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

package org.netbeans.spi.dashboard;

import java.util.List;
import java.util.Objects;
import org.openide.util.Lookup;

/**
 *
 */
public interface DashboardDisplayer extends Lookup.Provider {
    
    public void show(String category, List<WidgetReference> widgets);
    
    public static interface Panel extends Lookup.Provider {
        
        public DashboardDisplayer displayer();

        @Override
        public default Lookup getLookup() {
            return displayer().getLookup();
        }
        
        public String id();
        
        public void refresh();
        
    }
    
    public static final class WidgetReference implements Lookup.Provider {

        private final String id;
        private final DashboardWidget widget;
        private final Lookup lookup;
        
        public WidgetReference(String id, DashboardWidget widget) {
            this(id, widget, Lookup.EMPTY);
        }
        
        public WidgetReference(String id, DashboardWidget widget, Lookup lookup) {
            this.id = Objects.requireNonNull(id);
            this.widget = Objects.requireNonNull(widget);
            this.lookup = Objects.requireNonNull(lookup);
        }
        
        public String id() {
            return id;
        }
        
        public DashboardWidget widget() {
            return widget;
        }
        
        @Override
        public Lookup getLookup() {
            return lookup;
        }
        
        
        
    }

}
