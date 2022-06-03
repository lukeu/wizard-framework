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

class LastAction extends WizardAction {
    protected LastAction(Wizard model) {
        super("last", model);
    }

    @Override
    public void doAction(ActionEvent e) throws InvalidStateException {
        getModel().getActiveStep().applyState();
        getModel().lastStep();
    }

    @Override
    protected void updateState() {
        WizardStep activeStep = getModel().getActiveStep();
        boolean busy = activeStep != null && activeStep.isBusy();
        setEnabled(getModel().isLastAvailable() && !busy);
    }
}
