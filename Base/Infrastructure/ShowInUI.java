package Base.Infrastructure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Mateusz on 10/01/2016.
 * Attribute used to determination of display name. The model which are marked this attribute will be display in UI.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowInUI {
    String displayName();
}
