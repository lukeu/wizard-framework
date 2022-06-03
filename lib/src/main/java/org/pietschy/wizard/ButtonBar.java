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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The component that holds the wizards buttons. Subclasses may override
 * {@link #layoutButtons layoutButtons()} to customize the look of the wizrad.
 *
 * @see Wizard#createButtonBar()
 * @see #layoutButtons
 */
public class ButtonBar extends JPanel {

    public static final int RELATED_GAP = Wizard.BORDER_WIDTH / 2;
    public static final int UNRELATED_GAP = Wizard.BORDER_WIDTH;

    private Wizard wizard;
    private JButton lastButton;
    private JButton nextButton;
    private JButton previousButton;
    private JButton finishButton;
    private JButton cancelButton;
    private JButton closeButton;
    private JButton helpButton;

    protected Component lastButtonGap = Box.createHorizontalStrut(RELATED_GAP);
    protected Component helpButtonGap = Box.createHorizontalStrut(UNRELATED_GAP);

    public ButtonBar(Wizard wizard) {
        this.wizard = wizard;
        this.wizard.getModel().addPropertyChangeListener("lastVisible", evt -> configureLastButton());

        this.wizard.addPropertyChangeListener("helpBroker", evt -> configureHelpButton());

        previousButton = new JButton(wizard.getPreviousAction());
        nextButton = new JButton(wizard.getNextAction());
        nextButton.setHorizontalTextPosition(SwingConstants.LEADING);
        lastButton = new JButton(wizard.getLastAction());
        finishButton = new JButton(wizard.getFinishAction());
        cancelButton = new JButton(wizard.getCancelAction());
        closeButton = new JButton(wizard.getCloseAction());
        helpButton = new JButton(wizard.getHelpAction());

        setBorder(BorderFactory.createEmptyBorder(Wizard.BORDER_WIDTH, Wizard.BORDER_WIDTH, Wizard.BORDER_WIDTH,
                Wizard.BORDER_WIDTH));

        showCloseButton(false);

        equalizeButtonWidths(helpButton, previousButton, nextButton, lastButton, finishButton, cancelButton,
                closeButton);
        layoutButtons(helpButton, previousButton, nextButton, lastButton, finishButton, cancelButton, closeButton);

        configureLastButton();
        configureHelpButton();
    }

    private void configureLastButton() {
        setLastVisible(wizard.getModel().isLastVisible());
    }

    private void configureHelpButton() {
        setHelpVisible(wizard.getHelpBroker() != null);
    }

    /**
     * Called by the constructor to add the buttons to the button bar. This may be
     * overridden to alter the layout of the bar. Subclasses that override this
     * method are responsible for setting the layout manager using
     * {@link #setLayout(LayoutManager)}.
     *
     * @param help     the help button.
     * @param previous the previous button.
     * @param next     the next button
     * @param last     the last button
     * @param finish   the showCloseButton button
     * @param cancel   the cancel button.
     * @param close    the close button.
     */
    protected void layoutButtons(JButton help, JButton previous, JButton next, JButton last,
            JButton finish, JButton cancel, JButton close) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(help);
        add(helpButtonGap);
        add(Box.createHorizontalGlue());
        add(previous);
        add(Box.createHorizontalStrut(RELATED_GAP));
        add(next);
        add(lastButtonGap);
        add(last);
        add(Box.createHorizontalStrut(RELATED_GAP));
        add(finish);
        add(Box.createHorizontalStrut(UNRELATED_GAP));
        add(cancel);
        add(close);
    }

    /**
     * Call prior to {@link #layoutButtons} to make all the buttons the same width.
     */
    protected void equalizeButtonWidths(JButton help, JButton previous, JButton next,
            JButton last, JButton finish, JButton cancel, JButton close) {
        // make sure that every button has the same size
        Dimension d = new Dimension();
        JButton[] buttons = { help, previous, next, last, finish, cancel, close };
        for (JButton button : buttons) {
            Dimension buttonDim = button.getPreferredSize();
            if (buttonDim.width > d.width) {
                d.width = buttonDim.width;
            }
            if (buttonDim.height > d.height) {
                d.height = buttonDim.height;
            }
        }

        for (JButton button : buttons) {
            button.setPreferredSize(d);
        }
    }

    public void showCloseButton(boolean showClose) {
        previousButton.setVisible(!showClose);
        nextButton.setVisible(!showClose);
        lastButton.setVisible(!showClose);
        finishButton.setVisible(!showClose);
        cancelButton.setVisible(!showClose);
        closeButton.setVisible(showClose);
    }

    private void setLastVisible(boolean visible) {
        lastButton.setVisible(visible);
        lastButtonGap.setVisible(visible);
        revalidate();
        repaint();
    }

    private void setHelpVisible(boolean visible) {
        helpButton.setVisible(visible);
        helpButtonGap.setVisible(visible);
        revalidate();
        repaint();
    }
}
