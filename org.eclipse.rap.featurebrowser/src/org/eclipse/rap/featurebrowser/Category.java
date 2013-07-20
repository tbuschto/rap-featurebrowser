package org.eclipse.rap.featurebrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class Category {


  private TreeItem item;

  public Category( FeatureTree featureTree, String name ) {
    Tree tree = featureTree.getTree();
    item = new TreeItem( tree, SWT.NONE );
    item.setText( name );
  }

  public void addFeature( String name, FeatureCreator feature ) {
    TreeItem featureItem = new TreeItem( item, SWT.NONE );
    featureItem.setText( name );
    featureItem.setData( feature );
    item.setExpanded( true );
  }

}
