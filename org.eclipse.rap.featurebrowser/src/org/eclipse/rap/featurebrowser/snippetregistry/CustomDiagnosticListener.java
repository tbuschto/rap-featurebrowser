package org.eclipse.rap.featurebrowser.snippetregistry;

import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

public class CustomDiagnosticListener implements DiagnosticListener<JavaFileObject> {

    private StringBuilder out = new StringBuilder();

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
      out.append( "Line: " + diagnostic.getLineNumber() + "\n" );
      out.append(  diagnostic.getCode() + "\n" );
      out.append( "Message: " + diagnostic.getMessage( Locale.ENGLISH )  + "\n" );
      out.append( "\n" );
      throw new IllegalArgumentException( out.toString() );
    }

}
