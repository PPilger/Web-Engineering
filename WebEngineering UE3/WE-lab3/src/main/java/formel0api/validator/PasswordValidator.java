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
package formel0api.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Peter
 */
public class PasswordValidator implements Validator {

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{2,})";
	
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String str = (String) value;
		/*
		 * Hier MUSS die Pattern Klasse verwendet werden wegen ?=
		 */
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(str);
		
		if(!matcher.matches()){
			throw new ValidatorException(new FacesMessage("Die Eingabe muss aus mind. 1 Buchstaben und mind. 1 Zahl bestehen!"));
		}
    }
    
}
