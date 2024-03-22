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

import java.net.URI;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.Action;
import javax.swing.JComponent;

/**
 *
 */
public sealed abstract class WidgetElement {

    private WidgetElement() {
    }

    public static TextElement text(String text) {
        return new TextElement(text);
    }

    public static ImageElement image(String resourcePath) {
        return new ImageElement(resourcePath);
    }
    
    public static ActionElement action(Action action) {
        return new ActionElement(action, false, true);
    }
    
    public static ActionElement actionNoIcon(Action action) {
        return new ActionElement(action, false, false);
    }
    
    public static ActionElement actionLink(Action action) {
        return new ActionElement(action, true, true);
    }
    
    public static ActionElement actionLinkNoIcon(Action action) {
        return new ActionElement(action, true, false);
    }
    
    public static LinkElement link(String text, URI link) {
        return new LinkElement(text, link, false);
    }
    
    public static LinkElement linkButton(String text, URI link) {
        return new LinkElement(text, link, true);
    }
    

    public static ComponentElement component(Supplier<JComponent> componentSupplier) {
        return new ComponentElement(componentSupplier);
    }

    public static final class TextElement extends WidgetElement {

        private final String text;

        TextElement(String text) {
            this.text = Objects.requireNonNull(text);
        }

        public String text() {
            return text;
        }

    }

    public static final class ImageElement extends WidgetElement {

        private final String resourcePath;

        ImageElement(String resourcePath) {
            this.resourcePath = Objects.requireNonNull(resourcePath);
        }

        public String resourcePath() {
            return resourcePath;
        }

    }

    public static final class ActionElement extends WidgetElement {
        
        private final Action action;
        private final boolean link;
        private final boolean icon;
        
        ActionElement(Action action, boolean link, boolean icon) {
            this.action = Objects.requireNonNull(action);
            this.link = link;
            this.icon = icon;
        }
        
        public Action action() {
            return action;
        }
        
        public boolean asLink() {
            return link;
        }
        
        public boolean showIcon() {
            return icon;
        }

    }
    
    public static final class LinkElement extends WidgetElement {
        
        private final String text;
        private final URI link;
        private final boolean button;
        
        LinkElement(String text, URI link, boolean button) {
            this.text = Objects.requireNonNull(text);
            this.link = Objects.requireNonNull(link);
            this.button = button;
        }
        
        public String text() {
            return text;
        }
        
        public URI link() {
            return link;
        }
        
        public boolean asButton() {
            return button;
        }
        
    }

    public static final class ComponentElement extends WidgetElement {

        private final Supplier<JComponent> componentSupplier;

        ComponentElement(Supplier<JComponent> componentSupplier) {
            this.componentSupplier = Objects.requireNonNull(componentSupplier);
        }

        public Supplier<JComponent> componentSupplier() {
            return componentSupplier;
        }

        public JComponent component() {
            return componentSupplier.get();
        }

    }

}
