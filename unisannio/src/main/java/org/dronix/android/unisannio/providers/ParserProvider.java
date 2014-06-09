package org.dronix.android.unisannio.providers;

import org.dronix.android.unisannio.interfaces.IParser;
import org.dronix.android.unisannio.parsers.Parsers;
import org.dronix.android.unisannio.parsers.ScienzeDetailParser;
import org.dronix.android.unisannio.parsers.ScienzeParser;

public class ParserProvider {

    public static IParser get(Parsers parser) {

        switch (parser) {
            case SCIENZE:
                return new ScienzeParser();
            case SCIENZE_DETAILS:
                return new ScienzeDetailParser();
        }
        return null;
    }
}
