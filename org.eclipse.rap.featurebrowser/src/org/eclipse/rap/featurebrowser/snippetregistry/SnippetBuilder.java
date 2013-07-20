package org.eclipse.rap.featurebrowser.snippetregistry;


public class SnippetBuilder {

  private StringBuilder text = new StringBuilder();
  private int indent = 0;
  private String name;

  public SnippetBuilder( String name ) {
    this.name = name;
    writeln( "package snippets;" );
    newline();
    appendImport( org.eclipse.swt.SWT.class );
    appendImport( "org.eclipse.swt.widgets.*" );
    appendImport( org.eclipse.rap.rwt.application.AbstractEntryPoint.class );
    newline();
    writeln( "public class " + name + " extends AbstractEntryPoint { " );
    indent ++;
    newline();
  }

  public String getName() {
    return name;
  }

  public String generate() {
    if( indent != 1 ) {
      throw new IllegalStateException();
    }
    indent--;
    writeln( "}" );
    String result = text.toString();
    text = null;
    return result;
  }

  public void appendImport( Class< ? > clazz ) {
    text.append( "import " + clazz.getCanonicalName() + ";\n" );
  }

  private void appendImport( String string ) {
    text.append( "import " + string + ";\n" );
  }

  public void blockStart( String str ) {
    writeln( str );
    indent++;
  }

  public void blockEnd( String str ) {
    indent--;
    writeln( str );
  }

  public void writeln( String str ) {
    for( int i = 0; i < indent; i++ ) {
      text.append( "  " );
    }
    text.append( str );
    newline();
  }

  public void newline() {
    text.append( "\n" );
  }

}
