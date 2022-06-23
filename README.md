# Swing Wizard Framework

A Swing wizard library for Java based on a `WizardModel` and a number of
`WizardStep`s.  The library comes with three models that can be extended, they are as follows.

 * [StaticModel](http://pietschy.com/software/wizard-framework/api/org/pietschy/wizard/models/StaticModel.html): The most basic model
 * [DynamicModel](http://pietschy.com/software/wizard-framework/api/org/pietschy/wizard/models/DynamicModel.html): Similar to the static model, except that steps can be skiped dynamically at runtime based on the users response.
 * [MultiPathModel](http://pietschy.com/software/wizard-framework/api/org/pietschy/wizard/models/MultiPathModel.html"): The most complex model, similar to the dynamic model, except that steps are grouped into paths, and paths are linked
together.

## History and plans

This was a project originally hosted on java.net ([snapshot on web.archive.org](https://web.archive.org/web/20070728043340/https://wizard-framework.dev.java.net/)) before that site was dismantled.

I reached out to the primary author, Andrew Pietsch, asking if he had any preference on how to host this before extending, and his reply was:

>  "That's all fine, I'm happy for you to do what's easiest for you."

So I've resurrected it here on github, and retained the original package `org.pietschy.*` for compatibility. (Note however, his website is on pietschy.com these days.)

The last binary release was v0.1.12, I've started by importing its source as the baseline, then:
 * Modernised the code - now requires Java 8 or later
 * Switched to a Gradle build
 * Applied a few changes more recent than the binary release that I needed. (I only had a snapshot of the original SVN repo, but I did my best to reconstruct them as independent commits from the svn tags - see the git history for details.)

I've then bumped the version to v0.2. The hope is to keep the API of 0.2.x backwardly
compatible with 0.1.12, so that folks can just bump versions and pick up fixes and any
new features.

Things planned:

 * Being able to click the Outline view to jump straight to a step, with the `StaticModel`
 * Fixing few visual glitches on HiDPI / non-integer scaling levels (particularly
   when running on Java 9 or later)