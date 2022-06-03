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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class provides a base for implementors of {@link WizardModel}. It
 * provides the basic {@link PropertyChangeListener} management and fires the
 * appropriate events when the various properties are changed.
 * <p>
 * Subclasses will generally override {@link #refreshModelState} to update the
 * state of the various model properties.
 */
public abstract class AbstractWizardModel implements WizardModel {
    private WizardStep activeStep;
    private boolean previousAvailable;
    private boolean nextAvailable;
    private boolean lastAvailable;
    private boolean cancelAvailable;
    private boolean lastVisible = true;
    private final PropertyChangeSupport pcs;

    private final PropertyChangeListener completeListener = evt -> {
        if (evt.getPropertyName().equals("complete")) {
            refreshModelState();
        }
    };

    public AbstractWizardModel() {
        pcs = new PropertyChangeSupport(this);
    }

    @Override
    public WizardStep getActiveStep() {
        return activeStep;
    }

    /**
     * Provided for subclasses to change the current step in response to a call to
     * {@link #nextStep} or its related methods.
     *
     * @param activeStep the new step.
     */
    protected void setActiveStep(WizardStep activeStep) {
        if (this.activeStep != activeStep) {
            WizardStep old = this.activeStep;
            this.activeStep = activeStep;
            pcs.firePropertyChange("activeStep", old, activeStep);
            refreshModelState();
        }
    }

    @Override
    public boolean isPreviousAvailable() {
        return previousAvailable;
    }

    @Override
    public boolean isNextAvailable() {
        return nextAvailable;
    }

    @Override
    public boolean isLastAvailable() {
        return lastAvailable;
    }

    /**
     * Configures if the previous button should be enabled.
     *
     * @param previousAvailable <tt>true</tt> to enable the previous button,
     *                          <tt>false</tt> otherwise.
     */
    protected void setPreviousAvailable(boolean previousAvailable) {
        if (this.previousAvailable != previousAvailable) {
            boolean old = this.previousAvailable;
            this.previousAvailable = previousAvailable;
            pcs.firePropertyChange("previousAvailable", old, previousAvailable);
        }
    }

    /**
     * Configures if the next button should be enabled.
     *
     * @param nextAvailable <tt>true</tt> to enable the next button, <tt>false</tt>
     *                      otherwise.
     */
    protected void setNextAvailable(boolean nextAvailable) {
        if (this.nextAvailable != nextAvailable) {
            boolean old = this.nextAvailable;
            this.nextAvailable = nextAvailable;
            pcs.firePropertyChange("nextAvailable", old, nextAvailable);
        }
    }

    /**
     * Configures if the last button should be enabled.
     *
     * @param lastAvailable <tt>true</tt> to enable the last button, <tt>false</tt>
     *                      otherwise.
     */
    protected void setLastAvailable(boolean lastAvailable) {
        if (this.lastAvailable != lastAvailable) {
            boolean old = this.lastAvailable;
            this.lastAvailable = lastAvailable;
            pcs.firePropertyChange("lastAvailable", old, lastAvailable);
        }
    }

    /**
     * Configures if the cncel button should be enabled.
     *
     * @param cancelAvailable <tt>true</tt> to enable the cancel button,
     *                        <tt>false</tt> otherwise.
     */
    protected void setCancelAvailable(boolean cancelAvailable) {
        if (this.cancelAvailable != cancelAvailable) {
            boolean old = this.cancelAvailable;
            this.cancelAvailable = cancelAvailable;
            pcs.firePropertyChange("cancelAvailable", old, cancelAvailable);
        }
    }

    @Override
    public boolean isLastVisible() {
        return lastVisible;
    }

    /**
     * Configures if the last button should be displayed.
     *
     * @param lastVisible <tt>true</tt> to display the last button, <tt>false</tt>
     *                    otherwise.
     * @see #isLastVisible
     */
    public void setLastVisible(boolean lastVisible) {
        if (this.lastVisible != lastVisible) {
            boolean old = this.lastVisible;
            this.lastVisible = lastVisible;
            pcs.firePropertyChange("lastVisible", old, lastVisible);
        }
    }

    @Override
    public void refreshModelState() {
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Adds a listener to the "complete" property of the {@link WizardStep}. Any
     * changes to this property will in automatically invoke
     * {@link #refreshModelState()}.
     *
     * @param step the {@link WizardStep} to monitor.
     */
    protected void addCompleteListener(WizardStep step) {
        step.addPropertyChangeListener(completeListener);
    }
}
