-= Wizard Framework =-

1. Basic Usage
SimpleModel model = new SimleModel();

model.add(new MyFirstStep());
model.add(...);
model.add(...);

Wizard wizard = new Wizard(model);

wizard.showInFrame("My first wizard");

2. Basic WizardStep

public class MyFirstStep
extends WizardStep
{

   public MyFirstStep()
   {
      setView(new JLabel("My First Step!!!"));
   }

   public void init(WizardModel model)
   {
      // don't care, but might cast to a more specific model for later
      // use.
   }

   public void prepare()
   {
      // probably read the model and configure my current view..
   }

   public void applyState()
   {

   }

}