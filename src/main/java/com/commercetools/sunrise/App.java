package com.commercetools.sunrise;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.cms.contentful.ContentfulCmsService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static com.commercetools.sunrise.PropertiesUtils.loadProperties;

public class App {

    private static final List<Locale> LOCALES = Collections.singletonList(Locale.US);

    public static void main(String[] args ) throws ExecutionException, InterruptedException {
        final Properties properties = loadProperties();

        final String spaceId = properties.getProperty("spaceId");
        final String accessToken = properties.getProperty("accessToken");
        final String productContentTypeId = "2wKn6yEnZewu2SCCkus4as";
        final String pageSlugFieldId = "slug";

        final CmsService cmsService = ContentfulCmsService.of(spaceId, accessToken, productContentTypeId, pageSlugFieldId, ForkJoinPool.commonPool());

        printPost(cmsService, "seven-tips-from-ernest-hemingway-on-how-to-write-fiction");
        printPost(cmsService, "down-the-rabbit-hole");
    }

    private static void printPost(final CmsService cmsService, final String pageKey) throws ExecutionException, InterruptedException {
        final CmsPage cmsPage = fetchCmsPage(cmsService, pageKey);
        System.out.println("*** POST ***");
        System.out.println("Title: " + cmsPage.fieldOrEmpty("title"));
        System.out.println("Date: " + cmsPage.fieldOrEmpty("date"));
        System.out.println("Comments: " + cmsPage.fieldOrEmpty("comments"));
        System.out.println("Image: " + cmsPage.fieldOrEmpty("featuredImage"));
        System.out.println("Categories: " + cmsPage.fieldOrEmpty("category[0].title") + " " + cmsPage.fieldOrEmpty("category[1].title"));
        System.out.println("Author: " + cmsPage.fieldOrEmpty("author[0].name"));
        System.out.println("Author's entries: " + cmsPage.fieldOrEmpty("author[0].createdEntries[0].title"));
    }

    private static CmsPage fetchCmsPage(final CmsService cmsService, final String pageKey) throws InterruptedException, ExecutionException {
        return cmsService.page(pageKey, LOCALES).toCompletableFuture().get()
                    .orElseThrow(() -> new RuntimeException("Not found page " + pageKey));
    }
}
