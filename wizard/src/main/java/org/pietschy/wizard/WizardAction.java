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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Base class for all Wizard actions.
 */
public abstract class WizardAction extends AbstractAction implements PropertyChangeListener {
    protected Wizard wizard;
    private WizardStep activeStep;

    protected WizardAction(String key, Wizard wizard, Icon icon) {
        this(key, wizard);
        putValue(Action.SMALL_ICON, icon);
    }

    protected WizardAction(String key, Wizard wizard) {
        super(I18n.getString(key + ".text"));
        this.wizard = wizard;
        getModel().addPropertyChangeListener(this);
        activeStep = getModel().getActiveStep();
        if (activeStep != null) {
            activeStep.addPropertyChangeListener(this);
        }

        putValue(Action.MNEMONIC_KEY, I18n.getMnemonic(key + ".mnemonic"));

        updateState();
    }

    protected Wizard getWizard() {
        return wizard;
    }

    public WizardModel getModel() {
        return getWizard().getModel();
    }

    public WizardStep getActiveStep() {
        return activeStep;
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        try {
            doAction(e);
        } catch (InvalidStateException ise) {
            handleInvalideStateException(ise);
        }
    }

    protected void handleInvalideStateException(InvalidStateException ise) {
        if (ise.isShowUser()) {
            JOptionPane.showMessageDialog(getWizard(), ise.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public abstract void doAction(ActionEvent e) throws InvalidStateException;

    protected abstract void updateState();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("activeStep")) {
            if (activeStep != null) {
                activeStep.removePropertyChangeListener(this);
            }
            activeStep = (WizardStep) evt.getNewValue();
            activeStep.addPropertyChangeListener(this);
        }

        updateState();
    }

}
