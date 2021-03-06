
# Swing Wizard Framework

This format is (now) based on [Keep a Changelog](http://keepachangelog.com/)

## Not yet released...

### Added
 - Expose 'isCancelAvailable' and use in the CancelAction
 - Permit clicking the overview to jump to a step. (If using `StaticModel` and the code opts in)

### Fixed
 - Only respond to `setComplete` when there is an active step

## [0.2] - 2022-06-03

### Changed
 - Make copyright headers more inclusive to new authors
 - Switched to a Gradle build
 - Modernised the code:
   - Now requires Java 8 or later.
   - Reformatted to Google's Java Style Guidelines (with +4 space indent)
 - Fixed compiler warnings and enforce some strictly

-------------------------------------

# Original CHANGES.TXT ...

Changes in 0.1.12
- Updated LastAction to call applyState on the active page when pressed.

Changes in 0.1.11
- Added missing setters to AbstractWizardStep and PanelWizardStep and updated existing
  methods to fire property change events.
- Added the ability to specify a icon for wizards that use Wizard.showInFrame(..).

Changes in 0.1.10

Thanks to Bryan Scott and James Lemieux for their suggestions and contributions to
this release.

- Changed WizardStep from an abstract class to an interface.
  * The previous behaviour of WizardStep has been moved to AbstractWizardStep.
  * A new PanelWizardStep has been added that extends JPanel.
- Renamed AbstractWizardModel.afterStepChange() to refreshModelState() and made it a public
  method of the WizardModel interface.  This can now be invoked by wizard steps to force the
  model to refresh at any time.
- Added Wizard.setDefaultExitMode(int) and added the two constants EXIT_ON_FINISH and
  EXIT_ON_CLOSE.  Specifying EXIT_ON_FINSH will cause the wizard to exit without the user
  having to press close.  The default opertation is still EXIT_ON_CLOSE.
- Made WizardFrameCloser class public.
- Added AbstractWizardModel.addCompleteListener(WizardStep) to monitor the "complete" property
  of the step and call AbstractWizardModel.updateWizardState() when it changes.


Changes in 0.1.9
- Fixed null pointer exception in Wizard.showInDialog() (Issue 1,3).
- Stoped WizardActions printing stack traces when InvalidStateExceptions are thrown (Issue 4).
- Added a Wizard.wasCanceled() method to make it easier to check if a model wizard was
  canceled without having to implement a WizardListener.

Changes in 0.1.8
- Added patches submitted by Andrea Aime
 * Fixed exception when no resource bundle is available for the current locale.  Now defaults to
   English if no bundle is found.
 * Added separator above the button bar
 * Added WizardPane and WizardPaneStep classes to facilitate wizard construction in a GUI builder
   environment.
 * Changed the default text color in the title block to black.
 * Equalized the button widths in the button bar.
 * Made WizardModel.setLastVisible() public.

Changes in 0.1.7
- Renamed SimpleMode and SimpleModelOverview to StaticModel and StaticModelOverview.  This change is
  also reflected in the resource bundle for the StaticModelOverview.title property.
- Added new DynamicModel that sits between the static and multi-path models.
- Renamed BranchingCondition to the more generic Condition and renamed it's method from chooseWhen
  to evaluate.

Changes in 0.1.6
- Fixed internationalization bug with SimpleOverviewPanel and added formatting methods to
  simplify customization
- Renamed TitleComponent to DefaultTitleComponent and turned gradient off by default (as it
  looked really ugly when the overview component is visible).
- Allow developers to specify an explicit resource bundle to the I18n helper class.
- Added support for help via a HelpBroker property on the Wizard.  If non-null the help button
  will be visible and when clicked, the broker will be notified.  Also, if the Model implements
  HelpBroker, it will be automatically used, so you don't need to call Wizard.setHelpBroker(..)
  in this special case.
- Updated the title component to support HTML in the summary.


Changes in 0.1.5
- Renamed TitleBlock to TitleComponent and made it public.
- Added method Wizard.createTitleComponent to allow subclasses to override the look and behaviour of
  the wizard title area.
- Added a subtle gradient (white to light gray) to the TitleComponent and added the methods
  to disable it and control it's appearance.  See TitleComponent.setGradientBackground(boolean) and
  TitleComponent.prepareGradientPaint();

Changes in 0.1.4
- Added an optional icon to WizardStep.  The icon is displayed in the title panel.
- Renamed BranchingPath.addPath to BranchingPath.addBranch and PathSelector to BranchCondition
- Removed WizardModel.isCancelAvailable since cancel is now handled properly in the case of busy steps.
- Added convenience constructors for InvalidStateException that take the showUsers parameter.
- Made Wizard.createButtonBar protected to allow subclasses to alter the layout of the button bar.
- Changed org-pietschy-wizard_en_US.properties to org-pietschy-wizard_en.properties
- Updated javadoc

Changes in 0.1.3
- Added "busy" state to WizardStep.  When a step is busy, the wizard disables all the buttons
  except cancel.  If the wizard is cancelled while the current step is busy, the user is prompted
  to confirm.  If confirmed WizardStep.abortBusy() is invoked and the wizard is closed.
- Fixed bug in the build where the properties were not being included in the jar.
- Fixed bug where the close button wasn't internationalized.

Changes in 0.1.2
- Add support for models with mulitple paths.  See org.pietschy.wizard.model.MultiPathModel.
- Added support for internationalization using property resouce bundles.  Still only en_US defined
  at this point.  Additional languages can be supported by adding a property file to the class path
  with the form org-pietschy-wizard_en_US.properties.  See the existing file for details.

Changes in 0.1.1
- initial release