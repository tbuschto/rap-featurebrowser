package org.eclipse.rap.featurebrowser;

import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;


public class FeatureTree {

  private Tree tree;
  private FeaturePage featurePage;

  public FeatureTree( final Composite parent, Category category  ) {
    Composite outer = LayoutUtil.createHorizontalComposite( parent, 2 );
    outer.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ) );
    createTree( outer, parent, category );
    final Button bar = new Button( outer, SWT.PUSH | SWT.CENTER );
    tree.setLayoutData( LayoutUtil.createHorizontalLayoutData( 150 ) );
    tree.setData( RWT.CUSTOM_VARIANT, "featuretree" );
    outer.setData( RWT.CUSTOM_VARIANT, "featuretree" );
    bar.setText( "<" );
    bar.setLayoutData( LayoutUtil.createHorizontalLayoutData( LayoutUtil.BARWIDTH ) );
    bar.setData( RWT.CUSTOM_VARIANT, "vbar" );
    bar.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        boolean visible = !tree.getVisible();
        bar.setText( visible ? "<" : ">" );
        GridData data = ( GridData )tree.getLayoutData();
        data.exclude = !visible;
        tree.setVisible( visible );
        parent.layout();
      }
    } );
  }

  public void createTree( Composite outer, final Composite parent, Category features ) {
    tree = new Tree( outer, SWT.FULL_SELECTION );
    TreeViewer treeViewer = new TreeViewer( tree );
    treeViewer.setContentProvider( new ITreeContentProvider() {
      public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {}
      public void dispose() { }
      public boolean hasChildren( Object element ) {
        return element instanceof Category;
      }
      public Object getParent( Object element ) {
        return null;
      }
      public Object[] getElements( Object inputElement ) {
        return ( ( Category )inputElement ).getChildren();
      }
      public Object[] getChildren( Object parentElement ) {
        return ( ( Category )parentElement ).getChildren();
      }
    } );
    treeViewer.setLabelProvider( new LabelProviderImplementation() );
    treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        IStructuredSelection sel = ( IStructuredSelection )event.getSelection();
        if( featurePage != null ) {
          featurePage.dispose();
        }
        if( sel.getFirstElement() instanceof Feature ) {
          featurePage = new FeaturePage( parent, ( Feature )sel.getFirstElement() );
          parent.layout();
        }
      }
    } );
    treeViewer.setInput( features );
    treeViewer.expandAll();
  }

  public Tree getTree() {
    return tree;
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
      return element.toString();
    }

    public Image getImage( Object element ) {
      return null;
    }

    public Font getFont( Object element ) {
      boolean category = element instanceof Category;
      boolean exclusive = !category && ( ( Feature )element ).isExclusive();
      if( category || exclusive ) {
        Display display = tree.getDisplay();
        FontData fontData = tree.getFont().getFontData()[ 0 ];
        if( exclusive ) {
          fontData.setStyle( SWT.ITALIC );
        }
        if( category ) {
          fontData.setStyle( SWT.BOLD );
        }
        return new Font( display, fontData );
      }
      return null;
    }
  }

}
