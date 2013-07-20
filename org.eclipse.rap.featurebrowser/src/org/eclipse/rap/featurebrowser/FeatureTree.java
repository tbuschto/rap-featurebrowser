package org.eclipse.rap.featurebrowser;

import org.eclipse.rap.featurebrowser.features.AbstractFeature;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class FeatureTree {

  private Tree tree;
  private AbstractFeature currentFeature;
  private FeatureCreator currentFeatureCreator;

  public FeatureTree( final Composite parent ) {
    Composite outer = LayoutUtil.createHorizontalComposite( parent, 2 );
    outer.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ) );
    tree = new Tree( outer, SWT.FULL_SELECTION );
    final Button bar = new Button( outer, SWT.PUSH | SWT.CENTER );
    tree.setLayoutData( LayoutUtil.createHorizontalLayoutData( 150 ) );
    tree.setData( RWT.CUSTOM_VARIANT, "vbar" );
    bar.setText( "<" );
    bar.setLayoutData( LayoutUtil.createHorizontalLayoutData( LayoutUtil.BARWIDTH ) );
    bar.setData( RWT.CUSTOM_VARIANT, "vbar" );
    bar.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        boolean visible = !tree.getVisible();
        bar.setText( visible ? "<" : ">" );
        GridData data = ( GridData )tree.getLayoutData();
        data.exclude = !visible;
        tree.setVisible( visible );
        parent.layout();
      }
    } );
    tree.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        Object data = event.item.getData();
        if( data instanceof FeatureCreator && currentFeatureCreator != data ) {
          currentFeatureCreator = ( FeatureCreator )data;
          currentFeature = currentFeatureCreator.createFeature( parent );
          parent.layout();
        }
      }
    } );
  }

  public Tree getTree() {
    return tree;
  }

}
