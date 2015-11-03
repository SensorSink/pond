package ac.bali.sensorsink.hauler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils
{
    static void writeText( File componentFile, String entityAsText )
    {
        try (FileWriter writer = new FileWriter( componentFile ))
        {
            writer.write( entityAsText );
            writer.write( "\n" );
            writer.flush();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
