package Base.Infrastructure;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.*;

/**
 * Created by Mateusz on 07/01/2016.
 * ColorAdapter class is used to serialize objects to XML for Color type properties.
 */
public class ColorAdapter extends XmlAdapter<String, Color> {
    /**
     * Converts Color type to String type in HTML format.
     *
     * @param v Color to convert
     * @return Returns string representation for specified Color
     * @throws Exception
     */
    @Override
    public String marshal(Color v) throws Exception {
        return "#" + String.format("%02X", v.getRed()) + String.format("%02X", v.getGreen()) + String.format("%02X", v.getBlue());

    }

    /**
     * Parse Color type from String type (in HTML format)
     *
     * @param v The string to parse
     * @return Returns Color representation for specified string
     * @throws Exception The function throws exception when the specified format is invalid
     */
    @Override
    public Color unmarshal(String v) throws Exception {
        return Color.decode(v);
    }
}