package org.eclipse.rap.featurebrowser.ui;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.rap.featurebrowser.FeatureBrowser;


public class FeaturePage {

  private Feature feature;
  private FeatureBrowser browser;

  FeaturePage( FeatureBrowser browser, Feature feature ) {
    this.feature = feature;
    this.browser = browser;
    browser.getDemoArea().setFeature( feature );
    browser.getResourcesArea().setFeature( feature );
  }

  public void dispose() {
    browser.getDemoArea().clearContents();
    browser.getResourcesArea().clearContents();
  }

  public Feature getFeature() {
    return feature;
  }

}
