package org.eclipse.rap.featurebrowser.ui;

import static org.eclipse.rap.featurebrowser.GridDataUtil.applyGridData;
import static org.eclipse.rap.featurebrowser.GridLayoutUtil.applyGridLayout;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class FeaturePage {

  private SashForm page;

  FeaturePage( Composite parent, Feature feature, FeatureTree featureTree ) {
    page = new SashForm( parent, SWT.HORIZONTAL );
    applyGridData( page ).fill();
    page.setSashWidth( 1 );
    switch( feature.getView() ) {
      case SNIPPET:
        new SnippetInstanceArea( page, feature );
        createSnippetSource( feature );
        page.setWeights( new int[]{ 40, 60 } );
      break;
      case GALLERY:
        new FeatureGallery( page, feature, featureTree );
        if( feature.getUrl() != null ) {
          createBrowser( feature );
          page.setWeights( new int[]{ 40, 60 } );
        }
      break;
      case PREVIEW:
        new FeaturePreview( page, feature, featureTree );
        if( feature.getUrl() != null ) {
          createBrowser( feature );
          page.setWeights( new int[]{ 40, 60 } );
        }
      break;
      case NONE:
      default:
      break;
    }
  }

  public void createBrowser( Feature feature ) {
    Composite wrapper = new Composite( page, SWT.NONE );
    Browser browser = new Browser( wrapper, SWT.NONE );
    browser.setUrl( feature.getUrl() );
    wrapper.setLayout( new FillLayout() );
    applyGridData( wrapper ).fill();
    applyGridLayout( wrapper ).marginLeft( 10 );
    applyGridData( browser ).fill();
  }

  public void createSnippetSource( Feature feature ) {
    Browser browser = new Browser( page, SWT.NONE );
    browser.setUrl( feature.getSnippetHtmlUrl() );
  }

  public void dispose() {
    page.dispose();
  }

}
