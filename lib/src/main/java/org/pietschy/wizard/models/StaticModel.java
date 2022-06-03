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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JComponent;

import org.pietschy.wizard.AbstractWizardModel;
import org.pietschy.wizard.OverviewProvider;
import org.pietschy.wizard.WizardStep;

/**
 * This class provides the basis for a simple linear wizard model. Steps are
 * added by calling the {@link #add} method and are traversed in the order of
 * addition.
 */
public class StaticModel extends AbstractWizardModel implements OverviewProvider {

    private ArrayList steps = new ArrayList();

    private int currentStep = 0;
    private StaticModelOverview overviewComponent;

    public StaticModel() {
    }

    @Override
    public void reset() {
        currentStep = 0;
        setActiveStep((WizardStep) steps.get(currentStep));
    }

    @Override
    public void nextStep() {
        if (currentStep >= steps.size() - 1) {
            throw new IllegalStateException("Already on last step");
        }

        currentStep++;
        setActiveStep((WizardStep) steps.get(currentStep));
    }

    @Override
    public void previousStep() {
        if (currentStep == 0) {
            throw new IllegalStateException("Already at first step");
        }

        currentStep--;
        setActiveStep((WizardStep) steps.get(currentStep));
    }

    @Override
    public void lastStep() {
        currentStep = steps.size() - 1;
        setActiveStep((WizardStep) steps.get(currentStep));
    }

    @Override
    public boolean isLastStep(WizardStep step) {
        return steps.indexOf(step) == steps.size() - 1;
    }

    @Override
    public Iterator stepIterator() {
        return Collections.unmodifiableList(steps).iterator();
    }

    /**
     * Adds a step to the end of the wizard.
     *
     * @param step the {@link WizardStep} to add.
     */
    public void add(WizardStep step) {
        steps.add(step);
        addCompleteListener(step);
    }

    @Override
    public void refreshModelState() {
        setNextAvailable(getActiveStep().isComplete() && !isLastStep(getActiveStep()));
        setPreviousAvailable(currentStep > 0);
        setLastAvailable(allStepsComplete() && !isLastStep(getActiveStep()));
        setCancelAvailable(true);
    }

    /**
     * Returns true if all the steps in the wizard return <tt>true</tt> from
     * {@link WizardStep#isComplete}. This is primarily used to determine if the
     * last button can be enabled.
     *
     * @return <tt>true</tt> if all the steps in the wizard are complete,
     *         <tt>false</tt> otherwise.
     */
    public boolean allStepsComplete() {
        for (Iterator iterator = steps.iterator(); iterator.hasNext();) {
            if (!((WizardStep) iterator.next()).isComplete()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public JComponent getOverviewComponent() {
        if (overviewComponent == null) {
            overviewComponent = new StaticModelOverview(this);
        }

        return overviewComponent;
    }
}
