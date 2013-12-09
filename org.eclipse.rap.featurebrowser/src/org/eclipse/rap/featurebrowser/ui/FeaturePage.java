package org.eclipse.rap.featurebrowser.ui;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.rap.featurebrowser.FeatureBrowser;


public class FeaturePage {

  private Feature feature;
  private FeatureBrowser browser;

  FeaturePage( FeatureBrowser browser, Feature feature, FeatureTree featureTree ) {
    this.feature = feature;
    this.browser = browser;
//    page = new SashForm( browser.getMainComposite(), SWT.HORIZONTAL );
//    style( page ).background( "transparent" );
//    applyGridData( page ).fill();
//    page.setSashWidth( 8 );
    switch( feature.getView() ) {
      case SNIPPET:
        browser.getDemoArea().setFeature( feature );
        browser.getResourcesArea().setFeature( feature );
      break;
//      case GALLERY:
//        new FeatureGallery( page, feature, featureTree );
//        if( feature.getUrl() != null ) {
//          createAttachedBrowser( feature );
//          page.setWeights( new int[]{ 40, 60 } );
//        }
//      break;
//      case PREVIEW:
//        new FeaturePreview( page, feature, featureTree );
//        if( feature.getUrl() != null ) {
//          createAttachedBrowser( feature );
//          page.setWeights( new int[]{ 40, 60 } );
//        }
//      break;
//      case NONE:
      default:
      break;
    }
  }

//  public void createAttachedBrowser( Feature feature ) {
//    Composite wrapper = new Composite( page, SWT.NONE );
//    Browser browser = createBrowser( wrapper, feature.getUrl() );
//    wrapper.setLayout( new FillLayout() );
//    applyGridData( wrapper ).fill();
//    applyGridLayout( wrapper ).marginLeft( 10 );
//    applyGridData( browser ).fill();
//  }

  public void dispose() {
    browser.getDemoArea().clearContents();
    browser.getResourcesArea().clearContents();
  }

  public Feature getFeature() {
    return feature;
  }

}
