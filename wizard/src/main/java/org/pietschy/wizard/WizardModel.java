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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pietschy.wizard.models.MultiPathModel;
import org.pietschy.wizard.models.StaticModel;

/**
 * This interface defines the Model for wizards. It provides various methods for
 * determining the steps of the wizard along with the current traversal state of
 * the wizard. The {@link Wizard} monitors the model using a
 * {@link PropertyChangeListener} so all changes to the model properties must
 * fire {@link PropertyChangeEvent}s.
 * <p>
 * Most users should subclass either {@link StaticModel} or
 * {@link MultiPathModel}. Implementors of more specialized models should
 * consider extending {@link AbstractWizardModel} as it provides the basic
 * methods and propery change management required by all models.
 */
public interface WizardModel {

    /**
     * Checks if the previous button should be enabled.
     *
     * @return {@code true} if the previou button should be enabled, {@code false}
     *         otherwise.
     */
    boolean isPreviousAvailable();

    /**
     * Checks if the next button should be enabled.
     *
     * @return {@code true} if the next button should be enabled, {@code false}
     *         otherwise.
     */
    boolean isNextAvailable();

    /**
     * Checks if the last button should be enabled.
     *
     * @return {@code true} if the last button should be enabled, {@code false}
     *         otherwise.
     * @see #isLastVisible
     */
    boolean isLastAvailable();

    /**
     * Increments the model the the next step and fires the appropriate property
     * change events. This method must only be called if {@link #isNextAvailable}
     * returns {@code true}.
     */
    void nextStep();

    /**
     * Takes the model back to the previsou step and fires the appropriate property
     * change events. This method must only be called if
     * {@link #isPreviousAvailable} returns {@code true}.
     */
    void previousStep();

    /**
     * Takes the model to the last step in the wizard and fires the appropriate
     * property change events. This method must only be called if
     * {@link #isLastAvailable} returns {@code true}.
     */
    void lastStep();

    /**
     * Checks if the last button should be displayed. This method should only return
     * true if the {@link #isLastAvailable} will return true at any point. Returning
     * false will prevent the last button from appearing on the wizard at all.
     *
     * @return {@code true} if the last button should be displayed, {@code false}
     *         otherwise.
     */
    boolean isLastVisible();

    /**
     * Takes the model back to the first step and fires the appropriate property
     * change events.
     */
    void reset();

    /**
     * Gets the current active step the wizard should display.
     *
     * @return the active step.
     */
    WizardStep getActiveStep();

    /**
     * Checks if the specified step is the last step in the wizard.
     *
     * @param step the step to check
     * @return {@code true} if its the final step in the wizard, {@code false}
     *         otherwise.
     */
    boolean isLastStep(WizardStep step);

    /**
     * Returns an iterator over all the steps in the model. The iteration order is
     * not guaranteed to be the order of traversal.
     *
     * @implSpec Implementations must not call the default implementation of {@code steps()}
     *           (It would trigger an infinite loop.)
     */
    Iterator<WizardStep> stepIterator();

    /**
     * Returns all the steps in the model.
     * The iteration order is not guaranteed to be the order of traversal.
     *
     * @implNote This default implementation works with unknown external implementations of
     * stepIterator
     */
    default List<WizardStep> steps() {
        ArrayList<WizardStep> steps = new ArrayList<>();
        stepIterator().forEachRemaining(steps::add);
        return steps;
    }

    /**
     * Adds a {@link PropertyChangeListener} to this model.
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes a {@link PropertyChangeListener} from this model.
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Adds a {@link PropertyChangeListener} to this model.
     *
     * @param propertyName the property to listen to.
     * @param listener     the listener to add.
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Removes a {@link PropertyChangeListener} from this model.
     *
     * @param propertyName the property to stop listening to.
     * @param listener     the listener to remove.
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Called to request the model to update it current state. This will be called
     * when ever a step transition occurs but may also be called by the current
     * {@link WizardStep} to force a refresh.
     */
    void refreshModelState();
}
