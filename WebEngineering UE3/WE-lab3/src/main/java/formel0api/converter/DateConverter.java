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
package formel0api.converter;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Peter
 */
@FacesConverter(forClass=Date.class)
public class DateConverter implements Converter {

    private DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        try {
            return format.parse(value);
        } catch (ParseException ex) {
            throw new ConverterException(new FacesMessage("Fehler beim Konvertieren des Datums"), ex);
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        return format.format(value);
    }
}
