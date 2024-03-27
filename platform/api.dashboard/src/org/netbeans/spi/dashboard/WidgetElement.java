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

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 17 * hash + Objects.hashCode(this.text);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TextElement other = (TextElement) obj;
            return Objects.equals(this.text, other.text);
        }

        @Override
        public String toString() {
            return "TextElement{" + "text=" + text + '}';
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

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + Objects.hashCode(this.resourcePath);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ImageElement other = (ImageElement) obj;
            return Objects.equals(this.resourcePath, other.resourcePath);
        }

        @Override
        public String toString() {
            return "ImageElement{" + "resourcePath=" + resourcePath + '}';
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

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + Objects.hashCode(this.action);
            hash = 29 * hash + (this.link ? 1 : 0);
            hash = 29 * hash + (this.icon ? 1 : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ActionElement other = (ActionElement) obj;
            if (this.link != other.link) {
                return false;
            }
            if (this.icon != other.icon) {
                return false;
            }
            return Objects.equals(this.action, other.action);
        }

        @Override
        public String toString() {
            return "ActionElement{" + "action=" + action + ", link=" + link + ", icon=" + icon + '}';
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

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + Objects.hashCode(this.text);
            hash = 23 * hash + Objects.hashCode(this.link);
            hash = 23 * hash + (this.button ? 1 : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final LinkElement other = (LinkElement) obj;
            if (this.button != other.button) {
                return false;
            }
            if (!Objects.equals(this.text, other.text)) {
                return false;
            }
            return Objects.equals(this.link, other.link);
        }

        @Override
        public String toString() {
            return "LinkElement{" + "text=" + text + ", link=" + link + ", button=" + button + '}';
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

        @Override
        public String toString() {
            return "ComponentElement{" + "componentSupplier=" + componentSupplier + '}';
        }
        
        

    }

}
