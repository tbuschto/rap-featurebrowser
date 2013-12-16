/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.demo.samples.ui;

import static org.eclipse.rap.demo.samples.util.GridDataUtil.*;
import static org.eclipse.rap.demo.samples.util.GridLayoutUtil.*;
import static org.eclipse.rap.demo.samples.util.StyleUtil.*;

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
import org.eclipse.rap.demo.samples.Feature;
import org.eclipse.rap.demo.samples.SamplesBrowser;
import org.eclipse.rap.demo.samples.Navigation;
import org.eclipse.rap.demo.samples.util.ResourceUtil;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.template.Template;
import org.eclipse.rap.rwt.template.TextCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class FeatureTree {

  // NOTE: Firefox doesn't seem to understand single quotes (') in font family names
  private static final String TREE_FONT = "Verdana,\"Lucida Sans\",Arial,Helvetica,sans-serif";
  private Tree tree;
  private TreeViewer treeViewer;
  private SamplesBrowser browser;


  public FeatureTree( final SamplesBrowser browser ) {
    this.browser = browser;
    final Composite parent = browser.getMainComposite();
    Feature category = browser.getFeatures();
    Composite treeArea = new Composite( parent, SWT.NONE );
    applyGridLayout( treeArea ).cols( 1 );
    style( treeArea ).as( "floatingBox" );
    Composite toolBar = new Composite( treeArea, SWT.NONE );
    toolBar.setBackgroundMode( SWT.INHERIT_FORCE );
    toolBar.setLayout( new RowLayout() );
    style( toolBar ).background( "transparent" );
    applyGridData( toolBar ).height( 29 ).hGrab();
    tree = new Tree( treeArea, SWT.SINGLE );
    applyGridData( tree ).fill();
    style( tree ).font( TREE_FONT, 14, SWT.NONE );
    treeViewer = new TreeViewer( tree );
    customizeTree( treeViewer );
    treeViewer.setContentProvider( new ITreeContentProvider() {
      public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {}
      public void dispose() { }
      public boolean hasChildren( Object element ) {
        return ( ( Feature )element ).getChildren() != null;
      }
      public Object getParent( Object element ) {
        return ( ( Feature )element ).getParent();
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
        Feature feature = ( Feature )sel.getFirstElement();
        if( browser.getCurrentFeature() == feature ) {
          return;
        }
        Feature oldFeature = browser.getCurrentFeature();
        Navigation.getInstance().push( feature );
        browser.setCurrentFeature( feature );
        if( oldFeature != null ) {
          treeViewer.update( oldFeature, null );
        }
        treeViewer.update( feature, null );
      }
    } );
    treeViewer.setInput( category );
    createToolItem( toolBar, "icons/collapseall.gif", new Listener() {
      public void handleEvent( Event event ) {
        treeViewer.collapseAll();
      }
    } );
    createToolItem( toolBar, "icons/expandall.gif", new Listener() {
      public void handleEvent( Event event ) {
        treeViewer.expandAll();
      }
    } );
    createToolItem( toolBar, "icons/select_prev.gif", new Listener() {
      public void handleEvent( Event event ) {
        select( browser.getCurrentFeature().getPrevious() );
      }
    } );
    createToolItem( toolBar, "icons/select_next.gif", new Listener() {
      public void handleEvent( Event event ) {
        select( browser.getCurrentFeature().getNext() );
      }
    } );
  }

  private static Button createToolItem( Composite toolBar, String imagePath, Listener listener ) {
    Button item = new Button( toolBar, SWT.PUSH );
    item.setImage( ResourceUtil.getImage( imagePath ) );
    style( item ).as( "mini" );
    item.addListener( SWT.Selection, listener );
    return item;
  }

  // modifies low-level look and behavior, could be platform dependent
  private void customizeTree( final TreeViewer viewer ) {
    final Tree tree = viewer.getTree();
    Template template = new Template();
    TextCell cell = new TextCell( template );
    cell.setBindingIndex( 0 );
    cell.setSelectable( true );
    cell.setLeft( 0 ).setTop( 2 ).setRight( 0 ).setBottom( 3 );
    tree.setData( RWT.ROW_TEMPLATE, template );
    tree.setData(  RWT.CUSTOM_ITEM_HEIGHT, new Integer( 24 ) );
    tree.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        TreeItem item = ( TreeItem )event.item;
        if( item.getItemCount() > 0 && !item.getExpanded() ) {
          item.setExpanded( true );
          viewer.refresh();
        }
        if( tree.getSelection().length != 1 || tree.getSelection()[ 0 ] != item ) {
          // this happens due to templates, re-fire with correct selection set
          tree.setSelection( item );
          tree.notifyListeners( SWT.Selection, event );
        }
      }
    } );
  }


  public void select( Feature feature ) {
    if( feature != null ) {
      TreePath path = new TreePath( feature.getPath() );
      if( path.getSegmentCount() > 2 ) {
        treeViewer.expandToLevel( feature, 2 );
      }
      treeViewer.setSelection( new TreeSelection( path ), true );
    }
  }

  public Control getControl() {
    return tree.getParent();
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
      if( browser.getCurrentFeature() == element ) {
        return new Font( Display.getCurrent(), TREE_FONT, 14, SWT.BOLD );
      }
      return null;
    }

  }

}
