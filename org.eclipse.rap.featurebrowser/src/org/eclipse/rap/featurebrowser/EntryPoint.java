package org.eclipse.rap.featurebrowser;

import org.eclipse.rap.featurebrowser.features.AbstractFeature;
import org.eclipse.rap.featurebrowser.features.LabelFeature;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;


public class EntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
      parent.setLayout( LayoutUtil.createVerticalLayout() );
      CLabel header = new CLabel( parent, SWT.NONE );
      header.setText( ">>  REMOTE APPLICATION PLATFORM FEATURE BROWSER" );
      header.setMargins( 16, 16, 30, 10 );
      header.setData( RWT.CUSTOM_VARIANT, "header" );
      header.setLayoutData( LayoutUtil.createVerticalLayoutData( 45 ) );
      Composite subheader = new Composite( parent, SWT.NONE );
      subheader.setData( RWT.CUSTOM_VARIANT, "subheader" );
      subheader.setLayoutData( LayoutUtil.createVerticalLayoutData( LayoutUtil.BARWIDTH ) );
      Composite main = LayoutUtil.createHorizontalComposite( parent, 2 );
      main.setLayoutData( LayoutUtil.createFillData() );
      FeatureTree featureTree = new FeatureTree( main );
      Category widgets = new Category( featureTree, "Widgets" );
      widgets.addFeature( "Label", new FeatureCreator() {
        @Override
        public AbstractFeature createFeature( Composite parent ) {
          return new LabelFeature( parent );
        }
      } );
    }

}
