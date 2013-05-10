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

import formel0api.models.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Peter
 */
@ManagedBean(name="bean")
@ApplicationScoped
public class Application {
    private Map<String, User> users = new HashMap<String, User>();
    
    public String save(User user) {
        System.out.println(user.getFirstname()+" "+user.getLastname()+" "+user.getBirthdate());
        return "#";
    }
}
