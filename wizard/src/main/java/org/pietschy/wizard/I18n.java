/**
 * Wizard Framework
 * Copyright 2004 Andrew Pietsch or contributors
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.pietschy.wizard;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.KeyStroke;

/**
 * Internationalization Helper. By default this class attempts to load the
 * bundle called 'org-pietshcy-wizard' from the classpath but you can specify
 * you own bundle by calling the static {@link #setBundle} method.
 *
 * @author andrewp
 */
public class I18n {
    private static ResourceBundle bundle = null;

    private static ResourceBundle getBundle() {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("org-pietschy-wizard");
        }

        return bundle;
    }

    public static void setBundle(ResourceBundle bundle) {
        I18n.bundle = bundle;
    }

    public static String getString(String key) {
        return getBundle().getString(key);
    }

    public static Object getObject(String key) {
        return getBundle().getObject(key);
    }

    public static String[] getStringArray(String key) {
        return getBundle().getStringArray(key);
    }

    public static int getMnemonic(String key) {
        String mnemonicString = getBundle().getString(key);

        if (mnemonicString == null) {
            throw new MissingResourceException("Missing resource: " + key, I18n.class.getName(), key);
        }

        if (mnemonicString.length() != 1) {
            throw new IllegalStateException("mnemonic string invalid: " + mnemonicString);
        }

        KeyStroke ks = KeyStroke.getKeyStroke(mnemonicString.toUpperCase());

        if (ks == null) {
            throw new IllegalStateException("mnemonic string invalid: " + mnemonicString);
        }

        return ks.getKeyCode();
    }
}
