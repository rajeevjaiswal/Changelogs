package com.saladevs.changelogclone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PlayStoreParser {

    private static final String PUBLISH_DATE_FORMAT = "MMMM d, yyyy";

    private static final String DETAIL_SECTION_START = "details-section whatsnew";
    private static final String DETAIL_SECTION_END = "details-section recommendation";

    private static final String CHANGE_ELEMENT = "recent-change";
    public static final String CHANGE_SEPARATOR = "||";

    private static final String PROPERTY_ELEMENT = "itemprop";
    private static final String PUBLISH_DATE_PROPERTY = "datePublished";
    private static final String FILE_SIZE_PROPERTY = "fileSize";
    private static final String DOWNLOADS_PROPERTY = "numDownloads";
    private static final String VERSION_PROPERTY = "softwareVersion";
    private static final String DEVICE_OS_PROPERY = "operatingSystems";
    private static final String RATING_PROPERTY = "contentRating";

    private final SimpleDateFormat dateFormat;

    public PlayStoreParser() {
        this.dateFormat = new SimpleDateFormat(PUBLISH_DATE_FORMAT, Locale.ENGLISH);
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public String fetchPackageUpdate(String packageName) throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://play.google.com/store/apps/details?hl=en&id=" + packageName)
                .addHeader("Accept-Language", "en-US,en;q=0.8")
                .build();

        Response response = client.newCall(request).execute();
        return extractAppInfo(response.body().string());
    }

    public String extractAppInfo(String body) throws ParseException {
        // Find limits of the document
        int start = body.indexOf(DETAIL_SECTION_START);
        if (start < 0)
            throw new ParseException("unable to find start of detail section", start);
        int end = body.indexOf(DETAIL_SECTION_END, start);
        if (end < 0)
            throw new ParseException("unable to find end of detail section", end);

        // Extract document of interest
        String html = body.substring(start, end);
        Document doc = Jsoup.parseBodyFragment(html);

        // Parse update changes
        List<Element> changes = doc.getElementsByClass(CHANGE_ELEMENT);

        StringBuilder builder = new StringBuilder();
        String separator = "";
        for (Element c : changes) {
            builder.append(separator);
            builder.append(c.text());
            separator = CHANGE_SEPARATOR;
        }

        return builder.toString();

        // Parse update details
//        List<Element> details = doc.getElementsByAttribute(PROPERTY_ELEMENT);
//        for (Element e : details) {
//            String type = e.attr(PROPERTY_ELEMENT);
//            if (PUBLISH_DATE_PROPERTY.equals(type)) {
//                update.setDate(dateFormat.parse(e.text()));
//            } else if (FILE_SIZE_PROPERTY.equals(type)) {
//                update.setAppSize(e.text());
//            } else if (DOWNLOADS_PROPERTY.equals(type)) {
//                update.setDownloads(e.text());
//            } else if (VERSION_PROPERTY.equals(type)) {
//                update.setVersion(e.text());
//            } else if (DEVICE_OS_PROPERY.equals(type)) {
//                update.setAndroidVersion(e.text());
//            } else if (RATING_PROPERTY.equals(type)) {
//                // update.setContentRating(e.text());
//            }
//        }

    }
}
