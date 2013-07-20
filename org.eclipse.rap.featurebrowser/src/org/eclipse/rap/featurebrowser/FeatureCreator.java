package org.eclipse.rap.featurebrowser;

import org.eclipse.rap.featurebrowser.features.AbstractFeature;
import org.eclipse.swt.widgets.Composite;


public interface FeatureCreator {

  public AbstractFeature createFeature( Composite parent );

}
