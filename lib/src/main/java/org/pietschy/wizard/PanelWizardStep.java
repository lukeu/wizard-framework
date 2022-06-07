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

import javax.swing.Icon;
import javax.swing.JPanel;

/**
 * This is a base class for JPanel based wizard steps. Subclasses override the
 * methods {@link #init}, {@link #prepare}, {@link #applyState}.
 * <p>
 * The {@link Wizard} listens to property change events from the step and will
 * update accordingly when ever {@link #setComplete} or {@link #setBusy} is
 * called.
 * <p>
 * An example is shown below.
 *
 * <pre>
 *    public class MyWizardStep
 *    extends PanelWizardStep
 *    {
 *       private MyModel model;
 *       private JCheckBox agreeCheckbox;
 *       private JTextArea license;
 *
 *       public MyWizardStep()
 *       {
 *          super("My First Step", "A summary of the first step");
 *
 *          // build and layout the components..
 *          agreeCheckbox = new JCheckBox("Agree");
 *          license = new JTextArea();
 *          setLayout(...);
 *          add(agreeCheckbox);
 *          ...
 *
 *          // listen to changes in the state..
 *          agreeCheckbox.addItemListener(new ItemListener()
 *          {
 *             public void itemSelected(ItemEvent e)
 *             {
 *                // only continue if they agree
 *                MyWizardStep.this.<b>setComplete(agreeCheckbox.isSelected());</b>
 *             }
 *          });
 *       }
 *
 *       public void init(WizardModel model)
 *       {
 *          this.model = (MyModel) model;
 *       }
 *
 *       public void prepare()
 *       {
 *          // read the model and configure the panel
 *       }
 *
 *       public void applyState()
 *       throws InvalidStateException
 *       {
 *          // load a progress bar of some kind..
 *          ...
 *
 *          <b>setBusy(true);</b>
 *          try
 *          {
 *             // do some work on another thread.. see <a href=
"http://foxtrot.sourceforge.net/">Foxtrot</a>
 *             ...
 *          }
 *          finally
 *          {
 *             <b>setBusy(false);</b>
 *          }
 *
 *          // if error then throw an exception
 *          if (!ok)
 *          {
 *             // restore our original view..
 *             ......
 *             throw new InvalidStateException("That didn't work!");
 *          }
 *
 *          // this isn't really meaningful as we refuse to continue
 *          // while the checkbox is un-checked.
 *          model.setAcceptsLicense(agreeCheckbox.isSelected());
 *       }
 *
 *       public void getPreferredSize()
 *       {
 *          // use the size of our main view...
 *          return mainView.getPreferredSize();
 *       }
 *    }
 * </pre>
 */
public class PanelWizardStep extends JPanel implements WizardStep {
    /**
     * A summary of this step, or some usage advice.
     */
    private String summary;

    /**
     * An Icon that represents this step.
     */
    private Icon icon;

    /**
     * Marks this step as being fully configured. Only when this is {@code true}
     * can the wizard progress. This is a bound property.
     */
    private boolean complete;

    /**
     * Marks the task as being busy. While in this state the wizard will prevent cancel operations.
     */
    private boolean busy = false;

    /**
     * A default constructor to make this class JavaBean compatible.
     */
    public PanelWizardStep() {
    }

    /**
     * Creates a new step with the specified name and summary. The name and summary
     * are displayed in the wizard title block while this step is active.
     */
    public PanelWizardStep(String name, String summary) {
        this(name, summary, null);
    }

    /**
     * Creates a new step with the specified name and summary. The name and summary
     * are displayed in the wizard title block while this step is active.
     */
    public PanelWizardStep(String name, String summary, Icon icon) {
        setName(name);
        this.summary = summary;
        this.icon = icon;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    /**
     * Sets this steps summary. This will be displayed in the title of the wizard
     * while this step is active. The summary is typically an overview of the step
     * or some usage guidelines for the user.
     *
     * @param summary the summary of this step.
     */
    public void setSummary(String summary) {
        if ((this.summary != null && !this.summary.equals(summary)) || this.summary == null && summary != null) {
            String old = this.summary;
            this.summary = summary;
            firePropertyChange("summary", old, summary);
        }
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    /**
     * Sets the {@link javax.swing.Icon} that represents this step.
     *
     * @param icon the {@link javax.swing.Icon} that represents this step, or
     *             {@code null} if the step doesn't have an icon.
     */
    public void setIcon(Icon icon) {
        if ((this.icon != null && !this.icon.equals(icon)) || this.icon == null && icon != null) {
            Icon old = this.icon;
            this.icon = icon;
            firePropertyChange("icon", old, icon);
        }
    }

    /**
     * This implementation returns 'this'.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public Component getView() {
        return this;
    }

//   /**
//    * Sets the current view this step is displaying.  This component will be displayed in the main
//    * section of the wizard with this step is active.  This method may changed at any time and the
//    * wizard will update accordingly.
//    *
//    * @param component the current view of the step.
//    */
//   protected void
//   setView(Component component)
//   {
//      if (!component.equals(view))
//      {
//         Component old = view;
//         view = component;
//         pcs.firePropertyChange("view", old, view);
//      }
//   }

    @Override
    public boolean isComplete() {
        return complete;
    }

    /**
     * Marks this step as compete. The wizard will not be able to proceed from this
     * step until this property is configured to {@code true}.
     *
     * @param complete {@code true} to allow the wizard to proceed, {@code false}
     *                 otherwise.
     * @see #isComplete
     */
    public void setComplete(boolean complete) {
        if (this.complete != complete) {
            this.complete = complete;
            firePropertyChange("complete", !complete, complete);
        }
    }

    @Override
    public boolean isBusy() {
        return busy;
    }

    /**
     * Sets the busy state of this wizard step. This should usually be set when a
     * time consuming task is being performed on a background thread. The Wizard
     * responds by disabling the various buttons appropriately.
     * <p>
     * Wizard steps that go into a busy state must also implement {@link #abortBusy}
     * to cancel any in-progress operation.
     *
     * @param busy {@code true} to mark the step as busy and disable further user
     *             action, {@code false} to return the wizard to its normal state.
     */
    public void setBusy(boolean busy) {
        if (this.busy != busy) {
            boolean old = this.busy;
            this.busy = busy;
            firePropertyChange("busy", old, busy);
        }
    }

    /////////////////////////////////////////////////////////////////////
    // WizardStep Abstract Methods
    //

    @Override
    public void init(WizardModel model) {
    }

    @Override
    public void prepare() {
    }

    @Override
    public void applyState() throws InvalidStateException {
    }

    @Override
    public void abortBusy() {
    }
}
