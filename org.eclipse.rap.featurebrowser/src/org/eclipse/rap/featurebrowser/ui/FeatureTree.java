package org.eclipse.rap.featurebrowser.ui;

import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.rap.featurebrowser.Navigation;
import org.eclipse.rap.featurebrowser.visitor.TreeVisitor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class FeatureTree {

  private Tree tree;
  private FeaturePage featurePage;
  private TreeViewer treeViewer;

  public FeatureTree( final Composite parent, Feature category  ) {
    tree = new Tree( parent, SWT.SINGLE );
    tree.setData( RWT.CUSTOM_VARIANT, "featuretree" );
    treeViewer = new TreeViewer( tree );
    treeViewer.setContentProvider( new ITreeContentProvider() {
      public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {}
      public void dispose() { }
      public boolean hasChildren( Object element ) {
        return ( ( Feature )element ).getChildren() != null;
      }
      public Object getParent( Object element ) {
        return null;
      }
      public Object[] getElements( Object inputElement ) {
        return ( ( Feature )inputElement ).getChildren();
      }
      public Object[] getChildren( Object parentElement ) {
        return ( ( Feature )parentElement ).getChildren();
      }
    } );
    treeViewer.setLabelProvider( new LabelProviderImplementation() );
    treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        IStructuredSelection sel = ( IStructuredSelection )event.getSelection();
        if( featurePage != null ) {
          featurePage.dispose();
        }
        Feature feature = ( Feature )sel.getFirstElement();
        featurePage = new FeaturePage( parent, feature, FeatureTree.this );
        parent.layout();
        Navigation.getInstance().push( feature );
      }
    } );
    treeViewer.setInput( category );
    treeViewer.expandAll();
    TreeVisitor.forEach( treeViewer.getTree(), new TreeVisitor() {
      @Override
      public void visit( TreeItem item ) {
        item.setData( RWT.CUSTOM_VARIANT, "featuretree" );
      }
    } );
  }

  private final class LabelProviderImplementation implements ILabelProvider, IFontProvider {

    public void removeListener( ILabelProviderListener listener ) {
    }

    public boolean isLabelProperty( Object element, String property ) {
      return false;
    }

    public void dispose() {
    }

    public void addListener( ILabelProviderListener listener ) {
    }

    public String getText( Object element ) {
      return ( ( Feature )element ).getName();
    }

    public Image getImage( Object element ) {
      return null;
    }

    public Font getFont( Object element ) {
      Feature feature = ( Feature )element;
      boolean category = feature.getChildren() != null;
      boolean exclusive = feature.isExclusive();
      if( category || exclusive ) {
        Display display = tree.getDisplay();
        FontData fontData = tree.getFont().getFontData()[ 0 ];
        if( exclusive ) {
          fontData.setStyle( SWT.ITALIC );
        }
        if( category ) {
          //fontData.setStyle( SWT.BOLD );
        }
        return new Font( display, fontData );
      }
      return null;
    }
  }

  public void select( Feature feature ) {
    TreePath path = new TreePath( feature.getPath() );
    treeViewer.setSelection( new TreeSelection( path ) );
  }

  public Control getControl() {
    return tree;
  }

}