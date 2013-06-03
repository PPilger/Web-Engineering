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

import formel0api.beans.ApplicationBean;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import tuwien.big.formel0.picasa.RaceDriver;

/**
 *
 * @author Peter
 */
@FacesConverter(value="avatarConverter")
public class AvatarConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if(value == null) {
            return null;
        }
        ApplicationBean bean = (ApplicationBean) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("applicationBean");
        return bean.getRaceDriver(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if(value == null) {
            return null;
        }
        return ((RaceDriver)value).getName();
    }
}
