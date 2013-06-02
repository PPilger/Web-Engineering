    /*
 * Copyright 2013 juliadaurer.
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
package tuwien.big.formel0.picasa;

import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juliadaurer
 */
public class IRaceDriverImpl implements IRaceDriverService {

    @Override
    public List<RaceDriver> getRaceDrivers() throws IOException, ServiceException {
        System.out.println("Load race drivers");

        PicasawebService myService = new PicasawebService("example");
        
        URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/107302466601293793664?kind=album");
        AlbumFeed feed = myService.getFeed(feedUrl, AlbumFeed.class);

        
        for (PhotoEntry photo : feed.getPhotoEntries()) {
            RaceDriver temp = new RaceDriver();
            temp.setName(photo.getTitle().getPlainText());
            System.out.println(photo.getTitle().getPlainText());
        }
        List<RaceDriver> liste = new ArrayList<RaceDriver>();
        
        System.out.println("return list of race drivers");
        return liste;
    }
}
