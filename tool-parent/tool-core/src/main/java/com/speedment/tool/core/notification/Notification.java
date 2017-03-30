/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.tool.core.notification;


import com.speedment.tool.core.brand.Palette;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

/**
 * Container for the fields required to show a notification in the UI.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public interface Notification {
    
    /**
     * The Icon to use in this notification.
     * 
     * @return  the icon
     */
    FontAwesomeIcon icon();
    
    /**
     * The text message to show in the notification.
     * 
     * @return  the text message
     */
    String text();
    
    /**
     * The color palette to use for the icon and border.
     * 
     * @return  the color palette
     */
    Palette palette();
    
    /**
     * An action that should be called when the notification is closed.
     * 
     * @return  closing action
     */
    Runnable onClose();
}