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

import com.google.gdata.client.Query;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.TagEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author juliadaurer
 */
public class IRaceDriverImpl implements IRaceDriverService {

    @Override
    public List<RaceDriver> getRaceDrivers() throws IOException, ServiceException {
        System.out.println("Get race drivers in IRaceDriverImpl");

        PicasawebService myService = new PicasawebService("example");

        URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/107302466601293793664");

        List<RaceDriver> list = new ArrayList<RaceDriver>();

        Query myQuery = new Query(feedUrl);
        myQuery.setStringCustomParameter("kind", "photo");
        myQuery.setStringCustomParameter("tag", "driver");

        AlbumFeed searchResultsFeed = myService.query(myQuery, AlbumFeed.class);

        for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
            RaceDriver temp = new RaceDriver();

            for (TagEntry tag : photo.getFeed("tag").getTagEntries()) {
                String title = tag.getTitle().getPlainText();
                if (title.startsWith("wiki:")) {
                    temp.setWikiUrl("http://"+title.substring(5, title.length()));
                }
            }

            temp.setName(photo.getDescription().getPlainText());
            if (photo.getContent() instanceof MediaContent) {
                temp.setUrl(((MediaContent) photo.getContent()).getUri());
            }

            System.out.println("Wiki:" + temp.getWikiUrl());
            System.out.println("Rennfahrer:" + temp.getName());
            System.out.println("Link:" + temp.getUrl());

            list.add(temp);

        }

        System.out.println("return list of race drivers");

        return list;
    }
}
