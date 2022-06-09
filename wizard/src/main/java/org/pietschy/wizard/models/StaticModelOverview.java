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

package org.pietschy.wizard.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.pietschy.wizard.I18n;
import org.pietschy.wizard.WizardStep;

/**
 * This class provides an overview panel for instances of {@link StaticModel}.
 */
public class StaticModelOverview extends JPanel implements PropertyChangeListener {
    private final StaticModel model;
    private final HashMap<WizardStep, JLabel> labels = new HashMap<>();
    private boolean leftMouseDown = false;

    public StaticModelOverview(StaticModel model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel(I18n.getString("StaticModelOverview.title"));
        title.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));

        title.setAlignmentX(0);
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title.getMaximumSize().height));
        add(title);
        int i = 1;
        for (WizardStep step : model.steps()) {
            JLabel label = new JLabel("" + i + ". " + step.getName());
            i++;
            label.setBackground(new Color(240, 240, 240));
            label.setBorder(createEmptyBorder());
            label.setAlignmentX(0);
            label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getMaximumSize().height));
            addStepJumpingMouseListener(label, step);
            add(label);
            labels.put(step, label);
        }

        add(Box.createGlue());
    }

    /**
     * Adds a mouse listener to the given label which allows jumping directly to the given step.
     */
    private void addStepJumpingMouseListener(JLabel label, WizardStep step) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (leftMouseDown) {
                    jumpIfAllowed();
                } else if (isJumpAllowed(step)) {
                    label.setBorder(createHoverOverBorder());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBorder(createEmptyBorder());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    leftMouseDown = true;
                    label.setBorder(createEmptyBorder());
                    jumpIfAllowed();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    leftMouseDown = false;
                }
            }

            private void jumpIfAllowed() {
                if (isJumpAllowed(step)) {
                    model.jumpToStep(step);
                }
            }

            private boolean isJumpAllowed(WizardStep ws) {
                if (ws == model.getActiveStep()) {
                    return false;
                }
                return model.isJumpAllowed(ws);
            }
        });
    }

    /** Creates an empty border for a wizard step. */
    private static Border createEmptyBorder() {
        return BorderFactory.createEmptyBorder(2, 4, 2, 4);
    }

    /** Creates border to use when hovering over a wizard step. */
    private static Border createHoverOverBorder() {
        return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray),
                BorderFactory.createEmptyBorder(1, 3, 1, 3));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("activeStep")) {
            JLabel old = labels.get(evt.getOldValue());
            if (old != null) {
                formatInactive(old);
            }

            JLabel label = labels.get(evt.getNewValue());
            formatActive(label);
            repaint();
        }
    }

    protected void formatActive(JLabel label) {
        label.setOpaque(true);
    }

    protected void formatInactive(JLabel label) {
        label.setOpaque(false);
    }
}
