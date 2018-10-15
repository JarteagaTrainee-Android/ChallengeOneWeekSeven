package com.applaudostudio.weeksevenchallengeone.util;

import com.applaudostudio.weeksevenchallengeone.apiclient.PhotosService;
import com.applaudostudio.weeksevenchallengeone.apiclient.ApiClient;

/***
 * single isntances for the retrofic client
 */
public class ApiUtils {
    public static final String API_KEY = "D4qaqQHftAk3mp7OkM7kbUArBzTiGaxEZymsHXHT";
    public static final String API_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/";


    public ApiUtils() {
    }
    public static PhotosService getPhotosService() {
        return ApiClient.getClient(API_URL).create(PhotosService.class);
    }

}
