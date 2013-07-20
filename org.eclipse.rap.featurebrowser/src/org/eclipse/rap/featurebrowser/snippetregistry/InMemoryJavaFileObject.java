package org.eclipse.rap.featurebrowser.snippetregistry;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {
    private String text = null;

    public InMemoryJavaFileObject( String name, String text ) throws Exception {
        super( URI.create( "string:///" + name.replace('.', '/' )
                         + Kind.SOURCE.extension ), Kind.SOURCE );
        this.text = text;
    }

    @Override
    public CharSequence getCharContent( boolean ignoreEncodingErrors ) throws IOException {
        return text;
    }

}
