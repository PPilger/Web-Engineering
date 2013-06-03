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

    private EntityManager man;

    public IRaceDriverImpl() {
        EntityManagerFactory fac = Persistence.createEntityManagerFactory("lab4");
        man = fac.createEntityManager();
    }

    @Override
    public List<RaceDriver> getRaceDrivers() throws IOException, ServiceException {
        System.out.println("Load race drivers");

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

                if (tag.getTitle().getPlainText().startsWith("wiki:")) {
                    System.out.println("Wiki:" + tag.getTitle().getPlainText().substring(4, tag.getTitle().getPlainText().length() - 1));
                    temp.setWikiUrl(tag.getTitle().getPlainText().substring(4, tag.getTitle().getPlainText().length() - 1));
                }
            }

            temp.setName(photo.getDescription().getPlainText());
            if (photo.getContent() instanceof MediaContent) {
                temp.setUrl(((MediaContent) photo.getContent()).getUri());
            }

            System.out.println("Rennfahrer:" + photo.getDescription().getPlainText());
            System.out.println("Link:" + photo.getMediaThumbnails().get(photo.getMediaThumbnails().size() - 1).getUrl());

            list.add(temp);

        }

        System.out.println("return list of race drivers");

        return list;
    }
}
