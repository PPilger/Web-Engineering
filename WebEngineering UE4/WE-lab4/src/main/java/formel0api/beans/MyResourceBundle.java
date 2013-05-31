/*
 * Copyright 2013 Peter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package formel0api.beans;

import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author Peter
 */
public class MyResourceBundle {
    public static String getString(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle rb = ResourceBundle.getBundle("i18n", context.getExternalContext().getRequestLocale());
        return rb.getString(key);
    }
}
