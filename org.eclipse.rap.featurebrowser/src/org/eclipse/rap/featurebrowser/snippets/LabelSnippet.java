package org.eclipse.rap.featurebrowser.snippets;

import org.eclipse.rap.featurebrowser.snippetregistry.SnippetBuilder;


public class LabelSnippet extends SnippetBuilder {

  public LabelSnippet( String name ) {
    super( name );
    writeln( "@Override" );
    writeln( "protected void createContents( Composite parent ) {" );
    writeln( "  Label label = new Label( parent, SWT.NONE );" );
    writeln( "  label.setText( \"foo\" );" );
    writeln( "}" );
    newline();
  }

}
