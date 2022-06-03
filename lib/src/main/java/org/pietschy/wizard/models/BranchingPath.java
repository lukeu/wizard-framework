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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pietschy.wizard.WizardStep;

/**
 * BranchingPaths represent a sequence of {@link WizardStep}s that has multiple
 * choices for the next path to traverse.
 *
 * @see #addBranch
 * @see #addStep
 */
public class BranchingPath extends Path {
    private HashMap paths = new HashMap();

    /**
     * Creates a new empth BranchingPath.
     */
    public BranchingPath() {
    }

    /**
     * Creates a new BranchingPath that is initialized with the specified step.
     *
     * @param step the first step of the path.
     */
    public BranchingPath(WizardStep step) {
        addStep(step);
    }

    @Override
    protected Path getNextPath(MultiPathModel model) {
        for (Iterator iter = paths.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            Condition condition = (Condition) entry.getKey();
            if (condition.evaluate(model)) {
                return (Path) entry.getValue();
            }
        }

        throw new IllegalStateException("No next path selected");
    }

    /**
     * Adds a possible branch from this path.
     *
     * @param path      the {@link Path} to traverse based when the condition
     *                  returns <tt>true</tt>.
     * @param condition a {@link Condition} that activates this path.
     */
    public void addBranch(Path path, Condition condition) {
        paths.put(condition, path);
    }

    @Override
    public void acceptVisitor(PathVisitor visitor) {
        visitor.visitPath(this);
    }

    public void visitBranches(PathVisitor visitor) {
        for (Iterator iter = paths.values().iterator(); iter.hasNext();) {
            Path path = (Path) iter.next();
            path.acceptVisitor(visitor);
        }
    }
}
